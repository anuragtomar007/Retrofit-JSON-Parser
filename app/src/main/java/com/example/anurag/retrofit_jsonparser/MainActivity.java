package com.example.anurag.retrofit_jsonparser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, OnClickListenerInterface {
    private static RecyclerAdapter mAdapter;
    private static RecyclerView recyclerView;
    private static RecyclerView.LayoutManager mLayoutManager;
    private Toolbar toolbar;
    private List<UsersDetails> usersDetailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        mAdapter = new RecyclerAdapter(this, this);
        recyclerView.setAdapter(mAdapter);
        setSupportActionBar(toolbar);
        getApi();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setQueryHint("Search Github Users");
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    private void getApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(GithubUsersAPI.USERS_API_BaseURL)
                .build();

        GithubUsersAPI githubUsersAPI = retrofit.create(GithubUsersAPI.class);

        Call<List<UsersDetails>> call = githubUsersAPI.getApi();
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
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Toast.makeText(this, newText, Toast.LENGTH_SHORT).show();
        mAdapter.getFilter().filter(newText);
        return true;
    }

    @Override
    public void onItemClick(UsersDetails usersDetails) {

    }
}