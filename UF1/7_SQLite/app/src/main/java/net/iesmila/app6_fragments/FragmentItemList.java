package net.iesmila.app6_fragments;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.iesmila.app6_fragments.ItemAPI.ItemAPI;
import net.iesmila.app6_fragments.db.DatabaseHelper;
import net.iesmila.app6_fragments.dummy.DummyContent;
import net.iesmila.app6_fragments.dummy.DummyContent.DummyItem;
import net.iesmila.app6_fragments.model.Item;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class FragmentItemList extends Fragment  {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private ProgressBar prgProgress;
    private RecyclerView rcyList;
    private List<Item>  mItems;
    private ItemRecyclerViewAdapter mAdapter;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FragmentItemList() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static FragmentItemList newInstance(int columnCount) {
        FragmentItemList fragment = new FragmentItemList();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("SHIT_HAPPENS", "creating fragment");

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        // ---------------------------------------
        //  IMPORTANT : AQUESTA LÍNIA FA QUE L'ESTAT DEL FRAGMENT ES MANTINGUI EN
        //              MEMÒRIA DESPRÉS DE "CONFIGURATION CHANGES" -> ROTACIONS O CANVIS
        //              DE MIDA DE LA PANTALLA DEGUTS AL TECLAT VIRTUAL
        // ---------------------------------------
        this.setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        Log.d("SHIT_HAPPENS", "onCreateView");


        prgProgress = view.findViewById(R.id.prgProgress);
        rcyList = view.findViewById(R.id.rcyList);

        prgProgress.setVisibility(View.INVISIBLE);

        // Set the adapter

        Context context = view.getContext();

        if (mColumnCount <= 1) {
            rcyList.setLayoutManager(new LinearLayoutManager(context));
        } else {
            rcyList.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }

        loadData();


        return view;
    }

    private void loadData() {

        // Si mItems no és null, vol dir que algú ja ha carregat les dades anteriorment, i procedim directament
        // a mostrar-les.
        if(mAdapter!=null) {mostrarDades();return;}

        prgProgress.setVisibility(View.VISIBLE);


        try {
            DatabaseHelper dbh = new DatabaseHelper(getContext());
            SQLiteDatabase db = dbh.getWritableDatabase();
            db.beginTransaction();
            Cursor c = db.rawQuery("select * from item", null);

            mItems = new ArrayList<Item>();
            while(c.moveToNext()) {
                String id = c.getString( c.getColumnIndex("id") );
                String content = c.getString( c.getColumnIndex("content") );
                String details = c.getString( c.getColumnIndex("details") );
                String imageURI = c.getString( c.getColumnIndex("imageURI") );
                Item i = new Item( id, content, details, imageURI);
                mItems.add(i);
            }
            db.endTransaction();
            mostrarDades();

        }catch (Exception ex) {

        }
        prgProgress.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
            if(mAdapter!=null)  {mAdapter.setListener(mListener);}
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        if(mAdapter!=null)  mAdapter.setListener(null);
    }



    private void mostrarDades() {
        if(mAdapter==null) {
            mAdapter = new ItemRecyclerViewAdapter(mItems, mListener, selectedItemId);
        }
        rcyList.setAdapter(mAdapter);
    }


    public Item getItemSeleccionat() {
        if(mAdapter!=null) {
            int selectedPosition = mAdapter.getSelectedPosition();
            if(selectedPosition<0) return null;
            return mItems.get(selectedPosition);
        }
        return null;
    }

    private String selectedItemId = null;
    public void setSelectedItemId(String selectedItemId) {

        this.selectedItemId = selectedItemId;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListItemClick(Item item);
    }
}
