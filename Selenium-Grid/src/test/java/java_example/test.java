package java_example;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class test {

	private static final String VIDEO_URL = null;
	private RemoteWebDriver driver;

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() throws MalformedURLException {

		// Example test environment. NOTE: Gridlastic auto scaling requires all
		// these 3 environment variables in each request.
		String platformName = "win7";
		String browserName = "";
	//	String browserName = "chrome";
	//	String browserName = "firefox";
//		String browser_version = "41"; // for Chrome leave empty
		String browserVersion = ""; // for Chrome leave empty

		// optional video recording
	 String record_video = "True";
//		String record_video = "False";

		DesiredCapabilities capabilities = new DesiredCapabilities();
		if ("win7".equalsIgnoreCase(platformName)) {
			capabilities.setPlatform(Platform.VISTA);
		}
		if ("win8".equalsIgnoreCase(platformName)) {
			capabilities.setPlatform(Platform.WIN8);
		}
		if ("win8_1".equalsIgnoreCase(platformName)) {
			capabilities.setPlatform(Platform.WIN8_1);
		}
		if ("linux".equalsIgnoreCase(platformName)) {
			capabilities.setPlatform(Platform.LINUX);
		}
		capabilities.setBrowserName(browserName);
		capabilities.setVersion(browserVersion);

		// video record
		if ("True".equalsIgnoreCase(record_video)) {
			capabilities.setCapability("video", "True");
		} else {
			capabilities.setCapability("video", "False");
		}

		if ("chrome".equalsIgnoreCase(browserName)) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments(Arrays.asList("--start-maximized"));
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		}
		/*
		 * 	driver = new RemoteWebDriver(
		 *	new URL(
		 *	"http://USERNAME:ACCESS_KEY@SUBDOMAIN.gridlastic.com:4444/wd/hub"),
		 *	capabilities);
		 * 
		 */
		driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		if ("True".equalsIgnoreCase(record_video)) {
			System.out.println("Test Video: " + VIDEO_URL + ((RemoteWebDriver) driver).getSessionId());
		}
	}

	@Test(enabled = true)
	public void test_google() {
		driver.get("http://www.java.com");
		driver.findElementByLinkText("About Java").click();
		driver.findElementByLinkText("Troubleshoot Java").click();
		driver.findElementByLinkText("Support");
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		driver.quit();
	}

}
