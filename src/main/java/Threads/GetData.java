package Threads;

import Model.Game;
import com.github.cliftonlabs.json_simple.Jsoner;
import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class GetData {

    private final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


    public String getInfoGame(String jsonGame) throws ParseException, ExecutionException, InterruptedException {

        Gson g = new Gson();
        Game s = g.fromJson(jsonGame, Game.class);
        String name = s.getTitle();

        Future<String> metaCritic = fixedThreadPool.submit(new Thread(name,0));
        Future<String> howLongTo = fixedThreadPool.submit(new Thread(name,6));

        Future<String> eShop = null;
        Future<String> psStore = null;
        Future<String> amazon = null;
        Future<String> xboxStore = null;
        Future<String> steam = null;

        if(s.isEShop())
            eShop = fixedThreadPool.submit(new Thread(name, 1));

        if(s.isPsStore())
            psStore = fixedThreadPool.submit(new Thread(name, 2));

        if(s.isAmazon())
            amazon = fixedThreadPool.submit(new Thread(name, 3));

        if(s.isXboxStore())
            xboxStore = fixedThreadPool.submit(new Thread(name, 4));

        if(s.isSteam())
            steam = fixedThreadPool.submit(new Thread(name, 5));


        System.out.println("Name: "+ name);
        System.out.println("howLongTo: " + howLongTo.get());
        System.out.println("Score: " + metaCritic.get());
        assert eShop != null;
        System.out.println("EShop: " + eShop.get());
        assert psStore != null;
        System.out.println("psStore: " + psStore.get());
        assert amazon != null;
        System.out.println("amazon: " + amazon.get());
        assert xboxStore != null;
        System.out.println("xboxStore: " + xboxStore.get());
        assert steam != null;
        System.out.println("steam: " + steam.get());

        fixedThreadPool.shutdown();
        return null;
    }

    public static void main(String[] args) throws ParseException, ExecutionException, InterruptedException {

        Game game = new Game("Dead Cells",false,false,false,false,true);

        String json = new Gson().toJson(game);

        GetData data = new GetData();
        System.out.println(data.getInfoGame(json));

    }
}
