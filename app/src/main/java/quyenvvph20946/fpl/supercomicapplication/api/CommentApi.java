package quyenvvph20946.fpl.supercomicapplication.api;
import java.util.List;

import quyenvvph20946.fpl.supercomicapplication.model.Comment;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
public interface CommentApi {
    @GET("cmt/list/{id}")
    Call<List<Comment>> getCommentsByID(@Path("id") String comicId);

    @POST("cmt/add")
    Call<Comment> postComment(@Body Comment comment);
}
