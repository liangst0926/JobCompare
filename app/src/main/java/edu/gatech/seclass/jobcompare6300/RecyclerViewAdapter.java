package edu.gatech.seclass.jobcompare6300;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private ArrayList<Job> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    RecyclerViewAdapter(Context context, ArrayList<Job> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(mData != null &&  mData.size() > 0){
            Job job = mData.get(position);
            holder.rank_tv.setText(String.valueOf(job.getRank()));
            holder.title_tv.setText(job.getTitle());
            holder.company_tv.setText(job.getCompany());
            if(job.getCurrentJob() == 1){
                holder.rank_tv.setTextColor(Color.RED);
                holder.title_tv.setTextColor(Color.RED);
                holder.company_tv.setTextColor(Color.RED);
            }
        } else {
            return;
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView rank_tv, title_tv, company_tv;

        ViewHolder(View itemView) {
            super(itemView);
            rank_tv = itemView.findViewById(R.id.rank_tv);
            title_tv = itemView.findViewById(R.id.title_tv);
            company_tv = itemView.findViewById(R.id.company_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    Job getItem(int id) {
        return mData.get(id);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
