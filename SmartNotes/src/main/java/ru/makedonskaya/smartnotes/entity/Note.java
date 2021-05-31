package ru.makedonskaya.smartnotes.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@Table
public class Note {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="tenant_id")
	private String tenantId;
	
	@Column(name="full_text")
	private String fullText;
	
	@Column
	private String title; //заголовок
	
	@Column
	private String description; //краткое описание 

	@Column(name="start_date_time")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDateTime startDateTime;
	
	@Column(name="end_date_time")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDateTime endDateTime;
	
	@ManyToMany
	@JoinTable(name = "note_location", joinColumns = @JoinColumn(name = "note_id"), 
	inverseJoinColumns = @JoinColumn(name = "location_id"))
	private List<Location> locations = new ArrayList<>();
	
	@ManyToMany
	@JoinTable(name = "note_person", joinColumns = @JoinColumn(name = "note_id"), 
	inverseJoinColumns = @JoinColumn(name = "person_id"))
	private List<Person> persons = new ArrayList<>();
	
	@ManyToMany
	@JoinTable(name = "note_label", joinColumns = @JoinColumn(name = "note_id"), 
	inverseJoinColumns = @JoinColumn(name = "label_id"))
	private List<Label> labels = new ArrayList<>();

	public Note(String fullText, String title) {
		this.fullText = fullText;
		this.title = title;
	}

	public Note(String fullText, String title, String description, LocalDateTime startDateTime,
			LocalDateTime endDateTime) {
		this.fullText = fullText;
		this.title = title;
		this.description = description;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
	}

}
