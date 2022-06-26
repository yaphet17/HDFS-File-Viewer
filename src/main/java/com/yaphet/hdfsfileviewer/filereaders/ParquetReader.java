package com.yaphet.hdfsfileviewer.filereaders;

import com.northconcepts.datapipeline.core.RecordList;
import com.northconcepts.datapipeline.job.Job;
import com.northconcepts.datapipeline.memory.MemoryWriter;
import com.northconcepts.datapipeline.parquet.ParquetDataReader;
import com.yaphet.hdfsfileviewer.abstractions.IFileReader;

import java.io.File;

public class ParquetReader implements IFileReader {

    @Override
    public RecordList readFile(File file) {
        RecordList recordList = new RecordList();
        ParquetDataReader reader = new ParquetDataReader(file);
        MemoryWriter writer = new MemoryWriter(recordList);

        Job.run(reader, writer);
        return recordList;
    }
}
