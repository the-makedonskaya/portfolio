package ru.makedonskaya.smartnotes.model.response.note;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteResponse {
	
	@JsonProperty("full_text")
	private String fullText;
	
	private String title;
	
	private String description;
	
	private List<Long> labels = new ArrayList<>();
	
	private List<Long> locations = new ArrayList<>();
	
	private List<Long> persons = new ArrayList<>();

}
