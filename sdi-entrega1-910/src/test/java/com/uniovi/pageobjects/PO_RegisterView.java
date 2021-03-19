package com.uniovi.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.uniovi.utils.SeleniumUtils;

public class PO_RegisterView extends PO_View{
	public static void fillForm(WebDriver driver, String emailp, String namep, String lastnamep, String passwordp, String passwordconfp) {
			WebElement email = driver.findElement(By.name("email"));
			email.click();
			email.clear();
			email.sendKeys(emailp);
			WebElement name = driver.findElement(By.name("name"));
			name.click();
			name.clear();
			name.sendKeys(namep);
			WebElement lastname = driver.findElement(By.name("lastName"));
			lastname.click();
			lastname.clear();
			lastname.sendKeys(lastnamep);
			WebElement password = driver.findElement(By.name("password"));
			password.click();
			password.clear();
			password.sendKeys(passwordp);
			WebElement passwordConfirm = driver.findElement(By.name("passwordConfirm"));
			passwordConfirm.click();
			passwordConfirm.clear();
			passwordConfirm.sendKeys(passwordconfp);
			By boton = By.className("btn");
			driver.findElement(boton).click();
	}
	
	public static void checkChangeIdiom(WebDriver driver, String textIdiom1, String textIdiom2, int locale1, int locale2 ) {
		//Esperamos a que se cargue el saludo de bienvenida en Español
		PO_RegisterView.checkText(driver, locale1);
		//Cambiamos a segundo idioma
		PO_NavView.changeIdiom(driver, textIdiom2);
		//COmprobamos que el texto de bienvenida haya cambiado a segundo idioma
		PO_RegisterView.checkText(driver, locale2);
		//Volvemos a Español.
		PO_NavView.changeIdiom(driver, textIdiom1);
		//Esperamos a que se cargue el saludo de bienvenida en Español
		PO_RegisterView.checkText(driver, locale1);
	}
	
	public static void checkText(WebDriver driver, int language) {
		//Esperamos a que se cargue el saludo de bienvenida en Español
		SeleniumUtils.EsperaCargaPagina(driver, "text", p.getString("signup.message",language), getTimeout());
	}

}
