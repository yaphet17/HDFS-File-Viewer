package com.yaphet.hdfsfileviewer;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HdfsFileViewerApplication {

    public static void main(String[] args) {
        Application.launch(HdfsFileViewerApplicationUI.class, args);
    }

}
