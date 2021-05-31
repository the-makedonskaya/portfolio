package ru.makedonskaya.smartnotes.model.request.note;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddNoteRequest {
	
	@JsonProperty("full_text")
	private String fullText;
	
	private String title;
	
	@JsonProperty("start_date")
	private String startDateTime;
	
	@JsonProperty("end_date")
	private String endDateTime;
	
	private String description;

	private List<Long> labels =  new ArrayList<>();

	private List<Long> locations = new ArrayList<>();
	
	private List<Long> persons = new ArrayList<>();

	public AddNoteRequest(String fullText, String title) {
		this.fullText = fullText;
		this.title = title;
	}
	
}
