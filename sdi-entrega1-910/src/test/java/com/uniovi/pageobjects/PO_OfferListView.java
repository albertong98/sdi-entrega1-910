package com.uniovi.pageobjects;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_OfferListView extends PO_NavView{
	public static void checkEmptyTable(WebDriver driver) {
		List<WebElement> elementos = driver.findElements(By.xpath("//table/tbody/descendant::td"));
		assertTrue(elementos.isEmpty());
	}
}
