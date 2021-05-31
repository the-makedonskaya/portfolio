package ru.makedonskaya.smartnotes.model.request.label;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateLabelRequest {
	private String color;
	
	private String title;
	
	private Long parentLabel;


}
