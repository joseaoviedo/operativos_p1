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
        //Future<String> howLongTo = fixedThreadPool.submit(new Thread(name,6));

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

        String meta = metaCritic.get();
        //String how = howLongTo.get();
        String ES;

        if(eShop != null){
            ES = eShop.get();

        }else {
            assert false;
            ES = "No disponible";
        }
        String Ps;
        if(psStore != null){
            Ps = psStore.get();
        }else{
            assert false;
            Ps = "No disponible";
        }
        String Ama;
        if(amazon != null){
            Ama = amazon.get();
        }else{
            assert false;
            Ama = "No disponible";
        }
        String XS;
        if(xboxStore != null){
            XS = xboxStore.get();
        }else{
            assert false;
            XS = "No disponible";
        }
        String S;
        if(steam != null){
            S = steam.get();
        }else{
            assert false;
            S = "No disponible";
        }
        String result = createJson(name,ES,Ps,XS,S,Ama,"how",meta);
        fixedThreadPool.shutdown();
        return result;
    }

    public String createJson(String name,String priceEShop, String pricePSStore,String priceXboxStore,
                             String priceSteam, String priceAmazon, String averageTime, String calificacion){
        JSONObject json = new JSONObject();
        json.put("game", name);
        json.put("priceEShop", priceEShop);
        json.put("pricePSStore", pricePSStore);
        json.put("priceXboxStore", priceXboxStore);
        json.put("priceSteam", priceSteam);
        json.put("priceAmazon", priceAmazon);
        json.put("averageTime", averageTime);
        json.put("calificacion", calificacion);

        return json.toString();
    }

    public static void main(String[] args) throws ParseException, ExecutionException, InterruptedException {

        Game game = new Game("Dead Cells",false,false,true,false,false);

        String json = new Gson().toJson(game);

        GetData data = new GetData();
        System.out.println(data.getInfoGame(json));

    }
}
