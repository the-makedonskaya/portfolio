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
@Table
@Getter
@Setter
public class Person {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column
	private String name;
	
	@ManyToMany(mappedBy = "persons")
	List<Note> notes = new ArrayList<>();	


	public Person(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return id.toString() + name;
	}
		
}
