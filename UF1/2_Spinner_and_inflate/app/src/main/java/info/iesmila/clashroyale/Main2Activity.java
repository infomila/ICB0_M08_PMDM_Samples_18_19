package info.iesmila.clashroyale;

import android.drm.DrmStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import info.iesmila.clashroyale.model.Card;

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.mnuNew: // aquí programaríem l'acció de New ....
            default:
        }
        return true;
    }
}
