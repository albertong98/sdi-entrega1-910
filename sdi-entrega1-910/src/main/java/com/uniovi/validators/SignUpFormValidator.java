package com.uniovi.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.uniovi.entities.User;
import com.uniovi.services.UsersService;

@Component
public class SignUpFormValidator implements Validator{
	@Autowired
	private UsersService usersService;
	
	Logger logger = LoggerFactory.getLogger(SignUpFormValidator.class);
	
	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Error.empty");
		if (user.getEmail().length() < 5) {
			errors.rejectValue("email", "Error.signup.email.length");
			logger.error("Email length not valid");
		}
		if(user.getEmail().split("@").length < 2 && (user.getEmail().split("@")[1].endsWith(".com") || user.getEmail().split("@")[1].endsWith(".es"))) {
			errors.rejectValue("email", "Error.signup.email.format");
			logger.error("Email format not valid");
		}
		if (usersService.getUserByEmail(user.getEmail()) != null) {
			errors.rejectValue("email", "Error.signup.email.duplicate");
			logger.error("Email already exists");
		}
		if (user.getName().length() < 5 || user.getName().length() > 24) {
			errors.rejectValue("name", "Error.signup.name.length");
			logger.error("Name length not valid");
		}
		if (user.getLastName().length() < 5 || user.getLastName().length() > 24) {
			errors.rejectValue("lastName", "Error.signup.lastName.length");
			logger.error("Last name length not valid");
		}
		if (user.getPassword().length() < 5 || user.getPassword().length() > 24) {
			errors.rejectValue("password", "Error.signup.password.length");
			logger.error("Password length not valid");
		}
		if (!user.getPasswordConfirm().equals(user.getPassword())) {
			errors.rejectValue("passwordConfirm", "Error.signup.passwordConfirm.coincidence");
			logger.error("The passwords are different");
		}
	}
}
