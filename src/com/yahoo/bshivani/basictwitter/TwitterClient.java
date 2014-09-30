package com.yahoo.bshivani.basictwitter;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; 
	public static final String 	REST_URL = "https://api.twitter.com/1.1";
	public static final String 	REST_CONSUMER_KEY = "DaR6QQTWfe5SaXZ3UZK6t6uKN";
	public static final String 	REST_CONSUMER_SECRET = "nKfhFvqbj7gYsePZl0NdnlIukRYltwinTmmxXpPBE1vAFnACV3";
	public static final String 	REST_CALLBACK_URL = "oauth://cpbasictweets"; // Change this (here and in manifest)
	
	public static final String 	TWITTER_HOME_TIMELINE_URL= "/statuses/home_timeline.json";
	public static final	int 	MAX_NUM_OF_TWEETS = 25;
	public static final String 	TWITTER_TIMELINE_COUNT_PARAM = "?count=";
	public static final String 	TWITTER_TIMELINE_MAX_ID_PARAM = "max_id=";
	public static long	home_timeline_max_id	= 0;
	public static long	mention_timeline_max_id	= 0;
	public static final	int 	MAX_NUM_OF_MENTIONS = 20;
	public static long	user_timeline_max_id = 0;
	public static final	int 	MAX_NUM_OF_USER_TWEETS = 20;
	public static final String 	TWITTER_POST_TWEET_URL= "/statuses/update.json?status=";
	public static final String 	TWITTER_USER_ACCOUNT = "/account/verify_credentials.json";
	public static final String 	TWITTER_USER_TIMELINE = "/statuses/user_timeline.json";
	public static final String 	TWITTER_MENTION_TIMELINE = "/statuses/mentions_timeline.json";
	public static final String  TWITTER_USER_LOOKUP = "users/lookup.json";
//	public static final String 	TWITTER_USER_ID = "?user_id=";
//	public static long userIdToLookup = 0;
	public static final String 	TWITTER_USER_SCREEN_NAME = "?screen_name=";
	public static String userScreenNameToLookup = "";
	
	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}
	
	public void getHomeTimeline(AsyncHttpResponseHandler handler) {
		String apiURL ;
		if (home_timeline_max_id == 0) { 
			apiURL = getApiUrl(TWITTER_HOME_TIMELINE_URL + TWITTER_TIMELINE_COUNT_PARAM + MAX_NUM_OF_TWEETS);
		} else {
			apiURL = getApiUrl(TWITTER_HOME_TIMELINE_URL + TWITTER_TIMELINE_COUNT_PARAM + MAX_NUM_OF_TWEETS + "&" + TWITTER_TIMELINE_MAX_ID_PARAM + (home_timeline_max_id-1L));
		}
		RequestParams params = new RequestParams();
		params.put("since_id", "1");
		client.get(apiURL, null, handler);
	}

	public void getAccountDetails(AsyncHttpResponseHandler handler) {
		String apiURL = getApiUrl(TWITTER_USER_ACCOUNT);
		client.get(apiURL, null, handler);
	}
	
	public void getUserTimelines(AsyncHttpResponseHandler handler) {
//		String apiURL = getApiUrl(TWITTER_USER_TIMELINE);
		String apiURL, strApi ;
		if (user_timeline_max_id == 0) { 
			strApi = TWITTER_USER_TIMELINE + TWITTER_TIMELINE_COUNT_PARAM + MAX_NUM_OF_USER_TWEETS;
		} else {
			strApi = TWITTER_USER_TIMELINE + TWITTER_TIMELINE_COUNT_PARAM + MAX_NUM_OF_USER_TWEETS + "&" + TWITTER_TIMELINE_MAX_ID_PARAM + (user_timeline_max_id-1L);
		}
//		if (userIdToLookup != 0) {
//			strApi = strApi + "&user_id=" + userIdToLookup;
		if (userScreenNameToLookup != "") {
			strApi = strApi + "&screen_name=" + userScreenNameToLookup;
		
		}
		apiURL = getApiUrl(strApi);
		RequestParams params = new RequestParams();
		params.put("since_id", "1");
		client.get(apiURL, null, handler);
	}

	public void getMentionsTimeline(AsyncHttpResponseHandler handler) {
//		String apiURL = getApiUrl(TWITTER_MENTION_TIMELINE);
		String apiURL ;
		if (mention_timeline_max_id == 0) { 
			apiURL = getApiUrl(TWITTER_MENTION_TIMELINE + TWITTER_TIMELINE_COUNT_PARAM + MAX_NUM_OF_MENTIONS);
		} else {
			apiURL = getApiUrl(TWITTER_MENTION_TIMELINE + TWITTER_TIMELINE_COUNT_PARAM + MAX_NUM_OF_MENTIONS + "&" + TWITTER_TIMELINE_MAX_ID_PARAM + (mention_timeline_max_id-1L));
		}
		RequestParams params = new RequestParams();
		params.put("since_id", "1");
		client.get(apiURL, null, handler);
	}

	
	public void setUserScreenNameToLookup(String uScreenName) {
		
		if (uScreenName != "")
		{
			if (uScreenName.charAt(0) == '@')
				uScreenName = uScreenName.substring(1, uScreenName.length());
			userScreenNameToLookup = uScreenName;
		}
		else
			userScreenNameToLookup = "";
	}

//	public void setUserId(long uid) {
//		if (uid != 0)
//			userIdToLookup = uid;
//		else
//			userIdToLookup = 0;
//	}
	
	public void getUserLookup(AsyncHttpResponseHandler handler) {
		String apiURL ;
		if (userScreenNameToLookup == "") { 
			apiURL = getApiUrl(TWITTER_USER_LOOKUP);
		} else {
//			apiURL = getApiUrl(TWITTER_USER_LOOKUP + TWITTER_USER_ID + userIdToLookup + "&screen_name=%22%22");
			apiURL = getApiUrl(TWITTER_USER_LOOKUP + TWITTER_USER_SCREEN_NAME + userScreenNameToLookup);
		}
		client.get(apiURL, null, handler);
//		userIdToLookup = 0;
	}
	
	public void postTweet(AsyncHttpResponseHandler handler, String strTweet) {
		String apiURL = getApiUrl(TWITTER_POST_TWEET_URL + strTweet);
//		RequestParams params = new RequestParams();
		client.post(apiURL, null, handler);
	}
	

	public long getMaxId_HomeTimeline() {
		return home_timeline_max_id;
	}
	
	public void setMaxId_HomeTimeline(long lowestTweetId) {
		home_timeline_max_id = lowestTweetId;
	}
	
	
	public long getMaxId_MentionTimeline() {
		return mention_timeline_max_id;
	}
	
	public void setMaxId_MentionTimeline(long lowestTweetId) {
		mention_timeline_max_id = lowestTweetId;
	}
	
	public long getMaxId_UserTimeline() {
		return user_timeline_max_id;
	}
	
	public void setMaxId_UserTimeline(long lowestTweetId) {
		user_timeline_max_id = lowestTweetId;
	}
	
	// CHANGE THIS
	// DEFINE METHODS for different API endpoints here
	/*
	public void getInterestingnessList(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("format", "json");
		client.get(apiUrl, params, handler);
	}*/

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}