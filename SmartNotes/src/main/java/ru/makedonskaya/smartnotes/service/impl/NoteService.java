package ru.makedonskaya.smartnotes.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ru.makedonskaya.smartnotes.entity.Label;
import ru.makedonskaya.smartnotes.entity.Location;
import ru.makedonskaya.smartnotes.entity.Note;
import ru.makedonskaya.smartnotes.entity.Person;
import ru.makedonskaya.smartnotes.exception.ResourceNotFoundException;
import ru.makedonskaya.smartnotes.model.dto.NoteDto;
import ru.makedonskaya.smartnotes.model.request.note.AddLabelNoteRequest;
import ru.makedonskaya.smartnotes.model.request.note.AddLocationNoteRequest;
import ru.makedonskaya.smartnotes.model.request.note.AddNoteRequest;
import ru.makedonskaya.smartnotes.model.request.note.UpdateNoteRequest;
import ru.makedonskaya.smartnotes.model.response.note.NoteResponse;
import ru.makedonskaya.smartnotes.repository.NoteRepo;
import ru.makedonskaya.smartnotes.service.ILabelSevice;
import ru.makedonskaya.smartnotes.service.ILocationService;
import ru.makedonskaya.smartnotes.service.INoteService;
import ru.makedonskaya.smartnotes.service.IPersonService;
import ru.makedonskaya.smartnotes.user.ITenantService;

@Service
public class NoteService implements INoteService {
	
	@Autowired
	private NoteRepo repository;
	
	@Autowired
	private ILabelSevice labelService;
	
	@Autowired
	private ILocationService locationService;
	
	@Autowired
	private IPersonService personService;
	
	@Autowired
	private ITenantService tenantService;

	@Override
	public List<NoteDto> getAll() {
		return repository.findByTenantId(tenantService.getCurrentTenantIdString())
				         .stream()
				         .map(e -> convertToDto(e))
				         .collect(Collectors.toList());
	
	}

	@Override
	public NoteResponse addNote(AddNoteRequest request) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		Note newNote = new Note(
				request.getFullText(), 
				request.getTitle(), 
				request.getDescription(),
				StringUtils.isEmpty(request.getStartDateTime()) ? null : LocalDate.parse(request.getStartDateTime(), formatter).atStartOfDay(),
				StringUtils.isEmpty(request.getEndDateTime()) ? null : LocalDate.parse(request.getEndDateTime(), formatter).atStartOfDay());
			
		
		newNote.getLabels().addAll(labelService.getAllByIds(request.getLabels()));
		newNote.getLocations().addAll(locationService.getAllByIds(request.getLocations()));
		List<Person> p = personService.getAllByIds(request.getPersons());
		newNote.getPersons().addAll(p);
		newNote.setTenantId(tenantService.getCurrentTenantIdString());
		
		Note result = repository.save(newNote);
		
		return convertToReponse(result);
	}

	@Override
	public NoteResponse getById(Long id) {
		Note note = repository.findById(id).orElse(new Note());
		return convertToReponse(note);
	}

	@Override
	public NoteResponse updateNote(Long id, UpdateNoteRequest request) {
		Note note = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		note.setFullText(request.getFullText());
		note.setDescription(request.getDescription());
		note.setTitle(request.getTitle());
		note.setStartDateTime(request.getStartDateTime());
		note.setEndDateTime(request.getEndDateTime());
		
		Note result = repository.save(note);
		
		return convertToReponse(result);
	}

	@Override
	public void deleteNoteById(Long id) {
		repository.deleteById(id);
	}

	private NoteDto convertToDto(Note entity) {
		NoteDto dto = new NoteDto();
		dto.setId(entity.getId());
		dto.setStartDateTime(entity.getStartDateTime());
		dto.setTitle(entity.getTitle());
		dto.setDescription(entity.getDescription());
		dto.setLabels(entity.getLabels().stream().map(l -> l.getTitle()).collect(Collectors.joining(", "))); 
		dto.setLocations(entity.getLocations().stream().map(l -> l.getName()).collect(Collectors.joining(", "))); 
		dto.setPersons(entity.getPersons().stream().map(p -> p.getName()).collect(Collectors.joining(", "))); 
		
		return dto;
	}
	
	private NoteResponse convertToReponse(Note entity) {
		NoteResponse dto = new NoteResponse();
		dto.setFullText(entity.getFullText());
		dto.setTitle(entity.getTitle());
		dto.setDescription(entity.getDescription());
		dto.setLabels(entity.getLabels().stream().map(e -> e.getId()).collect(Collectors.toList()));
		dto.setLocations(entity.getLocations().stream().map(e -> e.getId()).collect(Collectors.toList()));
		dto.setPersons(entity.getPersons().stream().map(e -> e.getId()).collect(Collectors.toList()));
		
		return dto;
	}

	@Override
	public NoteResponse addLabels(Long id, AddLabelNoteRequest request) {
		Note note = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		List<Label> labels = labelService.getAllByIds(request.getId());
		
		note.getLabels().addAll(labels);
		repository.save(note);
		
		return convertToReponse(note);
	}

	@Override
	public NoteResponse addLocation(Long id, AddLocationNoteRequest request) {
		Note note = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		List<Location> locations = locationService.getAllByIds(request.getId());
		
		note.getLocations().addAll(locations);
		repository.save(note);
		
		return convertToReponse(note);
	}

	@Override
	public void deleteNote(Note note) {
		repository.delete(note);		
	}

	@Override
	public List<NoteDto> findByDataFilter(LocalDateTime dataStart, LocalDateTime dataEnd) {
		return repository.findByDataFilter(tenantService.getCurrentTenantIdString(), dataStart, dataEnd)
						 .stream().map(e -> convertToDto(e)).collect(Collectors.toList());
	}

	@Override
	public List<NoteDto> findByLocationFilter(String name) {
		return repository.findByLocationFilter(name)
						 .stream().map(e -> convertToDto(e)).collect(Collectors.toList());
	}
	
	@Override
	public List<NoteDto> findByAnyFilter(String title, String location, String label, String person, LocalDate dataStart, LocalDate dataEnd) {
		Boolean bothDatePresent = dataStart != null && dataEnd != null;
		Boolean onlyStartDatePresents = dataStart != null && dataEnd == null;
		Boolean onlyEndDatePresents = dataStart == null && dataEnd != null;
		
		Specification<Note> specification =  NoteRepo.NoteSpec();
		if (isNotEmpty(title)) {
			specification = specification.and(NoteRepo.titleContains(title));
		}
		if (isNotEmpty(location)) {
			specification = specification.and(NoteRepo.locationContains(location));
		}
		if (isNotEmpty(label)) {
			specification = specification.and(NoteRepo.labelsContains(label));
		}
		if (isNotEmpty(person)) {
			specification = specification.and(NoteRepo.personContains(person));
		}
		if (bothDatePresent) {
			specification = specification.and(NoteRepo.dataBetween(dataStart, dataEnd));
		} else if (onlyStartDatePresents) {
			specification = specification.and(NoteRepo.dataStart(dataStart));
		} else if (onlyEndDatePresents) {
			specification = specification.and(NoteRepo.dataEnd(dataEnd));
		}
			
		
		
		specification = specification.and(NoteRepo.tenantIdEqual(tenantService.getCurrentTenantIdString()));
	
		return repository.findAll(specification).stream().map(e -> convertToDto(e)).collect(Collectors.toList());
	}

	private boolean isNotEmpty(String str) {
		return StringUtils.hasText(str) && !str.equals("-1");
	}

}




