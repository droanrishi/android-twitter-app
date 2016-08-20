package com.codepath.apps.twitterapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.twitterapp.R;
import com.codepath.apps.twitterapp.models.Tweet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by drishi on 8/18/16.
 */
public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {
    private ArrayList<Tweet> mTweets;

    public Context getContext() {
        return mContext;
    }

    private Context mContext;

    public TweetsAdapter(Context context, ArrayList<Tweet> tweets) {
        mContext = context;
        mTweets = tweets;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tweet tweet = mTweets.get(position);

        holder.tvUserHandle.setText(tweet.getUser().getScreenName().toString());
        holder.tvUserName.setText(tweet.getUser().getName().toString());
        holder.tvTweet.setText(tweet.getBody().toString());
        holder.tvTime.setText(getRelativeTimeAgo(tweet.getCreatedAt()));

        Glide.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(holder.ivUserImage);
        //Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(holder.ivUserImage);
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.ivUserImage)
        ImageView ivUserImage;

        @BindView(R.id.tvUserHandle)
        TextView tvUserHandle;

        @BindView(R.id.tvUserName)
        TextView tvUserName;

        @BindView(R.id.tvTweet)
        TextView tvTweet;

        @BindView(R.id.tvTime)
        TextView tvTime;

        public ViewHolder (View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View view) {
            // TODO Launch Tweet detail Fragment/Activity
        }
    }

    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
}