package ru.makedonskaya.smartnotes.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteDto {
	
	private Long id;
	
	//private String tenantId;
	
	private LocalDateTime startDateTime;
	
	private String title; //заголовок
	
	private String description; //краткое описание 
	
	private String labels;
	
	private String locations;
	
	private String persons;

}
