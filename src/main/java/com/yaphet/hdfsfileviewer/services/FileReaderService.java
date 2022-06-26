package com.yaphet.hdfsfileviewer.services;

import com.northconcepts.datapipeline.core.RecordList;
import com.yaphet.hdfsfileviewer.abstractions.IFileReader;
import com.yaphet.hdfsfileviewer.filereaders.AvroFileReader;
import com.yaphet.hdfsfileviewer.filereaders.OrcFileReader;
import com.yaphet.hdfsfileviewer.filereaders.ParquetFileReader;
import javafx.concurrent.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileReaderService extends Task<RecordList> {

    private static final Logger logger = LogManager.getLogger(FileReaderService.class);
    private static final Map<String,IFileReader> FILE_READERS = new HashMap<>(){
        {
            put(".parquet", new ParquetFileReader());
            put(".avro", new AvroFileReader());
            put(".orc", new OrcFileReader());
        }
    };

    private final File file;

    public FileReaderService(File file){
        this.file = file;
    }


    @Override
    protected RecordList call() {
        String STARTING_MSG = "Reading file...";
        String SUCCESS_MSG = "Reading file completed";

        logger.debug(STARTING_MSG);
        updateMessage(STARTING_MSG);
        RecordList recordList = FILE_READERS.
                get(getFileExtension(file.getName())).
                readFile(file);
        updateMessage(SUCCESS_MSG);
        logger.debug(SUCCESS_MSG);
        return recordList;
    }


    private String getFileExtension(String fileName){
        return fileName.substring( fileName.lastIndexOf("."));
    }

    public static List<String> getAcceptedFormats(){
        List<String> acceptedFormatList = new ArrayList<>(){
            {
                for(String format : FILE_READERS.keySet()){
                    add("*" + format);
                }
            }
        };
        return acceptedFormatList;
    }
}
