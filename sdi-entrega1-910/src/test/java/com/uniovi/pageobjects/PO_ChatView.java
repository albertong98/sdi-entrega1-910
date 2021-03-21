package com.uniovi.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_ChatView extends PO_NavView {
	public static void sendMessage(WebDriver driver,String contentp) {
		WebElement content = driver.findElement(By.name("content"));
		content.click();
		content.clear();
		content.sendKeys(contentp);
		By boton = By.name("send");
		driver.findElement(boton).click();
	}
}
