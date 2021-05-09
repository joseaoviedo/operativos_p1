package utils;

import org.json.simple.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class StatusNotifier {
    private static boolean available = true;

    public static synchronized void setAvailable(boolean b){
        available = b;
    }

    public static void notifyStatus(){
        String status;
        if(available){
            status = "available";
        }else{
            status = "working";
        }
        try {
            Socket clientSocket = new Socket(Constants.SERVER_IP, Constants.SERVER_PORT);
            JSONObject object = new JSONObject();
            object.put("status", status);
            object.put("ip", Constants.CLIENT_IP);
            object.put("port", Constants.CLIENT_PORT);
            DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
            outputStream.writeUTF(object.toString());
            outputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void sendResult(String result){
        try {
            Socket clientSocket = new Socket(Constants.SERVER_IP, Constants.SERVER_PORT);
            JSONObject object = new JSONObject();
            object.put("result", result);
            DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
            outputStream.writeUTF(object.toString());
            outputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
