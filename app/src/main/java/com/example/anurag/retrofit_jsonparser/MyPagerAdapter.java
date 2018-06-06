package com.example.anurag.retrofit_jsonparser;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class MyPagerAdapter extends FragmentStatePagerAdapter {
    int count;
    Bundle bundle;
    Bundle bundle2;

    public MyPagerAdapter(FragmentManager fm, int count, Bundle bundle, Bundle bundle2) {
        super(fm);
        this.count = count;
        this.bundle = bundle;
        this.bundle2 = bundle2;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FirstFragment tab1 = new FirstFragment();
                tab1.setArguments(bundle);
                return tab1;
            case 1:
                SecondFragment tab2 = new SecondFragment();
                tab2.setArguments(bundle2);
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return count;
    }
}
