package edu.ucsb.cs.cs190i.jgee.cardcounting;

/**
 * Referenced: https://www.bignerdranch.com/blog/viewpager-without-fragments/
 * Created by Jenny on 3/13/2016.
 */
public enum HelpPagerEnum {
        HILO(0, R.layout.fragment_hilo),
        HIL_OPT1(1, R.layout.fragment_hilo_opt1),
        KO(2, R.layout.fragment_ko),
        RED7(3, R.layout.fragment_red7);

        private int mTitleResId;
        private int mLayoutResId;

        HelpPagerEnum(int titleResId, int layoutResId) {
            mTitleResId = titleResId;
            mLayoutResId = layoutResId;
        }

        public int getTitleResId() {
            return mTitleResId;
        }

        public int getLayoutResId() {
            return mLayoutResId;
        }
}

