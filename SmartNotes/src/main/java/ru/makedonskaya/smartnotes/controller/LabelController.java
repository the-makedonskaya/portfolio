package ru.makedonskaya.smartnotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.makedonskaya.smartnotes.entity.Label;
import ru.makedonskaya.smartnotes.model.request.label.AddLabelRequest;
import ru.makedonskaya.smartnotes.model.request.label.UpdateLabelRequest;
import ru.makedonskaya.smartnotes.model.response.label.LabelResponse;
import ru.makedonskaya.smartnotes.service.ILabelSevice;

@RestController
@RequestMapping("/labels")
public class LabelController {
	
	@Autowired
	private ILabelSevice service;
	
	@PostMapping//создание записи
	public LabelResponse add(@RequestBody AddLabelRequest request) {
		return service.addLabel(request);
	}
	
	@GetMapping("/{id}")//получение одной записи
	public LabelResponse getById(@PathVariable("id") Long id) {
		return service.getById(id);
	}
	
	@GetMapping //получение всех записей
	public List<Label> getAll(){
		return service.getAll();
	}
	
	@PutMapping("/{id}")//редактирование записи
	public LabelResponse updateById(@PathVariable("id") Long id, @RequestBody UpdateLabelRequest request) {
		return service.updateLabel(id, request);
	}
	
	@DeleteMapping("/{id}")// удаление записи
	public void deleteById(@PathVariable("id") Long id) {
		service.deleteLabel(id);
	}

}
