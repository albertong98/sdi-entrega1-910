package com.uniovi.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.uniovi.utils.SeleniumUtils;

public class PO_LoginView extends PO_NavView{
	public static void fillForm(WebDriver driver, String emailp,  String passwordp) {
		WebElement email = driver.findElement(By.name("username"));
		email.click();
		email.clear();
		email.sendKeys(emailp);
		WebElement password = driver.findElement(By.name("password"));
		password.click();
		password.clear();
		password.sendKeys(passwordp);
		By boton = By.className("btn");
		driver.findElement(boton).click();
	}
	
	public static void checkChangeIdiom(WebDriver driver, String textIdiom1, String textIdiom2, int locale1, int locale2 ) {
		//Esperamos a que se cargue el saludo de bienvenida en Español
		PO_LoginView.checkLogin(driver, locale1);
		//Cambiamos a segundo idioma
		PO_LoginView.changeIdiom(driver, textIdiom2);
		//COmprobamos que el texto de bienvenida haya cambiado a segundo idioma
		PO_LoginView.checkLogin(driver, locale2);
		//Volvemos a Español.
		PO_LoginView.changeIdiom(driver, textIdiom1);
		//Esperamos a que se cargue el saludo de bienvenida en Español
		PO_LoginView.checkLogin(driver, locale1);
	}
	
	public static void checkLogin(WebDriver driver, int language) {
		//Esperamos a que se cargue el saludo de bienvenida en Español
		SeleniumUtils.EsperaCargaPagina(driver, "text", p.getString("login.message",language), getTimeout());
	}
	
}
