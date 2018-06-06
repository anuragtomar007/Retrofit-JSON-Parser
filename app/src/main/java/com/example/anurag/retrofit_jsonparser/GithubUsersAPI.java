package com.example.anurag.retrofit_jsonparser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GithubUsersAPI {
    String USERS_API_BaseURL = "https://api.github.com/";

    @GET("users")
    Call<List<UsersDetails>> getApi();
}