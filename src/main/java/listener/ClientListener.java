package listener;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.Constants;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
                if(object.containsKey("gameToSearch")){
                    StatusNotifier.setAvailable(false);
                    //Buscar juego
                    StatusNotifier.setAvailable(true);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
