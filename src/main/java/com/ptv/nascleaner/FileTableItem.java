package com.ptv.nascleaner;

import javafx.beans.property.*;
import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class FileTableItem {

    private final File file;
    private final BooleanProperty selected;
    private final StringProperty fileName;
    private final StringProperty filePath;
    private final StringProperty modifiedDate;

    public FileTableItem(File file) {
        this.file = file;
        this.selected = new SimpleBooleanProperty(false);
        this.fileName = new SimpleStringProperty(file.getName());
        this.filePath = new SimpleStringProperty(file.getAbsolutePath());
        this.modifiedDate = new SimpleStringProperty(formatModifiedDate(file));
    }

    private String formatModifiedDate(File file) {
        long lastModified = file.lastModified();
        LocalDateTime dateTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(lastModified), ZoneId.systemDefault());
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    // Getter for file
    public File getFile() {
        return file;
    }

    // Getter/Setter for selected
    public boolean isSelected() {
        return selected.get();
    }

    public void setSelected(boolean value) {
        selected.set(value);
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    // Getter for fileName
    public String getFileName() {
        return fileName.get();
    }

    public StringProperty fileNameProperty() {
        return fileName;
    }

    // Getter for filePath
    public String getFilePath() {
        return filePath.get();
    }

    public StringProperty filePathProperty() {
        return filePath;
    }

    // Getter for modifiedDate
    public String getModifiedDate() {
        return modifiedDate.get();
    }

    public StringProperty modifiedDateProperty() {
        return modifiedDate;
    }
}
