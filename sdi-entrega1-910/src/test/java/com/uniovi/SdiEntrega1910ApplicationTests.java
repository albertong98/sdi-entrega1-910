package com.uniovi;


import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import com.uniovi.pageobjects.*;
import com.uniovi.utils.SeleniumUtils;

@SpringBootTest
class SdiEntrega1910ApplicationTests {

	@Test
	void contextLoads() {
	}
	//En Windows (Debe ser la versión 65.0.1 y desactivar las actualizacioens automáticas)):
		static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
		static String Geckdriver024 = "C:\\Users\\alber\\Downloads\\PL-SDI-Sesión5-material\\PL-SDI-Sesión5-material\\geckodriver024win64.exe";
		
		//En MACOSX (Debe ser la versión 65.0.1 y desactivar las actualizacioens automáticas):
		//static String PathFirefox65 = "/Applications/Firefox.app/Contents/MacOS/firefox-bin";
		//static String Geckdriver024 = "/Users/delacal/selenium/geckodriver024mac";
		
		//Común a Windows y a MACOSX
		static WebDriver driver = getDriver(PathFirefox65, Geckdriver024);
		static String URL = "http://localhost:8090";
		
		public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
			System.setProperty("webdriver.firefox.bin", PathFirefox);
			System.setProperty("webdriver.gecko.driver", Geckdriver);
			WebDriver driver = new FirefoxDriver();
			return driver;
		}
		
		@Before
		public void setUp() throws Exception {	
			driver.navigate().to(URL);
		}

		@After
		public void tearDown() throws Exception {
			driver.manage().deleteAllCookies();
		}
		
		@BeforeClass
		static public void begin() {
		}

		// Al finalizar la última prueba
		@AfterClass
		static public void end() {
			// Cerramos el navegador al finalizar las pruebas
			driver.quit();
		}
		//Registro con datos validos
		@Test
		public void PR01() {
			PO_PrivateView.doSignup(driver, "alber@ovi.com", "Alberto", "Martinez", "123456","123456");
			
			PO_View.checkElement(driver, "text", "Perfil");
			
			PO_PrivateView.clickOption(driver, "logout", "class", "btn btn-primary");
		}
		
		//Registro de usuario con datos invalidos (email no valido)
		@Test
		public void PR02() {
			PO_PrivateView.doSignup(driver, "a", "Alberto", "Martinez", "123456","123456");
			
			PO_RegisterView.checkKey(driver, "Error.signup.email.length", PO_Properties.getSPANISH());
		}
		
		//Registro de usuario con datos invalidos (repeticion invalida)
		@Test
		public void PR03() {
			PO_PrivateView.doSignup(driver, "anton@ovi.com", "Antonio", "Martinez", "123456","123458");
			
			PO_RegisterView.checkKey(driver, "Error.signup.passwordConfirm.coincidence", PO_Properties.getSPANISH());
		}
		//Registro de usuario con datos invalidos (email repetido)
		@Test
		public void PR04() {
			PO_PrivateView.doSignup(driver, "alber@ovi.com", "Alberto", "Perez", "123456","123456");
			
			PO_RegisterView.checkKey(driver, "Error.signup.email.duplicate", PO_Properties.getSPANISH());
		}
		
		//Inicio sesion con datos valido(usuario administrador)
		@Test
		public void PR05() {
			PO_PrivateView.doLogin(driver, "admin@email.com", "admin");
			
			PO_View.checkElement(driver, "text", "admin");
			
			PO_PrivateView.doLogout(driver);
		}
		
		//Inicio sesion con datos valido(usuario estandar)
		@Test
		public void PR06() {
			PO_PrivateView.doLogin(driver, "alber@ovi.com", "123456");
			
			PO_View.checkElement(driver, "text", "Perfil de usuario");
			
			PO_PrivateView.clickOption(driver, "logout", "class", "btn btn-primary");
		}
		
		//Inicio sesion con datos invalido(usuario estandar)
		@Test
		public void PR07() {
			PO_PrivateView.doLogin(driver, "", "");
		
			PO_View.checkElement(driver, "text", "Identifícate");
		}
		//Inicio sesion con datos invalidos contraseña incorrecta(usuario estandar)
		@Test
		public void PR08() {
			PO_PrivateView.doLogin(driver, "alber@ovi.com", "123");
			
			PO_View.checkElement(driver, "text", "Identifícate");
		}
		//Inicio de sesión invalido email inexistente
		@Test
		public void PR09() {
			PO_PrivateView.doLogin(driver, "burgun@ovi.com", "123");
			
			PO_View.checkElement(driver, "text", "Identifícate");
		}
		//Desconectarse y comprobar que nos devuelve al login
		@Test
		public void PR10() {
			PO_PrivateView.doLogin(driver, "alber@ovi.com", "123456");
			
			PO_PrivateView.clickOption(driver, "logout", "class", "btn btn-primary");
			
			PO_View.checkElement(driver, "text", "Identifícate");
		}
		
		//No poder hacer logout siendo usuario sin registrar
		@Test
		public void PR11() {
			SeleniumUtils.textoNoPresentePagina(driver, "Desconectar");
		}
		//Mostrar listado de usuarios
		@Test
		public void PR12() {
			PO_PrivateView.doLogin(driver, "admin@email.com", "admin");
			
			PO_NavView.clickOption(driver,"user/list","class","table table-hover");
		
			PO_View.checkElement(driver, "text", "Pedro");
			
			PO_View.checkElement(driver, "text", "Pelayo");
			
			PO_View.checkElement(driver, "text", "Paco");
			
			PO_View.checkElement(driver, "text", "María");
			
			PO_View.checkElement(driver, "text", "Lucas");
			
			PO_PrivateView.doLogout(driver);
		}	
		
		//Borrado del primer usuario
		@Test
		public void PR13() {
			PO_PrivateView.doLogin(driver, "admin@email.com", "admin");
			
			PO_NavView.clickOption(driver,"user/list","class","table table-hover");
		
			PO_UserListView.deleteUsers(1, driver);
			
			SeleniumUtils.textoNoPresentePagina(driver, "pedro@email.com");

			PO_PrivateView.doLogout(driver);
		}
		
		//Borrar el último usuario
		@Test
		public void PR14() {
			PO_PrivateView.doLogin(driver, "admin@email.com", "admin");
			
			PO_NavView.clickOption(driver,"user/list","class","table table-hover");
		
			List<WebElement> elementos = driver.findElements(By.xpath("//input[@type='checkbox'"));
			
			elementos.get(elementos.size()-1).click();
			
			driver.findElement(By.name("delete")).click();
			
			SeleniumUtils.textoNoPresentePagina(driver, "alber@ovi.com");

			PO_PrivateView.doLogout(driver);
		}
		
		//Borrar multiples usuarios
		@Test
		public void PR15() {
			PO_PrivateView.doLogin(driver, "admin@email.com", "admin");
			
			PO_NavView.clickOption(driver,"user/list","class","table table-hover");
		
			PO_UserListView.deleteUsers(3, driver);
			
			SeleniumUtils.textoNoPresentePagina(driver, "lucas@email.com");

			SeleniumUtils.textoNoPresentePagina(driver, "maria@email.com");

			SeleniumUtils.textoNoPresentePagina(driver, "marta@email.com");
			
			PO_PrivateView.doLogout(driver);
		}
		
		
		//Formulario oferta
		@Test
		public void PR16() {
			PO_PrivateView.doLogin(driver, "pelayo@email.com", "123456");
			
			PO_NavView.clickOption(driver,"offer/add","class","form-horizontal");
		
			PO_OfferAddView.fillForm(driver, "Zapatillas", "Zapatillas deportivas", "20");
			
			SeleniumUtils.textoPresentePagina(driver, "Zapatillas");
			
			PO_PrivateView.doLogout(driver);
		}
		//Formulario oferta con datos invalidos
		@Test
		public void PR17() {
			PO_PrivateView.doLogin(driver, "pelayo@email.com", "123456");
			
			PO_NavView.clickOption(driver,"offer/add","class","form-horizontal");
		
			PO_OfferAddView.fillForm(driver, "", "Zapatillas deportivas", "20");
			
			PO_View.checkKey(driver, "Error.offer.title", PO_Properties.getSPANISH());
			
			PO_PrivateView.doLogout(driver);
		}
		//Comprobar lista de ofertas
		@Test
		public void PR18() {
			PO_PrivateView.doLogin(driver, "pelayo@email.com", "123456");
			
			PO_NavView.clickOption(driver,"offer/list","class","table table-hover");
		
			PO_View.checkElement(driver, "text", "Zapatillas deportivas");
			//TODO: Añadir mas ofertas.
			
			PO_PrivateView.doLogout(driver);
		}
		//Eliminar primera ofertas
		@Test
		public void PR19() {
			PO_PrivateView.doLogin(driver, "pelayo@email.com", "123456");
			
			PO_NavView.clickOption(driver,"offer/list","class","table table-hover");
		
			List<WebElement> elementos = PO_View.checkElement(driver, "free","//td[contains(text(), 'Zapatillas')]/following-sibling::*/a[contains(@href, 'offer/delete')]");
			
			elementos.get(0).click();
			
			SeleniumUtils.textoNoPresentePagina(driver, "Zapatillas");
			
			PO_PrivateView.doLogout(driver);
		}
		//Eliminar última oferta
		@Test
		public void PR20() {
			
		}
		
		//Busqueda vacía
		@Test
		public void PR21() {
			PO_PrivateView.doLogin(driver, "pelayo@email.com", "123456");
			
			PO_NavView.searchOffers(driver, "");
			
			PO_View.checkElement(driver, "text", "coche");
			
			PO_View.checkElement(driver, "text", "cama");

			PO_PrivateView.doLogout(driver);
		}
		
		@Test
		public void PR22() {
			PO_PrivateView.doLogin(driver, "pelayo@email.com", "123456");
			
			PO_NavView.searchOffers(driver, "haherty");
			
			PO_OfferListView.checkEmptyTable(driver);
			
			PO_PrivateView.doLogout(driver);
		}
		
		
}
