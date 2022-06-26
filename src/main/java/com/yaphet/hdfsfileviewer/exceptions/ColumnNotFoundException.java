package com.yaphet.hdfsfileviewer.exceptions;

public class ColumnNotFoundException extends RuntimeException{

    public ColumnNotFoundException(){
        super("Columns not found in record list");
    }
}
