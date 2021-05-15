package com.example.vocabularioapp;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class CategoryAdapter extends FragmentPagerAdapter {
    private Context mContext;
    public CategoryAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext=context;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return mContext.getString(R.string.category_numbers);


            case 1:
                return mContext.getString(R.string.category_family);


            case 2:
                return mContext.getString(R.string.category_colors);


            case 3:
                return mContext.getString(R.string.category_phrases);


            default:
                return null;
        }
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new NumbersFragment();
        }
        else if(position == 1){
            return new FamilyFragment();
        }
        else if(position == 2){
            return new ColorsFragment();
        }
        else{
            return new PhrasesFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
