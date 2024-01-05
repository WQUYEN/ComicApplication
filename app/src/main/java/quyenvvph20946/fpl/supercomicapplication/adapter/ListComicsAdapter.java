package quyenvvph20946.fpl.supercomicapplication.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;


import quyenvvph20946.fpl.supercomicapplication.R;
import quyenvvph20946.fpl.supercomicapplication.RetrofitIntance;
import quyenvvph20946.fpl.supercomicapplication.api.ComicApi;
import quyenvvph20946.fpl.supercomicapplication.api.UserApi;
import quyenvvph20946.fpl.supercomicapplication.model.Comic;
import quyenvvph20946.fpl.supercomicapplication.model.User;
import quyenvvph20946.fpl.supercomicapplication.ui.DetailComicFragment;

public class ListComicsAdapter extends RecyclerView.Adapter<ListComicsAdapter.ComicViewHolder> {
    private List<Comic> list;
    private Context mContext;
    private Activity mActivity;
    private ComicApi comicApi;
    private SharedPreferences sharedPreferences;



    public ListComicsAdapter(List<Comic> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
        comicApi = RetrofitIntance.getApiServiceComic();
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public interface ItemClickListener {
        void onItemClick(Comic comic);
    }
    @NonNull
    @Override
    public ComicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = ((Activity)mContext).getLayoutInflater().inflate(R.layout.item_list_comics,parent,false);
        return new ComicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComicViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Comic comic = list.get(position);
        holder.tv_title.setText(comic.getTitle());
        Picasso.get().load(comic.getCoverImage()).into(holder.cover_img);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);

        // Xử lý sự kiện click trên item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(comic);
                    // Lưu thông tin người dùng vào SharedPreferences

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("comicId", comic.get_id());
                    editor.apply();
                    Log.d("AAAA", "onClick: item list " + comic.get_id());

                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ComicViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title,tv_author;
        private ImageView cover_img;
        private Button detail;
        public ComicViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.item_titleTextView);
            cover_img = itemView.findViewById(R.id.item_coverImageView);

        }
    }
}
