package info.iesmila.clashroyale;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import info.iesmila.clashroyale.model.Card;

public class Main2Activity extends AppCompatActivity {

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


    }
}
