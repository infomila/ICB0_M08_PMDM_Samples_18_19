package info.iesmila.clashroyale;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;

import info.iesmila.clashroyale.model.Card;

import static info.iesmila.clashroyale.Main2Activity.NEW_ACTIVITY_INTENT_PARAM___CARD;

public class NewCardActivity extends AppCompatActivity {

    Card mCurrentCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);


        Intent i = getIntent();
        int id = i.getIntExtra("id", -1);
        mCurrentCard = i.getParcelableExtra(NEW_ACTIVITY_INTENT_PARAM___CARD);

        final ImageView imvPhoto = findViewById(R.id.imvPhoto);
        final EditText edtName = findViewById(R.id.edtName);
        final EditText edtDesc = findViewById(R.id.edtDesc);
        final SeekBar sekElixir = findViewById(R.id.sekElixir);

        Button btnCancel =findViewById(R.id.btnCancel);
        Button btnSave = findViewById(R.id.btnSave);

        imvPhoto.setImageResource(mCurrentCard.getDrawable());
        edtName.setText(mCurrentCard.getName());
        edtDesc.setText(mCurrentCard.getDesc());
        sekElixir.setProgress(mCurrentCard.getElixirCost());

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewCardActivity.this.setResult(Activity.RESULT_CANCELED);
                NewCardActivity.this.finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentCard.setName(edtName.getText().toString());
                mCurrentCard.setDesc(edtDesc.getText().toString());
                mCurrentCard.setElixirCost(sekElixir.getProgress());
                Intent back = new Intent();
                back.putExtra(NEW_ACTIVITY_INTENT_PARAM___CARD, mCurrentCard);
                NewCardActivity.this.setResult(Activity.RESULT_OK, back);
                NewCardActivity.this.finish();
            }
        });


    }
}
