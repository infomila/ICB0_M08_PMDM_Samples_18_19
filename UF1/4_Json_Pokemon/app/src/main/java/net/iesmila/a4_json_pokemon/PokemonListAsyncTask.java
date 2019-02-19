package net.iesmila.a4_json_pokemon;

import android.os.AsyncTask;

import net.iesmila.a4_json_pokemon.network.HttpUtils;
//                                                   entrada, update, sortida
public class PokemonListAsyncTask extends AsyncTask< Void,    Void,   String > {

    private MainActivity mActivity;

    public PokemonListAsyncTask( MainActivity activity) {
        mActivity = activity;
    }

    @Override
    protected String doInBackground(Void... voids) {
        // S'executa aun fil en background
        return HttpUtils.doGet("https://pokeapi.co/api/v2/pokemon?offset=0&limit=1000");

    }


    @Override
    protected void onPostExecute(String resultat) {
        super.onPostExecute(resultat);
        // S'executa al fil de UI

        mActivity.setResultat(resultat);
    }
}
