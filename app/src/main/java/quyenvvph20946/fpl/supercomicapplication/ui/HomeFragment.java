package quyenvvph20946.fpl.supercomicapplication.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import quyenvvph20946.fpl.supercomicapplication.R;
import quyenvvph20946.fpl.supercomicapplication.RetrofitIntance;
import quyenvvph20946.fpl.supercomicapplication.adapter.ListComicsAdapter;
import quyenvvph20946.fpl.supercomicapplication.api.ComicApi;
import quyenvvph20946.fpl.supercomicapplication.model.Comic;
import quyenvvph20946.fpl.supercomicapplication.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements ListComicsAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    private ComicApi comicApi;
    private List<Comic> list = new ArrayList<>();
    private ListComicsAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView_Comics);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));


        comicApi = RetrofitIntance.getApiServiceComic();
        Call<List<Comic>> call = comicApi.getAllComics();
        call.enqueue(new Callback<List<Comic>>() {
            @Override
            public void onResponse(Call<List<Comic>> call, Response<List<Comic>> response) {
                if (response.isSuccessful()){
                    Log.e("111", "onResponse: "+ response.body().toString());
                    list = response.body();
                    adapter = new ListComicsAdapter(list, getActivity());
                    recyclerView.setAdapter(adapter);
                    adapter.setItemClickListener(new ListComicsAdapter.ItemClickListener() {
                        @Override
                        public void onItemClick(Comic comic) {
                            // Xử lý sự kiện khi item được click
                            // Thay đổi Fragment bằng cách thêm một Fragment mới hoặc thay đổi nội dung của Fragment hiện tại
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container, new DetailComicFragment());
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Comic>> call, Throwable t) {
                // Xử lý khi gặp lỗi
                Log.e("111", "onResponse: connect failed");

            }
        });

        return view;
    }

    @Override
    public void onItemClick(Comic comic) {
        // Handle item click here
        // Xử lý sự kiện khi item được click
        // Thay đổi Fragment bằng cách thêm một Fragment mới hoặc thay đổi nội dung của Fragment hiện tại
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new DetailComicFragment());
        transaction.addToBackStack(null);
        transaction.commit();

    }
}