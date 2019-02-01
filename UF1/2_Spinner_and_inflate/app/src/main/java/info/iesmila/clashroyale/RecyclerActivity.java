package info.iesmila.clashroyale;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import info.iesmila.clashroyale.model.Card;

public class RecyclerActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        RecyclerView rcvCards = findViewById(R.id.rcvCards);
        LinearLayoutManager llm = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rcvCards.setLayoutManager(llm);
        //--------------------------------------------------------
        CardAdapter adapter = new CardAdapter(Card.getCartes(), this);
        rcvCards.setAdapter(adapter);
        //--------------------------------------------------------

    }
}
