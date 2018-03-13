package org.gnu.automation.cucumber_bdd_se.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.After;

import java.util.concurrent.TimeUnit;

import org.gnu.automation.cucumber_bdd_se.util.WebDriverConstants;


public class WebDriverFactory {

	private static WebDriver driver;
	private static WebDriverWait driverWait;
	private static int timeWait = 30000;

	public static WebDriver getChromeDriver() {
		if (driver == null) {
			ChromeOptions options = new ChromeOptions();
			System.setProperty("webdriver.chrome.driver", "./lib/chromedriver.exe");
			options.addArguments("start-maximized");
			driver = new ChromeDriver(options);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		return driver;
	}

	public static WebDriver getIEDriver() {
		if (driver == null) {
			System.setProperty("webdriver.ie.driver", "./lib/IEDriverServer.exe");
			DesiredCapabilities desiredCapabilities = DesiredCapabilities.internetExplorer();
			desiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			desiredCapabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
			desiredCapabilities.setCapability("ignoreZoomSetting", true);
			driver = new InternetExplorerDriver(desiredCapabilities);
//			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		return driver;
	}

	public static WebDriverWait getWebDriverWait() {
		if (driverWait == null)
			driverWait = new WebDriverWait(driver, timeWait);
		return driverWait;
	}


	@After
	public static void tearDown() {
		driver.close();
	}

	
}
