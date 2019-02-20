package net.iesmila.a4_json_pokemon.pokeapi;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PokeDexAPI {


    @GET("pokemon") // "https://pokeapi.co/api/v2/" + "pokemon"
    Call<PokeDex> getData();


}
