package com.example.macbook.lab2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.macbook.lab2.models.Note;
import com.example.macbook.lab2.storage.NotesStorage;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

public class EditNoteActivity extends AppCompatActivity {

    public static final int PICK_IMAGE = 1;

    private ImageView imageView;
    private EditText titleET;
    private EditText descriptionET;

    private Note note = new Note();
    boolean isNew = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        imageView = (ImageView) findViewById(R.id.imageView);
        titleET = (EditText) findViewById(R.id.editTitle);
        descriptionET = (EditText) findViewById(R.id.editDescription);
        Button cancelBtn = (Button) findViewById(R.id.cancelButton);
        Button saveBtn = (Button) findViewById(R.id.saveButton);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditNoteActivity.this.finish();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                note.setTitle(titleET.getText().toString());
                note.setDescription(descriptionET.getText().toString());
                note.setDate(new Date());
                if (isNew) {
                    NotesStorage.add(note);
                }
                EditNoteActivity.this.finish();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        int id = this.getIntent().getIntExtra("id", 0);


        if (id != -1) {
            this.note = NotesStorage.getById(id);
            isNew = false;
        }

        titleET.setText(note.getTitle());
        descriptionET.setText(note.getDescription());
        if (note.getImageUri() != null){
            try{
                final InputStream imageStream = getContentResolver().openInputStream(note.getImageUri());
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(selectedImage);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }

        switch (note.getImportance()) {
            case 0:
                ((RadioButton) findViewById(R.id.radioDefault)).setChecked(true);
                break;
            case 1:
                ((RadioButton) findViewById(R.id.radioLow)).setChecked(true);
                break;
            case 2:
                ((RadioButton) findViewById(R.id.radioHigh)).setChecked(true);
                break;
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioHigh:
                if (checked)
                    this.note.setImportance(2);
                break;
            case R.id.radioDefault:
                if (checked)
                    this.note.setImportance(0);
                break;
            case R.id.radioLow:
                if (checked)
                    this.note.setImportance(1);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == RESULT_OK) {
            try{
                final Uri imageUri = data.getData();

                note.setImageUri(imageUri);
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(selectedImage);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }

        } else {
            Log.v("i", "Fail");
        }
    }


}
