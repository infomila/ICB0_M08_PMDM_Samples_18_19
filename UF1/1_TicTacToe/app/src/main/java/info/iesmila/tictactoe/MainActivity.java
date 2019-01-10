package info.iesmila.tictactoe;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity   {

    private enum Player {
        HUMAN,
        IA
    }

    private int         mMatriu[][];
    private ImageButton mMatriuIB[][];
    private Player      mJugadorActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void IAMoves() {

    }

    public boolean checkWinner() {
        return false;
    }

    public void theEnd(){

    }


}
