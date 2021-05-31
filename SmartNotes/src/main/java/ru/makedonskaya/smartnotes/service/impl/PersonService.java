package ru.makedonskaya.smartnotes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.makedonskaya.smartnotes.entity.Person;
import ru.makedonskaya.smartnotes.exception.ResourceNotFoundException;
import ru.makedonskaya.smartnotes.model.request.person.AddPersonRequest;
import ru.makedonskaya.smartnotes.model.request.person.UpdatePersonRequest;
import ru.makedonskaya.smartnotes.model.response.person.PersonResponse;
import ru.makedonskaya.smartnotes.repository.PersonRepo;
import ru.makedonskaya.smartnotes.service.IPersonService;

@Service
public class PersonService implements IPersonService{
	@Autowired
	private PersonRepo repository;

	@Override
	public PersonResponse addPerson(AddPersonRequest request) {
		Person person = repository.save(new Person(request.getName()));
		return new PersonResponse(person.getName());
	}

	@Override
	public PersonResponse getById(Long id) {
		Person person = repository.findById(id).orElse(new Person());
		return new PersonResponse(person.getName());
	}

	@Override
	public List<Person> getAll() {
		return repository.findAll();
	}

	@Override
	public PersonResponse updatePerson(Long id, UpdatePersonRequest request) {
		Person person = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		person.setName(request.getName());
		repository.save(person);
		return new PersonResponse(person.getName());
	}

	@Override
	public void deletePerson(Long id) {
		repository.deleteById(id);
		
	}

	@Override
	public List<Person> getAllByIds(List<Long> ids) {
		return repository.findAllById(ids);
	}

}
