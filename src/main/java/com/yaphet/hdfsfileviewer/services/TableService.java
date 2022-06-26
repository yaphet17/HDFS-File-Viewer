package com.yaphet.hdfsfileviewer.services;

import com.northconcepts.datapipeline.core.FieldList;
import com.northconcepts.datapipeline.core.Record;
import com.northconcepts.datapipeline.core.RecordList;
import com.yaphet.hdfsfileviewer.exceptions.ColumnNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class TableService {

    public List<String> getColumns(RecordList recordList){
        if(recordList == null){
           throw new ColumnNotFoundException();
        }
        List<String> columnList = new ArrayList<>();
        FieldList fieldList = recordList.get(1).getFieldNameList();
        for(String fieldName : fieldList){
            columnList.add(fieldName);
        }
        return columnList;
    }
    public ObservableList<String> getRow(Record record){
        List<String> row = new ArrayList<>();

        for(int i=0;i<record.getFieldCount();i++){
            row.add(String.valueOf(record.getField(i).getValue()));

        }
        return FXCollections.observableList(row);
    }
}
