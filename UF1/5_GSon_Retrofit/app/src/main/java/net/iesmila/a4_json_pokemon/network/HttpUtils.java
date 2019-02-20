package net.iesmila.a4_json_pokemon.network;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class HttpUtils {


    public static String doGet(String url) {
        InputStream is = null;
        try {

            Thread.sleep(4000);

            URL u = new URL(url);
            is = u.openStream();

            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            // retornem tot el resultat de la consulta concatenat
            return  sb.toString();
        } catch (Exception ex) {
            // Oops

            Log.e("POKEDEX","Error descarregant url "+url, ex);
        }
        finally {
            try{if(is!= null)is.close();}catch(Exception squish){}
        }
        return null;
    }

}
