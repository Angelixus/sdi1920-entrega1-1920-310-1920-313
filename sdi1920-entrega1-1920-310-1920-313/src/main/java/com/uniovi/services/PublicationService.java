package com.uniovi.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.uniovi.entities.Publication;
import com.uniovi.entities.User;
import com.uniovi.repositories.PublicationRepository;

@Service
public class PublicationService {

	@Autowired
	private PublicationRepository publicationRepository;
	
	public void addPublication(Publication publication, User user, Date date) {
		publication.setPoster(user);
		publication.setDate(date);
		publicationRepository.save(publication);
		user.addPublication(publication);
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

	public void addImageToPublication(Publication publication, MultipartFile image) {
		if(!image.getOriginalFilename().equals("")) {
			String extension = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf(".") + 1);
			String rightPathPath = "images/" + UUID.randomUUID().toString() + '_' + publication.getPoster().getEmail()
					+ '_' + publication.getId() + '.' + extension;
			String filePath = "src/main/resources/static/" + rightPathPath;
			File file = new File(filePath);
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(file);

				fos.write(image.getBytes());
				
				publication.setRelativeImagePath(rightPathPath);
				publicationRepository.save(publication);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (fos != null) {
						fos.close();
					}
				} catch (IOException ioe) {
					System.out.println("Error while closing stream: " + ioe);
				}

			}	
		} else {
			publication.setRelativeImagePath("");
			publicationRepository.save(publication);
		}
	}
	
}
