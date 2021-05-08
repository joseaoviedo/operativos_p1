package Model;

import java.util.Date;

public class Data {
    private Game game;
    private Date queryDate;
    private String priceEShop;
    private String pricePSStore;
    private String priceXboxStore;
    private String priceSteam;
    private String priceAmazon;
    private String averageTime;
    private String calificacion;

    public Data(Game game, Date queryDate, String priceEShop, String pricePSStore,
                    String priceXboxStore, String priceSteam, String priceAmazon,
                    String averageTime, String calificacion) {
        this.game = game;
        this.queryDate = queryDate;
        this.priceEShop = priceEShop;
        this.pricePSStore = pricePSStore;
        this.priceXboxStore = priceXboxStore;
        this.priceSteam = priceSteam;
        this.priceAmazon = priceAmazon;
        this.averageTime = averageTime;
        this.calificacion = calificacion;
    }

    public Date getqueryDate() {
        return queryDate;
    }

    public Game getGame() {
        return game;
    }

    public String getCalificacion() {
        return calificacion;
    }

    public String getpriceAmazon() {
        return priceAmazon;
    }

    public String getpriceEShop() {
        return priceEShop;
    }

    public String getpricePSStore() {
        return pricePSStore;
    }

    public String getpriceSteam() {
        return priceSteam;
    }

    public String getpriceXboxStore() {
        return priceXboxStore;
    }

    public String getaverageTime() {
        return averageTime;
    }
}