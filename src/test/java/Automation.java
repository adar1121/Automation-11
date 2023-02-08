import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.File;
public class Automation {


    static WebDriver driver;
    ExtentReports extentReports;
    ExtentSparkReporter htmlReporter;
    ExtentTest test;
    String timeNow = String.valueOf(System.currentTimeMillis());
    @BeforeClass
    public void setDriver(){
        System.setProperty("webdriver.chrome.driver","C:\\Users\\user\\Desktop\\Automation Course\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://dgotlieb.github.io/Navigation/Navigation.html");
        extentReports = new ExtentReports();
        htmlReporter = new ExtentSparkReporter("C:\\Users\\user\\IdeaProjects\\Automation-11\\report.html");
        extentReports.attachReporter(htmlReporter);
        test = extentReports.createTest("Automation-11", "HomeWork Results");
        test.log(Status.INFO,"@BeforeClass");
    }

    private static String takeScreenShot(String ImagesPath){
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File screenShotFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File destinationFile = new File(ImagesPath + ".png");
        try{
            FileUtils.copyFile(screenShotFile,destinationFile);
        }
        catch (Exception e){

        }
        return ImagesPath + ".png";
    }

    @Test
    public void Test01_Print_iFrame(){
        try {
            driver.switchTo().frame("my-frame");
            System.out.println(driver.findElement(By.id("iframe_container")).getText());
            test.pass("Print iFrame Passed", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot(timeNow)).build());
        }catch (Exception e){
            System.out.println(e.getMessage());
            test.log(Status.FAIL,"Print iFrame Failed");
        }

    }

    @Test
    public void Test02_PrintOriginalFrame(){
        try {
            driver.switchTo().defaultContent(); // back to default iFrame
            driver.findElement(By.id("openNewTab")).click();
            test.log(Status.PASS,"Original Frame Passed");
        }catch (Exception e){
            e.getMessage();
            test.log(Status.FAIL,"Original Frame Failed");
        }
    }

    @Test
    public void Test03_GoogleTranslate()
    {
        driver.get("https://translate.google.co.il/?hl=iw&sl=iw&tl=en&op=translate");
        test.pass("google translate", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot(timeNow)).build());
        driver.findElement(By.cssSelector("textarea[jsname=BJE2fc]")).sendKeys("גוגל");
    }

    @AfterClass
    public void done(){
        extentReports.flush();
//        driver.quit();
    }

}
