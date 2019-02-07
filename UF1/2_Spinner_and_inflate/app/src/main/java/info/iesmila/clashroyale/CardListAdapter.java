package info.iesmila.clashroyale;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import info.iesmila.clashroyale.model.Card;
import info.iesmila.clashroyale.model.Rarity;

public class CardListAdapter extends
        RecyclerView.Adapter<CardListAdapter.CardListAdapterViewHolder>
        implements ItemTouchHelperAdapter
{

    //============================== Mètodes de ItemTouchHelperAdapter =========================
    @Override
    public void onItemDelete(int position) {
        mCartes.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemDragged(int fromPosition, int toPosition) {

        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mCartes, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mCartes, i, i - 1);
            }
        }

        // Actualitzem la posició seleccionada si el que hem mogut és justament el que estava seleccionat.
        if(fromPosition==mSelectedPosition) {
            mSelectedPosition = toPosition;
        } else if(mSelectedPosition == toPosition) {
            mSelectedPosition = fromPosition;
        }

        notifyItemMoved(fromPosition, toPosition);
        //notifyItemMoved(toPosition, fromPosition);

    }
    //==========================================================================================

    /**
     * Conté la posició actualment seleccionada.
     */
    private int mSelectedPosition=-1;

    private List<Card> mCartes;
    private Main2Activity mActivity;
    private ItemTouchHelper mIth;

    public CardListAdapter(List<Card> pCartes, Main2Activity pActivity ) {
        mCartes = pCartes;
        mActivity = pActivity;
    }

    public void esborraSeleccionat() {

        mCartes.remove(mSelectedPosition);
        notifyItemRemoved(mSelectedPosition);

    }

    public void setItemTouchHelper(ItemTouchHelper itemTouchHelper) {
        this.mIth = itemTouchHelper;
    }


    class CardListAdapterViewHolder extends RecyclerView.ViewHolder {

        public TextView txvName;
        public TextView txvDesc;
        public ImageView imgPhoto;
        public LinearLayout llyInnerRow;
        public ImageView imvHandle;

        public CardListAdapterViewHolder(View itemView) {
            super(itemView);

            txvName = itemView.findViewById(R.id.txvName);
            txvDesc = itemView.findViewById(R.id.txvDesc);
            imgPhoto = itemView.findViewById(R.id.imgPhoto);
            llyInnerRow = itemView.findViewById(R.id.llyInnerRow);
            imvHandle = itemView.findViewById(R.id.imvHandle);

            // programem els listeners
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int previamentSeleccionada = CardListAdapter.this.mSelectedPosition;

                    if(mSelectedPosition == getAdapterPosition()) {
                        mSelectedPosition = -1;
                    } else {
                        mSelectedPosition = getAdapterPosition();
                    }
                    notifyItemChanged(previamentSeleccionada);
                    notifyItemChanged(mSelectedPosition);
                }
            });
            llyInnerRow.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    int previamentSeleccionada = mSelectedPosition;
                    mSelectedPosition = getAdapterPosition();
                    notifyItemChanged(mSelectedPosition);
                    notifyItemChanged(previamentSeleccionada);

                    mActivity.obrirMenuContextual();
                    return true;
                }
            });
            imvHandle.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mIth.startDrag(CardListAdapterViewHolder.this);
                    return true;
                }
            });

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
    public void onBindViewHolder(final @NonNull CardListAdapterViewHolder holder, final int position) {
            Card c = mCartes.get(position);
            holder.txvName.setText(c.getName());
            holder.txvDesc.setText(c.getDesc());
            holder.imgPhoto.setImageResource(c.getDrawable());
            if(position == mSelectedPosition) {
                holder.itemView.setBackgroundResource(R.drawable.background_selected_card);
            } else {
                holder.itemView.setBackground(null  );
            }
    }

    @Override
    public int getItemCount() {
        return mCartes.size();
    }
}
