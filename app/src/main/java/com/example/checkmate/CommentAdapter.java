package com.example.checkmate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{

    private List<Comment> commentList;

    public CommentAdapter(List<Comment> commentList) {
        this.commentList = commentList;
    }
    @NonNull
    @Override
    public CommentAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.comment, parent, false);
        return new CommentAdapter.CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        holder.writerView.setText(comment.getWriterName());
        holder.contentView.setText(comment.getContent());
        LocalDateTime dateTime = comment.getDateTime();
        holder.timestampView.setText(dateTime.getYear() + "." + dateTime.getMonthValue() + "." + dateTime.getDayOfMonth() + " " + dateTime.getHour() + ":" + dateTime.getMinute());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {

        TextView writerView;
        TextView contentView;
        TextView timestampView;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);

            contentView = itemView.findViewById(R.id.comment_content);
            timestampView = itemView.findViewById(R.id.comment_timestamp);
            writerView = itemView.findViewById(R.id.comment_writer);
        }
    }
}
