package com.ualr.securitydefender.ui.notes;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ualr.securitydefender.R;
import com.ualr.securitydefender.data.NoteEntity;

import java.util.List;

public class NotesFragment extends Fragment implements NoteRecyclerAdapter.OnItemClickListener {

    private NotesViewModel notesViewModel;
    private NoteRecyclerAdapter noteRecyclerAdapter;
    private FloatingActionButton addButton;

    public NotesFragment(){

    }
    public NotesFragment(NotesViewModel viewModel){
        this.notesViewModel = viewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notes, container, false);
        notesViewModel = new ViewModelProvider(getActivity()).get(NotesViewModel.class);
        notesViewModel.setNoteRepository(this.getActivity().getApplication());
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.addButton = view.findViewById(R.id.note_add_button);

        RecyclerView recyclerView = view.findViewById(R.id.notes_recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);

        noteRecyclerAdapter = new NoteRecyclerAdapter(getContext(), notesViewModel.getNotes().getValue());
        noteRecyclerAdapter.setOnItemClickListener(this);
        //added
        recyclerView.setAdapter(noteRecyclerAdapter);
        //added
        notesViewModel.getNotes().observeForever(new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(List<NoteEntity> noteEntities) {
                noteRecyclerAdapter.updateNoteList(notesViewModel.getNotes().getValue());
            }
        });
        //recyclerView.setAdapter(noteRecyclerAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewNoteDialog();
            }
        });
    }

    private void showNewNoteDialog() {
        FragmentManager fm = getChildFragmentManager();
        NewNoteFragment newNoteFragment = new NewNoteFragment(notesViewModel);
        newNoteFragment.show(fm, "noteFrag");
    }

    private void showEditNoteDialog() {
        FragmentManager fm = getChildFragmentManager();
        EditNoteFragment editNoteFragment = EditNoteFragment.newInstance(notesViewModel.getSelectedIndex().getValue());
        editNoteFragment.show(fm, "editNoteFrag");
    }

    public void removeNoteItem(){
        int currentNote = notesViewModel.getSelectedIndex().getValue();
        List<NoteEntity> current = notesViewModel.getNotes().getValue();

        if (currentNote != -1 && current != null){
            noteRecyclerAdapter.removeNote(currentNote);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit_action:
                showEditNoteDialog();
                return true;
            case R.id.delete_action:
                removeNoteItem();
                return true;
            default:
                return true;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        noteRecyclerAdapter.clearAllSelections();
        notesViewModel.selectNoteAtPos(position);
        noteRecyclerAdapter.notifyItemChanged(position);
        notesViewModel.setSelectedIndex(position);
    }
}