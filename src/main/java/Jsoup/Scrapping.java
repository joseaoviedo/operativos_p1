package Jsoup;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import Selenium.Base;


/**
 * Indeed Job Search Hello World Program
 *
 */
public class Scrapping
{
    Base selenium;
    private String precio_playStation(String Name) throws IOException {

        try {

            String steamSearchString = Name.replaceAll(" ", "%20");
            System.out.println(steamSearchString);

            String steamURL = String.format("https://store.playstation.com/es-cr/search/%s", steamSearchString);
            System.out.println(steamURL);

            WebDriver driver = null;
            selenium = new Base(driver);
            selenium.chromeDriverConnection();
            selenium.visit(steamURL);

            selenium.clickXPath("//*[@id=\"main\"]/section/div/ul/li[1]/div/a/div/div[1]/span[2]/img[2]");
            Thread.sleep(2000);

            String url = selenium.getDriver().getCurrentUrl();
            selenium.getDriver().close();


            Document steamDoc = Jsoup.connect(url).timeout(6000).get();


            Element gameName = steamDoc.selectFirst("h1.psw-m-b-xs.psw-h1.psw-l-line-break-word");
            Element gamePrice = steamDoc.selectFirst("#main > div.psw-grid-container.pdp-content > div.top-content-full > div > div.psw-cell.psw-tablet-l-6.psw-tablet-p-12.pdp-content-right > div > div.cta-container-mobile.psw-cell.psw-mobile-p-12.psw-tablet-p-6.psw-p-t-xl > div > div > div > label > div > span > span > span > span");

            System.out.println(gameName.text());
            System.out.println(gamePrice.text());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Your search returned 0 results");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ("price");
    }



    private String precio_NintendoEshop(String link) throws IOException {

        try {

            String steamSearchString = link.replaceAll(" ", "+");
            System.out.println(steamSearchString);

            String steamURL = String.format("https://www.nintendo.com/search/#category=all&page=1&query=%s", steamSearchString);
            System.out.println(steamURL);

            WebDriver driver = null;
            selenium = new Base(driver);
            selenium.chromeDriverConnection();
            selenium.visit(steamURL);

            selenium.clickXPath("//*[@id=\"main\"]/div/div/global-search/div/div[1]/tile-slider/game-tile[1]/h3");
            Thread.sleep(2000);

            String url = selenium.getDriver().getCurrentUrl();
            selenium.getDriver().close();

            Document steamDoc = Jsoup.connect(url).timeout(6000).get();
            Element steamGameTitle = steamDoc.selectFirst("#details > div > div > div.basic-info > h1");
            Elements steamDiscountPrice = steamDoc.select("#purchase-options > div.price > span.h2.msrp");

            System.out.println(steamGameTitle.text());
            System.out.println(steamDiscountPrice.text());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Your search returned 0 results");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ("price");
    }

    private String precio_Steam(String link) throws IOException {
        try {

            String steamSearchString = link.replaceAll(" ", "+");
            System.out.println(steamSearchString);

            String steamURL = String.format("https://store.steampowered.com/search/?term=%s", steamSearchString);
            System.out.println(steamURL);
            Document steamDoc = Jsoup.connect(steamURL).get();
            steamDoc.select("strike").remove();

            Element steamGameTitle = steamDoc.selectFirst("span.title");
            Element steamDiscountPrice = steamDoc.selectFirst("div.col.search_price.responsive_secondrow");

            System.out.println(steamGameTitle.text());
            System.out.println(steamDiscountPrice.text());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Your search returned 0 results");
        }
        return ("price");
    }

    private String score_Metacritic(String link) throws IOException {

        try {

            String SearchString = link.replaceAll(" ", "%20");

            String metacriticURL = String.format("https://www.metacritic.com/search/all/%s/results", SearchString);
            System.out.println(metacriticURL);

            WebDriver driver = null;
            selenium = new Base(driver);
            selenium.chromeDriverConnection();
            selenium.visit(metacriticURL);

            selenium.clickXPath("//*[@id=\"main_content\"]/div/div[3]/div/ul/li[1]/div/div[2]/div/h3/a");
            Thread.sleep(2000);

            String url = selenium.getDriver().getCurrentUrl();
            selenium.getDriver().close();

            Document metacriticDoc = Jsoup.connect(url).timeout(6000).get();
            Element GameTitle = metacriticDoc.selectFirst("#main > div > div:nth-child(1) > div.left > div.content_head.product_content_head.game_content_head > div.product_title > a > h1");
            Elements Score = metacriticDoc.select("#main > div > div:nth-child(1) > div.left > div.with_trailer > div > div > div.summary_wrap > div.section.product_scores > div.details.main_details > div > div > a > div > span");

            System.out.println(GameTitle.text());
            System.out.println("Score:"+ Score.text());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Your search returned 0 results");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ("score");
    }

    private String price_MicrosoftXbox(String link) throws IOException {

        try {

            String SearchString = link.replaceAll(" ", "+");

            String xboxURL = String.format("https://www.xbox.com/es-mx/Search?q=%s", SearchString);
            System.out.println(xboxURL);

            WebDriver driver = null;
            selenium = new Base(driver);
            selenium.chromeDriverConnection();
            selenium.visit(xboxURL);

            selenium.clickXPath("//*[@id=\"nav-gamesxboxone\"]/div/div/section[1]/a/div[1]/picture/img");
            //selenium.clickXPath("//*[@id=\"R1MarketRedirect-submit\"]");   //Redirige a la pagina en CR
            selenium.clickXPath("//*[@id=\"R1MarketRedirect-1\"]/button");
            Thread.sleep(2000);

            String url = selenium.getDriver().getCurrentUrl();
            selenium.getDriver().close();

            Document doc = Jsoup.connect(url).timeout(6000).get();
            Element GameTitle = doc.selectFirst("#DynamicHeading_productTitle");

            Elements price = doc.select("#ProductPrice_productPrice_PriceContainer-1");

            //Elements price = doc.select("#ProductPrice_productPrice_PriceContainer-2 > span:nth-child(1)");

            System.out.println(GameTitle.text());
            System.out.println(price);
            System.out.println("Price:"+ price.text());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Your search returned 0 results");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ("Price");
    }

    private String price_Amazon(String link) throws IOException {

        try {

            String SearchString = link.replaceAll(" ", "+");
            System.out.println(SearchString);

            String amazonURL = "https://www.amazon.com/s?k="+SearchString+"&__mk_es_US=%C3%85M%C3%85%C5%BD%C3%95%C3%91";
            System.out.println(amazonURL);

            WebDriver driver = null;
            selenium = new Base(driver);
            selenium.chromeDriverConnection();
            selenium.visit(amazonURL);

            selenium.clickXPath("//*[@id=\"search\"]/div[1]/div/div[1]/div/span[3]/div[2]/div[1]/div/span/div/div/div[2]/div[2]/div/div[1]/h2/a/span");
            //selenium.clickXPath("//*[@id=\"ourprice_shippingmessage\"]/span[2]/a/span");

            Thread.sleep(2000);

            String url = selenium.getDriver().getCurrentUrl();
            selenium.getDriver().close();

            Document amazonDoc = Jsoup.connect(url).get();
            Element GameTitle = amazonDoc.selectFirst("#productTitle");

            //Elements Price = amazonDoc.select("#a-popover-content-3 > table > tbody > tr:nth-child(5) > td.a-span2.a-text-right > span");
            Elements Price = amazonDoc.select("#priceblock_ourprice");


            System.out.println(GameTitle);
            System.out.println("Price:"+ Price.text());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Your search returned 0 results");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ("Precio");
    }

    public static void main(String[] args) throws IOException {
        Scrapping i = new Scrapping();
        //i.score_Metacritic("Dead Cells");
        //i.price_MicrosoftXbox("Dead Cells");
        i.price_Amazon("Dead Cells");
        //i.price_Amazon("ghost of tsushima");
        //i.score_Metacritic("ghost of tsushima");

    }
}
