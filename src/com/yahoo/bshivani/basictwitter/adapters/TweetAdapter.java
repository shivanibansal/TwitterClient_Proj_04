package com.yahoo.bshivani.basictwitter.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yahoo.bshivani.basictwitter.ProfileActivity;
import com.yahoo.bshivani.basictwitter.R;
import com.yahoo.bshivani.basictwitter.activity.TwitterApplication;
import com.yahoo.bshivani.basictwitter.models.*;
import com.yahoo.bshivani.basictwitter.utils.*;

public class TweetAdapter extends ArrayAdapter<Tweet> {

	public TweetAdapter(Context context, ArrayList<Tweet> tweet) {
		super(context, R.layout.tweet_item, tweet);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Tweet tweet = getItem(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.tweet_item, parent, false);
		}
		TextView tvTweetBody = (TextView) convertView.findViewById(R.id.tvBody);
		TextView tvTweetName = (TextView) convertView.findViewById(R.id.tvName);
		TextView tvTweetScreenName = (TextView) convertView.findViewById(R.id.tvScreenName);
		ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
		TextView tvTimeStamp = (TextView) convertView.findViewById(R.id.tvTimeStamp);
		
		tvTweetBody.setText(tweet.getBody());
		tvTweetName.setText(tweet.getUser().getName());
		tvTweetScreenName.setText("@" + tweet.getUser().getScreenName());
		
		ivProfileImage.setImageResource(android.R.color.transparent);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(tweet.getUser().getProfieImageUrl(), ivProfileImage);

//		String strTimeStamp = tweet.getCreatedAt();
		String strTimeStamp = UtilsClass.getRelativeTime(tweet.getCreatedAt());
		tvTimeStamp.setText(strTimeStamp);
		ivProfileImage.setTag(tvTweetScreenName.getText().toString());
		
		ivProfileImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				Toast.makeText(getContext(), "Clicked Here ", Toast.LENGTH_SHORT).show();
				Intent i = new Intent(getContext(), ProfileActivity.class);

				// FIND USER ID
//				String uScreenName = v.findViewById(R.id.tvScreenName).toString();
				String uScreenName = (String)v.getTag();
				i.putExtra("UserScreenName", uScreenName); // User Profile
//				i.putExtra("UserId", 0); // User Profile
//				TwitterApplication.getRestClient().setUserId(0);
				TwitterApplication.getRestClient().setUserScreenNameToLookup(uScreenName);
				getContext().startActivity(i);	
			}
		});
		
//		lvTweets.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				// TODO Auto-generated method stub
////				if (getActivity().getClass().getSimpleName() == "TimelineActivity")
//				{
//					Toast.makeText(getActivity(), "Clicked - " + getActivity().getClass().getSimpleName() + "- " + position, Toast.LENGTH_SHORT).show();
//					Tweet twt = tweets.get(position);
//					
//					Intent i = new Intent(getActivity(), ProfileActivity.class);
//					i.putExtra("UserId", twt.getUser().getUid()); // User Profile
//					TwitterApplication.getRestClient().setUserId(twt.getUser().getUid());
//					startActivity(i);
//				}
//			}
//		});
		
		return convertView;
	}
}

//@Override
//public View getView(int position, View convertView, ViewGroup parent) {
//	// Lookup view for data population
//	TextView tvMsg = (TextView) convertView.findViewById(R.id.tvUserMsg);
//	// Populate the data into the template view using the data object
//	tvMsg.setText(uMsg.strMessage);
//	// Return the completed view to render on screen
//	return convertView;
//}