package com.yaphet.hdfsfileviewer.controllers;

import com.northconcepts.datapipeline.core.Record;
import com.northconcepts.datapipeline.core.RecordList;
import com.yaphet.hdfsfileviewer.abstractions.FileReader;
import com.yaphet.hdfsfileviewer.filereaders.ParquetReader;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class HdfsFileViewerController {

    @FXML
    public TableView fileViewer;

    @FXML
    public void initialize(){
        File file=new File("input.parquet");
        FileReader fileReader=new ParquetReader(file);
        RecordList recordList=fileReader.read();
        List<String> columnList=fileReader.getColumns();
        for(int i=0;i<columnList.size();i++){
            final int finalIdx = i;
            TableColumn<ObservableList<String>,String> column=new TableColumn<>(columnList.get(i));
            column.setCellValueFactory(param ->
                    new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))
            );
            fileViewer.getColumns().add(column);

        }

       fileViewer.getItems().add(getRow(recordList.get(0)));

    }
    public ObservableList getRow(Record record){
        List<String> row=new ArrayList<>();
        for(int i=0;i<record.getFieldCount();i++){
            row.add(String.valueOf(record.getField(i).getValue()));

        }
        return FXCollections.observableList(row);
    }

}
