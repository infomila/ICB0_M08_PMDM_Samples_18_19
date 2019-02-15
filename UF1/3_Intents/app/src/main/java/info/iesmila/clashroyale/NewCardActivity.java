package info.iesmila.clashroyale;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import info.iesmila.clashroyale.model.Card;

import static info.iesmila.clashroyale.Main2Activity.NEW_ACTIVITY_INTENT_PARAM___CARD;

public class NewCardActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1 ;

    Card mCurrentCard;

    //----------------
    ImageView imvPhoto;
    EditText edtName;
    EditText edtDesc;
    SeekBar sekElixir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);


        Intent i = getIntent();
        int id = i.getIntExtra("id", -1);
        mCurrentCard = i.getParcelableExtra(NEW_ACTIVITY_INTENT_PARAM___CARD);

        imvPhoto = findViewById(R.id.imvPhoto);
        edtName = findViewById(R.id.edtName);
        edtDesc = findViewById(R.id.edtDesc);
        sekElixir = findViewById(R.id.sekElixir);

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

        imvPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imvPhoto.setImageBitmap(imageBitmap);
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


}
