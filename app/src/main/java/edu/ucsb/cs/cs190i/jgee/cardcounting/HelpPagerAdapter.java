package edu.ucsb.cs.cs190i.jgee.cardcounting;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Referenced: https://www.bignerdranch.com/blog/viewpager-without-fragments/
 * Created by Jenny on 3/13/2016.
 */
public class HelpPagerAdapter extends PagerAdapter {
    private Context mContext;

    public HelpPagerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        HelpPagerEnum customPagerEnum = HelpPagerEnum.values()[position];
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(customPagerEnum.getLayoutResId(), collection, false);
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return HelpPagerEnum.values().length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        HelpPagerEnum customPagerEnum = HelpPagerEnum.values()[position];
        return mContext.getString(customPagerEnum.getTitleResId());
    }

}
