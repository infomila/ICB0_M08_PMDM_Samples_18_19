package info.iesmila.tictactoe;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Player mPlayerActual ;
    private Player mMatriu[][] = new Player[3][3];

    private ImageButton mMatriuIB[][] = new ImageButton[3][3];

    private int mNumeroJugades = 0;

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
                R.id.btn0,R.id.btn1,R.id.btn2,R.id.btn3,R.id.btn4,
                R.id.btn5,R.id.btn6,R.id.btn7,R.id.btn8};
        int f=0, c=0;
        for(int i=0;i<ids.length;i++) {
            final ImageButton ib1 = findViewById(ids[i]);
            ib1.setOnClickListener(this);
            // El col·loquem a la matriu de ImageButtons
            mMatriuIB[f][c] = ib1;
            ib1.setTag(new Pair(f,c)); // desem una tupla de valors (fila i columna)
            c++;
            if(c>=3){
                c=0;
                f++;
            }
        }
        //------------------------------------------------
        resetTauler();

        //------------------------------------------------
        // preparació de la partida
        mPlayerActual= Player.HUMAN;

    }

    @Override
    public void onClick(View sender) {

        if(mPlayerActual!= Player.HUMAN) return;

        ImageButton ib = (ImageButton) sender;
        // Recuperem fila i columna
        Pair p = (Pair)ib.getTag();
        int f = (int) p.first;
        int c = (int) p.second;
        // mirem si la casella és clicable
        if( mMatriu[f][c]==Player.EMPTY ){
            mMatriu[f][c] = Player.HUMAN;
            ib.setImageResource(getDrawableForPlayer(Player.HUMAN));

            mNumeroJugades++;

            // Mirar si la partida s'ha acabat
            if(hiHaGuanyador(Player.HUMAN)){
                mostraFinalPartida(Player.HUMAN);
            } else {
                tornIA();
            }
        }
    }

    private void tornIA() {
        mPlayerActual = Player.IA;
        ArrayList<Pair<Integer,Integer>> indexosBuits = new ArrayList<Pair<Integer,Integer>>();
        for(int f=0;f<3;f++) {
            for(int c=0;c<3;c++) {
                if(mMatriu[f][c]==Player.EMPTY) indexosBuits.add(new Pair(f,c));
            }
        }
        // Tirar un dau de mida indexosBuits.length

        Random m = new Random();
        int i = m.nextInt(indexosBuits.size());

        Pair<Integer,Integer> p = indexosBuits.get(i);

        mMatriu[p.first][p.second] = Player.IA;
        mMatriuIB[p.first][p.second].setImageResource( getDrawableForPlayer(Player.IA) );

        mNumeroJugades++;

        if(hiHaGuanyador(Player.IA)){
            mostraFinalPartida(Player.IA);
        } else {
            mPlayerActual = Player.HUMAN;
        }
    }

    private void mostraFinalPartida(Player p) {
        /*

        Toast.makeText(this,
                "And the winner is....." +(p==Player.IA?" the machine":"YOU"),
                Toast.LENGTH_LONG
                ).show();
        */

        // Obrim el diàleg
        EndGameDialog dialeg = new EndGameDialog();
        dialeg.setWinner(mNumeroJugades==9?Player.EMPTY: p);
        dialeg.show(getSupportFragmentManager(),"EndGameDialog");
    }

    public void resetTauler() {

        mNumeroJugades = 0;

        for(int f=0;f<3;f++) {
            for(int c=0;c<3;c++) {
                mMatriu[f][c] = Player.EMPTY;
                mMatriuIB[f][c].setImageResource(R.drawable.fons_boto);
            }
        }
        mPlayerActual = Player.HUMAN;
    }

    private boolean hiHaGuanyador(Player p) {

        if(mNumeroJugades==9) return true;

        int[][][] combinacions =
                {
                        //HORITZONTALS
                        { {0,0},{0,1},{0,2} },
                        { {1,0},{1,1},{1,2} },
                        { {2,0},{2,1},{2,2} },
                        //VERTICALS
                        { {0,0},{1,0},{2,0} },
                        { {0,1},{1,1},{2,1} },
                        { {0,2},{1,2},{2,2} },
                        //DIAGONALS
                        { {0,0},{1,1},{2,2} },
                        { {2,0},{1,1},{0,2} }
                };

        for(int comb = 0; comb < combinacions.length; comb++) {
            int counter = 0;

            for (int cas = 0; cas < 3; cas++) {
                int x = combinacions[comb][cas][0];
                int y = combinacions[comb][cas][1];
                if (mMatriu[x][y]==p)counter++;
            }
            if (counter == 3) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retorna el drawable del jugador indicat per paràmetre
     */
    private int getDrawableForPlayer( Player p ) {
        if(p==Player.HUMAN) return R.drawable.round;
        else return R.drawable.cross;
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
