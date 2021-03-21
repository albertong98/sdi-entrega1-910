package com.uniovi;


import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.uniovi.pageobjects.*;
import com.uniovi.services.InsertSampleDataService;
import com.uniovi.pageobjects.PO_HomeView;
import com.uniovi.pageobjects.PO_Properties;
import com.uniovi.utils.SeleniumUtils;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.junit4.SpringRunner;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class SdiEntrega1910ApplicationTests {
	@Autowired
	private InsertSampleDataService insertSampleDataService;
	
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
		this.insertSampleDataService.initdb();
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
		
		PO_View.checkElement(driver, "text", "Esta es una zona privada de la web");
		
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
		PO_PrivateView.doSignup(driver, "pedro@email.com", "Pedro", "Perez", "123456","123456");
		
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
		PO_PrivateView.doLogin(driver, "pedro@email.com", "123456");
		
		PO_View.checkElement(driver, "text", "Esta es una zona privada de la web");
		
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
		PO_PrivateView.doLogin(driver, "pedro@email.com", "123456");
		
		PO_NavView.clickOption(driver, "logout", "class", "btn btn-primary");
		
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
		
		PO_NavView.clickMenuOption(driver, "users-menu", "/user/list", "class", "table table-hover");
		
		PO_View.checkElement(driver, "text", "Pedro");
		
		PO_View.checkElement(driver, "text", "Pelayo");
		
		PO_View.checkElement(driver, "text", "María");
		
		PO_View.checkElement(driver, "text", "Lucas");
		
		PO_PrivateView.doLogout(driver);
	}	
	
	//Borrado del primer usuario
	@Test
	public void PR13() {
		PO_PrivateView.doLogin(driver, "admin@email.com", "admin");
		
		PO_NavView.clickMenuOption(driver, "users-menu", "/user/list", "class", "table table-hover");
	
		PO_UserListView.deleteUsers(1, driver);
		
		SeleniumUtils.textoNoPresentePagina(driver, "pedro@email.com");

		PO_PrivateView.doLogout(driver);
	}
	
	//Borrar el último usuario
	@Test
	public void PR14() {
		PO_PrivateView.doLogin(driver, "admin@email.com", "admin");
		
		PO_NavView.clickMenuOption(driver, "users-menu", "/user/list", "class", "table table-hover");
	
		List<WebElement> elementos = driver.findElements(By.xpath("//input[contains(@type,'checkbox')]"));
		
		elementos.get(elementos.size()-1).click();
		
		driver.findElement(By.name("delete")).click();
		
		SeleniumUtils.textoNoPresentePagina(driver, "alber@ovi.com");

		PO_PrivateView.doLogout(driver);
	}
	
	//Borrar multiples usuarios
	@Test
	public void PR15() {
		PO_PrivateView.doLogin(driver, "admin@email.com", "admin");
		
		PO_NavView.clickMenuOption(driver, "users-menu", "/user/list", "class", "table table-hover");
		
		PO_UserListView.deleteUsers(3, driver);
		
		SeleniumUtils.textoNoPresentePagina(driver, "lucas@email.com");

		SeleniumUtils.textoNoPresentePagina(driver, "maria@email.com");

		SeleniumUtils.textoNoPresentePagina(driver, "pedro@email.com");
		
		PO_PrivateView.doLogout(driver);
	}
	
	
	//Formulario oferta
	@Test
	public void PR16() {
		PO_PrivateView.doLogin(driver, "pelayo@email.com", "123456");
		
		PO_NavView.clickMenuOption(driver, "offers-menu", "/offer/add", "class", "form-horizontal");
	
		PO_OfferAddView.fillForm(driver, "Zapatillas", "Zapatillas deportivas", "20");
		
		PO_View.checkElement(driver, "text", "Zapatillas");
		
		PO_PrivateView.doLogout(driver);
	}
	//Formulario oferta con datos invalidos
	@Test
	public void PR17() {
		PO_PrivateView.doLogin(driver, "pelayo@email.com", "123456");
		
		PO_NavView.clickMenuOption(driver, "offers-menu", "/offer/add", "class", "form-horizontal");
		
		PO_OfferAddView.fillForm(driver, " ", "Zapatillas deportivas", "20");
		
		PO_View.checkKey(driver, "Error.empty", PO_Properties.getSPANISH());
		
		PO_PrivateView.doLogout(driver);
	}
	//Comprobar lista de ofertas
	@Test
	public void PR18() {
		PO_PrivateView.doLogin(driver, "pelayo@email.com", "123456");
		
		PO_NavView.clickMenuOption(driver,"offers-menu","offer/list","class","table table-hover");

		PO_View.checkElement(driver, "text", "Zapatos");
		
		PO_View.checkElement(driver, "text", "Vaqueros");
		
		PO_View.checkElement(driver, "text", "Chaqueta");
		
		PO_PrivateView.doLogout(driver);
	}
	//Eliminar primera ofertas
	@Test
	public void PR19() {
		PO_PrivateView.doLogin(driver, "pelayo@email.com", "123456");
		
		PO_NavView.clickMenuOption(driver,"offers-menu","offer/list","class","table table-hover");
		
		PO_OfferListView.checkOfferDeleted(driver, 0);
		
		PO_PrivateView.doLogout(driver);
	}
	//Eliminar última oferta
	@Test
	public void PR20() {
		PO_PrivateView.doLogin(driver, "pelayo@email.com", "123456");
		
		PO_NavView.clickMenuOption(driver,"offers-menu","offer/list","class","table table-hover");
	
		PO_OfferListView.checkLast(driver);
		
		PO_PrivateView.doLogout(driver);
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
	//Busqueda sin resultado
	@Test
	public void PR22() {
		PO_PrivateView.doLogin(driver, "pelayo@email.com", "123456");
		
		PO_NavView.searchOffers(driver, "haherty");
		
		PO_OfferListView.checkEmptyTable(driver);
		
		PO_PrivateView.doLogout(driver);
	}
	//Comprar oferta con saldo suficiente
	@Test
	public void PR23() {
		PO_PrivateView.doLogin(driver, "pelayo@email.com", "123456");
		
		PO_NavView.searchOffers(driver, "");
		
		PO_View.checkElement(driver, "text", "100");
		
		PO_OfferListView.buyOffer(driver, "Coche");
		
		PO_View.checkElement(driver, "text", "90");
		
		PO_PrivateView.doLogout(driver);
	}
	//Comprar una oferta con saldo suficiente y que deje el contador a 0
	@Test
	public void PR24() {
		PO_PrivateView.doLogin(driver, "pelayo@email.com", "123456");
		
		PO_NavView.searchOffers(driver, "");
		
		PO_View.checkElement(driver, "text", "100");
		
		PO_OfferListView.buyOffer(driver, "Joya");
		
		PO_View.checkElement(driver, "text", "0");
		
		PO_PrivateView.doLogout(driver);
	}
	//Intentar comprar una oferta con saldo insuficiente
	@Test
	public void PR25() {
		PO_PrivateView.doLogin(driver, "pelayo@email.com", "123456");
		
		PO_NavView.searchOffers(driver, "");
		
		PO_OfferListView.buyOffer(driver, "Avion");
		
		PO_View.checkKey(driver, "Error.user.balance", PO_Properties.getSPANISH());
		
		PO_PrivateView.doLogout(driver);
	}
	//Comprobar la lista de pedidos
	@Test
	public void PR26() {
		PO_PrivateView.doLogin(driver, "pelayo@email.com", "123456");
		
		PO_NavView.searchOffers(driver, "");
		
		PO_OfferListView.buyOffer(driver, "Coche");

		PO_NavView.searchOffers(driver, "");
		
		PO_OfferListView.buyOffer(driver, "Cama");
		
		PO_NavView.clickMenuOption(driver,"offers-menu","offer/orders","class","table table-hover");
		
		PO_View.checkElement(driver, "text", "Coche");
		
		PO_View.checkElement(driver, "text", "Cama");
		
		PO_PrivateView.doLogout(driver);
	}
	//Internacionalización
	@Test
	public void PR27() {
		PO_PrivateView.doLogin(driver, "admin@email.com", "admin");
		
		PO_HomeView.checkChangeIdiom(driver, "btnSpanish", "btnEnglish", PO_Properties.getSPANISH(),PO_Properties.getENGLISH());
		
		PO_NavView.clickMenuOption(driver,"users-menu","user/profile","class","panel-body");
		
		PO_UserPrivateView.checkUserPrivateIdiom(driver, "btnSpanish", "btnEnglish", PO_Properties.getSPANISH(),PO_Properties.getENGLISH());
		
		PO_NavView.clickMenuOption(driver,"users-menu","user/list","class","table table-hover");
		
		PO_UserListView.checkUserListIdiom(driver, "btnSpanish", "btnEnglish", PO_Properties.getSPANISH(),PO_Properties.getENGLISH());
		
		PO_NavView.clickMenuOption(driver, "offers-menu", "/offer/add", "class", "form-horizontal");
		
		PO_OfferAddView.checkOfferAddIdiom(driver, "btnSpanish", "btnEnglish", PO_Properties.getSPANISH(),PO_Properties.getENGLISH());
	}
	//Acceso denegado a vista del administrador sin autenticar
	@Test
	public void PR28() {
		driver.navigate().to(URL+"/user/list");
		
		PO_View.checkKey(driver, "login.message", PO_Properties.getSPANISH());
	}
	//Acceso denegado a vista de ofertas sin autenticar
	@Test
	public void PR29() {
		driver.navigate().to(URL+"/offer/list");
		
		PO_View.checkKey(driver, "login.message", PO_Properties.getSPANISH());
	}
	//Acceso denegado a vista del administrador con usuario estandar
	@Test
	public void PR30() {
		PO_PrivateView.doLogin(driver, "pelayo@email.com", "123456");
		
		driver.navigate().to(URL+"/user/list");
		
		PO_PrivateView.checkForbidden(driver);
		
		
	}		
	//Abrir una conversación
	@Test
	public void PR31() {
		PO_PrivateView.doLogin(driver, "pelayo@email.com", "123456");
		
		PO_NavView.searchOffers(driver, "");
		
		PO_OfferListView.openChat(driver, "Cama");
		
		PO_ChatView.sendMessage(driver,"hola");
		
		PO_View.checkElement(driver, "text", "hola");
		
		PO_PrivateView.doLogout(driver);
	}
	//Enviar mensaje a conversacion ya abierta
	@Test
	public void PR32() {
		PO_PrivateView.doLogin(driver, "pelayo@email.com", "123456");
		
		PO_NavView.searchOffers(driver, "");
		
		PO_OfferListView.openChat(driver, "Cama");
		
		PO_NavView.clickMenuOption(driver,"conversations-menu","conversation/list","class","table table-hover");
		
		PO_ConversationListView.enterChat(driver,"Cama");
		
		PO_ChatView.sendMessage(driver,"adios");
		
		PO_View.checkElement(driver, "text", "adios");
		
		PO_PrivateView.doLogout(driver);
	}
	//comprobar que se muestra el listado de conversaciones
	@Test
	public void PR33() {
		PO_PrivateView.doLogin(driver, "pelayo@email.com", "123456");
		
		PO_NavView.searchOffers(driver, "");
		
		PO_OfferListView.openChat(driver, "Cama");
		
		PO_NavView.searchOffers(driver, "");
		
		PO_OfferListView.openChat(driver, "Coche");
		
		PO_NavView.clickMenuOption(driver,"conversations-menu","conversation/list","class","table table-hover");
		
		PO_View.checkElement(driver, "text", "Cama");
		
		PO_View.checkElement(driver, "text", "Coche");
		
		PO_PrivateView.doLogout(driver);
	}
	//Eliminar primera conversacion
	@Test
	public void PR34() {
		PO_PrivateView.doLogin(driver, "pelayo@email.com", "123456");
		
		PO_NavView.searchOffers(driver, "");
		
		PO_OfferListView.openChat(driver, "Cama");
		
		PO_NavView.searchOffers(driver, "");
		
		PO_OfferListView.openChat(driver, "Coche");
		
		PO_NavView.clickMenuOption(driver,"conversations-menu","conversation/list","class","table table-hover");
		
		PO_ConversationListView.deleteChat(driver,"Cama");
		
		SeleniumUtils.textoNoPresentePagina(driver, "Cama");
		
		PO_PrivateView.doLogout(driver);
	}
	
	//Eliminar ultima conversacion
	@Test
	public void PR35() {
		PO_PrivateView.doLogin(driver, "pelayo@email.com", "123456");
		
		PO_NavView.searchOffers(driver, "");
		
		PO_OfferListView.openChat(driver, "Cama");
		
		PO_NavView.searchOffers(driver, "");
		
		PO_OfferListView.openChat(driver, "Coche");
		
		PO_NavView.clickMenuOption(driver,"conversations-menu","conversation/list","class","table table-hover");
		
		PO_ConversationListView.deleteChat(driver,"Coche");
		
		SeleniumUtils.textoNoPresentePagina(driver, "Coche");
		
		PO_PrivateView.doLogout(driver);
	}
}
