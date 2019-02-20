package net.iesmila.a4_json_pokemon;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.iesmila.a4_json_pokemon.pokeapi.Result;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder>{

    private List<Result> pokemons;

    public PokemonAdapter(List<Result> theList) {
        pokemons = theList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View fila = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.pokemon_row, parent, false);
        return new ViewHolder(fila);
    }

    @Override
    public void onBindViewHolder(ViewHolder fila, int position) {
        Result p = pokemons.get(position);
        fila.txvName.setText(p.getName());
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // una referència a cadascun dels elements de la interfície gràfica de la fila
        TextView txvName;
        public ViewHolder(View fila) {
            super(fila);
            txvName = fila.findViewById(R.id.txvName);
        }
    }

}
