package org.gnu.automation.cucumber_bdd_se.util;

import java.util.HashMap;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WebDriverBrowserCapabilities {

	private DesiredCapabilities desiredCapabilities;
//	private PropertiesConfig properties;

	public DesiredCapabilities getConfigChrome() {
//		System.setProperty("webdriver.chrome.driver", properties.getProperty("webdriver.chrome.driver"));

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--use-fake-ui-for-media-stream=true");
		options.addArguments("--enable-media-stream");
		options.addArguments("--enable-peer-connection");
		options.addArguments("--disable-web-security chrome://version/");
		options.addArguments("--start-maximized");
		options.addArguments("--disable-extensions");
		
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("download.prompt_for_download", true);
		options.setExperimentalOption("prefs", chromePrefs);
		options.addArguments("--test-type");

		this.desiredCapabilities = DesiredCapabilities.chrome();
		this.desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, options);

		return desiredCapabilities;
	}

	public DesiredCapabilities getConfigIE() {
//		System.setProperty("webdriver.ie.driver", properties.getProperty("webdriver.ie.driver"));
		
		desiredCapabilities = DesiredCapabilities.internetExplorer();
		desiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		desiredCapabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
		return desiredCapabilities;
	}

}
