package com.javatechig.feedreader;

import android.os.Bundle;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockActivity;

/**
 * Created with IntelliJ IDEA.
 * User: simon_000
 * Date: 7/29/13
 * Time: 11:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class VoteCheckResultActivity extends SherlockActivity {
    private String value = null;
    private TextView VoteStaffID = null;
    private TextView VoteLocation = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.activity_vote_result);

        VoteStaffID = (TextView) findViewById(R.id.vote_staff_id);
        VoteLocation = (TextView) findViewById(R.id.vote_location);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("staff_id");
            VoteStaffID.setText(value);
            if (value.equals("00100")) {
                VoteLocation.setText("望廈票站");
            } else {
                VoteLocation.setText("沒有輸入");
            }
        }
    }


}
