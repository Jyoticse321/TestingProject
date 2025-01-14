package testBase;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {
    public WebDriver driver;
    public Properties p;

    @BeforeClass(groups = {"sanity","regression","Master"})
    @Parameters({"os","browser"})
    public void setup(String os,String br) throws IOException {
        FileReader file = new FileReader("./src/test/config.properties");
        p = new Properties();
        p.load(file);

        if (p.getProperty("execution_env").equalsIgnoreCase("remote")) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            if (os.equalsIgnoreCase("windows")) {
                capabilities.setPlatform(Platform.WIN11);
            } else if (os.equalsIgnoreCase("mac")) {
                capabilities.setPlatform(Platform.MAC);
            }
            switch (br.toLowerCase()) {
                case "chrome":
                    capabilities.setBrowserName("chrome");
                    break;
                case "edge":
                    capabilities.setBrowserName("MicrosoftEdge");
                    break;
                case "firefox":
                    capabilities.setBrowserName("firefox");
                    break;
                default:
                    System.out.println("No matching browser");
                    return;
            }
            driver = new RemoteWebDriver(new URL("http://192.168.0.111:4444"), capabilities);
        }

        else if (p.getProperty("execution_env").equalsIgnoreCase("local")) {


            switch (br.toLowerCase()) {
                case "chrome":
                    driver = new ChromeDriver();
                    break;
                case "edge":
                    driver = new EdgeDriver();
                    break;
                case "firefox":
                    driver = new FirefoxDriver();
                    break;
                default:
                    System.out.println("Inavlid browser name");
                    return;
            }

            driver.manage().deleteAllCookies();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            driver.get(p.getProperty("URL"));
            driver.manage().window().maximize();
        }
    }

    @AfterClass(groups = {"sanity","regression","Master"})
    public void tearDown()
    {
        driver.close();
    }


    public String randomeString()
    {
        String generatedString=RandomStringUtils.randomAlphabetic(5);
        return generatedString;

    }

    public String randomeNumber()
    {
        String generatedString=RandomStringUtils.randomNumeric(10);
        return generatedString;
    }

    public String randomAlphaNumeric()
    {
        String str=RandomStringUtils.randomAlphabetic(3);
        String num=RandomStringUtils.randomNumeric(3);

        return (str+"@"+num);
    }

}
