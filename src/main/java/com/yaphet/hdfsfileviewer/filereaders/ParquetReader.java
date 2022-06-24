package com.yaphet.hdfsfileviewer.filereaders;

import com.northconcepts.datapipeline.core.RecordList;
import com.northconcepts.datapipeline.job.Job;
import com.northconcepts.datapipeline.memory.MemoryWriter;
import com.northconcepts.datapipeline.parquet.ParquetDataReader;
import com.yaphet.hdfsfileviewer.abstractions.FileReader;
import javafx.concurrent.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class ParquetReader extends Task<RecordList> implements FileReader {

    private static final Logger logger = LogManager.getLogger(ParquetReader.class);
    private final File file;

    private static final String successMsg="Reading file completed";
    private static final String startingMsg="Reading file...";


    public ParquetReader(File file){
        this.file = file;
    }



    @Override
    protected RecordList call() {
        logger.debug(startingMsg);
        updateMessage(startingMsg);
        RecordList recordList = new RecordList();
        ParquetDataReader reader = new ParquetDataReader(file);
        MemoryWriter writer = new MemoryWriter(recordList);
        Job.run(reader,writer);
        updateMessage(successMsg);
        logger.debug(successMsg);
        return recordList;
    }
}
