package Test;

import org.testng.annotations.Test;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.zaproxy.clientapi.core.ApiResponse;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ZAPSecurityTest {

	static final String ZAP_PROXY_ADDRESS = "localhost";
	static final int ZAP_PROXY_PORT = 8080;
	static final String ZAP_API_KEY = "irmje6k7mttb7dlqmvfvoeqvij";

	private WebDriver driver;
	private ClientApi api;

	@BeforeMethod
	public void setup() {
		String proxyServerURL = ZAP_PROXY_ADDRESS + ":" + ZAP_PROXY_PORT;

		Proxy proxy1 = new Proxy();
		proxy1.setHttpProxy(proxyServerURL);
		proxy1.setSslProxy(proxyServerURL);
		
		 DesiredCapabilities capabilities = DesiredCapabilities.chrome();
	        capabilities.setCapability(CapabilityType.PROXY, proxy1);
	        //capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
	        capabilities.setCapability("acceptInsecureCerts", true);
	        
		//ChromeOptions co = new ChromeOptions();
		//co.setProxy(proxy1);
		WebDriverManager.chromedriver().setup();
       
		driver = new ChromeDriver(capabilities);

		api = new ClientApi(ZAP_PROXY_ADDRESS, ZAP_PROXY_PORT, ZAP_API_KEY);
	}

	@Test(priority = 0)
	public void amazonSecurityTest() {
		 //Thread.sleep(5000);
		driver.get("https://www.mrtesting.com/");
		//Assert.assertTrue(driver.getTitle().contains("Amazon"));
	}

	@AfterMethod
	public void tearDown() {
		if (api != null) {
			String title = "ZAP Security Report";
			String template = "traditional-html";
			String description = "This is zap security test report";
			String reportfilename = "zap-report.html";
			String targetFolder = System.getProperty("user.dir");

			try {
				ApiResponse response = api.reports.generate(title, template, null, description, null, null, null, null,
						null, reportfilename, null, targetFolder, null);
				System.out.println("ZAP report generated at this location:" + response.toString());
			} catch (ClientApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		driver.quit();
	}
	// driver.quit();
}
