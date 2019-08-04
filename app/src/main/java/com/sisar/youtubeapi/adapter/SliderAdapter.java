package com.sisar.youtubeapi.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.sisar.youtubeapi.Pojo.MediaList;
import com.sisar.youtubeapi.R;
import com.sisar.youtubeapi.databinding.SlideradapteBinding;

import java.util.ArrayList;

public class SliderAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<MediaList> mImageList;
    private LayoutInflater layoutInflater;
    private OnClickListener mOnClickListener;

    public SliderAdapter(Context mContext, ArrayList<MediaList> mImageList) {
        this.mContext = mContext;
        this.mImageList = mImageList;
    }

    public interface OnClickListener {
        void onMediaSelect(int position, View view);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(mContext);

        final SlideradapteBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.slideradapte, container, false);

        final MediaList item = mImageList.get(position);

        if (item.getFiletype().equals("image")) {
            binding.imageView.setVisibility(View.VISIBLE);
            binding.videoView.setVisibility(View.GONE);
//.thumbnail(Glide.with(mContext).load(R.drawable.image_loader))
            Glide.with(mContext)
                    .load(item.getPath())
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(binding.imageView);
        } else if (item.getFiletype().equals("url")) {
            binding.videoView.setVisibility(View.VISIBLE);
            binding.imageView.setVisibility(View.VISIBLE);
        }

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null)
                    mOnClickListener.onMediaSelect(position, binding.getRoot());
            }
        });

        container.addView(binding.getRoot());

        return binding.getRoot();
    }

    @Override
    public int getCount() {
        return mImageList == null ? 0 : mImageList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
