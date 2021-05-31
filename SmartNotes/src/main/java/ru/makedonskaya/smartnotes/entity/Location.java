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
public class Location {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private Double lon;
	
	@Column
	private Double lat;
	
	@ManyToMany(mappedBy = "locations")
	List<Note> notes = new ArrayList<>();	


	public Location(String name, Double lon, Double lat) {
		this.name = name;
		this.lon = lon;
		this.lat = lat;
	}


	public Location(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return id.toString() + name;
	}
}
