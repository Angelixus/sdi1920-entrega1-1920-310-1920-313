package com.uniovi.validators;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import com.uniovi.entities.Publication;

@Controller
public class CreatePublicationValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Publication.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "error.publication.title.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "text", "error.publication.text.empty");
	}
}
