package com.yahoo.bshivani.basictwitter.fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.bshivani.basictwitter.TwitterClient;
import com.yahoo.bshivani.basictwitter.activity.TwitterApplication;
import com.yahoo.bshivani.basictwitter.models.Tweet;

public class UserTimeLineFragment extends TweetsListFragment {
	private TwitterClient client;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		client = TwitterApplication.getRestClient();
//		setActionBar();
		populateTimeline();
	}

	public void populateTimeline() {
		client.getUserTimelines(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				Log.d("debug", json.toString());
				System.out.println("# of Tweets : " + json.length());
				addAll(Tweet.fromJsonArray(json));
				notifyDataSetChanged();
				client.setMaxId(Tweet.lowestTweetIdfromJsonArray(json));
			};

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});
	}
	
	public void setActionBar() {
		client.getAccountDetails(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				Log.d("debug", json.toString());
				try {
					String userScreenName = json.getString("screen_name");
					if (userScreenName != null)
						getActivity().getActionBar().setTitle("@" + userScreenName);
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
