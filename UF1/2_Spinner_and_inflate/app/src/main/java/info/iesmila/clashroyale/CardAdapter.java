package info.iesmila.clashroyale;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import info.iesmila.clashroyale.model.Card;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private List<Card> mCartes;

    public CardAdapter(List<Card> pCartes) {
        mCartes = pCartes;
    }


    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        FrameLayout fila = (FrameLayout)
            LayoutInflater.from(parent.getContext()).inflate(R.layout.card_info, parent , false);

        return new CardViewHolder(fila);
    }


    // El onBindViewHolder omple una fila amb valors d'un objecte que esta a la posició "position"
    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, final int position) {
        final Card c = mCartes.get(position);
        holder.txvId.setText( ""+c.getId() );
        holder.txvDesc.setText( c.getDesc() );
        holder.txvName.setText( c.getName() );
        holder.txvElixirCost.setText( ""+c.getElixirCost() );
        holder.imgPhoto.setImageResource( c.getDrawable() );
        holder.frlFila.setBackgroundColor(c.isSelected()?Color.GREEN:Color.WHITE);

        //------------------------------------------------------------------
        holder.frlFila.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.switchSelected();
                CardAdapter.this.notifyItemChanged(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        // retornem el número de cartes que hi ha la llista
        return mCartes.size();
    }

    //--------------------- Programem el Holder, que és un índex dels Views de cada fila ---------------
    public static class CardViewHolder  extends RecyclerView.ViewHolder {

        public TextView txvId;
        public TextView txvName;
        public TextView txvDesc;
        public TextView txvElixirCost;
        public ImageView imgPhoto;
        public FrameLayout frlFila;

        public CardViewHolder(FrameLayout fila) { // es correspon al node arrel del layout card_info.xml
            super(fila);
            // Busquem cadascun dels elements de la fila i en desem una referència.
            txvId = fila.findViewById(R.id.txvId);
            txvName = fila.findViewById(R.id.txvName);
            txvDesc = fila.findViewById(R.id.txvDesc);
            txvElixirCost = fila.findViewById(R.id.txvElixirCost);
            imgPhoto = fila.findViewById(R.id.imgPhoto);
            frlFila = fila;
        }
    }
    //----------------------------------------------------------------------------------------

}
