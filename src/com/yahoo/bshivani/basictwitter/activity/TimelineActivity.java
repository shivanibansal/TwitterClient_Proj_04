package com.yahoo.bshivani.basictwitter.activity;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.yahoo.bshivani.basictwitter.ProfileActivity;
import com.yahoo.bshivani.basictwitter.R;
import com.yahoo.bshivani.basictwitter.fragment.HomeTimelineFragment;
import com.yahoo.bshivani.basictwitter.fragment.MentionTimelineFragment;
import com.yahoo.bshivani.basictwitter.fragment.TweetsListFragment;
import com.yahoo.bshivani.basictwitter.listener.FragmentTabListener;
import com.yahoo.bshivani.basictwitter.models.Tweet;
import com.yahoo.bshivani.basictwitter.utils.UtilsClass;

public class TimelineActivity extends FragmentActivity {
	private TweetsListFragment fragmentTweetList;
	private final int REQ_COMPOSE_TWEET = 5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		setupTabs();
		
		fragmentTweetList = (TweetsListFragment)getSupportFragmentManager().findFragmentById(R.id.flContainer);
		
//		lvTweets = (ListView) findViewById(R.id.lvTweets);
//		tweets = new ArrayList<Tweet>();
//		tweetAdapter = new TweetAdapter(this, tweets);
		// aTweets = new ArrayAdapter<Tweet>(this,
		// android.R.layout.simple_list_item_1, tweets);
//		lvTweets.setAdapter(tweetAdapter);

		UtilsClass.setApplicationContext(getBaseContext());
//		setUpScrollListenerForListView();
//		setActionBar();
//		populateTimeline();
	}

//	public void setUpScrollListenerForListView() {
//		lvTweets.setOnScrollListener(new EndlessScrollListener() {
//
//			@Override
//			public void onLoadMore(int page, int totalItemsCount) {
//				// TODO Auto-generated method stub
//				System.out.println("onLoadMore");
//				// Triggered only when new data needs to be appended to the list
//				// Add whatever code is needed to append new items to your
//				// AdapterView
//				// or customLoadMoreDataFromApi(totalItemsCount);
//				customLoadMoreDataFromApi(page);
//			}
//		});
//	}

	private void customLoadMoreDataFromApi(int offset) {
		// TODO Auto-generated method stub

//		populateTimeline();
	}

	
	// Inflate the menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	

//	public void setActionBar() {
//		client.getAccountDetails(new JsonHttpResponseHandler() {
//			@Override
//			public void onSuccess(JSONObject json) {
//				Log.d("debug", json.toString());
//				try {
//					String userScreenName = json.getString("screen_name");
//					if (userScreenName != null)
//						getActionBar().setTitle("@" + userScreenName);
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

	public void onClickComposeTweet(MenuItem mi) {
		Intent i = new Intent(this, ComposeActivity.class);
		startActivityForResult(i, REQ_COMPOSE_TWEET);
	}

	public void onClickUserProfile(MenuItem mi) {
		Intent i = new Intent(this, ProfileActivity.class);
		i.putExtra("UserId", 0); // Self Profile
		startActivity(i);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == REQ_COMPOSE_TWEET) {
			if (resultCode == RESULT_OK) {
				/*
				 * Story : User should be taken back to home timeline with new
				 * tweet visible
				 */
				Tweet newerTweet = (Tweet) data.getSerializableExtra("NewerTweet");
//				fragmentTweetList.insert(newerTweet, 0);
//				fragmentTweetList.notifyDataSetChanged();
			}
		}
	}
	
	
	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar
			.newTab()
			.setText("Home")
			.setIcon(R.drawable.ic_home)
			.setTag("HomeTimelineFragment")
			.setTabListener(
				new FragmentTabListener<HomeTimelineFragment>(R.id.flContainer, this, "first",
								HomeTimelineFragment.class));

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
			.newTab()
			.setText("Mentions")
			.setIcon(R.drawable.ic_mentions)
			.setTag("MentionsTimelineFragment")
			.setTabListener(
			    new FragmentTabListener<MentionTimelineFragment>(R.id.flContainer, this, "second",
			    		MentionTimelineFragment.class));

		actionBar.addTab(tab2);
	}
}