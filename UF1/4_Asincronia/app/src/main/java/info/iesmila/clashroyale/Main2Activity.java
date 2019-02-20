package info.iesmila.clashroyale;

import android.app.Activity;
import android.content.Intent;
import android.drm.DrmStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.stream.Stream;

import info.iesmila.clashroyale.model.Card;
import info.iesmila.clashroyale.model.Rarity;

public class Main2Activity extends AppCompatActivity {

    //-------------------------------------------------------
    //CAB : Contextual Action Bar

    private ActionMode mActionMode;

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.context_menu, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.mnuDelete:
                    mAdapter.esborraSeleccionat();
                    mode.finish(); // Action picked, so close the CAB
                    return true;
                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };

    //--------------------------------------------------------

    public void obrirMenuContextual(){

        if (mActionMode == null) {
            // Start the CAB using the ActionMode.Callback defined above
            mActionMode = startActionMode(mActionModeCallback);
        }
    }

    private CardListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //------------------------------------------
        // Programem el Recycler

        RecyclerView rcvCards = findViewById(R.id.rcvCards);
        rcvCards.setLayoutManager(
                new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rcvCards.setHasFixedSize(true);




        mAdapter = new CardListAdapter(Card.getCartes(), this);
        rcvCards.setAdapter(mAdapter);


        //----------------------------------------------
        // Programació el ItemTouchHelper del Recycler per habilitar el swipe i el drag&drop
        SimpleItemTouchHelperCallback callback = new SimpleItemTouchHelperCallback(mAdapter);
        ItemTouchHelper ith = new ItemTouchHelper(callback);

        mAdapter.setItemTouchHelper(ith);

        ith.attachToRecyclerView(rcvCards);


        //----------------------------------------------
        // Programació del ToolBar
        Toolbar t = findViewById(R.id.tolPrincipal);
        setSupportActionBar(t);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }
    public static final int NEW_ACTIVITY_INTENT = 1;
    public static final String NEW_ACTIVITY_INTENT_PARAM___CARD = "Card";
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.mnuNew:
                Intent i = new Intent( this, NewCardActivity.class  );
                i.putExtra("id", 123);


                Card c = new Card(Card.nextId(), "<Empty>", Rarity.EPIC, R.drawable.newcard,"Write here your description", 10);
                i.putExtra(NEW_ACTIVITY_INTENT_PARAM___CARD, c);
                //startActivity(i);
                startActivityForResult(i, NEW_ACTIVITY_INTENT);

            default:
        }
        return true;
    }


@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==NEW_ACTIVITY_INTENT) {
            if(resultCode == Activity.RESULT_OK) {
                Card nova = data.getParcelableExtra(NEW_ACTIVITY_INTENT_PARAM___CARD);
                Card.getCartes().add(nova);
                mAdapter.notifyItemInserted(Card.getCartes().size()-1);

            }
        }

    }
}
