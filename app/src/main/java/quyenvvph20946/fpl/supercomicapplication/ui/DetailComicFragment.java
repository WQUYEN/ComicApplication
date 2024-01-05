package quyenvvph20946.fpl.supercomicapplication.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import quyenvvph20946.fpl.supercomicapplication.R;
import quyenvvph20946.fpl.supercomicapplication.RetrofitIntance;
import quyenvvph20946.fpl.supercomicapplication.adapter.ListCommentsAdapter;
import quyenvvph20946.fpl.supercomicapplication.api.ComicApi;
import quyenvvph20946.fpl.supercomicapplication.api.CommentApi;
import quyenvvph20946.fpl.supercomicapplication.api.UserApi;
import quyenvvph20946.fpl.supercomicapplication.model.Comic;
import quyenvvph20946.fpl.supercomicapplication.model.Comment;
import quyenvvph20946.fpl.supercomicapplication.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailComicFragment extends Fragment {
    private TextView tv_title, tv_author,tv_years,tv_description,tv_showMore,tv_collapse,tvContent ;
    private EditText ed_comment;
    private ImageView img;
    private Comic comic;
    private Comment comment;
    private String comicId;
    private RecyclerView recyclerViewCmt;
    private ListCommentsAdapter adapter;
    private Button btn_read_comic,btn_post_comment;
    private List<Comment> listComment = new ArrayList<>();

    private boolean isExpanded = false;
    private SharedPreferences sharedPreferences;
    private ComicApi comicApi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_comic, container, false);
        //Ánh xạ
        tv_title = view.findViewById(R.id.tv_detail_title);
        tv_author = view.findViewById(R.id.tv_detail_author);
        tv_years = view.findViewById(R.id.tv_detail_years);
        tv_description = view.findViewById(R.id.tv_detail_description);
        tv_showMore = view.findViewById(R.id.tv_detail_show_more);
        tv_collapse = view.findViewById(R.id.tv_detail_collapse);
        img = view.findViewById(R.id.img_detail_cover);
        btn_read_comic = view.findViewById(R.id.btn_read_comic);
        btn_post_comment =view.findViewById(R.id.btn_comment);
        ed_comment = view.findViewById(R.id.ed_comment);

        //Adapter
        recyclerViewCmt = view.findViewById(R.id.recyclerView_Comment);
        recyclerViewCmt.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ListCommentsAdapter(listComment, getActivity());
        recyclerViewCmt.setAdapter(adapter);

        // Khởi tạo sharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        // Sử dụng userId hoặc thông tin người dùng khác đã lấy theo nhu cầu

        String userId = sharedPreferences.getString("userId", "");
        Log.d("userId", "onCreateView: " + userId);
        // Sử dụng ComicId
        comicId = sharedPreferences.getString("comicId", "");
        Log.d("GGG", "onCreateView: "+comicId);
        // Tiếp tục xử lý dữ liệu và giao diện
        comicApi = RetrofitIntance.getApiServiceComic();
        Call<Comic> call = comicApi.getDetailComic(comicId);
        call.enqueue(new Callback<Comic>() {
            @Override
            public void onResponse(Call<Comic> call, Response<Comic> response) {
                if (response.isSuccessful()){
                    Comic comicById = response.body(); // Cập nhật biến comic với dữ liệu từ response
                    Log.d("EEEE", "onResponse: "+comicById);
                    // Hiển thị thông tin trên giao diện
                    Picasso.get()
                            .load(comicById.getCoverImage())
                            .error(R.drawable.ic_launcher_foreground)
                            .into(img);
                    tv_title.setText("Tên truyện: " + comicById.getTitle());
                    tv_author.setText("Tên tác giả: "+ comicById.getAuthor());
                    tv_years.setText("Năm phát hành: "+ String.valueOf(comicById.getYears()));
                    tv_description.setText("Giới thiệu: "+ comicById.getDescription());
                }
                Log.d("EEEE", "onResponse: Comic null data");

            }

            @Override
            public void onFailure(Call<Comic> call, Throwable t) {
                Log.d("AAAA", "onFailure: connect that bai");

            }
        });

        getAllCmt();
        //Post Comment
        btn_post_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Lấy user theo Userid đã đăng nhập vào
                UserApi userApi = RetrofitIntance.getApiUser();
                Call<User> callUserById = userApi.getUserById(userId);
                callUserById.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User userById = response.body();

                        //Lấy user về thành công tiến thành post comment
                        String content = ed_comment.getText().toString();

                        Comment.User userCmt = new Comment.User(userById.get_id(), userById.getUsername());

                        Log.d("EEEE", "onResponse: usercmt"+userCmt.getUsername());
                        Comment comment1 = new Comment(comicId,userCmt,content);

                        CommentApi commentApi = RetrofitIntance.getApiComment();
                        Call<Comment> callPostCmt = commentApi.postComment(comment1);
                        callPostCmt.enqueue(new Callback<Comment>() {
                            @Override
                            public void onResponse(Call<Comment> call, Response<Comment> response) {
                                // Thêm comment mới vào danh sách và thông báo cập nhật adapter
                                Comment comment1 = new Comment(comicId,userCmt,content);
                                listComment.add(comment1);
                                adapter.notifyDataSetChanged();
                                Log.d("EEEE", "onResponse: da chay server ");

                                ed_comment.setText("");
                            }

                            @Override
                            public void onFailure(Call<Comment> call, Throwable t) {
                                // Xử lý lỗi khi yêu cầu API không thành công
                                Log.d("eeeee", "onFailure: Không thêm được comment");
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.d("eeeee", "onFailure: Không lấy được user về");

                    }
                });


            }
        });

        //Event click show more
        tv_showMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị toàn bộ nội dung mô tả
                tv_description.setMaxLines(Integer.MAX_VALUE);
                tv_showMore.setVisibility(View.GONE); // Ẩn tv
                tv_collapse.setVisibility(View.VISIBLE);
                isExpanded = true;
            }
        });
        tv_collapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpanded) {
                    // Thu gọn nội dung mô tả lại chỉ hiển thị 3 dòng
                    tv_description.setMaxLines(3);
                    tv_showMore.setVisibility(View.VISIBLE);
                    tv_collapse.setVisibility(View.GONE);
                    isExpanded = false;
                }
            }
        });

        //Read Comic
        btn_read_comic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new ReadComicFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }
    public void getAllCmt(){
        CommentApi commentApi = RetrofitIntance.getApiComment();
        Call<List<Comment>> commentApiCall = commentApi.getCommentsByID(comicId);
        commentApiCall.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful()) {
                    List<Comment> commentList = response.body();
                    listComment.addAll(commentList);

                    adapter.notifyDataSetChanged();
                    Log.d("AAAA", "onResponse: connect được roi");
                } else {
                    // Xử lý lỗi khi yêu cầu API không thành công
                    Log.d("eeeee", "onFailure: Không lấy được danh sách cmttt");
                }
            }
            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                // Xử lý lỗi khi yêu cầu API thất bại
                Log.d("eeeee", "onFailure: Không lấy được danh sách cmt");
            }
        });
    }

}