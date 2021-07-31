package com.raydana.notes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raydana.notes.exception.ResourceNotFoundException;
import com.raydana.notes.model.Note;
import com.raydana.notes.repository.NoteRepository;

@Service
public class NoteServiceImpl implements NoteService {
	@Autowired
	private NoteRepository noteRepository; 

	@Override
	public List<Note> getAllByUsername(String username) {
		return noteRepository.getAllByUsername(username);
	}

	@Override
	public List<Note> getAll() {
		return noteRepository.findAll();
	}

	@Override
	public Note save(Note note) {
		return noteRepository.save(note);
	}

	@Override
	public Note findById(Long id) {
		return noteRepository.findById(id) .orElseThrow(() -> new ResourceNotFoundException("Note", "id", id));
	}

	@Override
	public Note update(Long id,Note noteDetails) {
		Note note = findById(id);
        note.setTitle(noteDetails.getTitle());
        note.setTitle(noteDetails.getNote());
        Note updatedNote = save(note);
        return updatedNote;
	}

	@Override
	public void delete(Long id) {
		noteRepository.deleteById(id);
	}

	@Override
	public Boolean userHasPermission(Long id, String username) {
		Note note = findById(id);
		if (note != null && username != null && !username.equals("") && note.getUsername().equals(username))
			return Boolean.TRUE;
		return Boolean.FALSE;
	}
	
}
