package com.uniovi.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.uniovi.utils.SeleniumUtils;

public class PO_View {
	
	protected static PO_Properties p = new PO_Properties("messages");
	protected static int timeout =2;

	public static int getTimeout() {
		return timeout;
	}

	public static void setTimeout(int timeout) {
		PO_View.timeout = timeout;
	}

	public static PO_Properties getP() {
		return p;
	}

	public static void setP(PO_Properties p) {
		PO_View.p = p;
	}
	
	/**
	 * Espera por la visibilidad de un texto correspondiente a la propiedad key en el idioma locale en la vista actualmente cargandose en driver..
	 * @param driver: apuntando al navegador abierto actualmente.
	 * @param key: clave del archivo de propiedades.
	 * @param locale: Retorna el índice correspondient al idioma. 0 p.SPANISH y 1 p.ENGLISH.
	 * @return Se retornará la lista de elementos resultantes de la búsqueda.
	 */
	static public List<WebElement> checkKey(WebDriver driver, String key, int locale) {
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "text", p.getString(key, locale), getTimeout());
		return elementos;
	}
	/**
	 *  Espera por la visibilidad de un elemento/s en la vista actualmente cargandose en driver..
	 * 
	 * @param driver: apuntando al navegador abierto actualmente.
	 * @param type: 
	 * @param text:
	 * @return Se retornará la lista de elementos resultantes de la búsqueda.
	 */
	static public List<WebElement> checkElement(WebDriver driver, String type, String text) {
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, type, text, getTimeout());
		return elementos;		
	}
	
	public static void checkMessage(WebDriver driver,String message,int language) {
		//Esperamos a que se cargue el saludo de bienvenida en Español
		SeleniumUtils.EsperaCargaPagina(driver, "text", p.getString(message,language), getTimeout());
	}
	
	public static void checkChangeIdiom(WebDriver driver,String message, String textIdiom1, String textIdiom2, int locale1, int locale2 ) {
		//Esperamos a que se cargue el saludo de bienvenida en Español
		PO_UserPrivateView.checkMessage(driver,message,locale1);
		//Cambiamos a segundo idioma
		PO_UserPrivateView.changeIdiom(driver, textIdiom2);
		//COmprobamos que el texto de bienvenida haya cambiado a segundo idioma
		PO_UserPrivateView.checkMessage(driver,message, locale2);
		//Volvemos a Español.
		PO_UserPrivateView.changeIdiom(driver, textIdiom1);
		//Esperamos a que se cargue el saludo de bienvenida en Español
		PO_UserPrivateView.checkMessage(driver,message,locale1);
	}
}
