package com.ptv.nascleaner;

import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileScannerService {

    public List<File> findOldFiles(String path, LocalDate beforeDate) {
        List<File> result = new ArrayList<>();
        File folder = new File(path);
        if (!folder.exists() || !folder.isDirectory()) return result;

        long cutoff = beforeDate.atStartOfDay()
                .toInstant(java.time.ZoneId.systemDefault().getRules().getOffset(beforeDate.atStartOfDay()))
                .toEpochMilli();

        scanRecursively(folder, cutoff, result);
        return result;
    }

    private void scanRecursively(File dir, long cutoff, List<File> result) {
        File[] files = dir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                scanRecursively(file, cutoff, result);
            } else if (file.lastModified() < cutoff) {
                result.add(file);
            }
        }
    }

    public void deleteFiles(List<File> files) {
        for (File file : files) {
            file.delete();
        }
    }
}
