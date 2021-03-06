package net.hankbot.superduperdrive.data;

import net.hankbot.superduperdrive.models.SuperFile;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface SuperFileMapper {

  @Select("SELECT fileId, filename, contenttype, filesize, userid, filedata  FROM FILES WHERE userid = #{userId}")
  ArrayList<SuperFile> findFilesForUserId(Integer userId);

  @Select("SELECT fileId FROM FILES WHERE filename = #{filename} AND userid = #{userId}")
  ArrayList<Integer> findFilesForNameWithUserId(String filename, Integer userId);

  @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{filename}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
  @Options(useGeneratedKeys = true, keyProperty = "fileId")
  Integer addFile(SuperFile uploadedFile);

  @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
  Integer deleteFileForFileId(Integer fileId);

  @Delete("DELETE FROM FILES WHERE fileId = #{fileId} AND userid = #{userId}")
  Integer deleteFileForFileIdWithUserId(Integer fileId, Integer userId);

  @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
  SuperFile findFileForFileId(Integer fileId);

  @Select("SELECT * FROM FILES WHERE fileId = #{fileId} AND userid = #{userId}")
  SuperFile findFileForFileIdWithUserId(Integer fileId, Integer userId);
}
