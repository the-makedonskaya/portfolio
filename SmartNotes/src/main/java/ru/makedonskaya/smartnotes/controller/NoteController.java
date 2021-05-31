package ru.makedonskaya.smartnotes.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.makedonskaya.smartnotes.entity.Note;
import ru.makedonskaya.smartnotes.model.dto.NoteDto;
import ru.makedonskaya.smartnotes.model.request.note.AddLabelNoteRequest;
import ru.makedonskaya.smartnotes.model.request.note.AddLocationNoteRequest;
import ru.makedonskaya.smartnotes.model.request.note.AddNoteRequest;
import ru.makedonskaya.smartnotes.model.request.note.UpdateNoteRequest;
import ru.makedonskaya.smartnotes.model.response.note.NoteResponse;
import ru.makedonskaya.smartnotes.service.INoteService;

@RestController
@RequestMapping("/notes")
public class NoteController {
	
	@Autowired
	private INoteService service;
		
	@PostMapping //создание записи
	public NoteResponse add(@RequestBody AddNoteRequest request) {
		return service.addNote(request);
	}
	
	@GetMapping("/{id}")//получение одной записи
	public NoteResponse getById(@PathVariable("id") Long id) {
		return service.getById(id);
	}
	
	@GetMapping //получение всех записей
	public List<NoteDto> getAll(){
		return service.getAll();
	}
	
	@PutMapping("/{id}")//редактирование записи
	public NoteResponse updateById(@PathVariable("id") Long id, @RequestBody UpdateNoteRequest request) {
		return service.updateNote(id, request);
	}
	
	@DeleteMapping("/{id}")// удаление записи
	public void deleteById(@PathVariable("id") Long id) {
		service.deleteNoteById(id);
	}
	
	@DeleteMapping
	public void deleteNote(@RequestBody Note note) {
		service.deleteNote(note);	
	}
	
	@PutMapping("/{id}/labels")
	public NoteResponse addLabels(@PathVariable("id") Long id, @RequestBody AddLabelNoteRequest request) {
		return service.addLabels(id, request);
	}
	
	@PutMapping("/{id}/locations")
	public NoteResponse addLocation(@PathVariable("id") Long id, @RequestBody AddLocationNoteRequest request) {
		return service.addLocation(id, request);
	}
	
	@GetMapping("/filterData")
	public List<NoteDto> getByDataFilter(
			@RequestParam("dataStart") 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			LocalDate dataStart, 
			@RequestParam("dataEnd") 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			LocalDate dataEnd) {
		return service.findByDataFilter(dataStart.atStartOfDay(), dataEnd.atStartOfDay());
	}
	
	@GetMapping("/filterLoc")
	public List<NoteDto> getByLocationFilter(@RequestParam("location") String locationName) {
		return service.findByLocationFilter(locationName);
	}
		
	@GetMapping("/filter")
    public List<NoteDto> getNotesFiltered (
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "label", required = false) String label, 
            @RequestParam(value = "person", required = false) String person,
            @RequestParam(value = "dataStart", required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataStart,
            @RequestParam(value = "dataEnd", required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataEnd) {
  
        return service.findByAnyFilter(title, location, label, person, dataStart, dataEnd);
    }		
}
