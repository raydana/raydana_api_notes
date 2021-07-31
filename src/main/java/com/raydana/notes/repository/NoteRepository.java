package com.raydana.notes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.raydana.notes.model.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
	@Query("SELECT n FROM Note n WHERE LOWER(n.username) = LOWER(:username)")
	public List<Note> getAllByUsername(@Param("username") String username);
}
