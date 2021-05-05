package Selenium;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class Selenium {

    Base base;

    @Before
    public void setUp(){
        WebDriver driver = null;
        base = new Base(null);
        base.chromeDriverConnection();
    }

    @Test
    public void testYT(){

    }


}
