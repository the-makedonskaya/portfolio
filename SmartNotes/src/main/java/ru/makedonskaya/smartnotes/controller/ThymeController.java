package ru.makedonskaya.smartnotes.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.makedonskaya.smartnotes.entity.Label;
import ru.makedonskaya.smartnotes.entity.Location;
import ru.makedonskaya.smartnotes.entity.Person;
import ru.makedonskaya.smartnotes.model.dto.NoteDto;
import ru.makedonskaya.smartnotes.model.request.label.AddLabelRequest;
import ru.makedonskaya.smartnotes.model.request.note.AddNoteRequest;
import ru.makedonskaya.smartnotes.model.request.note.DeleteNoteRequest;
import ru.makedonskaya.smartnotes.model.request.note.NoteSearchRequest;
import ru.makedonskaya.smartnotes.model.response.note.NoteResponse;
import ru.makedonskaya.smartnotes.service.ILabelSevice;
import ru.makedonskaya.smartnotes.service.ILocationService;
import ru.makedonskaya.smartnotes.service.INoteService;
import ru.makedonskaya.smartnotes.service.IPersonService;

@Controller
public class ThymeController {
	
	@Autowired
	private INoteService noteService;
	
	@Autowired
	private ILabelSevice labelService;
	
	@Autowired
	private ILocationService locationService;
	
	@Autowired
	private IPersonService personService;
	
	@ModelAttribute("allLocations")
	public List<Location> findAllLocations() {
		return locationService.getAll();
	}
	
	@ModelAttribute("allLabels")
	public List<Label> findAllLabels() {
		return labelService.getAll();
	}
	
	@ModelAttribute("allPersons")
	public List<Person> findAllPersons() {
		return personService.getAll();
	}

	@GetMapping("/notes/find")
	public String showSearchNotePage(Model model) {
		NoteSearchRequest request = new NoteSearchRequest();
		model.addAttribute("searchRequest", request);

		return "search-note";
	}

	@PostMapping("/notes/find") 
	public String findMyNotes(@ModelAttribute("searchRequest") NoteSearchRequest request, Model model) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		List<NoteDto> result = noteService.findByAnyFilter(request.getTitle(),
									request.getLocation(),
									request.getLabel(),
									request.getPerson(),
									StringUtils.isEmpty(request.getStartDateTime()) ? null : LocalDate.parse(request.getStartDateTime(), formatter),
											request.getEndDateTime() != null ? LocalDate.parse(request.getEndDateTime(), formatter) : null);
									
		model.addAttribute("filterNote", result);

		return "search-note";
	}
		
	@GetMapping("/notes/table")
	public String showNotesTablePage(Model model) {
		model.addAttribute("notes", noteService.getAll());
		return "note-table";
	}

	@GetMapping("/notes/table/add")
	public String showAddNotePage(Model model) {
		AddNoteRequest request = new AddNoteRequest();
		model.addAttribute("addNoteRequest", request);

		return "add-note";
	}

	@PostMapping("/notes/table/add") 
	public String addNote(@ModelAttribute("addNoteRequest") AddNoteRequest request, Model model) {
		noteService.addNote(request);

		return "redirect:/notes/table/add";
	}
	
	@RequestMapping(value = "/notes/table/add", params = { "addLoc" })
	public String addLocationToNote(AddNoteRequest request, BindingResult bindingResult) {
		request.getLocations().add(new Long(0));
		return "add-note";
	}
	
	@RequestMapping(value = "/notes/table/add", params = { "addLabel" })
	public String addLabelToNote(AddNoteRequest request, BindingResult bindingResult) {
		request.getLabels().add(new Long(0));
		
		return "add-note";
	}
	
	@RequestMapping(value = "/notes/table/add", params = { "addPerson" })
	public String addPersonToNote(AddNoteRequest request, BindingResult bindingResult) {
		request.getPersons().add(new Long(0));
		
		return "add-note";
	}
	
	@RequestMapping(value = "/notes/table/add", params = { "removeLoc" })
	public String removeLoc(AddNoteRequest request, final BindingResult bindingResult, final HttpServletRequest req) {
		final Long locId = Long.valueOf(req.getParameter("removeLoc"));
		request.getLocations().remove(locId.intValue());
		
		return "add-note";
	}
	
	@RequestMapping(value = "/notes/table/add", params = { "removeLabel" })
	public String removeLabel(AddNoteRequest request, final BindingResult bindingResult, final HttpServletRequest req) {
		final Long labelId = Long.valueOf(req.getParameter("removeLabel"));
		request.getLabels().remove(labelId.intValue());
		
		return "add-note";
	}
	
	@RequestMapping(value = "/notes/table/add", params = { "removePerson" })
	public String removePerson(AddNoteRequest request, final BindingResult bindingResult, final HttpServletRequest req) {
		final Long personId = Long.valueOf(req.getParameter("removePerson"));
		request.getPersons().remove(personId.intValue());
		
		return "add-note";
	}
	
	@GetMapping("/labels/table")
	public String labelList(Model model) {
		model.addAttribute("labels", labelService.getAll());

		return "label-table";
	}

	@GetMapping("labels/table/add")
	public String showAddLabelPage(Model model) {
		AddLabelRequest request = new AddLabelRequest();
		model.addAttribute("addLabelRequest", request);

		return "add-label";
	}

	@PostMapping("/labels/table/add")
	public String addLabel(@ModelAttribute("addLabelRequest") AddLabelRequest request, Model model) {
		labelService.addLabel(request);

		return "redirect:/labels/table/add";
	}
	
	@GetMapping("/notes/delete/{id}")
	public String showDeleteNotePage(@PathVariable("id") Long id, Model model) {
		NoteResponse note = noteService.getById(id);
        model.addAttribute("noteForDelete", note);
        model.addAttribute("noteForDeleteId", id);
        
        return "note-delete";		
	}
	
	@PostMapping("/notes/delete")
	public String deleteNote(@ModelAttribute("deleteNoteRequest") DeleteNoteRequest request, Model model) {
		noteService.deleteNoteById(request.getId());
		
		return "redirect:/notes/table";
	}
	
	@PostMapping("/notes/loc/add") 
	public String addNoteLoc(@ModelAttribute("addNoteRequest") AddNoteRequest request, Model model) {
		noteService.addNote(request);

		return "redirect:/notes/table";
	}

}
