package ru.makedonskaya.smartnotes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.makedonskaya.smartnotes.entity.Label;
import ru.makedonskaya.smartnotes.exception.ResourceNotFoundException;
import ru.makedonskaya.smartnotes.model.request.label.AddLabelRequest;
import ru.makedonskaya.smartnotes.model.request.label.UpdateLabelRequest;
import ru.makedonskaya.smartnotes.model.response.label.LabelResponse;
import ru.makedonskaya.smartnotes.repository.LabelRepo;
import ru.makedonskaya.smartnotes.service.ILabelSevice;

@Service
public class LabelService implements ILabelSevice {
	
	@Autowired
	private LabelRepo repository;

	@Override
	public LabelResponse addLabel(AddLabelRequest request) {
		Label newLabel = new Label(request.getTitle());
		Label result = repository.save(newLabel);
		
		return new LabelResponse(result.getId(), result.getTitle());
	}

	@Override
	public LabelResponse getById(Long id) {
		Label label = repository.findById(id).orElse(new Label());
		
		return new LabelResponse(label.getId(), label.getTitle());
	}

	@Override
	public List<Label> getAll() {
		return repository.findAll();
	}

	@Override
	public LabelResponse updateLabel(Long id, UpdateLabelRequest request) {
		Label label = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		label.setColor(request.getColor());
		label.setParentLabel(request.getParentLabel());
		label.setTitle(request.getTitle());
		
		Label result = repository.save(label);
		
		return new LabelResponse(result.getId(), result.getTitle());
	}

	@Override
	public void deleteLabel(Long id) {
		repository.deleteById(id);
		
	}

	@Override
	public List<Label> getAllByIds(List<Long> ids) {
		return repository.findAllById(ids);
	}

}
