package info.iesmila.tictactoe;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private enum Player {
        EMPTY,
        HUMAN,
        IA
    }

    private Player mMatriu[][] = new Player[3][3];

    private ImageButton mMatriuIB[][] = new ImageButton[3][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //---------------------------------------------------------------

        final ImageButton ib = findViewById(R.id.btn0);
/*
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // codi que s'executa quan s'apreta el botó
                ib.setImageResource(R.drawable.cross);
            }
        });
*/
        ib.setOnClickListener(this);

        int ids[] = {
                R.id.btn0,
                R.id.btn1,
                R.id.btn2,
                R.id.btn3,
                R.id.btn4,
                R.id.btn5,
                R.id.btn6,
                R.id.btn7,
                R.id.btn8};
        int f=0, c=0;
        for(int i=0;i<ids.length;i++) {
            final ImageButton ib1 = findViewById(ids[i]);
            ib1.setOnClickListener(this);
            // El col·loquem a la matriu de ImageButtons
            mMatriuIB[f][c] = ib1;
            ib1.setImageResource(R.drawable.fons_boto);
            mMatriu[f][c] = Player.EMPTY;
            c++;
            if(c>=3){
                c=0;
                f++;
            }
        }

    }

    @Override
    public void onClick(View v) {

    }
/*
    public void IAMoves() {

    }

    public boolean checkWinner() {
        return false;
    }

    public void theEnd(){

    }

*/
}
