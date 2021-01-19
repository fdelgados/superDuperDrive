package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.FileDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public List<FileDto> getUserFiles(Integer userId) {
        List<File> userFiles = fileMapper.searchByUser(userId);
        List<FileDto> fileDtos = new ArrayList<>();

        for (File userFile: userFiles) {
            fileDtos.add(toDto(userFile, userId));
        }

        return fileDtos;
    }

    public File getFile(Integer id) throws FileNotFoundException {
        File file = fileMapper.search(id);

        if (file == null) {
            throw new FileNotFoundException("File not found");
        }

        return file;
    }

    private FileDto toDto(File file, Integer userId) {
        return new FileDto(file.getFileId(), userId, file.getFileName());
    }

    public void createFile(MultipartFile uploadedFile, Integer userId) throws UnableToUploadFileException {
        String fileName = uploadedFile.getOriginalFilename();
        File file = fileMapper.searchByName(fileName);
        if (file != null) {
            throw new UnableToUploadFileException("A file with name '" + fileName + "' already exists" );
        }

        try {
            file = new File(fileName,
                    uploadedFile.getContentType(),
                    uploadedFile.getSize(),
                    userId,
                    uploadedFile.getBytes()
            );
            fileMapper.add(file);
        } catch (IOException e) {
            throw new UnableToUploadFileException("Cannot upload file");
        }
    }

    public void removeFile(Integer id) throws UnableToDeleteFileException {
        if (!fileMapper.remove(id)) {
            throw new UnableToDeleteFileException("Cannot delete file");
        }
    }
}
