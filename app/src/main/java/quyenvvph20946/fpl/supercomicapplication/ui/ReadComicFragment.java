package quyenvvph20946.fpl.supercomicapplication.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import quyenvvph20946.fpl.supercomicapplication.R;
import quyenvvph20946.fpl.supercomicapplication.RetrofitIntance;
import quyenvvph20946.fpl.supercomicapplication.adapter.ListReadComicAdapter;
import quyenvvph20946.fpl.supercomicapplication.api.ComicApi;
import quyenvvph20946.fpl.supercomicapplication.model.Comic;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadComicFragment extends Fragment {
    private String comicId;
    private RecyclerView recyclerView;
    private Comic comic;
    private ListReadComicAdapter adapter;
    private SharedPreferences sharedPreferences;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read_comic, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_readComic);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        // Khởi tạo sharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //Lấy comicId
        comicId = sharedPreferences.getString("comicId", "");

        ComicApi comicApi = RetrofitIntance.getApiServiceComic();
        Call<Comic> call = comicApi.getReadComic(comicId);
        call.enqueue(new Callback<Comic>() {
            @Override
            public void onResponse(Call<Comic> call, Response<Comic> response) {
                comic = response.body();
                List<String> imageList = comic.getImages();

                adapter = new ListReadComicAdapter(imageList, getActivity());
                recyclerView.setAdapter(adapter);
                Log.d("RRR", "onFailure: connect success");
            }

            @Override
            public void onFailure(Call<Comic> call, Throwable t) {
                Log.d("RRR", "onFailure: connect failed");
            }
        });



        return view;
    }
}