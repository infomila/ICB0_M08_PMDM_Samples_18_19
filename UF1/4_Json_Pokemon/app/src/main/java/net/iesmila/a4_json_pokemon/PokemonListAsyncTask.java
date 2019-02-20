package net.iesmila.a4_json_pokemon;

import android.os.AsyncTask;

import net.iesmila.a4_json_pokemon.network.HttpUtils;
import net.iesmila.a4_json_pokemon.pokeapi.GETPokemonJSONParser;
import net.iesmila.a4_json_pokemon.pokeapi.Pokemon;

import java.util.List;


//                                                   entrada, update, sortida
public class PokemonListAsyncTask extends AsyncTask< Void,    Void,   List<Pokemon> > {

    private MainActivity mActivity;

    public PokemonListAsyncTask( MainActivity activity) {
        mActivity = activity;
    }

    @Override
    protected List<Pokemon> doInBackground(Void... voids) {
        // S'executa aun fil en background
        String json = HttpUtils.doGet("https://pokeapi.co/api/v2/pokemon?offset=0&limit=1000");
        return GETPokemonJSONParser.parse(json);
    }


    @Override
    protected void onPostExecute(List<Pokemon> resultat) {
        super.onPostExecute(resultat);
        // S'executa al fil de UI

        mActivity.setResultat(resultat);
    }
}
