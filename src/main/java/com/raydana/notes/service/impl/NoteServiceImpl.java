package com.raydana.notes.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.raydana.notes.exception.ResourceNotFoundException;
import com.raydana.notes.model.Note;
import com.raydana.notes.model.User;
import com.raydana.notes.model.export.ExportNoteModel;
import com.raydana.notes.repository.NoteRepository;
import com.raydana.notes.repository.UserRepository;
import com.raydana.notes.service.NoteService;

@Service
public class NoteServiceImpl implements NoteService {
	@Autowired
	private UserRepository userRepository; 
	@Autowired
	private NoteRepository noteRepository; 
	
	@Value("${dataTransform.export.directoryPath}")
    private String exportDirectoryPath;

	private User getPrincipal() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getPrincipal() != null) {
			if (auth.getPrincipal() instanceof User) {
				return (User) auth.getPrincipal();
			}
		}
		return null;
	}
	
	@Override
	public List<Note> getAllByUsername() {
		return noteRepository.getAllByUsername(getPrincipal().getUsername());
	}

	@Override
	public List<Note> findAll() {
		return noteRepository.findAll();
	}

	@Override
	public Note save(Note note) {
		note.setUsername(getPrincipal().getUsername());
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
        note.setNote(noteDetails.getNote());
        Note updatedNote = save(note);
        return updatedNote;
	}

	@Override
	public void delete(Long id) {
		noteRepository.deleteById(id);
	}

	@Override
	public Boolean userHasPermission(Long id) {
		String username = getPrincipal().getUsername();
		Note note = findById(id);
		if (note != null && username != null && !username.equals("") && note.getUsername().equals(username))
			return Boolean.TRUE;
		return Boolean.FALSE;
	}

	@Override
	public File exportNoteAndUsers() {
		try {
			ExportNoteModel exportNoteModel = new ExportNoteModel();
			exportNoteModel.getAllUsers().addAll(userRepository.findAll());
			exportNoteModel.getAllNotes().addAll(noteRepository.findAll());
			JAXBContext jaxbContext = JAXBContext.newInstance(ExportNoteModel.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			Calendar calendar = Calendar.getInstance();
			String fileName = "export_"  + (calendar.get(Calendar.HOUR_OF_DAY) < 10 ? "0" : "") + (calendar.get(Calendar.HOUR_OF_DAY) + (calendar.get(Calendar.MINUTE) < 10 ? "0" : "") + calendar.get(Calendar.MINUTE)) + ".xml";
			File file = new File(exportDirectoryPath +"/"+ fileName);
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			OutputStream os = new FileOutputStream(file);
			jaxbMarshaller.marshal(exportNoteModel, os);
			return file;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
