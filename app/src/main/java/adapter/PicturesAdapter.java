package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nianxin.component.pictures.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import model.Picture;

/**
 * Created by liuhuiliang on 16/6/6.
 */
public class PicturesAdapter extends RecyclerView.Adapter<PicturesAdapter.PictureViewHolder> {

    public static final int COLS = 3;

    protected Context context;

    protected List<Picture> models;

    public PicturesAdapter(Context context, List<Picture> models) {
        this.context = context;
        this.models = models;
    }


    @Override
    public PictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PictureViewHolder((LayoutInflater.from(context).inflate(R.layout.item_pictures, null)));
    }

    @Override
    public void onBindViewHolder(PictureViewHolder holder, int position) {
        int start = position * COLS;
        for (int i = 0; i < COLS; i++) {
            int currentIndex = start + i;
            if (currentIndex >= models.size()) {
                holder.imageViews.get(i).setVisibility(View.GONE);
            } else {
                holder.imageViews.get(i).setVisibility(View.VISIBLE);
                Picasso.with(context)
                        .load("file://" + models.get(currentIndex).getPicturePath())
                        .fit().centerCrop()
                        .placeholder(R.drawable.ic_picture_holder)
                        .into(holder.imageViews.get(i));
            }
        }
    }

    @Override
    public int getItemCount() {
        return models == null ? 0 : (models.size() / COLS + (models.size() % COLS == 0 ? 0 : 1));
    }

    public static class PictureViewHolder extends RecyclerView.ViewHolder {

        public List<ImageView> imageViews = new ArrayList<>();

        public PictureViewHolder(View itemView) {
            super(itemView);
            imageViews.add((ImageView) itemView.findViewById(R.id.iv_picture1));
            imageViews.add((ImageView) itemView.findViewById(R.id.iv_picture2));
            imageViews.add((ImageView) itemView.findViewById(R.id.iv_picture3));
        }
    }
}
