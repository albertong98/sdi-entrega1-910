package com.uniovi.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.uniovi.entities.Offer;
import com.uniovi.services.RolesService;

@Component
public class OfferAddValidator implements Validator {
	@Autowired
	private RolesService rolesService;
	
	Logger logger = LoggerFactory.getLogger(OfferAddValidator.class);
	@Override
	public boolean supports(Class<?> clazz) {
		return Offer.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Offer offer = (Offer) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "titulo", "Error.empty");
		if (offer.getTitulo().length() < 3) {
			errors.rejectValue("titulo", "Error.offer.title");
			logger.error("Offer title not valid");
		}
		if (offer.getDescripcion().length() < 5) {
			errors.rejectValue("descripcion", "Error.offer.description");
			logger.error("Offer description not valid");
		}
		if (offer.getPrecio() < 1) {
			errors.rejectValue("precio", "Error.offer.precio");
			logger.error("Offer price not valid");
		}
		if(offer.getSeller().getRole().equals(rolesService.getRoles()[1])) {
			errors.rejectValue("seller","Error.admin.add");
			logger.error("Admin tried to upload offer");
		}
	}

}
