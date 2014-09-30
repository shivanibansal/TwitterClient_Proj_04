package com.yahoo.bshivani.basictwitter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.yahoo.bshivani.basictwitter.activity.TwitterApplication;

public class ProfileActivity extends FragmentActivity {
	private ImageView 	ivUserProfileImage;
	private	TextView 	tvUserName;
	private	TextView	tvUserTagLine;
	private	TextView 	tvFollowers;
	private	TextView 	tvFollowing;
	private static long		userId = 0; // 0 means self
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		ivUserProfileImage = (ImageView)findViewById(R.id.ivUserProfileImage);
		tvUserName = (TextView)findViewById(R.id.tvUserName);
		tvUserTagLine = (TextView)findViewById(R.id.tvUserTagLine);
		tvFollowers = (TextView)findViewById(R.id.tvFollowers);
		tvFollowing = (TextView)findViewById(R.id.tvFollowing);
		userId = 0;
		userId = getIntent().getLongExtra("UserId", 0);
		setUserData();
	}
	
	public static long getUserId (){
		return userId;
	}
	
	public void setUserData() { 
	TwitterClient 	client = TwitterApplication.getRestClient();
//	client.setUserId(userId);
	if (userId == 0) {
		client.getAccountDetails(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				String userProfileImageUrl = "";
				Log.d("debug", json.toString());
				try {
					String userName = json.getString("name");
					String userScreenName = json.getString("screen_name");
					String userTagLine = json.getString("description");
					String uProfileIgUrl = json.getString("profile_image_url_https");
					int	   iFollower = json.getInt("followers_count");
				int	   iFollowing = json.getInt("friends_count");
				
				if (userName != null)
					tvUserName.setText(userName);
				if (userScreenName != null)
					getActionBar().setTitle(userScreenName);
				else
					getActionBar().setTitle(userName);
//					tvUserScreenName.setText("@" + userScreenName);
				if (userTagLine != null)
					tvUserTagLine.setText(userTagLine);
				
				if (uProfileIgUrl != null)
					userProfileImageUrl = uProfileIgUrl;
				tvFollowers.setText(iFollower + " Followers");
				tvFollowing.setText(iFollowing + " Following");

				Picasso.with(getBaseContext()).load(userProfileImageUrl).into(ivUserProfileImage);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			};
		
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});
	}
	else { // For a particular User
		client.getUserLookup(new JsonHttpResponseHandler() {
			@Override
			public void onFailure(Throwable arg0) {
				// TODO Auto-generated method stub
				super.onFailure(arg0);
			}
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
			}
			
			@Override
			public void onSuccess(int arg0, JSONArray arg1) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
			}
			
//			@Override
//			public void onSuccess(JSONArray arg0) {
//				// TODO Auto-generated method stub
//				Log.d("debug", arg0.toString());
//				super.onSuccess(arg0);
//			}
			@Override
			public void onSuccess(String arg0) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0);
			}
			@Override
//			public void onSuccess(JSONObject json) {
			public void onSuccess(JSONArray arg0) {
				String userProfileImageUrl = "";
				JSONObject json;
				try {
					json = arg0.getJSONObject(0);
					Log.d("debug", json.toString());

					String userName = json.getString("name");
					String userScreenName = json.getString("screen_name");
					String userTagLine = json.getString("description");
					String uProfileIgUrl = json.getString("profile_image_url_https");
					int	   iFollower = json.getInt("followers_count");
					int	   iFollowing = json.getInt("friends_count");
				
					if (userName != null)
						tvUserName.setText(userName);
					if (userScreenName != null)
						getActionBar().setTitle(userScreenName);
					else
						getActionBar().setTitle(userName);
//					tvUserScreenName.setText("@" + userScreenName);
					if (userTagLine != null)
						tvUserTagLine.setText(userTagLine);
				
					if (uProfileIgUrl != null)
						userProfileImageUrl = uProfileIgUrl;
					tvFollowers.setText(iFollower + " Followers");
					tvFollowing.setText(iFollowing + " Following");

					Picasso.with(getBaseContext()).load(userProfileImageUrl).into(ivUserProfileImage);
				} catch (JSONException e) {
					e.printStackTrace();
				}			
			};
		
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});
	}

	}
}
