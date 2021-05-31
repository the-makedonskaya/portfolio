package ru.makedonskaya.smartnotes.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table
public class Label {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column
	private String color;
	
	@Column
	private String title;
	
	@Column(name="parent_label_id")
	private Long parentLabel;
	
	@ManyToMany(mappedBy = "labels")
	List<Note> notes = new ArrayList<>();	

	public Label(String color, String title, Long parentLabel) {
		this.color = color;
		this.title = title;
		this.parentLabel = parentLabel;
	}

	public Label(String title) {
		this.title = title;
	}

	public Label(Long labelId) {
		this.id = labelId;
	}

	@Override
	public String toString() {
		return id.toString() + title;
	}
}
