package com.yaphet.hdfsfileviewer.filereaders;

import com.northconcepts.datapipeline.core.RecordList;
import com.northconcepts.datapipeline.job.Job;
import com.northconcepts.datapipeline.orc.OrcDataReader;
import com.yaphet.hdfsfileviewer.abstractions.IFileReader;

import java.io.File;

public class OrcFileReader implements IFileReader {
    @Override
    public RecordList readFile(File file) {
        OrcDataReader reader = new OrcDataReader(file);
        Job.run(reader, writer);
        return recordList;
    }
}
