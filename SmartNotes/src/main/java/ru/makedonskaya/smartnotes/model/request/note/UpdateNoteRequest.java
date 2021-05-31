package ru.makedonskaya.smartnotes.model.request.note;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateNoteRequest {
	
	@JsonProperty("full_text")
	private String fullText;
	
	private String title;
	
	private String description;
	
	@JsonProperty("start_date")
	private LocalDateTime startDateTime;
	
	@JsonProperty("end_date")
	private LocalDateTime endDateTime;

}
