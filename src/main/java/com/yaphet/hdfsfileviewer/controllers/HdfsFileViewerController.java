package com.yaphet.hdfsfileviewer.controllers;

import com.northconcepts.datapipeline.core.FieldList;
import com.northconcepts.datapipeline.core.Record;
import com.northconcepts.datapipeline.core.RecordList;
import com.yaphet.hdfsfileviewer.services.ExportImageService;
import com.yaphet.hdfsfileviewer.services.FileReaderService;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class HdfsFileViewerController {

    private static final Logger logger = LogManager.getLogger(HdfsFileViewerController.class);
    private Stage chooseFile;
    private final List<String> ACCEPTED_FORMATS = List.of("*.parquet");
    private final List<String> EXPORT_FORMATS = List.of("*.png");
    private Service<RecordList> service;

    @FXML
    public AnchorPane container;
    @FXML
    public VBox subContainer;
    @FXML
    public HBox searchBox;
    @FXML
    public HBox statusBox;
    @FXML
    public TableView fileViewer;
    @FXML
    public HBox counterBox;
    @FXML
    public HBox btnBox;
    @FXML
    public Button browseBtn;
    @FXML
    public Label statusLabel;
    @FXML
    public ProgressIndicator indicator;
    @FXML
    public Label rowCountLabel;

    @FXML
    public void initialize(){
    }

    @FXML
    public void browse(){
        File file = getSelectedFile();

        if(file == null){
            return;
        }
        readFile(file);
    }

    @FXML
    public void cancelProcess(){
        terminatedProcess("Process terminated");
        service.cancel();
    }
    @FXML
    public void exportImage(){
        File file = chooseSaveFile();

        if(file == null){
            showErrorMsg("Folder not selected");
            return;
        }
        new ExportImageService(file, fileViewer);
        showSuccessMsg("Image successfully exported");
    }
    private File getSelectedFile(){
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("HDFS files", ACCEPTED_FORMATS));
        fileChooser.setTitle("Choose file");
        return fileChooser.showOpenDialog(chooseFile);
    }

    public void readFile(File file){
        service = new Service<>() {
            @Override
            protected Task<RecordList> createTask() {
                return new FileReaderService(file);
            }
        };
        service.start();
        bindServiceToStatus();
        if(service.isRunning()){
            serviceRunning();
        }

        service.setOnFailed(e -> serviceFailed(service));
        service.setOnSucceeded(e -> Platform.runLater(this::serviceSucceeded));
        service.setOnCancelled(e -> terminatedProcess("Process terminated"));
    }
    private File chooseSaveFile(){
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image file", EXPORT_FORMATS));
        fileChooser.setTitle("Save");
        return fileChooser.showSaveDialog(chooseFile);
    }
    private void bindServiceToStatus(){
        //show service status value in status label
        statusLabel.setStyle("-fx-text-fill: #0BC902");
        statusLabel.textProperty().bind(service.messageProperty());
    }
    private void serviceRunning(){
        indicator.setVisible(true);
        //prevent double-clicking
        browseBtn.setDisable(true);
    }
    private void serviceFailed(Service<RecordList> service){
        //automatic retry for 3 times
        AtomicInteger fails = new AtomicInteger();

        if (fails.get() <= 3) {
            fails.getAndIncrement();
            service.reset();
            service.start();
        } else {
            terminatedProcess("Couldn't read file.");
        }
    }
    private void serviceSucceeded() {
        RecordList recordList = service.getValue();

        if(!recordList.isEmpty()){
            prepareTable(getColumns(recordList));
            populateTable(recordList);
            updateRowCounter(recordList.size());
            showSuccessMsg("");
        }else{
            showErrorMsg("Couldn't translate source text");
        }
        indicator.setVisible(false);
        browseBtn.setDisable(false);

    }
    private void terminatedProcess(String msg){
        showErrorMsg(msg);
        indicator.setVisible(false);
        browseBtn.setDisable(false);
        logger.debug("Process terminated");
    }
    private void prepareTable(List<String> columnList){
        logger.debug("Preparing table...");
        for(int i=0;i<columnList.size();i++){
            final int finalIdx = i;
            TableColumn<ObservableList<String>,String> column = new TableColumn<>(columnList.get(i));
            column.setCellValueFactory(param ->
                    new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))
            );
            fileViewer.getColumns().add(column);
        }
        logger.debug("Table prepared");
    }
    private void populateTable(RecordList recordList){
        logger.debug("Populating table...");
        for(int i=0;i<recordList.size();i++){
            fileViewer.getItems().add(getRow(recordList.get(i)));
        }
        logger.debug("Table populated");
    }
    private List<String> getColumns(RecordList recordList){
        if(recordList == null){
            //TODO: throw exception
        }
        List<String> columnList = new ArrayList<>();
        FieldList fieldList = recordList.get(1).getFieldNameList();
        for(String fieldName : fieldList){
            columnList.add(fieldName);
        }
        return columnList;
    }
    private ObservableList<String> getRow(Record record){
        List<String> row = new ArrayList<>();

        for(int i=0;i<record.getFieldCount();i++){
            row.add(String.valueOf(record.getField(i).getValue()));

        }
        return FXCollections.observableList(row);
    }
    private void updateRowCounter(int rows){
        rowCountLabel.setText(rows+" records");
    }
    private void showErrorMsg(String msg){
        statusLabel.setStyle("-fx-text-fill: #FB1705");
        unbindAndSetText(msg);
    }
    private void showSuccessMsg(String msg){
        statusLabel.setStyle("-fx-text-fill: #0BC902");
        unbindAndSetText(msg);
    }
    private void unbindAndSetText(String msg){
        if(statusLabel.textProperty().isBound()){
            statusLabel.textProperty().unbind();
        }
        statusLabel.setText(msg);

    }

}
