package com.raydana.notes.service;

import java.util.List;

import com.raydana.notes.model.Note;

public interface NoteService {
	public List<Note> getAllByUsername();
	public List<Note> findAll();
	public Note save(Note note);
	public Note findById(Long id);
	public Note update(Long id,Note noteDetails);
	public void delete(Long id);
	public Boolean userHasPermission(Long id );
	

}
