package ru.makedonskaya.smartnotes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.makedonskaya.smartnotes.entity.Location;
import ru.makedonskaya.smartnotes.exception.ResourceNotFoundException;
import ru.makedonskaya.smartnotes.model.request.location.AddLocationRequest;
import ru.makedonskaya.smartnotes.model.request.location.UpdateLocationRequest;
import ru.makedonskaya.smartnotes.model.response.location.LocationResponse;
import ru.makedonskaya.smartnotes.repository.LocationRepo;
import ru.makedonskaya.smartnotes.service.ILocationService;


@Service
public class LocationService implements ILocationService {
	
	@Autowired
	private LocationRepo repository;

	@Override
	public LocationResponse addLocation(AddLocationRequest request) {
		Location location = repository.save(new Location(request.getName()));
		return new LocationResponse(location.getId(), location.getName());
	}

	@Override
	public LocationResponse getById(Long id) {
		Location location = repository.findById(id).orElse(new Location());
		return new LocationResponse(location.getId(), location.getName());
	}

	@Override
	public List<Location> getAll() {
		return repository.findAll();
	}

	@Override
	public LocationResponse updateLocation(Long id, UpdateLocationRequest request) {
		Location location = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		location.setName(request.getName());
		location.setLat(request.getLat());
		location.setLon(request.getLon());

		repository.save(location);

		return new LocationResponse(location.getId(), location.getName());
	}

	@Override
	public void deleteLocation(Long id) {
		repository.deleteById(id);
	}

	@Override
	public List<Location> getAllByIds(List<Long> ids) {
		
		return repository.findAllById(ids);
	}
}
