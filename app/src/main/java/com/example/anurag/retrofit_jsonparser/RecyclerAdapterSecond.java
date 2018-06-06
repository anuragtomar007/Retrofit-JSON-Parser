package com.example.anurag.retrofit_jsonparser;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapterSecond extends RecyclerView.Adapter<RecyclerAdapterSecond.ViewHandler> {
    private ArrayList<UsersDetails> userList = new ArrayList<>();

    private ArrayList<UsersDetails> duplicateList = new ArrayList<>();
    private Context context;

    public RecyclerAdapterSecond(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerAdapterSecond.ViewHandler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerlist_second, parent, false);
        return new ViewHandler(view);
    }

    public void setDatas(List<UsersDetails> usersDetails) {
        if (usersDetails != null) {
            userList.addAll(usersDetails);
            duplicateList.addAll(usersDetails);
            notifyDataSetChanged();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterSecond.ViewHandler holder, int position) {
        TextView Name = holder.Name;
//        ImageView imageViewIcon = holder.imageViewIcon;

        Name.setText(userList.get(position).getNameRepo());
//        Glide.with(context)
//                .load(userList.get(position).getAvatar_url())
//                .bitmapTransform(new GlideCircleTransformation(context))
//                .into(imageViewIcon);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHandler extends RecyclerView.ViewHolder {
//        private ImageView imageViewIcon;
        private TextView Name;

        public ViewHandler(View itemView) {
            super(itemView);
            this.Name = itemView.findViewById(R.id.name);
//            this.imageViewIcon = itemView.findViewById(R.id.imagePicture);
        }

    }
}
