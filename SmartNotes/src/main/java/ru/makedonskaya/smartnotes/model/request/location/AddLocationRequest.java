package ru.makedonskaya.smartnotes.model.request.location;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddLocationRequest {
	
	@JsonProperty
	private String name;
	
	//private Double lon;
	
	//private Double lat;
}
