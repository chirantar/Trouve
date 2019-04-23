package thenvengers.com.trouve.ui.search;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import thenvengers.com.trouve.R;
import thenvengers.com.trouve.model.Photo;
import thenvengers.com.trouve.model.Photos;
import thenvengers.com.trouve.ui.imageView.ImageHolder;

public class ImageItemViewAdapter extends RecyclerView.Adapter<ImageItemViewAdapter.ImageItemViewHolder> {

    private static final String TAG = "ImageItemViewAdapter";
    private Photos mDataset;
    private Context mContext;
    private TextView mHeader;


    public ImageItemViewAdapter(Context context, Photos dataset) {
        this.mDataset = dataset;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ImageItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.image_item, parent, false);
        return new ImageItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageItemViewHolder holder, int position) {
        final Photo photoObj = mDataset.getPhoto().get(position);
        holder.mHeaderText.setText(photoObj.getId());
        //holder.mDetailsText.setText(mDataset[position].getDetails());
        Glide.with(mContext).load(getPhotoUrl(photoObj)).centerCrop().into(holder.mImageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "in Item click Listener");
                //Toast.makeText(mContext, "Hiii", Toast.LENGTH_SHORT);
                Intent i = new Intent(mContext, ImageHolder.class);
                i.putExtra("m_url", getPhotoUrl(photoObj));
                mContext.startActivity(i);
            }
        });
    }

    private String getPhotoUrl(Photo photoObj) {

        return String.format("https://farm%s.staticflickr.com/%s/%s_%s.jpg",
                photoObj.getFarm(),
                photoObj.getServer(),
                photoObj.getId(),
                photoObj.getSecret());
    }

    @Override
    public int getItemCount() {
        return mDataset.getPhoto().size();
    }

    public static class ImageItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.headerTv) TextView mHeaderText;
        @BindView(R.id.image_view) ImageView mImageView;

        public ImageItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
