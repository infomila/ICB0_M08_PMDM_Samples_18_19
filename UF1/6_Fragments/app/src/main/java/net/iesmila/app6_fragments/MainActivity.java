package net.iesmila.app6_fragments;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.iesmila.app6_fragments.dummy.DummyContent;

public class MainActivity extends AppCompatActivity implements
                                        FragmentItemList.OnListFragmentInteractionListener,
                                        FragmentDetail.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onListItemClick(DummyContent.DummyItem item) {
        if(findViewById(R.id.frmDetail)!=null) {

            // Estic en Landscape
            //------------------------------

            // Creem un fragment de tipus FragmentDetail i l'enxufem a frmDetail
            // a) Creació del fragment
            FragmentDetail frag = FragmentDetail.newInstance(item.id);
            // b) El col·loquem a l'activity
            getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.frmDetail,frag).
                    commit();
        }
         else {
            // Estic en portrait
            // Crearem un intent per obrir el DetailActivity
            Intent intent = new Intent(this,  DetailActivity.class );
            intent.putExtra(DetailActivity.PARAM_ID, item.id );
            startActivity(intent);
        }
    }

    @Override
    public void onItemChanged(String id) {

    }

    @Override
    public void onItemDeleted(String id) {

    }
}
