package net.iesmila.a4_json_pokemon;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.iesmila.a4_json_pokemon.pokeapi.PokeDex;
import net.iesmila.a4_json_pokemon.pokeapi.PokeDexAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements Callback<PokeDex> {

    private ProgressBar pgrLoading;
    private RecyclerView rcyPokemons;
    private PokemonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcyPokemons = findViewById(R.id.rcyPokemons);
        pgrLoading = findViewById(R.id.pgrLoading);


        rcyPokemons.setHasFixedSize(true);
        rcyPokemons.setLayoutManager(new LinearLayoutManager(this));
        //rcyPokemons.setLayoutManager(new GridLayoutManager(this,2));


        pgrLoading.setVisibility(View.VISIBLE);
        loadJSON();
    }

    private void loadJSON() {

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        PokeDexAPI api = retrofit.create(PokeDexAPI.class);

        Call<PokeDex> call = api.getData();
        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<PokeDex> call, Response<PokeDex> response) {
        if(response.code()==200) {
            pgrLoading.setVisibility(View.INVISIBLE);
            adapter = new PokemonAdapter(response.body().getResults());
            rcyPokemons.setAdapter(adapter);
        }
    }

    @Override
    public void onFailure(Call<PokeDex> call, Throwable t) {

    }

/*    public void setResultat(List<Pokemon> resultat) {

        pgrLoading.setVisibility(View.INVISIBLE);
        adapter = new PokemonAdapter(resultat);
        rcyPokemons.setAdapter(adapter);

    }*/
 /*

    @Override
    public void onResponse(Call<PokeDex> call, Response<PokeDex> response) {

        List<Pokemon> pokemons = new ArrayList<Pokemon>();
        for(Result r:response.body().getResults()) {

            Pokemon p = new Pokemon();
            p.name = r.getName();
            pokemons.add(p);
        }
        mAdapter = new PokAdapter(pokemons);
        mAdapter.notifyDataSetChanged();

        recPokemons.setLayoutManager(new LinearLayoutManager(this) );
        recPokemons.setHasFixedSize(true);
        recPokemons.setAdapter(mAdapter);
    }

    @Override
    public void onFailure(Call<PokeDex> call, Throwable t) {

    }

    public class PokAdapter extends RecyclerView.Adapter<PokAdapter.ViewHolder>  {

        private List<Pokemon> mPokemons;

        public PokAdapter(List<Pokemon> pokemons) {
            mPokemons = pokemons;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View  v = getLayoutInflater().inflate(R.layout.row,parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.edtName.setText(mPokemons.get(position).name);
        }

        @Override
        public int getItemCount() {
            return mPokemons.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            EditText edtName;
            public ViewHolder(View itemView) {
                super(itemView);
                edtName = itemView.findViewById(R.id.edtName);
            }
        }
    }

    static final String BASE_URL = "https://pokeapi.co/api/v2/";


    public void start() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        PokeAPI pokeAPI = retrofit.create(PokeAPI.class);

        Call<PokeDex> call = pokeAPI.loadPokemons();
        call.enqueue(this);

    }

*/


}
