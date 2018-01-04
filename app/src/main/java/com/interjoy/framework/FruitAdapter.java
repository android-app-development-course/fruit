package com.interjoy.framework;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.interjoy.FruitsIdentification.R;

import java.util.List;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder>{

    private static final String TAG = "FruitAdapter";

    private Context mContext;

    private List<Fruit> mFruitList;

    boolean isShow=false;
    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView fruitImage;
        TextView fruitName;
        TextView fruitInfo;
        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
            fruitName = (TextView) view.findViewById(R.id.fruit_name);
        }
    }

    public FruitAdapter(List<Fruit> fruitList) {
        mFruitList = fruitList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.fruit_item, parent, false);

        //return new ViewHolder(view);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//点击item出现详细信息。
                isShow=false;
                int position = holder.getAdapterPosition();
                Fruit fruit = mFruitList.get(position);
                Intent intent = new Intent(mContext, FruitActivity.class);
                intent.putExtra(FruitActivity.FRUIT_NAME, fruit.getName());
                intent.putExtra(FruitActivity.FRUIT_IMAGE_ID, fruit.getImageId());
                intent.putExtra(FruitActivity.FRUIT_INFO,fruit.getFruitinfo());
                intent.putExtra(FruitActivity.FRUIT_IMAGE,fruit.getFruitBmp());
                mContext.startActivity(intent);
            }
        });
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {//长按item删除功能
                isShow=true;
                int position=holder.getAdapterPosition();
                mFruitList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,mFruitList.size());
                return false;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Fruit fruit = mFruitList.get(position);
        holder.fruitName.setText(fruit.getName());
        if(fruit.getImageId()==0)holder.fruitImage.setImageBitmap(fruit2image(fruit.getFruitBmp()));
        else Glide.with(mContext).load(fruit.getImageId()).into(holder.fruitImage);
    }

    public  Bitmap fruit2image(byte[] in){
        Bitmap fruitImage =BitmapFactory.decodeByteArray(in, 0, in.length);
        return fruitImage;
    }
    @Override
    public int getItemCount() {
        return mFruitList.size();
    }

}
