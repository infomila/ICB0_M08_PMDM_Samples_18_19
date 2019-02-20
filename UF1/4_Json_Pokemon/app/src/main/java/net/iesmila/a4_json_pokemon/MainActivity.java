package net.iesmila.a4_json_pokemon;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import net.iesmila.a4_json_pokemon.pokeapi.Pokemon;

import java.util.List;


public class MainActivity extends AppCompatActivity  {

    private ProgressBar pgrLoading;
    private RecyclerView rcyPokemons;
    private PokemonListAsyncTask at;
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

        at = new PokemonListAsyncTask(this);
        pgrLoading.setVisibility(View.VISIBLE);
        at.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
    }

    public void setResultat(List<Pokemon> resultat) {

        pgrLoading.setVisibility(View.INVISIBLE);
        adapter = new PokemonAdapter(resultat);
        rcyPokemons.setAdapter(adapter);

    }
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
