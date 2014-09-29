package com.yahoo.bshivani.basictwitter.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.yahoo.bshivani.basictwitter.R;
import com.yahoo.bshivani.basictwitter.TwitterClient;
import com.yahoo.bshivani.basictwitter.R.id;
import com.yahoo.bshivani.basictwitter.R.layout;
import com.yahoo.bshivani.basictwitter.R.menu;
import com.yahoo.bshivani.basictwitter.models.Tweet;


public class ComposeActivity extends Activity {
	public EditText 	etEnterNewTweet;
	public TextView		tvUserName;
	public TextView		tvUserScreenName;
	public ImageView	ivUserImage;
	public String		userProfileImageUrl;
	public final int 	MAX_TWEET_LEN = 140;
	public MenuItem 	miNumChars;
	public MenuItem		miPostTweetBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose_tweet);
		etEnterNewTweet = (EditText) findViewById(R.id.etEnterNewTweet);
		tvUserName = (TextView) findViewById(R.id.tvUserName);
		tvUserScreenName = (TextView) findViewById(R.id.tvUserScreenName);
		ivUserImage = (ImageView) findViewById(R.id.ivUserProfileImage);

		setUserData();
		getActionBar().setTitle("Compose Tweet");
		
		etEnterNewTweet.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				int textLen = etEnterNewTweet.getText().toString().length();
				textLen = MAX_TWEET_LEN - textLen;
				String strTextLen = ""+ textLen;
//				SpannableString spanString;
				
				SpannableString spanString = new SpannableString(strTextLen);
//				if (textLen > MAX_TWEET_LEN) {
				if (textLen < 0) {
					// Error
					spanString.setSpan(new ForegroundColorSpan(Color.RED), 0, spanString.length(), 0);
//					spanString.setSpan(new ForegroundColorSpan(Color.parseColor("#00FFBB")), 0, spanString.length(), 0);
//					miComposeTweetBtn.setEnabled(false);
				} else {
					spanString.setSpan(new ForegroundColorSpan(Color.GRAY), 0, spanString.length(), 0); //fix the color to white
//					miComposeTweetBtn.setEnabled(true);
				}
				miNumChars.setTitle(spanString);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}
	
	// Inflate the menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_compose, menu);
		miNumChars = menu.findItem(R.id.miNChars);
//		miPostTweetBtn = (MenuItem) findViewById(R.id.miPostTweet);
//		miPostTweetBtn.setEnabled(false);
//		miNumChars.setTitle(MAX_TWEET_LEN);
		return true;
	}
	
	public void onClickPostTweet(MenuItem mi) {
		// TODO Auto-generated method stub
		String strTweet = etEnterNewTweet.getText().toString();
		if (strTweet.length() > MAX_TWEET_LEN) {
			Toast.makeText(this, "Max Tweet length should be 140 characters", Toast.LENGTH_SHORT).show();
			return;
		} else if (strTweet.length() == 0) {
			Toast.makeText(this, "Enter message to Tweet", Toast.LENGTH_SHORT).show();
			return;			
		}

		TwitterClient 	client;
		client = TwitterApplication.getRestClient();

		client.postTweet((new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				Log.d("debug", json.toString());
				Intent i = new Intent();
				/* Story : User should be taken back to home timeline with new tweet visible */
				Tweet newerTweetObj = Tweet.fromJson(json);
				i.putExtra("NewerTweet", newerTweetObj);
				setResult(RESULT_OK, i);
				finish();
			};
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		}), strTweet);
	}

	public void setUserData() { 
		TwitterClient 	client = TwitterApplication.getRestClient();
		client.getAccountDetails(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				Log.d("debug", json.toString());
				try {
					String userName = json.getString("name");
					String userScreenName = json.getString("screen_name");
					String uProfileIgUrl = json.getString("profile_image_url_https");
					
					if (userName != null)
						tvUserName.setText(userName);
					if (userScreenName != null)
						tvUserScreenName.setText("@" + userScreenName);
					if (uProfileIgUrl != null)
						userProfileImageUrl = uProfileIgUrl;

					Picasso.with(getBaseContext()).load(userProfileImageUrl).into(ivUserImage);
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
}
