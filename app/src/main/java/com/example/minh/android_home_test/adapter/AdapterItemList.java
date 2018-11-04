package com.example.minh.android_home_test.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.minh.android_home_test.R;
import com.example.minh.android_home_test.model.Item;
import com.example.minh.android_home_test.utils.Utils;
import java.util.List;
import java.util.Random;

public class AdapterItemList extends RecyclerView.Adapter<AdapterItemList.ViewHolder> {
    private Context context;
    private List<Item>itemList;

    public AdapterItemList(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.item_list, parent,false);
        return new  ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String multiLine = Utils.getMultiline(itemList.get(position).getText());
        holder.txtItem.setText(multiLine);


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtItem;
        private Random rnd = new Random();
         ViewHolder(View itemView) {
            super(itemView);
             txtItem = itemView.findViewById(R.id.txtItem);
             txtItem.setBackgroundResource( R.drawable.bg_item);
             GradientDrawable gradientDrawable = (GradientDrawable)txtItem.getBackground();
             int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(150), rnd.nextInt(255));
             gradientDrawable.setColor(color);
             txtItem.setOnClickListener(this);


         }

         @Override
         public void onClick(View v) {
             if(v == txtItem){
                 Log.d("Minh", "onClick: " + txtItem.getText());
             }
         }
     }
}
