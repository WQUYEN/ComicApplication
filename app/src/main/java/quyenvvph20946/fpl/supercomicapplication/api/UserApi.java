package quyenvvph20946.fpl.supercomicapplication.api;

import java.util.List;

import quyenvvph20946.fpl.supercomicapplication.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {
    @GET("user/list/")
    Call<List<User>> getAllUsers();

    @POST("user/add/")
    Call<User> postUsers(@Body User user);

    @PATCH("user/edit/{id}")
    Call<User> updateUsers(@Path("id") String id, @Body User user);

    @DELETE("user/delete/{id}")
    Call<Void> deleteUsers(@Path("id") String id);

    @POST("user/register")
    Call<User> register(@Body User user);

    @POST("user/login")
    Call<User>  login(@Body User user);
    @GET("user/{id}")
    Call<User> getUserById(@Path("id") String id);
}
