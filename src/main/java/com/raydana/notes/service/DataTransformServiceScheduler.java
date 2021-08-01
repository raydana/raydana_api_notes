package com.raydana.notes.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.raydana.notes.model.export.ExportNoteModel;

@Configuration
@EnableScheduling
@Component
public class DataTransformServiceScheduler {
    @Autowired
    private NoteService noteService;
    @Autowired
    private UserService userService;
    
    @Value("${dataTransform.export.directoryPath}")
    private String exportDirectoryPath;
    
	@Scheduled(fixedDelay = 5000)
	public void dataTransform() {
		try {
			ExportNoteModel exportNoteModel = new ExportNoteModel();
			exportNoteModel.getAllUsers().addAll(userService.findAll());
			exportNoteModel.getAllNotes().addAll(noteService.findAll());
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
