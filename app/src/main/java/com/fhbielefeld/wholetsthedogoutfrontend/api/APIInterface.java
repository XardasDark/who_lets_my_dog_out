package com.fhbielefeld.wholetsthedogoutfrontend.api;

import com.fhbielefeld.wholetsthedogoutfrontend.api.models.GetUsersModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIInterface {

/*    @GET("/api/unknown")
    Call<MultipleResource> doGetListResources();

    @POST("/api/users")
    Call<User> createUser(@Body User user);

    @GET("/api/users?")
    Call<UserList> doGetUserList(@Query("page") String page);

    @FormUrlEncoded
    @POST("/api/users?")
    Call<UserList> doCreateUserWithField(@Field("name") String name, @Field("job") String job);

    @GET("/user")
    Call<Ingredient> getUser(@Path("username") String username);*/

    @GET("/user/{username}")
    Call<List<GetUsersModel>> getUser(@Path("username") String username);
}
