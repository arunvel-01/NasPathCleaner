package com.ptv.nascleaner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileScannerService {

    // Logger for logging messages instead of using System.out
    private static final Logger logger = LoggerFactory.getLogger(FileScannerService.class);

    // Finds files older than the given date and matching the mask
    public List<File> findOldFiles(String path, LocalDate beforeDate, String mask) {
        List<File> result = new ArrayList<>();
        File folder = new File(path);

        if (!folder.exists() || !folder.isDirectory()) return result;

        // Convert selected date to milliseconds
        long cutoff = beforeDate.atStartOfDay()
                .atZone(java.time.ZoneId.systemDefault())
                .toInstant().toEpochMilli();

        // Recursively scan and collect matching files
        scanRecursively(folder, cutoff, result, mask);

        logger.info("Scanned folder: {} with mask: '{}', found: {} file(s)", path, mask, result.size());

        return result;
    }

    // Recursively scans the directory and filters files
    private void scanRecursively(File dir, long cutoff, List<File> result, String mask) {
        File[] files = dir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                scanRecursively(file, cutoff, result, mask); // Dive into subfolders
            } else if (file.lastModified() < cutoff && matchesMask(file, mask)) {
                result.add(file); // Add file if it matches the condition
            }
        }
    }

    // Checks if file name matches given mask (* and ? supported)
    private boolean matchesMask(File file, String mask) {
        if (mask == null || mask.trim().isEmpty()) return true;

        String fileName = file.getName().toLowerCase();
        String fixedMask = mask.toLowerCase().trim();

        if (!fixedMask.contains("*") && !fixedMask.contains("?")) {
            fixedMask = "*" + fixedMask;
        }

        // Convert mask to regex
        String pattern = fixedMask
                .replace(".", "\\.")
                .replace("*", ".*")
                .replace("?", ".");

        return fileName.matches(pattern);
    }

    // Deletes all selected files and logs the result
    public void deleteFiles(List<File> files) {
        for (File file : files) {
            if (file.delete()) {
                logger.info("Deleted: {}", file.getAbsolutePath());
            } else {
                logger.warn("Failed to delete: {}", file.getAbsolutePath());
            }
        }
    }
}
