package br.edu.bribeiro;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Actions {

	private WebDriver driver;
	private int timeOut;

	public Actions(WebDriver driver, int timeOut) {
		this.driver = driver;
		this.timeOut = timeOut;
	}

	// open and maximize a page
	public void openAndMaxPage(String url) {
		driver.get(url);
		driver.manage().window().maximize();
	}

	// method to get all the links from an page
	public List<WebElement> getAllClickableObjectsFromThisPage() {
		List<WebElement> possibleLinkList = new ArrayList<WebElement>();
		List<WebElement> finalLinkList = new ArrayList<WebElement>();

		try {
			possibleLinkList.addAll(driver.findElements(By.tagName("a")));
			possibleLinkList.addAll(driver.findElements(By.tagName("img")));

			for (WebElement element : possibleLinkList) {
				if ((isClickable(element)) && (element.getAttribute("href") != null)
						&& (!element.getAttribute("href").isEmpty())) {
					finalLinkList.add(element);
				}
			}
		} catch (Exception e) {
			System.out.println("Could no add all <a> or <img> tag objects to the list");
		}
		return finalLinkList;
	}

	// check if the link is Broken doesn't matter if will be redirected or not
	public Boolean isTheLinkInsecure(URL url) {
		String urlProtocol = null;

		try {
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			urlProtocol = connection.getURL().getProtocol();			
			connection.disconnect();
		} catch (Exception e) {
			System.out.println("Could not stablish the connection");
		}
		if (urlProtocol.equalsIgnoreCase("http")) {
			return true;
		} else {
			return false;
		}
	}

	// this check if the element is clickable what means that it is enable and
	// displayed at the same time
	public boolean isClickable(WebElement element) {
		if ((element.isDisplayed()) && (element.isEnabled())) {
			return true;
		} else {
			return false;
		}
	}

}
