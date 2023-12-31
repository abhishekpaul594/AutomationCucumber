package methods;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import static org.openqa.selenium.support.locators.RelativeLocator.with;

public class ReusableMethods {

    protected static WebDriver driver;
    protected WebElement elem;

    protected List<WebElement> elementList=new ArrayList<>();
    public ReusableMethods() {
        this.driver = DriverUtil.getDefaultDriver();
    }

    public void waitPageLoad() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(30)).until(
                    webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public WebElement findElement(String element) throws Exception {
        String locator="";
        try {
            String[] pageObjectSeperator=element.split("\\.");
            String pageName=pageObjectSeperator[0];
            String objectName=pageObjectSeperator[1];
            locator=PageObjectGenerator.pageObjects.get(pageName+"."+objectName);
            elem=driver.findElement(with(By.xpath(locator)));
        } catch (Exception e) {
            throw new Exception(element+" element not found");
        }
        return elem;
    }

    public List<WebElement> findElements(String element) {
        String locator="";
        try {
            String[] pageObjectSeperator=element.split("\\.");
            String pageName=pageObjectSeperator[0];
            String objectName=pageObjectSeperator[1];
            locator=PageObjectGenerator.pageObjects.get(pageName+"."+objectName);
            elementList=driver.findElements(By.xpath(locator));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return elementList;
    }

    public Map<String,String> fetchData(String dataKey,String pageName)
    {

        Map<String,String> fetchedInputData=InputDataGenerator.inputDataSet.get(pageName+"."+dataKey);
        return fetchedInputData;
    }
}
