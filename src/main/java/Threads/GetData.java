package Threads;

import Model.Game;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class GetData {

    private final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


    public JSONObject getInfoGame(Game g) throws ParseException, ExecutionException, InterruptedException {
        JSONObject object = new JSONObject();
        try {
            String name = g.getTitle();

            Future<String> metaCritic = fixedThreadPool.submit(new Thread(name, 0));
            Future<String> howLongTo = fixedThreadPool.submit(new Thread(name, 6));

            Future<String> eShop = null;
            Future<String> psStore = null;
            Future<String> amazon = null;
            Future<String> xboxStore = null;
            Future<String> steam = null;

            if (g.isEShop())
                eShop = fixedThreadPool.submit(new Thread(name, 1));

            if (g.isPsStore())
                psStore = fixedThreadPool.submit(new Thread(name, 2));

            if (g.isAmazon())
                amazon = fixedThreadPool.submit(new Thread(name, 3));

            if (g.isXboxStore())
                xboxStore = fixedThreadPool.submit(new Thread(name, 4));

            if (g.isSteam())
                steam = fixedThreadPool.submit(new Thread(name, 5));

            String meta = metaCritic.get();
            String how = howLongTo.get();
            String ES;

            if (eShop != null) {
                ES = eShop.get();

            } else {
                assert false;
                ES = "NA";
            }
            String Ps;
            if (psStore != null) {
                Ps = psStore.get();
            } else {
                assert false;
                Ps = "NA";
            }
            String Ama;
            if (amazon != null) {
                Ama = amazon.get();
            } else {
                assert false;
                Ama = "NA";
            }
            String XS;
            if (xboxStore != null) {
                XS = xboxStore.get();
            } else {
                assert false;
                XS = "NA";
            }
            String S;
            if (steam != null) {
                S = steam.get();
            } else {
                assert false;
                S = "NA";
            }
            object.put("result", "sucess");
            object.put("title", name);
            object.put("eShop", ES);
            object.put("psStore", Ps);
            object.put("amazon", Ama);
            object.put("xboxStore", XS);
            object.put("steam", S);
            object.put("score", meta);
            object.put("timeToComplete", how);
            return object;
        }catch (Exception e){
            e.printStackTrace();
            object.put("result", "failed");
            return object;
        }
    }
}
