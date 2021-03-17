package com.uniovi.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.uniovi.entities.Offer;

@Component
public class OfferAddValidator implements Validator {

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
		}
		if (offer.getDescripcion().length() < 5) {
			errors.rejectValue("descripcion", "Error.offer.description");
		}
		if (offer.getPrecio() < 1) {
			errors.rejectValue("precio", "Error.offer.precio");
		}
	}

}
