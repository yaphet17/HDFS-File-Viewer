package com.yaphet.hdfsfileviewer.abstractions;

import com.northconcepts.datapipeline.core.RecordList;

import java.io.File;
import java.util.List;

public interface FileReader {
    public RecordList read();

    public List<String> getColumns();
}
