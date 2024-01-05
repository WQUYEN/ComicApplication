package quyenvvph20946.fpl.supercomicapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import quyenvvph20946.fpl.supercomicapplication.R;
import quyenvvph20946.fpl.supercomicapplication.model.Comment;

public class ListCommentsAdapter extends RecyclerView.Adapter<ListCommentsAdapter.ComicViewHolder > {
    private List<Comment> list;
    private Context mContext;
    private Comment comment;

    public ListCommentsAdapter(List<Comment> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ComicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_comment,null);

        return new ComicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComicViewHolder holder,  int position) {
        comment = list.get(position);

        holder.tv_name.setText(comment.getUserId().getUsername()+": ");
        holder.tv_content.setText(comment.getContent());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ComicViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name,tv_content;
        public ComicViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.item_tv_comment_username);
            tv_content = itemView.findViewById(R.id.item_tv_comment_content);
        }
    }
}
