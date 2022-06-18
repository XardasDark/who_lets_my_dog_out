package com.fhbielefeld.wholetsthedogoutfrontend.api;

import com.fhbielefeld.wholetsthedogoutfrontend.api.models.AllChatsModel;
import com.fhbielefeld.wholetsthedogoutfrontend.api.models.GetUsersByDistanceModel;
import com.fhbielefeld.wholetsthedogoutfrontend.api.models.GetUsersModel;
import com.fhbielefeld.wholetsthedogoutfrontend.api.models.LoginUserModel;
import com.fhbielefeld.wholetsthedogoutfrontend.api.models.MessagesModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
    Call<UserList> doCreateUserWithField(@Field("name") String name, @Field("job") String job);*/

    @GET("/chat/{username}")
    Call<List<MessagesModel>> getMessages(@Path("username") String username, @Header("currentUser") String currentUser);

    @GET("/chat")
    Call<List<AllChatsModel>> getAllChats(@Header("username") String username);

    @PUT("/user")
    Call<GetUsersModel> changeUser(@Header("username") String username, @Header("firstname") String firstname, @Header("lastname") String lastname, @Header("birthday") String birthday, @Header("email") String email, @Header("picture") String picture, @Header("dogwalker") Boolean dogwalker, @Header("latitude") Double latitude, @Header("longitude") Double longitude);

    @POST("/user")
    Call<GetUsersModel> createUser(@Header("firstname") String firstname, @Header("lastname") String lastname, @Header("username") String username, @Header("password") String password, @Header("birthday") String birthday, @Header("email") String email, @Header("picture") String picture, @Header("dogwalker") Boolean dogwalker, @Header("latitude") Number latitude, @Header("longitude") Number longitude);

    @POST("/login")
    Call<LoginUserModel> loginUser(@Header("username") String username, @Header("password") String password);

    @GET("/user")
    Call<List<GetUsersByDistanceModel>> getUserRange(@Header("username") String username, @Header("range") String range);

    @GET("/user/{username}")
    Call<List<GetUsersModel>> getUser(@Path("username") String username);
}
