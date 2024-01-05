package quyenvvph20946.fpl.supercomicapplication;

import quyenvvph20946.fpl.supercomicapplication.api.ComicApi;
import quyenvvph20946.fpl.supercomicapplication.api.CommentApi;
import quyenvvph20946.fpl.supercomicapplication.api.UserApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitIntance {
    private static final String BASE_URL = "http://192.168.1.247:8000/";
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static ComicApi getApiServiceComic() {
        return retrofit.create(ComicApi.class);
    }
    public static UserApi getApiUser(){
        return  retrofit.create(UserApi.class);
    }
    public static CommentApi getApiComment(){
        return retrofit.create(CommentApi.class);
    }
}
