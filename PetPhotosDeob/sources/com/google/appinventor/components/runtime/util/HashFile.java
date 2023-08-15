package com.google.appinventor.components.runtime.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes.dex */
public class HashFile {
    private String fileName;
    private String hash;
    private String timestamp;

    public HashFile() {
    }

    public HashFile(String fileName, String hash, Date time) {
        this.fileName = fileName;
        this.hash = hash;
        this.timestamp = formatTimestamp(time);
    }

    public HashFile(String fileName, String hash, String timestamp) {
        this.fileName = fileName;
        this.hash = hash;
        this.timestamp = timestamp;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getHash() {
        return this.hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestampInDb(Date time) {
        this.timestamp = formatTimestamp(time);
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String formatTimestamp(Date timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(timestamp);
    }
}
