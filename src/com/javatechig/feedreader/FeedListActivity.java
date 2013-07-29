package com.javatechig.feedreader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.PushService;
import com.viewpagerindicator.TitlePageIndicator;

import java.util.ArrayList;
import java.util.List;

public class FeedListActivity extends SherlockFragmentActivity {

    private static final String VOTECHECKFRAGMENT = VoteCheckFragment.class.getName();
    private static final String FEEDLISTFRAGMENT = FeedListFragment.class.getName();

    MainPagerAdapter mAdapter;
    ViewPager mPager;
    TitlePageIndicator mIndicator;
    List<Fragment> mFragments;

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


        setContentView(R.layout.activity_main);

        // add fragments
        mFragments = new ArrayList<Fragment>();
        mFragments.add(Fragment.instantiate(this, VOTECHECKFRAGMENT));
        mFragments.add(Fragment.instantiate(this, FEEDLISTFRAGMENT));

        // adapter
        mAdapter = new MainPagerAdapter(getSupportFragmentManager(), mFragments);

        // pager
        mPager = (ViewPager) findViewById(R.id.view_pager);
        mPager.setAdapter(mAdapter);

        // indicator
        mIndicator = (TitlePageIndicator) findViewById(R.id.title_indicator);
        mIndicator.setViewPager(mPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


    public class MainPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragments;
        private String[] titles = new String[]{"查詢票站", "新聞"};

        private int mCount = titles.length;

        public MainPagerAdapter(FragmentManager fm, List<Fragment> f) {
            super(fm);
            mFragments = f;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mCount;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }


}
