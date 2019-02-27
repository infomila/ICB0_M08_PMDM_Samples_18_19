package net.iesmila.app6_fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import net.iesmila.app6_fragments.dummy.DummyContent;
import net.iesmila.app6_fragments.model.Item;

public class MainActivity extends AppCompatActivity implements
                                        FragmentItemList.OnListFragmentInteractionListener,
                                        FragmentDetail.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //----------------------------------------------
        // Create global configuration and initialize ImageLoader with this config
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
			.build();
        ImageLoader.getInstance().init(config);


        //--------------------------------------------------------------------
        // Intentar buscar el FragmentItemList al fragment manager, per evitar
        // tornar-lo a crear si ja existia.
        FragmentItemList frag =  (FragmentItemList)getSupportFragmentManager().findFragmentById(R.id.frgItemList);

        //si frag és null, vol dir que no existia prèviament el fragment i s'ha de crear
        if( frag == null) {
            frag = new FragmentItemList();
            // Col·loquem el fragment
            getSupportFragmentManager().
                    beginTransaction().
                    add(R.id.frgItemList, frag).
                    commit();
        } else {
            // La llista ja existeix:
            // Ens cal publicar el fragment que correspon a la posició selecionada (només si estic en landscape)
            if(findViewById(R.id.frmDetail)!=null) { // NOMÉS LANDSCAPE
                onListItemClick(frag.getItemSeleccionat());
            }
        }


    }

    @Override
    public void onListItemClick(Item item) {
        if(findViewById(R.id.frmDetail)!=null) {

            // Estic en Landscape
            //------------------------------

            // Creem un fragment de tipus FragmentDetail i l'enxufem a frmDetail
            // a) Creació del fragment
            FragmentDetail frag = FragmentDetail.newInstance(item.getId());
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
            intent.putExtra(DetailActivity.PARAM_ID, item.getId() );
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
