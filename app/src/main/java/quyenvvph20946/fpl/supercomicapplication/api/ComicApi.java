package quyenvvph20946.fpl.supercomicapplication.api;

import java.util.List;

import quyenvvph20946.fpl.supercomicapplication.model.Comic;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ComicApi {
    // Lấy danh sách truyện
    @GET("comic/list")
    Call<List<Comic>> getAllComics();

    // Lấy chi tiết truyện theo ID
    @GET("comic/detail/{id}")
    Call<Comic> getDetailComic(@Path("id") String comicId);

    // Đọc truyện
    @GET("comic/{id}/read")
    Call<Comic> getReadComic(@Path("id") String comicId);
}
