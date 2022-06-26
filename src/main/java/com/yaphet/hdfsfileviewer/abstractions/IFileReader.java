package com.yaphet.hdfsfileviewer.abstractions;

import com.northconcepts.datapipeline.core.RecordList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public interface IFileReader {

    static Logger logger = LogManager.getLogger(IFileReader.class);

    RecordList readFile(File file);
}
