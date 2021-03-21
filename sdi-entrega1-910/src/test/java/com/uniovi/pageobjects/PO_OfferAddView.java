package com.uniovi.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_OfferAddView extends PO_NavView{
	public static void fillForm(WebDriver driver, String titulop,  String descripcionp, String preciop) {
		WebElement  titulo = driver.findElement(By.name("titulo"));
		titulo.click();
		titulo.clear();
		titulo.sendKeys(titulop);
		WebElement descripcion = driver.findElement(By.name("descripcion"));
		descripcion.click();
		descripcion.clear();
		descripcion.sendKeys(descripcionp);
		WebElement precio = driver.findElement(By.name("precio"));
		precio.click();
		precio.clear();
		precio.sendKeys(preciop);
		By boton = By.id("send");
		driver.findElement(boton).click();
	}
	
	public static void checkOfferAddIdiom(WebDriver driver, String textIdiom1, String textIdiom2, int locale1, int locale2) {
		PO_View.checkChangeIdiom(driver, "offer.add.header", textIdiom1, textIdiom2, locale1, locale2);
	}
}
