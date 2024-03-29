package com.uniovi.pageobjects;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.uniovi.utils.SeleniumUtils;

public class PO_OfferListView extends PO_NavView{
	public static void checkEmptyTable(WebDriver driver) {
		List<WebElement> elementos = driver.findElements(By.xpath("//table/tbody/descendant::td"));
		assertTrue(elementos.isEmpty());
	}
	
	public static void buyOffer(WebDriver driver,String texto) {
		List<WebElement> elementos = PO_View.checkElement(driver, "free","//td[contains(text(), '"+texto+"')]/following-sibling::*/a[contains(@href, 'offer/buy')]");
		elementos.get(0).click();
	}
	
	public static void openChat(WebDriver driver,String texto) {
		List<WebElement> elementos = PO_View.checkElement(driver, "free","//td[contains(text(), '"+texto+"')]/following-sibling::*/a[contains(@href, 'conversation/create')]");
		elementos.get(0).click();
	}
	public static void checkOfferDeleted(WebDriver driver, int offerIndex) {
		List<WebElement> elementos = PO_View.checkElement(driver, "free","//td");
		String texto = elementos.get(offerIndex*4).getText();
		elementos = PO_View.checkElement(driver, "free","//td[contains(text(), '')]/following-sibling::*/a[contains(@href, 'offer/delete')]");
		elementos.get(offerIndex).click();
		SeleniumUtils.textoNoPresentePagina(driver, texto);
	}
	
	public static void checkLast(WebDriver driver) {
		int i = PO_View.checkElement(driver, "free","//tr").size() - 2;
		checkOfferDeleted(driver,i);
	}
}
