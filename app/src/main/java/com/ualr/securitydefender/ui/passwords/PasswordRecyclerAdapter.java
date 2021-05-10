package com.ualr.securitydefender.ui.passwords;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.ualr.securitydefender.R;
import com.ualr.securitydefender.data.PasswordEntity;

import java.util.List;

public class PasswordRecyclerAdapter extends RecyclerView.Adapter {
    private List<PasswordEntity> passwordEntityList;
    private Context context;
    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.password_list_item, parent, false);
        return new PasswordViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PasswordEntity passwordEntity = passwordEntityList.get(position);
        PasswordViewHolder passwordViewHolder = (PasswordViewHolder) holder;
        passwordViewHolder.setUsernameValue(passwordEntity.getUsername());
        passwordViewHolder.setPasswordValue(passwordEntity.getPassword());
        passwordViewHolder.setWebsiteName(passwordEntity.getWebsite());
        passwordViewHolder.setWebsiteIcon(passwordEntity.getWebsiteIconLetter());


        // TODO
        if (passwordEntity.isSelected()) {
            passwordViewHolder.lyt_parent.setBackgroundColor(ContextCompat.getColor(context, R.color.teal_200));
            Log.d("irconde", "replace this line with something that changes the background color of the item");
        }
        else{
            passwordViewHolder.lyt_parent.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        if (this.passwordEntityList == null) {
            return 0;
        } else {
            return passwordEntityList.size();
        }
    }
    public void updatePasswordList(List<PasswordEntity> passwords) {
        this.passwordEntityList = passwords;
        this.notifyDataSetChanged();
    }

//    public PasswordRecyclerAdapter(Context context){ this.context = context;}
    public PasswordRecyclerAdapter(Context context, List<PasswordEntity> passwords) {
        this.context = context;
        this.passwordEntityList = passwords;
    }
    public void removePassword(int position){
        if (position >= passwordEntityList.size()){
            return;
        }
        passwordEntityList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }
    public void addPasswordItem(int position, PasswordEntity item){
        passwordEntityList.add(position, item);
        notifyItemInserted(position);
    }

    public void clearAllSelections(){
        for (PasswordEntity mItem: passwordEntityList) {
            mItem.setSelected(false);
        }
        notifyDataSetChanged();
    }
//    public List<PasswordEntity> getPasswordEntityList(){
//        return this.passwordEntityList;
//    }


    class PasswordViewHolder extends RecyclerView.ViewHolder {
        private TextView websiteIcon;
        private TextView websiteTitle;
        private TextView usernameValue;
        private EditText passwordValue;
        public View lyt_parent;

        public PasswordViewHolder(@NonNull View itemView) {
            super(itemView);
            this.websiteIcon = itemView.findViewById(R.id.icon);
            this.websiteTitle = itemView.findViewById(R.id.website_value);
            this.usernameValue = itemView.findViewById(R.id.username_value);
            this.passwordValue = itemView.findViewById(R.id.password_value);
            this.passwordValue.setKeyListener(null);
            lyt_parent = itemView.findViewById(R.id.lyt_parent);
            /*lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(v, getLayoutPosition());
                }
            });*/
        }
        //SETTERS

        public void setUsernameValue(String username) {
            this.usernameValue.setText(username);
        }

        public void setPasswordValue(String password) {
            this.passwordValue.setText(password);
        }
        /* Icon is from PasswordEntity's websiteIconLetter variable, which is initialized when
         *  PasswordEntity's website String variable is set */
        public void setWebsiteIcon(String icon) {
            this.websiteIcon.setText(icon);

        }

        public void setWebsiteName(String websiteName) {
            this.websiteTitle.setText(websiteName);
        }

    }

}