package com.yahoo.bshivani.basictwitter.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.yahoo.bshivani.basictwitter.R;
import com.yahoo.bshivani.basictwitter.TwitterClient;
import com.yahoo.bshivani.basictwitter.activity.TwitterApplication;
import com.yahoo.bshivani.basictwitter.adapters.TweetAdapter;
import com.yahoo.bshivani.basictwitter.listener.EndlessScrollListener;
import com.yahoo.bshivani.basictwitter.models.Tweet;

public abstract class TweetsListFragment extends Fragment {
	private ArrayList<Tweet> tweets;
	private TweetAdapter aTweets;
	//private ArrayAdapter<Tweet> aTweets;
	private ListView lvTweets;
	private TwitterClient client;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Infalte the layout
		View v = inflater.inflate(R.layout.fragment_tweet_list, container, false);
		// Assign our new references
		lvTweets = (ListView) v.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(aTweets);

		lvTweets.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
//				if (getActivity().getClass().getSimpleName() == "TimelineActivity")
				{
					Toast.makeText(getActivity(), "Click User profile Image infront of tweet to check user details ", Toast.LENGTH_SHORT).show();
				}
			}
		});
//		setActionBar();
//		populateTimeline();
//		// return the layout view
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
		
					
		lvTweets.setOnScrollListener(new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				System.out.println("onLoadMore");
				// Triggered only when new data needs to be appended to the list
				// Add whatever code is needed to append new items to your AdapterView
				// or customLoadMoreDataFromApi(totalItemsCount);
				scorllListView(page);
//				customLoadMoreDataFromApi(page); 
			}
		});
		
		return v;
	}
	
	public abstract void scorllListView(int offset);
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_timeline);
		client = TwitterApplication.getRestClient();
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetAdapter(getActivity(), tweets);
		
		
//		UtilsClass.setApplicationContext(getActivity());
//		setUpScrollListenerForListView();
//		setActionBar();
//		populateTimeline();
	}	
	
	
	public void addAll(ArrayList<Tweet> tweets) {
		aTweets.addAll(tweets);
	}

	public void notifyDataSetChanged() {
		aTweets.notifyDataSetChanged();
	}
	
	public void insert(Tweet newerTweet, int pos) {
		aTweets.insert(newerTweet, pos);
	}
//
//	public void populateTimeline() {
//		client.getHomeTimeline(new JsonHttpResponseHandler() {
//			@Override
//			public void onSuccess(JSONArray json) {
//				Log.d("debug", json.toString());
//				System.out.println("# of Tweets : " + json.length());
//				addAll(Tweet.fromJsonArray(json));
//				notifyDataSetChanged();
//				client.setMaxId(Tweet.lowestTweetIdfromJsonArray(json));
//			};
//
//			@Override
//			public void onFailure(Throwable e, String s) {
//				Log.d("debug", e.toString());
//				Log.d("debug", s.toString());
//			}
//		});
//	}
//	
//	public void setActionBar() {
//		client.getAccountDetails(new JsonHttpResponseHandler() {
//			@Override
//			public void onSuccess(JSONObject json) {
//				Log.d("debug", json.toString());
//				try {
//					String userScreenName = json.getString("screen_name");
//					if (userScreenName != null)
//						getActivity().getActionBar().setTitle("@" + userScreenName);
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}				
//			};
//			
//			@Override
//			public void onFailure(Throwable e, String s) {
//				Log.d("debug", e.toString());
//				Log.d("debug", s.toString());
//			}
//		});
//	}
	
}
