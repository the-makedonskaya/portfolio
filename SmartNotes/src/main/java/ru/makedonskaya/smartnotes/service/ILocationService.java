package ru.makedonskaya.smartnotes.service;


import java.util.List;

import ru.makedonskaya.smartnotes.entity.Location;
import ru.makedonskaya.smartnotes.model.request.location.AddLocationRequest;
import ru.makedonskaya.smartnotes.model.request.location.UpdateLocationRequest;
import ru.makedonskaya.smartnotes.model.response.location.LocationResponse;

public interface ILocationService {

	LocationResponse addLocation(AddLocationRequest request);

	LocationResponse getById(Long id);

	List<Location> getAll();

	LocationResponse updateLocation(Long id, UpdateLocationRequest request);

	void deleteLocation(Long id);
	
	List<Location> getAllByIds(List<Long> id);

}
