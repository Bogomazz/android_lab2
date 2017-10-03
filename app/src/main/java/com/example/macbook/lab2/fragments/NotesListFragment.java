package com.example.macbook.lab2.fragments;

import android.app.Fragment;
import android.app.ListFragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import com.example.macbook.lab2.EditNoteActivity;
import com.example.macbook.lab2.R;
import com.example.macbook.lab2.models.Note;
import com.example.macbook.lab2.storage.NotesStorage;
import com.example.macbook.lab2.tools.NotesAdapter;

import java.util.ArrayList;

public class NotesListFragment extends Fragment implements SearchView.OnQueryTextListener {

    ListView listView;
    int selectedListItemPosition = -1;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_notes_list, container, false);


        Note note1 = new Note();
        note1.setTitle("Note1");
        note1.setDescription("Description for note 1");

        Note note2 = new Note();
        note2.setTitle("Note2");
        note2.setDescription("Description for note 2");

        Note note3 = new Note();
        note3.setTitle("Note3");
        note3.setDescription("Description for note 3");

        NotesStorage.add(note1);
        NotesStorage.add(note2);
        NotesStorage.add(note3);

        setHasOptionsMenu(true);

        updateNoteList(null);
        handleIntent(getActivity().getIntent());
        registerForContextMenu(listView);

        return view;
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.v("Search string: ", query);
        }
    }

    private void updateNoteList(ArrayList<Note> notes) {
        if (notes == null){
            notes = NotesStorage.get();
        }
        NotesAdapter na = new NotesAdapter(getActivity(), notes);
        listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(na);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        updateNoteList(NotesStorage.find(s));
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        updateNoteList(NotesStorage.find(s));
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        this.selectedListItemPosition = info.position;
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select The Action");
        menu.add(0, v.getId(), 0, "Edit");//groupId, itemId, order, title
        menu.add(0, v.getId(), 0, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        Log.v("menuItemId:", ""+item.getItemId());
        if(item.getTitle()=="Edit"){
            if (selectedListItemPosition != -1){
                Note note = (Note)listView.getAdapter().getItem(selectedListItemPosition);
                Intent intent = new Intent();
                intent.putExtra("id", note.getId());
                intent.setClass(getActivity(), EditNoteActivity.class);
                startActivity(intent);
            }
        }
        else if(item.getTitle()=="Delete"){
            if(selectedListItemPosition != -1){
                NotesStorage.remove(selectedListItemPosition);
                updateNoteList(null);
            }
        }else{
            return false;
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);

        SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem mi = menu.findItem(R.id.search);
        SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(mi);
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));

        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item); // get the spinner
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.importance_array, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    case 0:
                        updateNoteList(null);
                        break;
                    case 1:
                        updateNoteList(NotesStorage.getHigh());
                        break;
                    case 2:
                        updateNoteList(NotesStorage.getLow());
                        break;
                    case 3:
                        updateNoteList(NotesStorage.getDefault());
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.search:
                Log.v("v", "Search action clicked");
                return true;
            case R.id.action_add:
                Intent intent = new Intent();
                intent.putExtra("id", -1);
                intent.setClass(getActivity(), EditNoteActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
