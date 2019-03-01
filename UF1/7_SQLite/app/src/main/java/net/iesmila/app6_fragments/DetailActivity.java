package net.iesmila.app6_fragments;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailActivity extends AppCompatActivity
        implements FragmentDetail.OnFragmentInteractionListener {

    public static final String PARAM_ID = "paramId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // recuperem l'identificador dels paràmetres de l'Intent
        String id = getIntent().getStringExtra(PARAM_ID);

        FragmentDetail frag = FragmentDetail.newInstance(id);

        getSupportFragmentManager().
                beginTransaction().
                add(R.id.clyMain, frag).
                commit();

    }

    @Override
    public void onItemChanged(String id) {

    }

    @Override
    public void onItemDeleted(String id) {

    }
}
