package Jsoup;

import Selenium.Base;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import java.io.IOException;

//Cambiar los returns
public class JsoupQueries {


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


    public static void main(String[] args) throws IOException {

        JsoupQueries JSprecio = new JsoupQueries();

        JSprecio.precio_playStation("Dead Cells");

        JSprecio.precio_NintendoEshop("Dead Cells");

        JSprecio.precio_Steam("Dead Cells");

    }
}
