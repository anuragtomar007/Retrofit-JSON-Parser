package com.example.anurag.retrofit_jsonparser;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SecondFragment extends Fragment {
    private TextView textView;
    private String Followers;
    private String Name;
    private static RecyclerAdapterSecond mAdapter;
    private static RecyclerView recyclerView;
    private static RecyclerView.LayoutManager mLayoutManager;
    private List<UsersDetails> usersDetailsList;


    public SecondFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        textView = view.findViewById(R.id.text2);
        recyclerView = view.findViewById(R.id.recyclerSecond);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        mAdapter = new RecyclerAdapterSecond(getContext());
        recyclerView.setAdapter(mAdapter);


        if (getArguments() != null) {
            Followers = this.getArguments().getString("Followers Url");
            Name = this.getArguments().getString("Name");
        }
        getApi();
        return view;
    }

    private void getApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(GithubUsersAPISecond.USERS_API_BaseURL)
                .build();

        GithubUsersAPISecond githubUsersAPISecond = retrofit.create(GithubUsersAPISecond.class);

        Call<List<UsersDetails>> call = githubUsersAPISecond.getApiS(Name);
        call.enqueue(new Callback<List<UsersDetails>>() {
            @Override
            public void onResponse(Call<List<UsersDetails>> call, Response<List<UsersDetails>> response) {
                usersDetailsList = response.body();
                String[] githubApi = new String[usersDetailsList.size()];
                for (int i = 0; i < usersDetailsList.size(); i++) {
                    githubApi[i] = usersDetailsList.get(i).getLogin();
                    Log.v("New Log to Check", "Check Id :" + githubApi[i]);
                }
                mAdapter.setDatas(usersDetailsList);
            }

            @Override
            public void onFailure(Call<List<UsersDetails>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
