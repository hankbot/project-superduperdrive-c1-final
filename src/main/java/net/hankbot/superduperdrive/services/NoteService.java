package net.hankbot.superduperdrive.services;

import net.hankbot.superduperdrive.data.SuperNoteMapper;
import net.hankbot.superduperdrive.models.SuperNote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NoteService {

  private SuperNoteMapper noteMapper;
  private Logger logger = LoggerFactory.getLogger(NoteService.class);

  public NoteService(SuperNoteMapper noteMapper) {
    this.noteMapper = noteMapper;
  }

  public ArrayList<SuperNote> userNoteList(Integer userId) {
    return noteMapper.findNotesForUserId(userId);
  }

  public boolean addNoteForUserId(Integer userId, SuperNote newNote) {
    newNote.setUserId(userId);
    noteMapper.addNote(newNote);

    return true;
  }

  public boolean updateNoteForUserId(Integer userId, SuperNote updatedNote) {
    updatedNote.setUserId(userId);
    noteMapper.updateNote(updatedNote);
    return true;
  }

  public boolean deleteNote(Integer noteId) {
    noteMapper.deleteNoteForNoteId(noteId);
    return true;
  }

  public boolean deleteNote(Integer noteId, Integer userId) {
    noteMapper.deleteNoteForNoteIdWithUserId(noteId, userId);
    return true;
  }

  public SuperNote noteForId(Integer noteId) {
    SuperNote note = noteMapper.findNoteForNoteId(noteId);

    return note;
  }

  public SuperNote noteForId(Integer noteId, Integer userId) {
    SuperNote note = noteMapper.findNoteForNoteIdWithUserId(noteId, userId);

    return note;
  }

}
