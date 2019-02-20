package net.iesmila.a4_json_pokemon.pokeapi;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GETPokemonJSONParser {

    public static List<Pokemon> parse(String json) {

        try {
            List<Pokemon> pokemons = new ArrayList<>();
            JSONObject root  = new JSONObject(json);
            JSONArray resultsArray =  root.getJSONArray("results");
            for(int i=0;i<resultsArray.length();i++) {
                JSONObject pokemonObject = resultsArray.getJSONObject(i);
                Pokemon p = new Pokemon();
                p.name = pokemonObject.getString("name");
                p.url = pokemonObject.getString("url");
                pokemons.add(p);
            }
            return pokemons;

        } catch (JSONException e) {
            Log.e("POKEDEX", "Error parsejant json:"+json, e);
        }
        return null;
    }

}
