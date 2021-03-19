package com.uniovi.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_OfferAddView extends PO_NavView{
	public static void fillForm(WebDriver driver, String titulop,  String descripcionp,String preciop) {
		WebElement  titulo = driver.findElement(By.name("titulo"));
		titulo.click();
		titulo.clear();
		titulo.sendKeys(titulop);
		WebElement descripcion = driver.findElement(By.name("descripcion"));
		descripcion.click();
		descripcion.clear();
		descripcion.sendKeys(descripcionp);
		WebElement precio = driver.findElement(By.name("descripcion"));
		precio.click();
		precio.clear();
		precio.sendKeys(preciop);
		By boton = By.id("send");
		driver.findElement(boton).click();
	}
}
