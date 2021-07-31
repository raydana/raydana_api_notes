package com.raydana.notes.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
    public List<Note> getAllNotesByUsername(@RequestHeader("header-username") String username) {
        return noteService.getAllByUsername(username);
    }
 

    @PostMapping("/notes")
    public Note createNote(@Valid @RequestBody Note note) {
        return noteService.save(note);
    }

    @GetMapping("/notes/{id}")
    public Note getNoteById(@PathVariable(value = "id") Long noteId , @RequestHeader("header-username") String username) {
        return noteService.findById(noteId);
    }

    @PutMapping("/notes/{id}")
    public String updateNote(@PathVariable(value = "id") Long noteId,@RequestBody Note noteDetails,@RequestHeader("header-username") String username) {
    	Boolean hasPermission  = noteService.userHasPermission(noteId, username);
    	if (hasPermission) {
    		Note updatedNote = noteService.update(noteId, noteDetails);
            return "note successfully updated";
    	}
    	return "you have not permission for update note Id : "+noteId;
    }

    @DeleteMapping("/notes/{id}")
    public String deleteNote(@PathVariable(value = "id") Long noteId,@RequestHeader("header-username") String username) {
    	Boolean hasPermission  = noteService.userHasPermission(noteId, username);
    	if (hasPermission) {
    		noteService.delete(noteId);
    		return "delete successfully";
    	}
    	return "you have not permission for delete note Id : "+noteId;
    }
}
