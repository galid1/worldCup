package trip.trip.com.worldcup;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import trip.trip.com.worldcup.lib.BitmapFromAsset;
import trip.trip.com.worldcup.lib.CelebrityManager;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private ArrayList<Celebrity> mDataset;
    private BitmapFromAsset mBitmapFromAsset;

    public MyAdapter(BitmapFromAsset bitmapFromAsset, ArrayList<Celebrity> dataset) {
        this.mDataset = dataset;
        mBitmapFromAsset = bitmapFromAsset;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageViewCelebrityImage;
        TextView textViewCelebriryName;

        public ViewHolder(View itemView) {
            super(itemView);

            imageViewCelebrityImage = itemView.findViewById(R.id.imageview_celebrity_image);
            textViewCelebriryName = itemView.findViewById(R.id.textview_celebrity_name);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout v = (LinearLayout)(inflater.inflate(R.layout.imageview_recyclerview_item, parent , false));
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bitmap bitmap = mBitmapFromAsset.loadBitmap(mDataset.get(position).getImagePath());
        holder.imageViewCelebrityImage.setImageBitmap(bitmap);
        holder.textViewCelebriryName.setText(mDataset.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
