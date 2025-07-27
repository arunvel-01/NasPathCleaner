package com.ptv.nascleaner;

import javafx.beans.property.*;
import java.io.File;
import java.text.SimpleDateFormat;

/**
 * Represents a file row in the TableView.
 */
public class FileTableItem {

    private final File file;                        // Actual file
    private final BooleanProperty selected;         // Checkbox state
    private final StringProperty fileName;          // File name
    private final StringProperty filePath;          // File path
    private final StringProperty modifiedDate;      // Last modified timestamp

    public FileTableItem(File file) {
        this.file = file;
        this.selected = new SimpleBooleanProperty(false); // Default unselected
        this.fileName = new SimpleStringProperty(file.getName());
        this.filePath = new SimpleStringProperty(file.getAbsolutePath());
        this.modifiedDate = new SimpleStringProperty(
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
    }

    public File getFile() {
        return file;
    }

    public boolean isSelected() {
        return selected.get();
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public StringProperty fileNameProperty() {
        return fileName;
    }

    public StringProperty filePathProperty() {
        return filePath;
    }

    public StringProperty modifiedDateProperty() {
        return modifiedDate;
    }
}
