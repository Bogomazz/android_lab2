package com.example.macbook.lab2.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.macbook.lab2.R;
import com.example.macbook.lab2.models.Note;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class NotesAdapter extends BaseAdapter {

    private final Context context;
    private final ArrayList<Note> notes;

    public NotesAdapter(Context context, ArrayList<Note> notes) {
        this.notes = notes;
        this.context = context;
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int i) {
        return notes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Note note = notes.get(position);

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.note_raw, parent, false);
        }

        TextView titleView = (TextView) convertView.findViewById(R.id.note_title);
        TextView descriptionView = (TextView) convertView.findViewById(R.id.note_description);
        TextView dateView = (TextView) convertView.findViewById(R.id.dateRow);
        ImageView importanceView = (ImageView) convertView.findViewById(R.id.importance);


        if (note.getImageUri() != null) {
            ImageView imageView = (ImageView) convertView.findViewById(R.id.note_image);
            try{
                final InputStream imageStream = context.getContentResolver().openInputStream(note.getImageUri());
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(selectedImage);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }

        }

        switch (note.getImportance()){
            case 1:
                importanceView.setImageResource(R.drawable.ic_importance_low);
                break;
            case 2:
                importanceView.setImageResource(R.drawable.ic_importance_high);
                break;
        }

        titleView.setText(note.getTitle());
        descriptionView.setText(note.getDescription());
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        dateView.setText(dateFormat.format(note.getDate()));


        return convertView;
    }
}
