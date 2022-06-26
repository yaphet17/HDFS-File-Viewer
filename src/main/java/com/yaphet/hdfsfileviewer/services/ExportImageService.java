package com.yaphet.hdfsfileviewer.services;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.TableView;
import javafx.scene.image.WritableImage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ExportImageService {

    private static final Logger logger = LogManager.getLogger(ExportImageService.class);
    private final TableView fileViewer;
    private final File file;

    private final String EXPORT_IMAGE_FORMATS = "png";

    public ExportImageService(File file, TableView fileViewer){
        this.file = file;
        this.fileViewer = fileViewer;
    }


    public void export(){
        SnapshotParameters param = new SnapshotParameters();

        try {
            logger.debug("Exporting image started...");
            param.setDepthBuffer(true);
            WritableImage snapshot = fileViewer.snapshot(param, null);
            BufferedImage img = SwingFXUtils.fromFXImage(snapshot, null);
            ImageIO.write(img, EXPORT_IMAGE_FORMATS, file);
            logger.debug("Image successfully exported...");
        } catch (IOException e) {
            logger.debug(e);
        }

    }
}
