package com.uniovi.validators;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.uniovi.entities.User;
import com.uniovi.services.UserService;

@Controller
public class SignUpFormValidator implements Validator {

	@Autowired
	private UserService userService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User toValidate = (User) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.email.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.name.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "error.lastName.empty");
		
		final Pattern pattern = Pattern.compile("[^\\s]+@[^\\s]+\\.[^\\s]+");
		if(!pattern.matcher(toValidate.getEmail()).matches())
			errors.rejectValue("email", "error.signup.email.format");
		
		if(userService.getUserByEmail(toValidate.getEmail()) != null)
			errors.rejectValue("email", "error.signup.email.duplicate");
		
		if(toValidate.getName().length() < 3 || toValidate.getName().length() > 24)
			errors.rejectValue("name", "error.signup.name.length");
		
		if(toValidate.getLastName().length() < 3 || toValidate.getLastName().length() > 24)
			errors.rejectValue("lastName", "error.signup.lastname.length");
		
		if(toValidate.getPassword().length() < 5 || toValidate.getPassword().length() > 24)
			errors.rejectValue("password", "error.signup.password.length");
			
		if(!toValidate.getPasswordConfirm().equals(toValidate.getPassword()))
			errors.rejectValue("passwordConfirm", "error.signup.passwordConfirm.coincidence");
	}

}
