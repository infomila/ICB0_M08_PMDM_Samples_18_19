package info.iesmila.clashroyale;

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



    public static class CardViewHolder  extends RecyclerView.ViewHolder {

        public TextView txvId;
        public TextView txvName;
        public TextView txvDesc;
        public TextView txvElixirCost;
        public ImageView imgPhoto;

        public CardViewHolder(FrameLayout view) {

        }
    }

}
