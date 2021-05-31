package ru.makedonskaya.smartnotes.service;

import java.util.List;

import ru.makedonskaya.smartnotes.entity.Label;
import ru.makedonskaya.smartnotes.model.request.label.AddLabelRequest;
import ru.makedonskaya.smartnotes.model.request.label.UpdateLabelRequest;
import ru.makedonskaya.smartnotes.model.response.label.LabelResponse;

public interface ILabelSevice {

	LabelResponse addLabel(AddLabelRequest request);

	LabelResponse getById(Long id);

	List<Label> getAll();

	LabelResponse updateLabel(Long id, UpdateLabelRequest request);

	void deleteLabel(Long id);

	List<Label> getAllByIds(List<Long> id);

}
