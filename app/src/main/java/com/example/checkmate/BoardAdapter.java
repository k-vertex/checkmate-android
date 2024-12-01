package com.example.checkmate;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.BoardViewHolder> {

    private List<Article> articleList;

    public BoardAdapter(List<Article> articleList) {
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public BoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.board_cell, parent, false);
        return new BoardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BoardViewHolder holder, int position) {
        Article article = articleList.get(position);
        holder.titleView.setText(article.getTitle());
        holder.contentView.setText(article.getContent());
        LocalDateTime articleTime = article.getDateTime();
        Duration duration = Duration.between(articleTime,LocalDateTime.now());
        String timestampStr = "";
        long second = duration.getSeconds();
        if(second < 60)
            timestampStr = second + "초전";
        else if(second < 3600)
            timestampStr = duration.toMinutes() + "분전";
        else if(second < 86400)
            timestampStr = duration.toHours() + "시간전";
        else {
            timestampStr = articleTime.getMonthValue() + "." + articleTime.getDayOfMonth();
        }
        holder.timestampView.setText(timestampStr);
        holder.writerView.setText(article.getWriterName());
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void addItems(List<Article> newItems) {
        int positionStart = articleList.size();
        articleList.addAll(newItems);
        notifyItemRangeInserted(positionStart, newItems.size());
    }

    class BoardViewHolder extends RecyclerView.ViewHolder {

        TextView titleView;
        TextView contentView;
        TextView likeView;
        TextView commentView;
        TextView timestampView;
        TextView writerView;

        public BoardViewHolder(@NonNull View itemView) {
            super(itemView);

            titleView = itemView.findViewById(R.id.article_title);
            contentView = itemView.findViewById(R.id.article_content);
            likeView = itemView.findViewById(R.id.article_like);
            commentView = itemView.findViewById(R.id.article_comment);
            timestampView = itemView.findViewById(R.id.article_timestamp);
            writerView = itemView.findViewById(R.id.article_writer);
        }
    }
}
