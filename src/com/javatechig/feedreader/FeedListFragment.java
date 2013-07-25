package com.javatechig.feedreader;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.javatechig.feedreader.model.FeedItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: simon_000
 * Date: 7/26/13
 * Time: 12:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class FeedListFragment extends SherlockFragment {

    private ListView mList;
    private ArrayList<FeedItem> feedList = null;
    private ProgressBar progressbar = null;
    private ListView feedListView = null;
    private RequestQueue mRequestQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_posts_list, container, false);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        mRequestQueue = Volley.newRequestQueue(getActivity());

        progressbar = (ProgressBar) getActivity().findViewById(R.id.progressBar);
        String url = "http://heavyhongkong.com/wordpress/api?json=get_recent_posts";
        //String url = "http://home258695.mycloudnas.com/wordpress/?json=get_recent_posts";
        //	new DownloadFilesTask().execute(url);


        JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override

            public void onResponse(JSONObject response) {

                //    Log.i(TAG,response.toString());

                parseJson(response);
                updateList();


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
        feedListView = (ListView) getActivity().findViewById(R.id.custom_list);
        feedListView.setVisibility(View.VISIBLE);
        progressbar.setVisibility(View.GONE);

        feedListView.setAdapter(new CustomListAdapter(getActivity(), feedList));
        feedListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = feedListView.getItemAtPosition(position);
                FeedItem newsData = (FeedItem) o;

                Intent intent = new Intent(getActivity(), FeedDetailsActivity.class);
                intent.putExtra("feed", newsData);
                startActivity(intent);
            }
        });
    }


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
