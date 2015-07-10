package com;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import twitter4j.*;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterServlet extends HttpServlet {
	Twitter twitter = null;
	// enter your keys
	private static final String CONSUMER_KEY = "JVQ3Eb3hqAABGdneXcGR90NB6";
	private static final String CONSUMER_SECRET = "PcCqZq5a3sl0d5kWaSNN5x3kgc81B1tCOOtZfKNXrHPCjikYFl";
	private static final String ACCESS_TOKEN = "79958846-Q9ceHkwceci4FHD7PJ80qUQytPBBwFcpFHi7wE0qd";
	private static final String ACCESS_TOKEN_SECRET = "pMFWoNBJLibAMrToRxgv9SmsCd0nf98kkNlAiBKid4jsy";

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// construct Twitter instance using configuration
		resp.setContentType("text/plain");
		int woeid = Integer.parseInt(req.getParameter("woeid"));
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey(CONSUMER_KEY);
		cb.setOAuthConsumerSecret(CONSUMER_SECRET);
		cb.setOAuthAccessToken(ACCESS_TOKEN);
		cb.setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);

		Configuration conf = cb.build();

		twitter = new TwitterFactory(conf).getInstance();

		// get screen name and # of tweets
		// resp.getWriter().println("screen name: @" + twitter.getScreenName() +
		// ", # of tweets: " + twitter.getAccountTotals().getUpdates()
		// + "\n");

		// IllegalStateException,TwitterException

		// get trends by woeid (Where On Earth ID)

		List<String> trendNames = null;
		try {

			trendNames = getTrendsByWoeid(woeid);
		} catch (Exception e) {
			resp.getWriter().println(e);
		}

		for (Object object : trendNames) {
			resp.getWriter().println(object.toString());
		}

		// String message =
		// "Finally sending tweet using Twitter Developer API!";
		// postTweet(message);

	}

	/**
	 * Gets list of trend names by Where On Earth ID
	 * 
	 * @param woeid
	 *            Where On Earth ID of the place to get trends
	 * @return List of trend names
	 * @throws TwitterException
	 */
	private List getTrendsByWoeid(int woeid) throws TwitterException {
		List trendNames = new ArrayList();
		Trends trends = twitter.getPlaceTrends(woeid);
		Trend[] trend = trends.getTrends();
		for (Trend t : trend) {
			trendNames.add(t.getName().toString());
		}
		return trendNames;
	}

	/**
	 * Posts tweet for specified configuration
	 * 
	 * @param message
	 *            Status message to be tweeted
	 * @throws TwitterException
	 */
	// private static void postTweet(String message) throws TwitterException {
	// twitter.updateStatus(message);
	// }
	// //https://twittercommunity.com/t/what-are-the-list-of-woeids-supported-by-twitter/8493
	// https://wowjava.wordpress.com/2010/11/11/autocomplete-textbox-in-jsp/

}
