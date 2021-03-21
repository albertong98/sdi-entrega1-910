package com.uniovi.pageobjects;

import org.openqa.selenium.WebDriver;

import com.uniovi.utils.SeleniumUtils;

public class PO_UserPrivateView extends PO_NavView{
	public static void checkUserPrivateIdiom(WebDriver driver, String textIdiom1, String textIdiom2, int locale1, int locale2 ) {
		PO_View.checkChangeIdiom(driver, "user.profile.details", textIdiom1, textIdiom2, locale1, locale2);
	}
}
