package ru.makedonskaya.smartnotes.model.request.note;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteSearchRequest {
	
	private String title; 
	
	private String label;
	
	private String location;
	
	private String person;
	
	@JsonProperty("start_date")
	private String startDateTime;
	
	@JsonProperty("end_date")
	private String endDateTime;

}
