package com.example.anurag.retrofit_jsonparser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubUsersAPISecond {
    String USERS_API_BaseURL = "https://api.github.com/users/";

    @GET("{name}/repos")
    Call<List<UsersDetails>> getApiS(@Path("name") String name);
}