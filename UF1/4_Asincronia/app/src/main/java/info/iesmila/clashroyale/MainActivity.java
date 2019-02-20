package info.iesmila.clashroyale;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import info.iesmila.clashroyale.model.Card;
import info.iesmila.clashroyale.model.Rarity;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {

    private Spinner spnRarity;
    private RadioGroup rdoTabs;
    private RadioButton rdoInfo;
    private RadioButton rdoPhoto;
    private LinearLayout llyCardContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //--------------------------------------------------
        spnRarity =             findViewById(R.id.spnRarity);
        rdoTabs=                findViewById(R.id.rdoTabs);
        rdoInfo=                findViewById(R.id.rdoInfo);
        rdoPhoto=               findViewById(R.id.rdoPhoto);
        llyCardContainer=       findViewById(R.id.llyCardContainer);
        //--------------------------------------------------

        //--------------------------------------------------
        //Omplim les dades de l'Spinner
        spnRarity.setAdapter(
                new ArrayAdapter(this,
                        android.R.layout.simple_spinner_item,
                        Rarity.values() ));
        // Programem l'spinner
        spnRarity.setOnItemSelectedListener(this);
        //--------------------------------------------------
        // Programació dels RadioButtons
        rdoInfo.setOnCheckedChangeListener(this);
        rdoPhoto.setOnCheckedChangeListener(this);
        rdoInfo.setChecked(true);
        //--------------------------------------------------

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Rarity seleccionada = Rarity.values()[position];

        llyCardContainer.removeAllViews();
        // seleccionar totes les cartes que són d'aquesta raresa
        List<Card> cartes = Card.getCartes();
        for(Card c:cartes) {
            if(c.getRarity()==seleccionada) {
                // inflar el layout de carta
                LayoutInflater manxa = getLayoutInflater();
                View cardView = manxa.inflate(R.layout.card_info,llyCardContainer,false);
                // Modificar els textos dins del cardView
                TextView txvId = cardView.findViewById(R.id.txvId);
                TextView txvName = cardView.findViewById(R.id.txvName);
                TextView txvDesc = cardView.findViewById(R.id.txvDesc);
                ImageView imgPhoto = cardView.findViewById(R.id.imgPhoto);
                TextView txvElixirCost = cardView.findViewById(R.id.txvElixirCost);
                txvId.setText(""+c.getId());
                txvName.setText(c.getName());
                txvDesc.setText(c.getDesc());
                txvElixirCost.setText(""+c.getElixirCost());
                //imgPhoto.setImageResource(c.getDrawable());
                c.loadPhoto(imgPhoto);
                llyCardContainer.addView(cardView);
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked) {
            switch (buttonView.getId()) {
                case R.id.rdoInfo:
                    switchInfoOrPhoto(true);
                    break;
                case R.id.rdoPhoto:
                    switchInfoOrPhoto(false);
                    break;
            }
        }
    }

    private void switchInfoOrPhoto(boolean viewInfoOn) {

        for(int i=0;i< llyCardContainer.getChildCount();i++) {
            View v = llyCardContainer.getChildAt(i);
            ImageView imgPhoto =        v.findViewById(R.id.imgPhoto);
            ConstraintLayout cntCard =  v. findViewById(R.id.cntCard);
            cntCard.setVisibility(  viewInfoOn? View.VISIBLE : View.INVISIBLE);
            imgPhoto.setVisibility(!viewInfoOn? View.VISIBLE : View.INVISIBLE);
        }
    }
}
