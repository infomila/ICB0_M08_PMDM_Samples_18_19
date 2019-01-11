package info.iesmila.tictactoe;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class EndGameDialog extends DialogFragment {

    Player mWinner;
    //MainActivity mActivity;

    public EndGameDialog() {
    }

    public void setWinner(Player p) {
        mWinner = p;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String missatge;
        switch (mWinner) {
            case IA: missatge="Guanya la màquina, pallús !!!";break;
            case HUMAN: missatge="YOU WIN !!!";break;
            default: missatge="Empat";
        }

        builder.setMessage(missatge)//R.string.dialog_fire_missiles)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        // vull avisar a l'activity que es tanca el dialeg
                        MainActivity a = (MainActivity)getActivity();
                        a.resetTauler();
                    }
                })
            .setTitle("Game Over");
        // Create the AlertDialog object and return it
        return builder.create();
    }
}