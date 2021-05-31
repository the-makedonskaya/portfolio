package ru.makedonskaya.smartnotes.model.request.label;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddLabelRequest {
//	@JsonProperty("color")
//	private String color;
	
	@JsonProperty("title")
	private String title;
	
//	@JsonProperty("parent_label")
//	private Long parentLabel;
//	
	

}
