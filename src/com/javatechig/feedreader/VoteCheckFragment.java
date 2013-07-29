package com.javatechig.feedreader;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.actionbarsherlock.app.SherlockFragment;

/**
 * Created with IntelliJ IDEA.
 * User: simon_000
 * Date: 7/26/13
 * Time: 1:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class VoteCheckFragment extends SherlockFragment {

    private Button VoteCheckButton = null;
    private EditText Staff_ID = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_vote_check, container, false);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Staff_ID = (EditText) getActivity().findViewById(R.id.vote_check_staff_id);
        VoteCheckButton = (Button) getActivity().findViewById(R.id.vote_check_button);
        VoteCheckButton.setOnClickListener(new Button.OnClickListener() {

            @Override

            public void onClick(View v) {

                Intent myIntent = new Intent(getActivity(), VoteCheckResultActivity.class);
                myIntent.putExtra("staff_id", Staff_ID.getText().toString()); //Optional parameters
                startActivity(myIntent);

            }

        });

    }


}
