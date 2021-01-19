package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<File> searchByUser(Integer userId);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File search(Integer fileId);

    @Select("SELECT * FROM FILES WHERE filename = #{fileName}")
    File searchByName(String fileName);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer add(File file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    Boolean remove(Integer fileId);
}
