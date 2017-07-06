package br.edu.bribeiro;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class ScanningForInsecureContent {

	private String url = "https://www.rakuten.de";

	private WebDriver driver;
	private Actions actions;
	private Util util;
	private int timeOut = 45;
	private List<WebElement> links = new ArrayList<WebElement>();

	@Before
	public void setup() throws Exception {

		// chrome browser set up
		System.setProperty("webdriver.chrome.driver", "/Users/breno.ribeiro/Documents/vip_projects/chromedriver");
		this.driver = new ChromeDriver();

		// setting up a time out
		this.driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
		this.actions = new Actions(driver, timeOut);
		this.util = new Util(actions);

		// open browser and getting all available links
		actions.openAndMaxPage(url);
		System.out.println("Please wait...");
		System.out.println("We are searching for all available links in the page.");
		links = actions.getAllClickableObjectsFromThisPage();
		System.out.println("Total of " + links.size() + " available links found");

	}

	@After
	public void closeBrowser() {
		driver.quit();
	}

	@Test
	public void insecureLinks() {

		List<WebElement> insecureLinks = new ArrayList<WebElement>();
		try {
			insecureLinks = util.gettingInsecureLinks(links);
		} catch (Exception e) {
			System.out.println("Something went wrong at gettingBrokenLinks method");
			e.printStackTrace();
		}
		System.out.println();
		System.out.println("Check below the Insecure Links List. It means, the protocol is still HTTP...");
		util.printLinkList(insecureLinks);
	}

}