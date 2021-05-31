package ru.makedonskaya.smartnotes.model.request.person;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdatePersonRequest {
	String name;
}
