package com.uniovi.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_ConversationListView extends PO_NavView{
	public static void enterChat(WebDriver driver,String email) {
		List<WebElement> elementos = PO_View.checkElement(driver, "free","//td[contains(text(), '"+email+"')]/following-sibling::*/a[contains(@href, 'conversation')]");
		elementos.get(0).click();
	}
	
	public static void deleteChat(WebDriver driver,String email) {
		List<WebElement> elementos = PO_View.checkElement(driver, "free","//td[contains(text(), '"+email+"')]/following-sibling::*/a[contains(@href, 'conversation/delete')]");
		elementos.get(0).click();
	}
}
