package ru.makedonskaya.smartnotes.model.request.note;

import lombok.Data;

@Data
public class DeleteNoteRequest {
	
	private Long id;
	
	private String title;

}
