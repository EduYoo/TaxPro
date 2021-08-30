package com.example.taxpro;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class SavingAdapter extends RecyclerView.Adapter<SavingAdapter.ViewHolder>
{
    private ArrayList<Saving> list;
    private Context context;

    public SavingAdapter(Context context, ArrayList<Saving> list)
    {
        this.context=context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view;

        if (context instanceof SavingStateActivity)
        {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_saving_state_row_item,parent,false);
        }
        else if (context instanceof SavingClosingActivity)
        {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_saving_state_row_item,parent,false);

        }
        else
        {
            Log.d("???", "error on \"onCreateViewHolder\" in SavingAdapter");
            view=null;
        }

        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.getNumber_Text().setText(list.get(position).getNumber());
        holder.getName_Text().setText(list.get(position).getName());
        holder.getSaving_Text().setText(list.get(position).getType());
        holder.getdDay_Text().setText(list.get(position).getdDay());
        holder.getDueDate_Text().setText(list.get(position).getDueDate());
        if (context instanceof SavingClosingActivity)
        {
            holder.getClosing_Btn().setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    FireStoreAPI.Bank.closeSaving(list.get(position).getNumber(), list.get(position).getName());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView number_Text;
        private TextView name_Text;
        private TextView saving_Text;
        private TextView dDay_Text;
        private TextView dueDate_Text;
        private Button closing_Btn;

        public ViewHolder(@NonNull View itemView, Context context)
        {
            super(itemView);
            if (context instanceof SavingStateActivity)
            {
                number_Text=(TextView) itemView.findViewById(R.id.SavingStateActivity_Row_Item_Number);
                name_Text=(TextView) itemView.findViewById(R.id.SavingStateActivity_Row_Item_Name);
                saving_Text=(TextView) itemView.findViewById(R.id.SavingStateActivity_Row_Item_SavingType);
                dDay_Text=(TextView) itemView.findViewById(R.id.SavingStateActivity_Row_Item_Dday);
                dueDate_Text=(TextView) itemView.findViewById(R.id.SavingStateActivity_Row_Item_DueDate);
            }
            else if (context instanceof SavingClosingActivity)
            {
                number_Text=(TextView) itemView.findViewById(R.id.SavingClosingActivity_Row_Item_Number);
                name_Text=(TextView) itemView.findViewById(R.id.SavingClosingActivity_Row_Item_Name);
                saving_Text=(TextView) itemView.findViewById(R.id.SavingClosingActivity_Row_Item_SavingType);
                dDay_Text=(TextView) itemView.findViewById(R.id.SavingClosingActivity_Row_Item_Dday);
                dueDate_Text=(TextView) itemView.findViewById(R.id.SavingClosingActivity_Row_Item_DueDate);
                closing_Btn= (Button) itemView.findViewById(R.id.SavingClosingActivity_Row_Item_btn_Closing);
            }

        }

        public TextView getNumber_Text() { return number_Text; }
        public TextView getName_Text() { return name_Text; }
        public TextView getSaving_Text() { return saving_Text; }
        public TextView getdDay_Text() { return dDay_Text; }
        public TextView getDueDate_Text() { return dueDate_Text; }
        public Button getClosing_Btn() { return closing_Btn; }
    }
}
