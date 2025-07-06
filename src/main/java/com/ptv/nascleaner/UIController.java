package com.ptv.nascleaner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.DirectoryChooser;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UIController {

    @FXML private TextField pathField;
    @FXML private DatePicker datePicker;
    @FXML private TableView<FileTableItem> fileTable;
    @FXML private TableColumn<FileTableItem, Boolean> selectColumn;
    @FXML private TableColumn<FileTableItem, String> fileNameColumn;
    @FXML private TableColumn<FileTableItem, String> filePathColumn;
    @FXML private TableColumn<FileTableItem, String> modifiedDateColumn;

    private final FileScannerService scannerService;
    private final ObservableList<FileTableItem> fileList = FXCollections.observableArrayList();

    public UIController(FileScannerService scannerService) {
        this.scannerService = scannerService;
    }

    @FXML
    public void initialize() {
        selectColumn.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
        selectColumn.setCellFactory(CheckBoxTableCell.forTableColumn(selectColumn));

        fileNameColumn.setCellValueFactory(cellData -> cellData.getValue().fileNameProperty());
        filePathColumn.setCellValueFactory(cellData -> cellData.getValue().filePathProperty());
        modifiedDateColumn.setCellValueFactory(cellData -> cellData.getValue().modifiedDateProperty());

        fileTable.setItems(fileList);
    }

    @FXML
    public void chooseDirectory() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select NAS Folder");
        File folder = chooser.showDialog(null);
        if (folder != null) {
            pathField.setText(folder.getAbsolutePath());
        }
    }

    @FXML
    public void scanFiles() {
        String path = pathField.getText();
        LocalDate date = datePicker.getValue();

        if (path.isEmpty() || date == null) {
            showAlert("Please select both folder and date.");
            return;
        }

        fileList.clear();
        List<File> files = scannerService.findOldFiles(path, date);
        files.forEach(f -> fileList.add(new FileTableItem(f)));
    }

    @FXML
    public void deleteFiles() {
        List<File> selectedFiles = fileList.stream()
                .filter(FileTableItem::isSelected)
                .map(FileTableItem::getFile)
                .collect(Collectors.toList());

        if (selectedFiles.isEmpty()) {
            showAlert("No files selected for deletion.");
            return;
        }

        if (confirm("Are you sure you want to delete the selected files?")) {
            scannerService.deleteFiles(selectedFiles);
            fileList.removeIf(FileTableItem::isSelected);
            showAlert("Selected files deleted.");
        }
    }

    @FXML
    public void selectAll() {
        fileList.forEach(item -> item.setSelected(true));
        fileTable.refresh();
    }

    @FXML
    public void deselectAll() {
        fileList.forEach(item -> item.setSelected(false));
        fileTable.refresh();
    }

    private boolean confirm(String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, msg, ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        return alert.getResult() == ButtonType.YES;
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.showAndWait();
    }
}
