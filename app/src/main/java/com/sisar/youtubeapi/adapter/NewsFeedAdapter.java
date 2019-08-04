package com.sisar.youtubeapi.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.sisar.youtubeapi.Pojo.MediaList;
import com.sisar.youtubeapi.Pojo.NewsFeed;
import com.sisar.youtubeapi.R;
import com.sisar.youtubeapi.databinding.ListNewsfeedBinding;

import java.util.ArrayList;

public class NewsFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private LayoutInflater layoutInflater;
    ArrayList<NewsFeed> mList;
    private Context mContext;
    private OnClickListener mOnClickListener;

    public NewsFeedAdapter(Context context, ArrayList<NewsFeed> list) {
        mContext = context;
        mList = list;
    }

    public interface OnClickListener {

        void onMediaClick(int position, int mediaPosition);
    }
    public void setList(ArrayList<NewsFeed> list) {
        mList = list;
    }
    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        if (viewType == VIEW_TYPE_ITEM) {
            ListNewsfeedBinding mBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_newsfeed, parent, false);
            return new MyViewHolder(mBinding);

        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_item, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        NewsFeed item = mList.get(position);
        if (holder instanceof MyViewHolder) {
            final ListNewsfeedBinding binding=((MyViewHolder)holder).mbinding;
           // binding.tvName.setText(item.getUserName());

            if (item.getMediaList().size() > 0) {
                binding.vpImages.setVisibility(View.VISIBLE);
                binding.llDotLayout.setVisibility(View.VISIBLE);

                final ArrayList<MediaList> mediaList = mList.get(position).getMediaList();

                if (mediaList.size() > 0) {
                    SliderAdapter pagerAdapter = new SliderAdapter(mContext, mediaList);
                    binding.vpImages.setAdapter(pagerAdapter);
                    setUiPageViewController(binding.llDotLayout, mediaList.size(), 0, true);

                    binding.vpImages.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {
                            setUiPageViewController(binding.llDotLayout, mediaList.size(), position, false);
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });


                    pagerAdapter.setOnClickListener(new SliderAdapter.OnClickListener() {
                        @Override
                        public void onMediaSelect(int mediaPosition, View view) {
                            if (mOnClickListener != null)
                                mOnClickListener.onMediaClick(position, mediaPosition);
                        }
                    });

                }

            } else {
                binding.vpImages.setVisibility(View.GONE);
                binding.llDotLayout.setVisibility(View.GONE);
            }

        }else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }
    @Override
    public int getItemViewType(int position) {
        return mList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }
    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ListNewsfeedBinding mbinding;

        public MyViewHolder(ListNewsfeedBinding binding) {
            super(binding.getRoot());
            mbinding=binding;

        }
    }
    class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }

    public void setUiPageViewController(LinearLayout dotLayout, int dotsCount, int position, boolean isLoad) {

        ImageView[] dots = new ImageView[dotsCount];
        dotLayout.removeAllViews();

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(mContext);

            if (i == position)
                dots[i].setImageDrawable(mContext.getResources().getDrawable(R.drawable.selected_dot));
            else
                dots[i].setImageDrawable(mContext.getResources().getDrawable(R.drawable.default_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            dotLayout.addView(dots[i], params);
        }

        dotLayout.setVisibility(dotsCount == 1 ? View.GONE : View.VISIBLE);

    }
}
