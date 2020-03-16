package com.uniovi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Publication;
import com.uniovi.entities.User;
import com.uniovi.repositories.PublicationRepository;

@Service
public class PublicationService {

	@Autowired
	private PublicationRepository publicationRepository;
	
	public void addPublication(Publication publication) {
		publicationRepository.save(publication);
	}

	public List<Publication> getPublicationsForUser(User user) {
		return publicationRepository.findByPoster(user);
	}

	public void deletePublicationsOfUser(User user) {
		List<Publication> publications = publicationRepository.findByPoster(user);
		for(Publication publication : publications) {
			publicationRepository.delete(publication);
		}
	}
	
}
