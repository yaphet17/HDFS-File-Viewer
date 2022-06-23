package com.yaphet.hdfsfileviewer.filereaders;

import com.northconcepts.datapipeline.core.FieldList;
import com.northconcepts.datapipeline.core.RecordList;
import com.northconcepts.datapipeline.job.Job;
import com.northconcepts.datapipeline.memory.MemoryWriter;
import com.northconcepts.datapipeline.parquet.ParquetDataReader;
import com.yaphet.hdfsfileviewer.abstractions.FileReader;
import javafx.concurrent.Task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ParquetReader extends Task<RecordList> implements FileReader {
    private final File file;

    public ParquetReader(File file){
        this.file=file;
    }



    @Override
    protected RecordList call() throws Exception {
        updateMessage("Reading file...");
        RecordList recordList=new RecordList();
        ParquetDataReader reader=new ParquetDataReader(file);
        MemoryWriter writer=new MemoryWriter(recordList);
        Job.run(reader,writer);
        updateMessage("Reading file completed");
        return recordList;
    }
}
