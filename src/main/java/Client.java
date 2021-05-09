import listener.ClientListener;
import utils.StatusNotifier;

import java.util.Timer;
import java.util.TimerTask;

public class Client {

    public static void main(String[] args){
        ClientListener listener = new ClientListener();
        listener.run();
        Timer t = new Timer();
        t.scheduleAtFixedRate(new ClientNotifier(), 0, 5000);
    }


    public static class ClientNotifier extends TimerTask{
        @Override
        public void run() {
            StatusNotifier.notifyStatus();
        }
    }
}
