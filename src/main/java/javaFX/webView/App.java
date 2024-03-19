package javaFX.webView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws IOException {
		String url = "https://hnbmg.com/tat-tan-tat-ve-air-jordan-1-19986.html";
		String nextButtonXPath = "/html/body/div[2]/div[1]/nav/button";
		String chromeDriverPath = "C:\\Users\\admin\\Documents\\eclipse_project\\webView\\src\\resource\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe";

		System.setProperty("webdriver.chrome.driver", chromeDriverPath);

		WebDriver driver = new ChromeDriver();

		driver.get(url);

		while (true) {
			String htmlContent = driver.getPageSource();
			System.out.println("Scrapeed page: " + url);
			savedHTMLContent(htmlContent, "scraped_page.html");
			
			WebElement nextButton = null;
			try {
				if(nextButtonXPath.endsWith("button")) {
				nextButton = driver.findElement(By.xpath(nextButtonXPath));
				}else {
					
				}
			} catch (Exception e) {
				System.out.println("No button found");
				break;
			}
			
			if(nextButton.isEnabled() && nextButton != null) {
				nextButton.click();
			}else {
				try {
					WebElement nextLink = driver.findElement(By.xpath(nextButtonXPath));
					String nextUrl = nextLink.getAttribute("href");
					driver.get(nextUrl);
				} catch (Exception e) {
					System.out.println("Next button is not clickable. Exiting loop");
					break;
					// TODO: handle exception
				}
			}
			
			url = driver.getCurrentUrl();
		}
		driver.quit();
	}

	public static void savedHTMLContent(String content, String fileName) throws IOException {
		String downloadFolder = System.getProperty("user.home") + File.separator + "Downloads";
		File file = new File(downloadFolder, fileName);
		FileWriter writer = new FileWriter(file);
		writer.write(content);
		writer.close();
	}
}
