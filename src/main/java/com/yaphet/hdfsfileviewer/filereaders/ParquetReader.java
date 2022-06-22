package com.yaphet.hdfsfileviewer.filereaders;

import com.northconcepts.datapipeline.core.*;
import com.northconcepts.datapipeline.core.Record;
import com.northconcepts.datapipeline.job.Job;
import com.northconcepts.datapipeline.memory.MemoryWriter;
import com.northconcepts.datapipeline.parquet.ParquetDataReader;
import com.yaphet.hdfsfileviewer.abstractions.FileReader;
import javafx.concurrent.Task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ParquetReader implements FileReader {
    private RecordList recordList;
    private final File file;

    public ParquetReader(File file){
        this.file=file;
    }
    @Override
    public RecordList read() {
        recordList=new RecordList();
        ParquetDataReader reader=new ParquetDataReader(file);
        MemoryWriter writer=new MemoryWriter(recordList);
        Job.run(reader,writer);
        return recordList;
    }

    public List<String> getColumns(){
        List<String> columnList=new ArrayList<>();
        if(recordList==null){
            //TODO: throw exception
        }
        FieldList fieldList=recordList.get(1).getFieldNameList();
        for(String fieldName:fieldList){
            columnList.add(fieldName);

        }
        return columnList;
    }
}
