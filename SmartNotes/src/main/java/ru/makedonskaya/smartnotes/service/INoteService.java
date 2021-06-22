package ru.makedonskaya.smartnotes.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import ru.makedonskaya.smartnotes.entity.Note;
import ru.makedonskaya.smartnotes.model.dto.NoteDto;
import ru.makedonskaya.smartnotes.model.request.note.AddLabelNoteRequest;
import ru.makedonskaya.smartnotes.model.request.note.AddLocationNoteRequest;
import ru.makedonskaya.smartnotes.model.request.note.AddNoteRequest;
import ru.makedonskaya.smartnotes.model.request.note.UpdateNoteRequest;
import ru.makedonskaya.smartnotes.model.response.note.NoteResponse;

public interface INoteService {
	
	List<NoteDto> getAll();

	NoteResponse addNote(AddNoteRequest request);

	NoteResponse getById(Long id);

	NoteResponse updateNote(Long id, UpdateNoteRequest request);

	void deleteNoteById(Long id);

	NoteResponse addLabels(Long id, AddLabelNoteRequest request);

	NoteResponse addLocation(Long id, AddLocationNoteRequest request);

	void deleteNote(Note note);

	List<NoteDto> findByLocationFilter(String name);
	
	List<NoteDto> findByAnyFilter(String title, String location, String label, String person, LocalDate dataStart, LocalDate dataEnd);

	List<NoteDto> findByDataFilter(LocalDateTime dataStart, LocalDateTime dataEnd);

}
