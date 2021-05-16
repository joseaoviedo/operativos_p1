package Jsoup;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.*;


import com.posadskiy.currencyconverter.CurrencyConverter;
import com.posadskiy.currencyconverter.config.ConfigBuilder;
import com.posadskiy.currencyconverter.enums.Currency;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import Selenium.Base;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Indeed Job Search Hello World Program
 *
 */
public class Scrapping
{

    public Base selenium;

    private static String CURRENCY_CONVERTER_API_API_KEY = "1040dc060d777d74aa6f";


    // Init converter with your API key
    CurrencyConverter converter = new CurrencyConverter(
            new ConfigBuilder()
                    .currencyConverterApiApiKey(CURRENCY_CONVERTER_API_API_KEY)
                    .build()
    );

    //Double CRCToUSD = converter.rate("CRC", "USD");
    Double CRCToUSD = 0.0016;


    public String precio_playStation(String Name) throws IOException {
        String result = null;
        try {

            String steamSearchString = Name.replaceAll(" ", "%20");
            System.out.println(steamSearchString);

            String steamURL = String.format("https://store.playstation.com/es-cr/search/%s", steamSearchString);
            System.out.println(steamURL);

            WebDriver driver = null;
            selenium = new Base(driver);
            selenium.fireFoxDriverConnection();
            selenium.visit(steamURL);
            new WebDriverWait(selenium.getDriver(), 20).until(ExpectedConditions.
                    elementToBeClickable(By.xpath("//*[@id=\"main\"]/section/div/ul/li[1]/div/a/div/div[1]/span[2]/img[2]")));

            selenium.clickXPath("//*[@id=\"main\"]/section/div/ul/li[1]/div/a/div/div[1]/span[2]/img[2]");
            String url = selenium.getDriver().getCurrentUrl();
            selenium.getDriver().close();

            Document steamDoc = Jsoup.connect(url).timeout(6000).get();

            Element gameName = steamDoc.selectFirst("h1.psw-m-b-xs.psw-h1.psw-l-line-break-word");
            Element gamePrice = steamDoc.selectFirst("#main > div.psw-grid-container.pdp-content > div.top-content-full > div > div.psw-cell.psw-tablet-l-6.psw-tablet-p-12.pdp-content-right > div > div.cta-container-mobile.psw-cell.psw-mobile-p-12.psw-tablet-p-6.psw-p-t-xl > div > div > div > label > div > span > span > span > span");

            result = gamePrice.text();

            result = result.replaceAll("US","");

            result = result.replaceAll("\\$","");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Your search returned 0 results");
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public String precio_NintendoEshop(String Name) throws IOException {
        String result = null;
        try {

            String steamSearchString = Name.replaceAll(" ", "+");
            System.out.println(steamSearchString);

            String steamURL = String.format("https://www.nintendo.com/search/#category=all&page=1&query=%s", steamSearchString);
            System.out.println(steamURL);

            WebDriver driver = null;
            selenium = new Base(driver);
            selenium.fireFoxDriverConnection();
            selenium.visit(steamURL);
            new WebDriverWait(selenium.getDriver(), 20).until(ExpectedConditions.
                    elementToBeClickable(By.xpath("//*[@id=\"main\"]/div/div/global-search/div/div[1]/tile-slider/game-tile[1]/h3")));
            selenium.clickXPath("//*[@id=\"main\"]/div/div/global-search/div/div[1]/tile-slider/game-tile[1]/h3");

            Thread.sleep(1000);
            String url = selenium.getDriver().getCurrentUrl();
            selenium.getDriver().close();

            Document steamDoc = Jsoup.connect(url).timeout(6000).get();
            Element steamGameTitle = steamDoc.selectFirst("#details > div > div > div.basic-info > h1");
            Elements steamDiscountPrice = steamDoc.select("#purchase-options > div.price > span.h2.msrp");
            result = steamDiscountPrice.text();

            result = result = result.replaceAll("\\$", "");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Your search returned 0 results");
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public String precio_Steam(String Name) throws IOException {
        String price = null;
        float i = -1;
        try {

            String steamSearchString = Name.replaceAll(" ", "+");
            System.out.println(steamSearchString);

            String steamURL = String.format("https://store.steampowered.com/search/?term=%s", steamSearchString);
            System.out.println(steamURL);
            Document steamDoc = Jsoup.connect(steamURL).get();
            steamDoc.select("strike").remove();

            Element steamGameTitle = steamDoc.selectFirst("span.title");
            Element steamDiscountPrice = steamDoc.selectFirst("div.col.search_price.responsive_secondrow");

            price = steamDiscountPrice.text();

            price = price.replaceAll("₡", "");
            price = price.replaceAll("\\.", "");

            System.out.println(price);

            i = (float) (Float.parseFloat(price) * CRCToUSD);

            i = Math.round(i);

            price = String.valueOf(i);
            price = price + "0";

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Your search returned 0 results");
        } catch (Exception e){
            e.printStackTrace();
        }
        return price;
    }

    public String score_Metacritic(String Name) throws IOException {
        String result = null;
        try {

            String SearchString = Name.replaceAll(" ", "%20");

            String metacriticURL = String.format("https://www.metacritic.com/search/all/%s/results", SearchString);
            System.out.println(metacriticURL);

            WebDriver driver = null;
            selenium = new Base(driver);
            selenium.fireFoxDriverConnection();
            selenium.visit(metacriticURL);

            new WebDriverWait(selenium.getDriver(), 20).until(ExpectedConditions.
                    elementToBeClickable(By.xpath("//*[@id=\"main_content\"]/div/div[3]/div/ul/li[1]/div/div[2]/div/h3/a")));

            selenium.clickXPath("//*[@id=\"main_content\"]/div/div[3]/div/ul/li[1]/div/div[2]/div/h3/a");

            String url = selenium.getDriver().getCurrentUrl();
            selenium.getDriver().close();

            Document metacriticDoc = Jsoup.connect(url).timeout(6000).get();
            Element GameTitle = metacriticDoc.selectFirst("#main > div > div:nth-child(1) > div.left > div.content_head.product_content_head.game_content_head > div.product_title > a > h1");
            Elements Score = metacriticDoc.select("#main > div > div:nth-child(1) > div.left > div.with_trailer > div > div > div.summary_wrap > div.section.product_scores > div.details.main_details > div > div > a > div > span");
            result = Score.text();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Your search returned 0 results");
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public String price_MicrosoftXbox(String Name) throws IOException {
        String result = null;
        float i = -1;
        try {
            /*
            String SearchString = Name.replaceAll(" ", "+");

            String xboxURL = String.format("https://www.xbox.com/en-US/Search?q=%s", SearchString);
            System.out.println(xboxURL);
            */
            WebDriver driver = null;
            selenium = new Base(driver);
            selenium.fireFoxDriverConnection();
            selenium.visit("https://www.xbox.com/es-cr/");

            new WebDriverWait(selenium.getDriver(), 20).until(ExpectedConditions.
                    elementToBeClickable(By.xpath("//*[@id=\"search\"]")));

            selenium.clickXPath("//*[@id=\"search\"]");

            selenium.setText(Name, By.xpath("//*[@id=\"cli_shellHeaderSearchInput\"]"));

            new WebDriverWait(selenium.getDriver(), 20).until(ExpectedConditions.
                    elementToBeClickable(By.xpath("//*[@id=\"universal-header-search-auto-suggest-ul\"]/li[1]/a")));

            selenium.clickXPath("//*[@id=\"universal-header-search-auto-suggest-ul\"]/li[1]/a");

            new WebDriverWait(selenium.getDriver(), 20).until(ExpectedConditions.
                    elementToBeClickable(By.xpath("//*[@id=\"buttonPanel_AppIdentityBuyButton\"]")));

            String url = selenium.getDriver().getCurrentUrl();
            selenium.getDriver().close();
            System.out.println(url);

            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:88.0) Gecko/20100101 Firefox/88.0").timeout(6000).get();

            Element GameTitle = doc.selectFirst("#DynamicHeading_productTitle");

            Elements price = doc.select("#productPrice");

            //Elements price = doc.select("#ProductPrice_productPrice_PriceContainer-2 > span:nth-child(1)");
            result = price.text();
            String parts[] = result.split("\\+");
            result = parts[0];
            result = result.replaceAll("₡", "");
            result = result.replaceAll("\\.", "");

            System.out.println(result);

            i = (float) (Float.parseFloat(result) * CRCToUSD);

            System.out.println(i);

            i = Math.round(i);

            result = String.valueOf(i);
            result = result + "0";

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Your search returned 0 results");
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public String price_Amazon(String Name) throws IOException {
        /*
        String result = null;
        try {

            String SearchString = Name.replaceAll(" ", "+");
            System.out.println(SearchString);

            String amazonURL = "https://www.amazon.com/s?k="+SearchString+"&__mk_es_US=%C3%85M%C3%85%C5%BD%C3%95%C3%91";
            System.out.println(amazonURL);

            WebDriver driver = null;
            selenium = new Base(driver);
            selenium.fireFoxDriverConnection();
            selenium.visit(amazonURL);

            selenium.clickXPath("//*[@id=\"search\"]/div[1]/div/div[1]/div/span[3]/div[2]/div[1]/div/span/div/div/div[2]/div[2]/div/div[1]/h2/a/span");

            String url = selenium.getDriver().getCurrentUrl();
            selenium.getDriver().close();

            System.out.println(url);

            Document amazonDoc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:88.0) Gecko/20100101 Firefox/88.0").timeout(6000).get();
            Element GameTitle = amazonDoc.selectFirst("#productTitle");
            Element Price = amazonDoc.selectFirst("#priceblock_ourprice");

            result = Price.text();
            result = result.replaceAll("\\$", "");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Your search returned 0 results");
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
        */
        return "NA";
    }

    public String timeToComplete(String name){
        String time = null;
        try {
            String url = "https://howlongtobeat.com/#search";
            System.out.println(url);
            WebDriver driver = null;
            selenium = new Base(driver);
            driver = selenium.fireFoxDriverConnection();
            selenium.visit(url);
            new WebDriverWait(selenium.getDriver(), 20).until(ExpectedConditions.
                    elementToBeClickable(By.xpath("//*[@id=\"global_search_box\"]")));

            WebElement input = driver.findElement(By.xpath("//*[@id=\"global_search_box\"]"));
            input.sendKeys(name);
            Thread.sleep(2000);
            new WebDriverWait(selenium.getDriver(), 20).until(ExpectedConditions.
                    elementToBeClickable(By.xpath("//*[@id=\"global_search_content\"]/ul/li[1]/div[2]/h3/a")));

            WebElement firstItem = driver.findElement(By.xpath("//*[@id=\"global_search_content\"]/ul/li[1]/div[2]/h3/a"));
            firstItem.click();
            Thread.sleep(2000);
            WebElement timeElement = driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div/div[2]/div[1]/ul/li[4]/div"));
            time = timeElement.getText();
            driver.close();

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Your search returned 0 results");
            e.printStackTrace();
        } catch (NullPointerException e){
            System.out.println("Element not found");
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return time;
    }
}
