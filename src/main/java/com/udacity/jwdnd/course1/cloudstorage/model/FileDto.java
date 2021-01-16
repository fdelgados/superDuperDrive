package com.udacity.jwdnd.course1.cloudstorage.model;

public class FileDto {
    private Integer fileId;
    private Integer userId;
    private String name;

    public FileDto(Integer fileId, Integer userId, String name) {
        this.fileId = fileId;
        this.userId = userId;
        this.name = name;
    }

    public Integer getFileId() {
        return fileId;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }
}
