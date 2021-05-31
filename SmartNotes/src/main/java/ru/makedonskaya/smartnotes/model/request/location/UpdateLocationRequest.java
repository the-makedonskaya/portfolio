package ru.makedonskaya.smartnotes.model.request.location;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateLocationRequest {
	private String name;
	
	private Double lon;
	
	private Double lat;

}
