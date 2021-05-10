package Threads;

import Jsoup.Scrapping;

import java.io.IOException;
import java.util.concurrent.Callable;

public class Thread implements Callable<String> {

    private String Name;
    private int Code;
    private Scrapping scrapping = new Scrapping();


    public Thread(String name, int code) {
        Name = name;
        Code = code;
    }

    @Override
    public String call() throws Exception {

        System.out.println("Thread: " + Name + "Web: " + Code);
        String value = null;

        if(Code ==  0){
            try {
                value = (scrapping.score_Metacritic(Name));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(Code == 1){
            try{
                value = (scrapping.precio_NintendoEshop(Name));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(Code ==2){
            try{
                value = (scrapping.precio_playStation(Name));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(Code == 3){
            try{
                value = (scrapping.price_Amazon(Name));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(Code == 4 ){
            try{
                value = (scrapping.price_MicrosoftXbox(Name));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(Code == 5){
            try{
                value = (scrapping.precio_Steam(Name));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(Code == 6){
            try {
                value =  (scrapping.timeToComplete(Name));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return value;
    }
}
