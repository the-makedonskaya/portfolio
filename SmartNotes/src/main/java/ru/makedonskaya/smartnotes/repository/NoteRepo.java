package ru.makedonskaya.smartnotes.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.criteria.ListJoin;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.makedonskaya.smartnotes.entity.Label;
import ru.makedonskaya.smartnotes.entity.Location;
import ru.makedonskaya.smartnotes.entity.Note;
import ru.makedonskaya.smartnotes.entity.Person;


@Repository
public interface NoteRepo extends JpaRepository<Note, Long>, JpaSpecificationExecutor<Note> {
	
	List<Note> findByTenantId(String tenantId);
	
	List<Note> findByTitle(String title);
	
	
	@Query("SELECT n FROM Note n WHERE n.tenantId = :paramTenantId "
			+ "AND n.startDateTime BETWEEN :paramDataStart AND :paramDataEnd")
	List<Note> findByDataFilter(@Param("paramTenantId") String tenantId, @Param("paramDataStart") LocalDateTime paramDataStart, 
			@Param("paramDataEnd")LocalDateTime paramDataEnd);
	
//	@Query("SELECT n FROM Note n WHERE n.startDateTime BETWEEN :paramDataStart AND :paramDataEnd")
//	List<Note> findByDataFilter(@Param("paramDataStart") LocalDateTime paramDataStart, 
//								@Param("paramDataEnd")LocalDateTime paramDataEnd);
	
	@Query("SELECT n FROM Note n JOIN n.locations l where l.name = :paramLocationName")
	List<Note> findByLocationFilter(@Param("paramLocationName") String name);
	
	static Specification<Note> NoteSpec () {
		return (note, query, criteriaBuilder) -> 
			
			criteriaBuilder.createQuery().getRestriction();	
	}
	
	static Specification<Note> titleContains(String title) {
	    return (note, cq, cb) -> cb.like(note.get("title"), "%" + title + "%");
	}
		
	static Specification<Note> locationContains (String locationsName) {
		return (note, query, criteriaBuilder) -> {
			ListJoin<Note, Location> locationJoin = note.joinList("locations");
			return query.where(criteriaBuilder.equal(locationJoin.get("name"), locationsName)).getRestriction();	
		};
	}
	
	static Specification<Note> labelsContains(String label) {
		return (note, query, criteriaBuilder) -> {
			ListJoin<Note, Label> labelJoin = note.joinList("labels");
			return query.where(criteriaBuilder.equal(labelJoin.get("title"), label)).getRestriction();	
		};
	}

	static Specification<Note> personContains(String personName) {
		return (note, query, criteriaBuilder) -> {
			ListJoin<Note, Person> personJoin = note.joinList("persons");
			return query.where(criteriaBuilder.equal(personJoin.get("name"), personName)).getRestriction();	
		};
	}
	
	static Specification<Note> dataBetween(LocalDate paramDataStart, LocalDate paramDataEnd) {
		return (note, query, criteriaBuilder) -> {
			return query.where(criteriaBuilder.between(note.get("startDateTime"), paramDataStart.atStartOfDay(), paramDataEnd.atStartOfDay())).getRestriction();
		};
	}
	
	static Specification<Note> tenantIdEqual(String tenantId) {
		return (note, cq, cb) -> cb.equal(note.get("tenantId"), tenantId);
		
	}
}

