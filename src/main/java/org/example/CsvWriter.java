package org.example;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class CsvWriter {
    private final Path path;
    private boolean headerWritten = false;

    public CsvWriter(String file) {
        this.path = Paths.get(file);
    }

    public void appendRow(LinkedHashMap<String, String> cols) {
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent() == null ? Paths.get(".") : path.getParent());
                headerWritten = false;
            }
            var sb = new StringBuilder();
            if (!headerWritten || isEmptyFile()) {
                headerWritten = true;
                boolean first = true;
                for (String k : cols.keySet()) {
                    if (!first) sb.append(",");
                    sb.append(escape(k));
                    first = false;
                }
                sb.append("\n");
                Files.write(path, sb.toString().getBytes(StandardCharsets.UTF_8),
                        StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                sb.setLength(0);
            }
            boolean first = true;
            for (Map.Entry<String, String> e : cols.entrySet()) {
                if (!first) sb.append(",");
                sb.append(escape(e.getValue()));
                first = false;
            }
            sb.append("\n");
            Files.write(path, sb.toString().getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("CSV write failed: " + path, e);
        }
    }

    private boolean isEmptyFile() {
        try { return !Files.exists(path) || Files.size(path) == 0; }
        catch (IOException e) { return true; }
    }

    private String escape(String s) {
        if (s == null) return "";
        boolean needQuotes = s.contains(",") || s.contains("\"") || s.contains("\n");
        String t = s.replace("\"", "\"\"");
        return needQuotes ? "\"" + t + "\"" : t;
    }
}