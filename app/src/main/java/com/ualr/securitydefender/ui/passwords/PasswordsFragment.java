//package com.ualr.securitydefender.ui.passwords;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProvider;
//
//import com.ualr.securitydefender.R;
//
//public class PasswordsFragment extends Fragment {
//
//    private PasswordsViewModel passwordsViewModel;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        passwordsViewModel =
//                new ViewModelProvider(this).get(PasswordsViewModel.class);
//        View root = inflater.inflate(R.layout.fragment_passwords, container, false);
//        final TextView textView = root.findViewById(R.id.text_passwords);
//        passwordsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        return root;
//    }
//}
package com.ualr.securitydefender.ui.passwords;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

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
import com.ualr.securitydefender.data.PasswordEntity;

import java.util.List;

public class PasswordsFragment extends Fragment implements PasswordRecyclerAdapter.OnItemClickListener{


    private PasswordsViewModel passwordsViewModel;
    private com.ualr.securitydefender.ui.passwords.PasswordRecyclerAdapter passwordRecyclerAdapter;
    private FloatingActionButton addButton;

    public PasswordsFragment(PasswordsViewModel vm) {
        this.passwordsViewModel = vm;
    }

    public PasswordsFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_passwords, container, false);
        passwordsViewModel = new ViewModelProvider(getActivity()).get(PasswordsViewModel.class);
        passwordsViewModel.setPasswordRepository(this.getActivity().getApplication());
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.addButton = view.findViewById(R.id.password_add_button);

        RecyclerView recyclerView = view.findViewById(R.id.passwords_recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        /*todo replace with IF passwordList from database is null then handle with message
            that there's no passwords yet or something idk
            or not, not a huge deal
        */

        passwordRecyclerAdapter = new PasswordRecyclerAdapter(getContext(), passwordsViewModel.getPasswords().getValue());
        passwordRecyclerAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(passwordRecyclerAdapter);
        passwordsViewModel.getPasswords().observe(getViewLifecycleOwner(), new Observer<List<PasswordEntity>>() {
            @Override
            public void onChanged(@Nullable List<PasswordEntity> passwordEntities) {
                passwordRecyclerAdapter.updatePasswordList(passwordEntities);
            }
        });
//        recyclerView.setAdapter(passwordRecyclerAdapter);

        //##########_ ADD PASSWORD BUTTON
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewPasswordDialog();
            }
        });


    }

    private void showNewPasswordDialog() {
        FragmentManager fm = getChildFragmentManager();
        NewPasswordFragment newPasswordFragment = new NewPasswordFragment(passwordsViewModel);
        newPasswordFragment.show(fm, "passwordFrag");

    }

    private void showEditPasswordDialog() {
        FragmentManager fm = getChildFragmentManager();
        EditPasswordFragment editPasswordFragment = EditPasswordFragment.newInstance(passwordsViewModel.getSelectedIndex().getValue());
        editPasswordFragment.show(fm,"editPasswordFrag");
//        passwordRecyclerAdapter.clearAllSelections();
    }

    public void removePasswordItem(){
        int currentPassword = passwordsViewModel.getSelectedIndex().getValue();

        List<PasswordEntity> current = passwordsViewModel.getPasswords().getValue();
        PasswordEntity passwordEntity = current.get(currentPassword);

        if (currentPassword != -1 && current != null){
//            passwordRecyclerAdapter.removePassword(currentPassword);
            passwordsViewModel.delete(passwordEntity);
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
                showEditPasswordDialog();
                return true;
            case R.id.delete_action:
                removePasswordItem();
                return true;
            default:
                return true;

        }
//        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View view, int position) {
        passwordRecyclerAdapter.clearAllSelections();
        passwordsViewModel.selectPasswordAtPos(position);
        passwordRecyclerAdapter.notifyItemChanged(position);
        passwordsViewModel.setSelectedIndex(position);

    }
}