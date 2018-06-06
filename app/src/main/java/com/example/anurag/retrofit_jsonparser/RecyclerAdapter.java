package com.example.anurag.retrofit_jsonparser;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHandler>
        implements Filterable {
    private ArrayList<UsersDetails> userList = new ArrayList<>();

    private ArrayList<UsersDetails> duplicateList = new ArrayList<>();
    private Context context;
    private OnClickListenerInterface listenerInterface;
    private OnItemClickListener mlistener;

    public RecyclerAdapter(Context context, OnClickListenerInterface listenerInterface) {
        this.context = context;
        this.listenerInterface = listenerInterface;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mlistener = listener;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHandler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerlist, parent, false);
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
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHandler holder, int position) {
        TextView Name = holder.Name;
        TextView Followers = holder.Followers;
        TextView Profile = holder.Profile;
        ImageView imageViewIcon = holder.imageViewIcon;

        Name.setText(userList.get(position).getLogin());
        Followers.setText(userList.get(position).getFollowers_url());
        Profile.setText(userList.get(position).getHtml_url());
        Glide.with(context)
                .load(userList.get(position).getAvatar_url())
                .bitmapTransform(new GlideCircleTransformation(context))
                .into(imageViewIcon);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    @Override
    public Filter getFilter() {
        return userFilter;
    }

    public class ViewHandler extends RecyclerView.ViewHolder {
        private ImageView imageViewIcon;
        private TextView Name;
        private TextView Followers;
        private TextView Profile;

        public ViewHandler(View itemView) {
            super(itemView);
            this.Name = itemView.findViewById(R.id.name);
            this.Followers = itemView.findViewById(R.id.followers);
            this.Profile = itemView.findViewById(R.id.webView);
            this.imageViewIcon = itemView.findViewById(R.id.imagePicture);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        UsersDetails clickedDataItem = userList.get(pos);
                        Intent intent = new Intent(context, MainActivity_Tabs.class);
                        intent.putExtra("login", userList.get(pos).getLogin());
                        intent.putExtra("html_url", userList.get(pos).getHtml_url());
                        intent.putExtra("avatar_url", userList.get(pos).getAvatar_url());
                        intent.putExtra("followers_url", userList.get(pos).getFollowers_url());
                        intent.putExtra("repos_url", userList.get(pos).getRepo_url());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        Toast.makeText(context, "You clicked " + clickedDataItem.getLogin(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }

    }


    Filter userFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            String charString = charSequence.toString();

            if (charString.isEmpty()) {

                userList = duplicateList;
            } else {

                ArrayList<UsersDetails> filteredList = new ArrayList<>();

                for (UsersDetails userdetailsInstance : duplicateList) {

                    if (userdetailsInstance.getLogin().toLowerCase().contains(charString)) {

                        filteredList.add(userdetailsInstance);
                    }
                }
                userList = filteredList;
            }

            FilterResults filterResults = new FilterResults();
            filterResults.count = userList.size();
            filterResults.values = userList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            userList = (ArrayList<UsersDetails>) filterResults.values;
            notifyDataSetChanged();
        }
    };

}
