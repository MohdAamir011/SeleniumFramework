package base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
public class Base {
    public WebDriver driver;

    public WebDriver launchDriver() throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"/src/main/java/Properties/GlobalData.properties");
        prop.load(fis);
        String browserName=prop.getProperty("browser");

        if(browserName.equalsIgnoreCase("chrome")){
            System.setProperty("webdriver.chrome.driver","D://selenium library/Webdrivers/Chrome/chromedriver-win64/chromedriver.exe");
//            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();

        }
        else if(browserName.equalsIgnoreCase("firefox")) {
//            System.setProperty("webdriver.gecko.driver","D://selenium library/Webdrivers/geckodriver.exe");
            WebDriverManager.firefoxdriver().setup(); // This line sets up GeckoDriver using WebDriverManager
            driver = new FirefoxDriver();
        }
        else if(browserName.equalsIgnoreCase("edge")) {
            System.setProperty("webdriver.edge.driver","D://selenium library/Webdrivers/msedgedriver.exe");
            driver = new EdgeDriver();
        }
        else{
            System.setProperty("webdriver.chrome.driver","D://selenium library/Webdrivers/Chrome/chromedriver-win64/chromedriver.exe");
            driver = new ChromeDriver();
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return driver;
    }
    //    read json file and convert to hashmap and return to data provider functions
    public List<HashMap<String,String>>  getJsonDataToMap(String filePath) throws IOException {
        String jsonContent = FileUtils.readFileToString(new File(filePath),
                StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        List<HashMap<String,String>>data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {

        });
        return data;
    }
    // take screen shots
    public String getScreenShot(String testCaseName,WebDriver driver) throws IOException {
        TakesScreenshot ts = (TakesScreenshot)driver;
        File source =ts.getScreenshotAs(OutputType.FILE);
        File desFile = new File(System.getProperty("user.dir")+"/reports/"+testCaseName+".png");
        FileUtils.copyFile(source,desFile);
        return System.getProperty("user.dir")+"/reports/"+testCaseName+".png";
    }

}
