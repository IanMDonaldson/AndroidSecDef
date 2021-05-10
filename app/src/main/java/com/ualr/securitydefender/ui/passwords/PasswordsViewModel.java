package com.ualr.securitydefender.ui.passwords;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ualr.securitydefender.data.PasswordEntity;
import com.ualr.securitydefender.data.PasswordRepository;
//import com.ualr.securitydefender.data.UserpassGenerator;

import java.util.List;

public class PasswordsViewModel extends ViewModel {
    private PasswordRepository passwordRepository;

    private static final int NOT_SELECTED = -1;
    private int passwordIndex = NOT_SELECTED;

//    private MediatorLiveData<List<PasswordEntity>> passwords = null;
//    private MutableLiveData<List<PasswordEntity>> passwords = new MutableLiveData<>();
//    private UserpassGenerator passGen = new UserpassGenerator();

    private LiveData<List<PasswordEntity>> passwords;

    private MutableLiveData<Integer> selectedIndex;

    public PasswordsViewModel(@NonNull Application application) {
        super();

    }
    public PasswordsViewModel() {

    }
    public void setPasswordRepository(Application application) {
        this.passwordRepository = new PasswordRepository(application);
        this.passwords = passwordRepository.getAllPasswords();

    }
    public void setGeneratedPasswords() {}

    public LiveData<Integer> getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selected) {
        this.selectedIndex.setValue(selected);
    }
    public LiveData<List<PasswordEntity>> getPasswordsFromDB() {
        return this.passwordRepository.getPasswordsFromDB();
    }
    public LiveData<List<PasswordEntity>> getPasswords() {
        return this.passwords;
    }
    //changed
    public void setPasswords(List<PasswordEntity> passwordsList) {
        this.passwords.getValue().addAll(passwordsList);
    }
    //changed from passwords.setValue(passwordsList);
    public void selectPasswordAtPos(int pos) {
        List<PasswordEntity> passwordList = passwords.getValue();
        if (passwordIndex != NOT_SELECTED) {
            passwordList.get(passwordIndex).setSelected(false);
        }
        passwordList.get(pos).toggleSelection();
        passwords.getValue().addAll(passwordList);
    }

    public void insert(PasswordEntity passwordEntity) {
        passwordRepository.addPassword(passwordEntity);
    }

    public void update(PasswordEntity passwordEntity){
        passwordRepository.updatePassword((passwordEntity));
    }
    public void delete(PasswordEntity passwordEntity){
        passwordRepository.deletePassword(passwordEntity);
    }


}