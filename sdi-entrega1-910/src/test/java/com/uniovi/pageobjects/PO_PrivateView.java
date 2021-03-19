package com.uniovi.pageobjects;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.uniovi.utils.SeleniumUtils;


public class PO_PrivateView extends PO_NavView {
	public static void doSignup(WebDriver driver, String email,String name,String surname,String password,String passwordConfirm) {
		PO_NavView.clickOption(driver, "signup","class", "btn btn-primary");
		
		PO_RegisterView.fillForm(driver, email, name, surname, password, passwordConfirm);
	}
	
	public static void doLogin(WebDriver driver, String email,String password) {
		PO_NavView.clickOption(driver, "login","class", "btn btn-primary");
		
		PO_LoginView.fillForm(driver, email, password);
	}
	
	public static void doLogout(WebDriver driver) {
		PO_PrivateView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	public static void checkChangeIdiom(WebDriver driver, String textIdiom1, String textIdiom2, int locale1, int locale2 ) {
		//Esperamos a que se cargue el saludo de bienvenida en Español
		PO_PrivateView.checkText(driver, locale1);
		//Cambiamos a segundo idioma
		PO_PrivateView.changeIdiom(driver, textIdiom2);
		//COmprobamos que el texto de bienvenida haya cambiado a segundo idioma
		PO_PrivateView.checkText(driver, locale2);
		//Volvemos a Español.
		PO_PrivateView.changeIdiom(driver, textIdiom1);
		//Esperamos a que se cargue el saludo de bienvenida en Español
		PO_PrivateView.checkText(driver, locale1);
	}
	
	public static void checkText(WebDriver driver, int language) {
		//Esperamos a que se cargue el saludo de bienvenida en Español
		SeleniumUtils.EsperaCargaPagina(driver, "text", p.getString("private.message",language), getTimeout());
	}
	
	public static void checkForbidden(WebDriver driver) {
		WebElement element = driver.findElement(By.tagName("h1"));
		assertTrue(element.getText().contains("Forbidden"));
	}
}
