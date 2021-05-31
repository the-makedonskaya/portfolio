package ru.makedonskaya.smartnotes.service;

import java.util.List;

import ru.makedonskaya.smartnotes.entity.Person;
import ru.makedonskaya.smartnotes.model.request.person.AddPersonRequest;
import ru.makedonskaya.smartnotes.model.request.person.UpdatePersonRequest;
import ru.makedonskaya.smartnotes.model.response.person.PersonResponse;

public interface IPersonService {

	PersonResponse addPerson(AddPersonRequest request);

	PersonResponse getById(Long id);

	List<Person> getAll();

	PersonResponse updatePerson(Long id, UpdatePersonRequest request);

	void deletePerson(Long id);
	
	List<Person> getAllByIds(List<Long> ids);

}
