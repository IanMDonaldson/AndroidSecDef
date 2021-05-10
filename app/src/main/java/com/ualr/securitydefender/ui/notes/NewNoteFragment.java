package com.ualr.securitydefender.ui.notes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.ualr.securitydefender.R;
import com.ualr.securitydefender.data.NoteEntity;

import java.util.List;

public class NewNoteFragment extends DialogFragment {

    private Button createNoteButton;
    private NotesViewModel vm;
    private EditText noteTitleEditText;
    private EditText noteInfoEditText;

    public NewNoteFragment(NotesViewModel vm){
        this.vm = vm;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_add, container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.vm = new ViewModelProvider(getActivity()).get(NotesViewModel.class);
        this.createNoteButton = view.findViewById(R.id.new_note_create_button);
        this.noteTitleEditText = view.findViewById(R.id.new_note_title);
        this.noteInfoEditText = view.findViewById(R.id.new_note_info);

        this.createNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteEntity noteEntity = new NoteEntity();
                noteEntity.setTitle(noteTitleEditText.getEditableText().toString());
                noteEntity.setNote(noteInfoEditText.getEditableText().toString());
                noteEntity.setSelected(false);
                //don't need this as viewmodel adds it to database anyway
                // should add it during the onPause or onStop method of the parent
                // fragment this is called from but fuck all that
                /*List<NoteEntity> currentNoteItems = vm.getNotes().getValue();
                if (currentNoteItems != null) {
                    currentNoteItems.add(0,noteEntity);
                }*/
                vm.insert(noteEntity);
                dismissFragment();
            }
        });
    }

    private void dismissFragment() {
        this.dismiss();
    }


}
