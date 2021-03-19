package com.uniovi.pageobjects;

import static org.junit.Assert.assertTrue;
import java.util.List;
import org.openqa.selenium.*;
import com.uniovi.utils.SeleniumUtils;

public class PO_NavView extends PO_View {
	/**
	* CLicka una de las opciones principales (a href) y comprueba que se vaya a la vista
	con el elemento de tipo type con el texto Destino
	* @param driver: apuntando al navegador abierto actualmente.
	* @param textOption: Texto de la opción principal.
	* @param criterio: "id" or "class" or "text" or "@attribute" or "free". Si el valor de
	criterio es free es una expresion xpath completa.
	* @param textoDestino: texto correspondiente a la búsqueda de la página destino.
	*/
	public static void clickOption(WebDriver driver, String textOption, String criterio, String textoDestino) {
		//Clickamos en la opción de registro y esperamos a que se cargue el enlace de registro.
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "@href", textOption, getTimeout());
		assertTrue(elementos.size() > 0);
		//Ahora lo clickamos
		elementos.get(0).click();
		//Esperamos a que sea visible un elemento concreto
		elementos = SeleniumUtils.EsperaCargaPagina(driver, criterio, textoDestino, getTimeout());
		
		assertTrue(elementos.size()>0);
	}
	
	public static void changeIdiom(WebDriver driver,String textLanguage) {
		//clickamos la opcion idioma
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "btnLanguage", getTimeout());
		elementos.get(0).click();
		//Esperamos a que aparezca el menu opciones
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "languageDropdownMenuButton", getTimeout());
		//SeleniumUtils.esperarSegundos(driver, 2);
		//Clickamos la opción Ingles partiendo de la opcion Español
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", textLanguage, getTimeout());
		elementos.get(0).click();
	}
	
	public static void searchOffers(WebDriver driver,String searchTextp) {
		WebElement searchText = driver.findElement(By.name("searchText"));
		searchText.click();
		searchText.clear();
		searchText.sendKeys(searchTextp);
		By boton = By.className("btn");
		driver.findElement(boton).click();
	}
}
