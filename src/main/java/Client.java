import listener.ClientListener;
import utils.Constants;
import utils.StatusNotifier;

import java.text.ParseException;
import java.util.Timer;
import java.util.TimerTask;

public class Client {

    public static void main(String[] args){
        try{
            String ip;
            int port;
            if(args[0].equalsIgnoreCase("-ip")){
                if(args[1].matches("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$") || args[1].equalsIgnoreCase("localhost")){
                    ip = args[1];
                }else{
                    System.out.println("Invalid ip");
                    return;
                }
            }else{
                System.out.println("Expected argument: \"-ip\" Given: \"" + args[0] + "\"");
                return;
            }
            if(args[2].equalsIgnoreCase("-port")){
                try{
                    port = Integer.parseInt(args[3]);
                }catch (Exception e){
                    System.out.println("Invalid port");
                    return;
                }
            }else{
                System.out.println("Expected argument: \"-port\" Given: \"" + args[0] + "\"");
                return;
            }
            Constants.CLIENT_IP = ip;
            Constants.CLIENT_PORT = port;
        }catch (IndexOutOfBoundsException e){
            System.out.println("Not enought arguments, expected: 4 given: " +  args.length);
        }
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
