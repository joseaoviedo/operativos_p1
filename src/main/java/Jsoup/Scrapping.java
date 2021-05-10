package Jsoup;

import java.io.IOException;
import java.util.concurrent.*;

import Model.Game;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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

    //private final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

/*
    public String call() throws IOException {

        System.out.println("Thread: " + Name + "Web: " + code);
        String value = null;

        if(code ==  0){
            try {
                value = (score_Metacritic(Name));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(code == 1){
            try{
                value = (precio_NintendoEshop(Name));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(code ==2){
            try{
                value = (precio_playStation(Name));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(code == 3){
            try{
                value = (price_Amazon(Name));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(code == 4 ){
            try{
                value = (price_MicrosoftXbox(Name));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(code == 5){
            try{
                value = (precio_Steam(Name));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(code == 6){
            try {
                value =  timeToComplete(Name);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return value;
    }
*/
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

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Your search returned 0 results");
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

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Your search returned 0 results");
        }
        return result;
    }

    public String precio_Steam(String Name) throws IOException {
        String price = null;
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

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Your search returned 0 results");
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
        }
        return result;
    }

    public String price_MicrosoftXbox(String Name) throws IOException {
        String result = null;
        try {

            String SearchString = Name.replaceAll(" ", "+");

            String xboxURL = String.format("https://www.xbox.com/en-US/Search?q=%s", SearchString);
            System.out.println(xboxURL);

            WebDriver driver = null;
            selenium = new Base(driver);
            selenium.fireFoxDriverConnection();
            selenium.visit(xboxURL);

            new WebDriverWait(selenium.getDriver(), 20).until(ExpectedConditions.
                    elementToBeClickable(By.xpath("//*[@id=\"nav-gamesxboxone\"]/div/div/section[1]/a/div[1]/picture/img")));

            selenium.clickXPath("//*[@id=\"nav-gamesxboxone\"]/div/div/section[1]/a/div[1]/picture/img");
            //selenium.clickXPath("//*[@id=\"R1MarketRedirect-submit\"]");   //Redirige a la pagina en CR

            new WebDriverWait(selenium.getDriver(), 20).until(ExpectedConditions.
                    elementToBeClickable(By.xpath("//*[@id=\"R1MarketRedirect-1\"]/button")));

            selenium.clickXPath("//*[@id=\"R1MarketRedirect-1\"]/button");

            String url = selenium.getDriver().getCurrentUrl();
            selenium.getDriver().close();

            Document doc = Jsoup.connect(url).timeout(6000).get();
            Element GameTitle = doc.selectFirst("#DynamicHeading_productTitle");

            Elements price = doc.select("#ProductPrice_productPrice_PriceContainer-1");

            //Elements price = doc.select("#ProductPrice_productPrice_PriceContainer-2 > span:nth-child(1)");
            result = price.text();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Your search returned 0 results");
        }
        return result;
    }

    public String price_Amazon(String Name) throws IOException {
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
            new WebDriverWait(selenium.getDriver(), 20).until(ExpectedConditions.
                    elementToBeClickable(By.xpath("//*[@id=\"search\"]/div[1]/div/div[1]/div/span[3]/div[2]/div[1]/div/span/div/div/div[2]/div[2]/div/div[1]/h2/a/span")));

            selenium.clickXPath("//*[@id=\"search\"]/div[1]/div/div[1]/div/span[3]/div[2]/div[1]/div/span/div/div/div[2]/div[2]/div/div[1]/h2/a/span");
            //selenium.clickXPath("//*[@id=\"ourprice_shippingmessage\"]/span[2]/a/span");

            String url = selenium.getDriver().getCurrentUrl();
            selenium.getDriver().close();

            Document amazonDoc = Jsoup.connect(url).get();
            Element GameTitle = amazonDoc.selectFirst("#productTitle");

            //Elements Price = amazonDoc.select("#a-popover-content-3 > table > tbody > tr:nth-child(5) > td.a-span2.a-text-right > span");
            Elements Price = amazonDoc.select("#priceblock_ourprice");
            result = Price.text();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Your search returned 0 results");
        }
        return result;
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
            new WebDriverWait(selenium.getDriver(), 20).until(ExpectedConditions.
                    elementToBeClickable(By.xpath("//*[@id=\"global_site\"]/div[2]/div/div[2]/div[1]/ul/li[4]/div")));
            WebElement timeElement = driver.findElement(By.xpath("//*[@id=\"global_site\"]/div[2]/div/div[2]/div[1]/ul/li[4]/div"));
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
        }
        return time;
    }

    public String getInfoGame(String jsonGame) throws ParseException {
        String jsonResult = null;

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(jsonGame);
        String Name = (String) json.get(0);

        return Name;
    }



    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        /*
        Scrapping t1 = new Scrapping("Dead Cells", 0);
        Scrapping t2 = new Scrapping("Dead Cells", 1);
        Scrapping t3 = new Scrapping("Dead Cells", 2);
        Scrapping t4 = new Scrapping("Dead Cells", 6);

        //Scrapping i = new Scrapping("Mario", 0);
        //System.out.println(i.precio_NintendoEshop("Mario"));

        Future<String> future0 = t1.fixedThreadPool.submit(t1);
        Future<String> future1 = t2.fixedThreadPool.submit(t2);
        Future<String> future2 = t3.fixedThreadPool.submit(t3);
        Future<String> future6 = t4.fixedThreadPool.submit(t4);



        System.out.println("Dead Cells: ");
        System.out.println("Score:" + future0.get());
        System.out.println("EShop: " + future1.get());
        System.out.println("psStore:" + future2.get());
        System.out.println("Time: " + future6.get());

/*
        //i.score_Metacritic("Dead Cells");
        //i.price_MicrosoftXbox("Dead Cells");
        //i.price_Amazon("Dead Cells");
        //i.price_Amazon("ghost of tsushima");
        //i.score_Metacritic("ghost of tsushima");
        */

        //Scrapping t1 = new Scrapping("Dead Cells", 0);

        Game game = new Game("Dead Cells",true,true,false,false,true);

    }
}
