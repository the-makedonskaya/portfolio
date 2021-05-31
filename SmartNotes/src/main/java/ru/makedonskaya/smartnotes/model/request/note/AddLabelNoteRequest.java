package ru.makedonskaya.smartnotes.model.request.note;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddLabelNoteRequest {
	
	@JsonProperty("lable_id")
	private List<Long> id;

}
