package com.uniovi.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.uniovi.utils.SeleniumUtils;

public class PO_UserListView extends PO_NavView{
	public static void checkChangeIdiom(WebDriver driver, String textIdiom1, String textIdiom2, int locale1, int locale2 ) {
		//Esperamos a que se cargue el saludo de bienvenida en Español
		PO_UserListView.checkText(driver, locale1);
		//Cambiamos a segundo idioma
		PO_UserListView.changeIdiom(driver, textIdiom2);
		//COmprobamos que el texto de bienvenida haya cambiado a segundo idioma
		PO_UserListView.checkText(driver, locale2);
		//Volvemos a Español.
		PO_UserListView.changeIdiom(driver, textIdiom1);
		//Esperamos a que se cargue el saludo de bienvenida en Español
		PO_UserListView.checkText(driver, locale1);
	}
	
	public static void checkText(WebDriver driver, int language) {
		//Esperamos a que se cargue el saludo de bienvenida en Español
		SeleniumUtils.EsperaCargaPagina(driver, "text", p.getString("users.message",language), getTimeout());
	}
	
	
	public static void deleteUsers(int users,WebDriver driver) {
		List<WebElement> elementos = driver.findElements(By.xpath("//input[contains(@type,'checkbox')]"));
		for(int i=0;i<users;i++) 
			elementos.get(i).click();
		
		driver.findElement(By.name("delete")).click();
	}
	
	public static void checkUserListIdiom(WebDriver driver, String textIdiom1, String textIdiom2, int locale1, int locale2) {
		PO_View.checkChangeIdiom(driver, "user.list.description", textIdiom1, textIdiom2, locale1, locale2);
	}
}
