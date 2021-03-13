package net.hankbot.superduperdrive.data;

import net.hankbot.superduperdrive.models.SuperNote;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface SuperNoteMapper {

  @Select("SELECT noteid, notetitle, notedescription, userid  FROM NOTES WHERE userid = #{userId}")
  ArrayList<SuperNote> findNotesForUserId(Integer userId);

  @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
  @Options(useGeneratedKeys = true, keyProperty = "noteId")
  Integer addNote(SuperNote newNote);

  @Update("UPDATE NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription}, userid = #{userId} WHERE noteid = #{noteId}")
  Integer updateNote(SuperNote updatedNote);

  @Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
  Integer deleteNoteForNoteId(Integer noteId);

  @Delete("DELETE FROM NOTES WHERE noteid = #{noteId} AND userid = #{userId}")
  Integer deleteNoteForNoteIdWithUserId(Integer noteId, Integer userId);

  @Select("SELECT * FROM NOTES WHERE noteid = #{noteId}")
  SuperNote findNoteForNoteId(Integer noteId);

  @Select("SELECT * FROM NOTES WHERE noteid = #{noteId} AND userid = #{userId}")
  SuperNote findNoteForNoteIdWithUserId(Integer noteId, Integer userId);
}
