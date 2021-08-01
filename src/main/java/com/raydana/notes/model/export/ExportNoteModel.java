package com.raydana.notes.model.export;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.raydana.notes.model.Note;
import com.raydana.notes.model.User;

@XmlRootElement(name = "export")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExportNoteModel {
	@XmlElement(name = "users")
	private List<User> allUsers = new ArrayList<User>();
	@XmlElement(name = "notes")
	private List<Note> allNotes = new ArrayList<Note>();
	
	public List<User> getAllUsers() {
		return allUsers;
	}
	public void setAllUsers(List<User> allUsers) {
		this.allUsers = allUsers;
	}
	public List<Note> getAllNotes() {
		return allNotes;
	}
	public void setAllNotes(List<Note> allNotes) {
		this.allNotes = allNotes;
	}
}
