package com.ogunjinmi.droneapp.model;

public class ImageMessage {
    //{"fileUri":"uploadImages/77497f32-3da4-48b9-a08a-f6fca6e0cac2.png","
    // fullUrl":null,"imageHeaders":"data:image/png;base64,","fileName":null,
    // "latitude":0,"longitude":0,"dateCreated":"2020-02-29T16:39:18.5608888+00:00"}

    private String fileUri;
    private String fullUrl;
    private String imageHeaders;
    private String fileName;

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public String getImageHeaders() {
        return imageHeaders;
    }

    public void setImageHeaders(String imageHeaders) {
        this.imageHeaders = imageHeaders;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
