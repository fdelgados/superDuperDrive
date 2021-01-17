package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public void saveNote(NoteDto noteDto) throws UnableToSaveNoteException, NoteNotFoundException {
        if (noteDto.noteExist()) {
            updateNote(noteDto);
            return;
        }

        createNewNote(noteDto);
    }

    private void createNewNote(NoteDto noteDto) throws UnableToSaveNoteException {
        Note note = new Note(
                noteDto.getNoteId(),
                noteDto.getNoteTitle(),
                noteDto.getNoteDescription(),
                noteDto.getUserId()
        );

        if (noteMapper.add(note) == 0) {
            throw new UnableToSaveNoteException("Cannot create new note");
        }
    }

    private void updateNote(NoteDto noteDto) throws UnableToSaveNoteException, NoteNotFoundException {
        Note note = noteMapper.search(noteDto.getNoteId());

        if (note == null) {
            throw new NoteNotFoundException("Note not found");
        }

        note.setNoteTitle(noteDto.getNoteTitle());
        note.setNoteDescription(noteDto.getNoteDescription());

        if (!noteMapper.update(note)) {
            throw new UnableToSaveNoteException("Cannot update note");
        }
    }

    public void deleteNote(Integer noteId) {
        if (!noteMapper.remove(noteId)) {
            throw new UnableToDeleteNoteException("Cannot delete note");
        }
    }

    public List<NoteDto> getUserNotes(Integer userId) {
        List<Note> notes = noteMapper.searchByUser(userId);
        List<NoteDto> noteDtos = new ArrayList<>();

        for (Note note: notes) {
            noteDtos.add(toDto(note));
        }

        return noteDtos;
    }

    private NoteDto toDto(Note note) {
        NoteDto noteDto = new NoteDto();

        noteDto.setUserId(note.getUserId());
        noteDto.setNoteId(note.getNoteId());
        noteDto.setNoteTitle(note.getNoteTitle());
        noteDto.setNoteDescription(note.getNoteDescription());

        return noteDto;
    }
}
