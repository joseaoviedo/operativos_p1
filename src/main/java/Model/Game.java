package Model;

public class Game {
    private String title;
    private boolean eShop;
    private boolean psStore;
    private boolean amazon;
    private boolean xboxStore;
    private boolean steam;

    public Game(String title, boolean eShop, boolean psStore, boolean amazon, boolean xboxStore, boolean steam){
        this.title = title;
        this.eShop = eShop;
        this.psStore = psStore;
        this.amazon = amazon;
        this.xboxStore = xboxStore;
        this.steam = steam;
    }

    public String getTitle(){
        return title;
    }

    public boolean isAmazon() {
        return amazon;
    }

    public boolean isEShop() {
        return eShop;
    }

    public boolean isPsStore() {
        return psStore;
    }

    public boolean isSteam() {
        return steam;
    }

    public boolean isXboxStore() {
        return xboxStore;
    }
}

