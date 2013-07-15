package com.javatechig.feedreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.javatechig.feedreader.model.FeedItem;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.PushService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FeedListActivity extends Activity {

    private ArrayList<FeedItem> feedList = null;
    private ProgressBar progressbar = null;
    private ListView feedListView = null;
    private RequestQueue mRequestQueue;

    //
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.initialize(this, "ETsSaCTzyfkpHMitM4qeLHbGoNd9NjYkoiuDkUqj", "DC2ItrQKMbKyiPrvxna686DHKoQcBaNHbLVxpoj2");
        ParseAnalytics.trackAppOpened(getIntent());
       // PushService.setDefaultPushCallback(this, FeedListActivity.class);
        PushService.subscribe(this, "Uncategorized", FeedListActivity.class);
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.saveInBackground();

        mRequestQueue = Volley.newRequestQueue(this);
        setContentView(R.layout.activity_posts_list);
        progressbar = (ProgressBar) findViewById(R.id.progressBar);
        String url = "http://heavyhongkong.com/wordpress/api?json=get_recent_posts";
        //	new DownloadFilesTask().execute(url);

        try {

           // Thread.sleep(2000);

        } catch (Exception e) {


        }

        JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override

            public void onResponse(JSONObject response) {

                //    Log.i(TAG,response.toString());

                parseJson(response);
                updateList();

                ;
            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {

                //  Log.i(TAG,error.getMessage());

            }

        }
        );
        mRequestQueue.add(jr);


    }

    public void updateList() {
        feedListView = (ListView) findViewById(R.id.custom_list);
        feedListView.setVisibility(View.VISIBLE);
        progressbar.setVisibility(View.GONE);

        feedListView.setAdapter(new CustomListAdapter(this, feedList));
        feedListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = feedListView.getItemAtPosition(position);
                FeedItem newsData = (FeedItem) o;

                Intent intent = new Intent(FeedListActivity.this, FeedDetailsActivity.class);
                intent.putExtra("feed", newsData);
                startActivity(intent);
            }
        });
    }


//	public JSONObject getJSONFromUrl(String url) {
//		InputStream is = null;
//		JSONObject jObj = null;
//		String json = null;
//
//		// Making HTTP request
//		try {
//			// defaultHttpClient
//			DefaultHttpClient httpClient = new DefaultHttpClient();
//			HttpPost httpPost = new HttpPost(url);
//
//			HttpResponse httpResponse = httpClient.execute(httpPost);
//			HttpEntity httpEntity = httpResponse.getEntity();
//			is = httpEntity.getContent();
//
//			BufferedReader reader = new BufferedReader(new InputStreamReader(
//					is, "iso-8859-1"), 8);
//			StringBuilder sb = new StringBuilder();
//			String line = null;
//			while ((line = reader.readLine()) != null) {
//				sb.append(line + "\n");
//			}
//			is.close();
//			json = sb.toString();
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		try {
//			jObj = new JSONObject(json);
//		} catch (JSONException e) {
//			Log.e("JSON Parser", "Error parsing data " + e.toString());
//		}
//
//		// return JSON String
//		return jObj;
//
//	}


    public void parseJson(JSONObject json) {
        try {

            // parsing json object
            if (json.getString("status").equalsIgnoreCase("ok")) {
                JSONArray posts = json.getJSONArray("posts");

                feedList = new ArrayList<FeedItem>();

                for (int i = 0; i < posts.length(); i++) {
                    JSONObject post = (JSONObject) posts.getJSONObject(i);
                    FeedItem item = new FeedItem();
                    item.setTitle(post.getString("title"));
                    item.setDate(post.getString("date"));
                    item.setId(post.getString("id"));
                    item.setUrl(post.getString("url"));
                    item.setContent(post.getString("content"));
                    JSONArray attachments = post.getJSONArray("attachments");

                    if (null != attachments && attachments.length() > 0) {
                        JSONObject attachment = attachments.getJSONObject(0);
                        if (attachment != null)
                            item.setAttachmentUrl(attachment.getString("url"));
                    }

                    feedList.add(item);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
