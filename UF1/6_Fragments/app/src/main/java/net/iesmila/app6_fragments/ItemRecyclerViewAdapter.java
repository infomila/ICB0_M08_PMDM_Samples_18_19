package net.iesmila.app6_fragments;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import net.iesmila.app6_fragments.FragmentItemList.OnListFragmentInteractionListener;
import net.iesmila.app6_fragments.dummy.DummyContent.DummyItem;
import net.iesmila.app6_fragments.model.Item;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Item} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder> {

    private final List<Item> mValues;
    private OnListFragmentInteractionListener mListener;

    private int mSelectedPosition = -1;

    public ItemRecyclerViewAdapter(List<Item> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Item itemActual = mValues.get(position);
        holder.mItem = itemActual;
        holder.mIdView.setText(itemActual.getId());
        holder.mContentView.setText(itemActual.getContent());

        // Mostrar la imatge de la posició actual
        ImageLoader loader = ImageLoader.getInstance();
        loader.displayImage( itemActual.getImageURI(), holder.mImvPhoto );

        if(position==mSelectedPosition) {
            holder.mView.setBackgroundColor(Color.GREEN);
        } else {
            holder.mView.setBackgroundColor(Color.TRANSPARENT);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int originalPosition = mSelectedPosition;
                mSelectedPosition = position;
                notifyItemChanged(mSelectedPosition);
                notifyItemChanged(originalPosition);

                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListItemClick(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setListener(OnListFragmentInteractionListener listener) {
        this.mListener = listener;
    }

    public int getSelectedPosition() {
        return mSelectedPosition;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final ImageView mImvPhoto;
        public Item mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
            mImvPhoto = (ImageView) view.findViewById(R.id.imvPhoto);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
