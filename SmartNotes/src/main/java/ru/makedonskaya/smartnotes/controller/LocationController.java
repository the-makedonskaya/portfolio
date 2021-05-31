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

import ru.makedonskaya.smartnotes.entity.Location;
import ru.makedonskaya.smartnotes.model.request.location.AddLocationRequest;
import ru.makedonskaya.smartnotes.model.request.location.UpdateLocationRequest;
import ru.makedonskaya.smartnotes.model.response.location.LocationResponse;
import ru.makedonskaya.smartnotes.service.ILocationService;

@RestController
@RequestMapping("/locations")
public class LocationController {
	@Autowired
	private ILocationService service;
	
	@PostMapping
	public LocationResponse add(@RequestBody AddLocationRequest request) {
		return service.addLocation(request);
	}
	
	@GetMapping("/{id}")//получение одной записи
	public LocationResponse getById (@PathVariable ("id") Long id) {
		return service.getById(id);
	}
	
	@GetMapping //получение всех записей
	public List<Location> getAll(){
		return service.getAll();
	}
	
	@PutMapping("/{id}")//редактирование записи
	public LocationResponse updateById(@PathVariable("id") Long id, @RequestBody UpdateLocationRequest request) {
		return service.updateLocation(id, request);
	}
	
	@DeleteMapping("/{id}")// удаление записи
	public void deleteById(@PathVariable("id") Long id) {
		service.deleteLocation(id);
	}

}
