package info.iesmila.clashroyale;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import info.iesmila.clashroyale.model.Card;
import info.iesmila.clashroyale.model.Rarity;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.CardListAdapterViewHolder> {


    /**
     * Conté la posició actualment seleccionada.
     */
    private int mSelectedPosition=2;

    private List<Card> mCartes;
    private Main2Activity mActivity;

    public CardListAdapter( List<Card> pCartes, Main2Activity pActivity) {
        mCartes = pCartes;
        mActivity = pActivity;
    }


    static class CardListAdapterViewHolder extends RecyclerView.ViewHolder {

        public TextView txvName;
        public TextView txvDesc;
        public ImageView imgPhoto;

        public CardListAdapterViewHolder(View itemView) {
            super(itemView);

            txvName = itemView.findViewById(R.id.txvName);
            txvDesc = itemView.findViewById(R.id.txvDesc);
            imgPhoto = itemView.findViewById(R.id.imgPhoto);
        }
    }

    @NonNull
    @Override
    public CardListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutFila;
        if(viewType==EPICA) {
            layoutFila = R.layout.list_row_epic;
        } else  {
            layoutFila = R.layout.list_row;
        }
        View fila = mActivity.getLayoutInflater().inflate(layoutFila, parent, false);
        return new CardListAdapterViewHolder(fila);
    }

    private int EPICA = 1;
    private int NO_EPICA = 0;

    @Override
    public int getItemViewType(int position){
        Card c = mCartes.get(position);
        if(c.getRarity()== Rarity.EPIC) return EPICA; else return NO_EPICA;
    }

    @Override
    public void onBindViewHolder(@NonNull CardListAdapterViewHolder holder, final int position) {
            Card c = mCartes.get(position);
            holder.txvName.setText(c.getName());
            holder.txvDesc.setText(c.getDesc());
            holder.imgPhoto.setImageResource(c.getDrawable());
            if(position == mSelectedPosition) {
                holder.itemView.setBackgroundResource(R.drawable.background_selected_card);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedPosition = position;
                }
            });


    }

    @Override
    public int getItemCount() {
        return mCartes.size();
    }
}
