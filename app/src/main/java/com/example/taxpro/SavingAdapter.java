package com.example.taxpro;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SavingAdapter extends RecyclerView.Adapter<SavingAdapter.ViewHolder>
{
    private ArrayList<String> mDataSet;


    public SavingAdapter()
    {
        mDataSet= new ArrayList<>();
        mDataSet.add("1");
        mDataSet.add("2");
        mDataSet.add("3");
        mDataSet.add("4");
        mDataSet.add("5");
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_saving_state_row_item, parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.getTextView().setText(mDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView textView;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            textView=(TextView) itemView.findViewById(R.id.textView);
        }

        public TextView getTextView()
        {
            return textView;
        }
    }
}
