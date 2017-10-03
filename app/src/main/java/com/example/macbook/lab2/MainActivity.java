package com.example.macbook.lab2;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.example.macbook.lab2.models.Note;
import com.example.macbook.lab2.storage.NotesStorage;
import com.example.macbook.lab2.tools.NotesAdapter;

import java.util.ArrayList;

public class MainActivity extends Activity {

    ListView listView;
    int selectedListItemPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
