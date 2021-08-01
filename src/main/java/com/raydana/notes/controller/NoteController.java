package com.raydana.notes.controller;

import java.io.File;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raydana.notes.model.Note;
import com.raydana.notes.service.NoteService;

@RestController
@RequestMapping("/api")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping("/notes")
    public List<Note> getAllNotesByUsername() {
        return noteService.getAllByUsername();
    }
    
    @GetMapping("/notes/{id}")
    public Note getNoteById(@PathVariable(value = "id") Long noteId) throws Exception {
    	Boolean hasPermission  = noteService.userHasPermission(noteId);
    	if (hasPermission)
    		return noteService.findById(noteId);
    	else {
    		throw new Exception("you have not permission for update note Id : "+noteId);
    	}
    }


    @PostMapping("/notes")
    public Note createNote(@Valid @RequestBody Note note) {
        return noteService.save(note);
    }

    @PutMapping("/notes/{id}")
    public String updateNote(@PathVariable(value = "id") Long noteId,@Valid @RequestBody Note noteDetails) {
    	Boolean hasPermission  = noteService.userHasPermission(noteId);
    	if (hasPermission) {
    		noteService.update(noteId, noteDetails);
            return "note successfully updated";
    	}
    	return "you have not permission for update note Id : "+noteId;
    }

    @DeleteMapping("/notes/{id}")
    public String deleteNote(@PathVariable(value = "id") Long noteId) {
    	Boolean hasPermission  = noteService.userHasPermission(noteId);
    	if (hasPermission) {
    		noteService.delete(noteId);
    		return "delete successfully";
    	}
    	return "you have not permission for delete note Id : "+noteId;
    }
    @PostMapping("/notes/exportNoteAndUsers")
    public String exportNoteAndUsers() {
    	try {
            File exportedFile = noteService.exportNoteAndUsers();
            if (exportedFile != null)
            	return "export file " + exportedFile.getName() + " successfully created.";
		} catch (Exception e) {
			e.printStackTrace();
			return "export file failed";
		}
    	return "export file failed";
    }
}
