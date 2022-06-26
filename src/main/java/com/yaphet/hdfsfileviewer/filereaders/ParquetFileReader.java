package com.yaphet.hdfsfileviewer.filereaders;

import com.northconcepts.datapipeline.core.RecordList;
import com.northconcepts.datapipeline.job.Job;
import com.northconcepts.datapipeline.parquet.ParquetDataReader;
import com.yaphet.hdfsfileviewer.abstractions.IFileReader;

import java.io.File;

public class ParquetFileReader implements IFileReader {

    @Override
    public RecordList readFile(File file) {
        ParquetDataReader reader = new ParquetDataReader(file);
        Job.run(reader, writer);
        return recordList;
    }
}
