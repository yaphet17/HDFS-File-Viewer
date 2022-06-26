package com.yaphet.hdfsfileviewer.abstractions;

import com.northconcepts.datapipeline.core.RecordList;
import com.northconcepts.datapipeline.memory.MemoryWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public interface IFileReader {
    
    static Logger logger = LogManager.getLogger(IFileReader.class);

    RecordList recordList = new RecordList();
    MemoryWriter writer = new MemoryWriter(recordList);

    RecordList readFile(File file);
}
