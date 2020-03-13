package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Publication;
import com.uniovi.repositories.PublicationRepository;

@Service
public class PublicationService {

	@Autowired
	private PublicationRepository publicationRepository;
	
	public void addPublication(Publication publication) {
		publicationRepository.save(publication);
	}
	
}
