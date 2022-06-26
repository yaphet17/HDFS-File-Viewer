package com.yaphet.hdfsfileviewer.filereaders;

import com.northconcepts.datapipeline.avro.AvroReader;
import com.northconcepts.datapipeline.core.RecordList;
import com.northconcepts.datapipeline.job.Job;
import com.yaphet.hdfsfileviewer.abstractions.IFileReader;

import java.io.File;

public class AvroFileReader implements IFileReader {
    @Override
    public RecordList readFile(File file) {
        AvroReader reader = new AvroReader(file);
        Job.run(reader, writer);
        return recordList;
    }
}
