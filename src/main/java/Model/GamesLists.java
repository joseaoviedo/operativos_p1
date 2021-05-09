package Model;
import Model.Game;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GamesLists {

    public List<Game> getGames(){
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        List<Game> Games = new ArrayList<Game>();
        try {
            archivo = new File ("juegos.txt");
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while((linea=br.readLine())!=null) {

                String[] parts = linea.split("/");

                Game game = new Game(parts[0],store(parts[1]),store(parts[2]),store(parts[3]),store(parts[4]),store(parts[5]));

                Games.add(game);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }finally{
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta
            // una excepcion.
            try{
                if( null != fr ){
                    fr.close();
                }
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
        return Games;
    }

    private boolean store(String available){
        return available.equals("1");
    }

    public static void main(String[] args) {

        GamesLists lista = new GamesLists();

        List<Game> Games = new ArrayList<Game>();

        Games = lista.getGames();

        for(int i=0;i<Games.size();i++){

            System.out.println(Games.get(i).toString());
        }
    }

}
