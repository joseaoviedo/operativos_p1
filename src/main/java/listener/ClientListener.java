package listener;

import Model.Game;
import Threads.GetData;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.Constants;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutionException;

import org.json.simple.JSONObject;
import utils.StatusNotifier;

public class ClientListener implements Runnable{
    public ClientListener(){
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        try {
            ServerSocket listener = new ServerSocket(Constants.CLIENT_PORT);
            while(true){
                Socket entering = listener.accept();
                DataInputStream inputStream = new DataInputStream(entering.getInputStream());
                String message = inputStream.readUTF();
                JSONParser parser = new JSONParser();
                JSONObject object = (JSONObject) parser.parse(message);
                if(isValidGame(object)){
                    StatusNotifier.setAvailable(false);
                    Game g = loadFromJSON(object);
                    GetData gd = new GetData();
                    JSONObject result = gd.getInfoGame(g);
                    StatusNotifier.sendResult(result);
                }
                StatusNotifier.setAvailable(true);
            }
        } catch (IOException | ParseException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isValidGame(JSONObject object){
        return object.containsKey("title") && object.containsKey("eShop") && object.containsKey("psStore") &&
                object.containsKey("amazon") && object.containsKey("xboxStore") && object.containsKey("steam");
    }

    public Game loadFromJSON(JSONObject object){
        Game g = new Game((String) object.get("title"), (boolean) object.get("eShop"), (boolean) object.get("psStore"),
                (boolean) object.get("amazon"), (boolean) object.get("xboxStore"),
                (boolean) object.get("steam"));
        return g;
    }
}
