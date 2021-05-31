package ru.makedonskaya.smartnotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.makedonskaya.smartnotes.entity.Person;
import ru.makedonskaya.smartnotes.model.request.person.AddPersonRequest;
import ru.makedonskaya.smartnotes.model.request.person.UpdatePersonRequest;
import ru.makedonskaya.smartnotes.model.response.person.PersonResponse;
import ru.makedonskaya.smartnotes.service.IPersonService;

@RestController
@RequestMapping("/person")
public class PersonController {
	@Autowired
	IPersonService service;
	
	@PostMapping
	public PersonResponse add(@RequestBody AddPersonRequest request) {
		return service.addPerson(request);
	}
	
	@GetMapping("/{id}")
	public PersonResponse getById(@PathVariable("id") Long id) {
		return service.getById(id);
	}
	
	@GetMapping
	public List<Person> getAll(){
		return service.getAll();
	}
	
	@PutMapping("/{id}")
	public PersonResponse updateById(@PathVariable("id") Long id, @RequestBody UpdatePersonRequest request) {
		return service.updatePerson(id, request);
	}
	
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable("id") Long id) {
		service.deletePerson(id);
	}

}
