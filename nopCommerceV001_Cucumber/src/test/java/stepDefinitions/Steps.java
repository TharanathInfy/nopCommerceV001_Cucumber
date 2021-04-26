package stepDefinitions;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import pageObjects.AddCustomerPage;
import pageObjects.LoginPage;
import pageObjects.SearchCustomerPage;

public class Steps extends BaseClass {
	
	@Before
	public void setup() throws IOException {
		configProp= new Properties();
		FileInputStream configPropFile= new FileInputStream("config.properties");
		configProp.load(configPropFile);
		
		logger=Logger.getLogger("nopCommerce");
		PropertyConfigurator.configure("log4j.properties");
		
		String br = configProp.getProperty("browser");
		
		if (br.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver",configProp.getProperty("chromepath") );
			driver = new ChromeDriver();			
		}
		
		else if (br.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver",configProp.getProperty("firefoxpath") );
			driver = new FirefoxDriver();			
		}

		else if (br.equals("ie")) {
			System.setProperty("webdriver.ie.driver",configProp.getProperty("iepath") );
			driver = new InternetExplorerDriver();			
		}
		
		/* System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//Drivers/chromedriver.exe");
		driver = new ChromeDriver(); */

		
		logger.info("****************** launching browser***************");
	}
	
	@Given("User Launch Chrome browser")
	public void user_Launch_Chrome_browser() {

	   lp = new LoginPage(driver);
	   
	}

	@When("User opens URL {string}")
	public void user_opens_URL(String url) {
		logger.info("****************** launching url***************");
		driver.get(url);
	    driver.manage().window().maximize();
	}

	@When("User enters Email as {string} and Password as {string}")
	public void user_enters_Email_as_and_Password_as(String email, String pwd) {
		logger.info("****************** providing user details**************");
		lp.setUserName(email);
		lp.setPassword(pwd);
	    
	}

	@When("Click on Login")
	public void click_on_Login() throws InterruptedException {
		logger.info("****************** started login***************");
	    lp.clickLogin();
	    Thread.sleep(3000);
	}

	@Then("Page Title should be {string}")
	public void page_Title_should_be(String title) {
		if (driver.getPageSource().contains("Login was unsuccessful.")) {
			driver.close();
			logger.info("****************** login passed***************");
			Assert.assertTrue(false);
		} else {
			logger.info("****************** login failed***************");
			Assert.assertEquals(title, driver.getTitle());
		}
			
	  
	}

	@When("User click on Log out link")
	public void user_click_on_Log_out_link() throws InterruptedException {
		logger.info("****************** clcik on logout ***************");
		lp.clickLogout();
		Thread.sleep(3000);
	    
	}

	@Then("close browser")
	public void close_browser() {
		driver.quit();
	    
	}
	
	//Steps for Add Customer
	
	@Then("User can view Dashboard")
	public void user_can_view_Dashboard() throws InterruptedException {
		Thread.sleep(5000);
		addCust = new AddCustomerPage(driver);
		Assert.assertEquals("Dashboard / nopCommerce administration", addCust.getPageTitle());
	    
	}

	@When("User click on customers Menu")
	public void user_click_on_customers_Menu() throws InterruptedException {
		Thread.sleep(3000);
		addCust.clickOnCustomersMenu();
	}

	@When("click on customers Menu Item")
	public void click_on_customers_Menu_Item() throws InterruptedException {
		Thread.sleep(2000);
		addCust.clickOnCustomersMenuItem();
	}

	@When("click on Add new button")
	public void click_on_Add_new_button() throws InterruptedException {
		addCust.clickOnAddnew();
		Thread.sleep(2000);
	    
	}

	@Then("User can view Add new customer page")
	public void user_can_view_Add_new_customer_page() {
		Assert.assertEquals("Add a new customer / nopCommerce administration", addCust.getPageTitle());
	}

	@When("User enter customer info")
	public void user_enter_customer_info() throws InterruptedException {
		String email = randomestring()+"@gmail.com";
		addCust.setEmail(email);
		addCust.setPassword("test123");
		// Registered - default
        // The customer cannot be in both 'Guests' and 'Registered' customer roles
        // Add the customer to 'Guests' or 'Registered' customer role
        addCust.setCustomerRoles("Guest");
        Thread.sleep(3000);

        addCust.setManagerOfVendor("Vendor 2");
        addCust.setGender("Male");
        addCust.setFirstName("Pavan");
        addCust.setLastName("Kumar");
        addCust.setDob("7/05/1985"); // Format: D/MM/YYY
        addCust.setCompanyName("busyQA");
        addCust.setAdminContent("This is for testing.........");
	}

	@When("click on Save button")
	public void click_on_Save_button() throws InterruptedException {
		addCust.clickOnSave();
		Thread.sleep(3000);
	}

	@Then("User can view confirmation message {string}")
	public void user_can_view_confirmation_message(String msg) {
		Assert.assertTrue(driver.findElement(By.tagName("body")).getText()
                .contains("The new customer has been added successfully"));
	    
	}

	 //Searching customers using EMail ID ...................................

    @When("Enter customer EMail")
    public void enter_customer_EMail() {
        //logger.info("********* Search Customer by Email ID Scenario started ***************");
        searchCust=new SearchCustomerPage(driver);
        searchCust.setEmail("victoria_victoria@nopCommerce.com");
    }

    @When("Click on search button")
    public void click_on_search_button() throws InterruptedException
    {
        searchCust.clickSearch();
        Thread.sleep(3000);
    }

    @Then("User should found Email in the Search table")
    public void user_should_found_Email_in_the_Search_table() {
        //logger.info("********* Search customer by email validation ***************");
        boolean status=searchCust.searchCustomerByEmail("victoria_victoria@nopCommerce.com");
        Assert.assertEquals(true, status);
    }

    //Searching customers using Name ...................................
    @When("Enter customer FirstName")
    public void enter_customer_FirstName() {
        //logger.info("********* Seqarch custoemr by Name Scenario started ***************");
        searchCust=new SearchCustomerPage(driver);
        searchCust.setFirstName("Victoria");
    }

    @When("Enter customer LastName")
    public void enter_customer_LastName() {
        //logger.info("********* Providing customer name ***************");

        searchCust.setLastName("Terces");
    }

    @Then("User should found Name in the Search table")
    public void user_should_found_Name_in_the_Search_table() {
        //logger.info("********* Search customer by name validation ***************");

        boolean status=searchCust.searchCustomerByName("Victoria Terces");
        Assert.assertEquals(true, status);
    }
	
}
