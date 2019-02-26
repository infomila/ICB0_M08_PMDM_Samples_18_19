package net.iesmila.app6_fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.iesmila.app6_fragments.ItemAPI.ItemAPI;
import net.iesmila.app6_fragments.dummy.DummyContent;
import net.iesmila.app6_fragments.dummy.DummyContent.DummyItem;
import net.iesmila.app6_fragments.model.Item;

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
public class FragmentItemList extends Fragment implements Callback<List<Item>> {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private ProgressBar prgProgress;
    private RecyclerView rcyList;
    private List<Item>  mItems;

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

        prgProgress.setVisibility(View.VISIBLE);

        // S'inicialitza el parser de GSON
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        // S'incialitza Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.132.0.0/items/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ItemAPI api = retrofit.create(ItemAPI.class);

        Call<List<Item>> call = api.getItems();
        call.enqueue(this);


        // S'incia la descàrrega




    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        prgProgress.setVisibility(View.INVISIBLE);

        if(response.code()== 200) { // CODI DE RESPOSTA HTTP :
            mItems = response.body();
            rcyList.setAdapter(
                    new ItemRecyclerViewAdapter(
                            mItems, mListener));
        }
    }

    @Override
    public void onFailure(Call<List<Item>> call, Throwable t) {
        prgProgress.setVisibility(View.INVISIBLE);
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
