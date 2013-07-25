package com.javatechig.feedreader;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.actionbarsherlock.app.SherlockFragment;

/**
 * Created with IntelliJ IDEA.
 * User: simon_000
 * Date: 7/26/13
 * Time: 1:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class VoteCheckFragment extends SherlockFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_vote_check, container, false);

        return v;
    }
}
