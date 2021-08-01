package com.raydana.notes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Configuration
@EnableScheduling
@Component
public class DataTransformServiceScheduler {
    @Autowired
    private NoteService noteService;
    
	@Scheduled(fixedDelay = 3600000)
	public void dataTransform() {
		try {
			noteService.exportNoteAndUsers();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
