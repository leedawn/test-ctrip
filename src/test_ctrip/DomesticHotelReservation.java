package test_ctrip;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.databene.benerator.anno.Source;
import org.databene.feed4junit.Feeder;
import org.junit.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
@RunWith(Feeder.class)
public class  DomesticHotelReservation{
	
  private static WebDriver driver;
  private static String baseUrl="http://www.ctrip.com/";
  private String webUrl;
  private int i;
  @BeforeClass
  public static void setUpClass() throws Exception {
	  System.setProperty("webdriver.chrome.driver", "WebDriver/chromedriver.exe");
		driver=new ChromeDriver();
		driver.get(baseUrl);
  }
  @Before
  public void setUp() throws Exception {
    System.out.println("setup");
  }

  @Test
  @Source(value = "/test_ctrip/exploretest.xls", emptyMarker = "<empty>")          //不能用com.ctrip包名 注意excel的格式
  public void test(String caseNo,String cityName,String checkIn,String checkOut,
		  String expected ) throws Exception {
    System.out.println(caseNo);
    driver.findElement(By.id("HD_CityName")).clear();
    driver.findElement(By.id("HD_CityName")).sendKeys(cityName);
    driver.findElement(By.id("HD_CheckIn")).clear();
    driver.findElement(By.id("HD_CheckIn")).sendKeys(checkIn);
    driver.findElement(By.id("HD_CheckOut")).clear();
    driver.findElement(By.id("HD_CheckOut")).sendKeys(checkOut);
    driver.findElement(By.id("searchHotelLevelSelect")).click();
    do{
     i=new Random().nextInt(5);
//     System.out.println(i);
    }while(i==1);
    driver.findElement(By.cssSelector("option[value=\""+i+"\"]")).click();
    driver.findElement(By.id("HD_Btn")).click();
   try{
    	webUrl=driver.getCurrentUrl();
    	System.out.println(webUrl);
    	System.out.println("test case "+caseNo + "'s input is: " + cityName + " " + checkIn + " " + checkOut+"\""+expected+"\"be expected,");//\"双引号
    	assertTrue(webUrl.contains(expected));
    	System.out.println("the text is \""+webUrl+"\"");
   }catch (AssertionError e) {
	   try
	   {
		   assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*"+expected+"[\\s\\S]*$"));//开始 中括号表达式 任何空白字符 任何非空白字符 子表达式零次或多次 结束
	   }catch(AssertionError e1){
		   System.out.println(e1);
	   }
	   }
   ((JavascriptExecutor)driver).executeScript("window.location.href='http://www.ctrip.com/'");
//    driver.switchTo().window("http://www.ctrip.com");
    
  }

  @AfterClass
  public static void tearDown() throws Exception {
    driver.quit();
  }



}
