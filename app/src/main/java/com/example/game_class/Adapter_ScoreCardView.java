package com.example.game_class;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class Adapter_ScoreCardView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<Score> scores;
    private OnItemClickListener mItemClickListener;

    public Adapter_ScoreCardView(Context context, ArrayList<Score> scores) {
        this.context = context;
            this.scores = scores;
    }

    public void updateList(ArrayList<Score> scores) {
        this.scores = scores;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            final Score score = getItem(position);
            final ViewHolder genericViewHolder = (ViewHolder) holder;

            genericViewHolder.chart_TXT_name.setText(score.getName());
        }
    }

    @Override
    public int getItemCount() {
        return scores.size();
    }

    private Score getItem(int position) {
        return scores.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView chart_TXT_name;

        public ViewHolder(final View itemView) {
            super(itemView);
            this.chart_TXT_name = itemView.findViewById(R.id.chart_TXT_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), getItem(getAdapterPosition()));
                }
            });
        }
    }

    public void removeAt(int position) {
        scores.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, scores.size());
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, Score score);
    }
}