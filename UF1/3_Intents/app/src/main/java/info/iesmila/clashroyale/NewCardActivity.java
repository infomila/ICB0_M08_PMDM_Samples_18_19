package info.iesmila.clashroyale;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
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

    private Card mCurrentCard;

    //----------------
    private ImageView imvPhoto;
    private EditText edtName;
    private EditText edtDesc;
    private SeekBar sekElixir;
    private String mCurrentPhotoPath;


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        storeCardTempData();
        outState.putParcelable("currentCard", mCurrentCard);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCurrentCard = savedInstanceState.getParcelable("currentCard");
        if(mCurrentCard!=null) {
            edtName.setText(mCurrentCard.getName());
            edtDesc.setText(mCurrentCard.getDesc());
            sekElixir.setProgress(mCurrentCard.getElixirCost());
            mCurrentPhotoPath = mCurrentCard.getPhotoPath();

            PictureUtils.setPic(imvPhoto, mCurrentPhotoPath);
        }
    }

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

        //imvPhoto.setImageResource(mCurrentCard.getDrawable());
        mCurrentCard.loadPhoto(imvPhoto);
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
                storeCardTempData();
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

                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                    }


                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(NewCardActivity.this,
                                "com.example.android.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }




                    //startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }

            }
        });
    }

    private void storeCardTempData() {
        mCurrentCard.setName(edtName.getText().toString());
        mCurrentCard.setDesc(edtDesc.getText().toString());
        mCurrentCard.setElixirCost(sekElixir.getProgress());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            //Bitmap imageBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
            /*
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            */
            //imvPhoto.setImageBitmap(imageBitmap);


            PictureUtils.setPic(imvPhoto, mCurrentPhotoPath);

            mCurrentCard.setPhotoPath(mCurrentPhotoPath);

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
