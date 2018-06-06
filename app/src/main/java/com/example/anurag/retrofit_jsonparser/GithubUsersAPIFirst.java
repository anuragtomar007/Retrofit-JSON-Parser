package com.example.anurag.retrofit_jsonparser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubUsersAPIFirst {
    String USERS_API_BaseURL = "https://api.github.com/users/";

    @GET("{name}/followers")
    Call<List<UsersDetails>> getApiF(@Path("name") String name);
}