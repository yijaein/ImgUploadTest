package studios.codelight.smartlogin.Fragment;

import android.support.v4.app.FragmentStatePagerAdapter;


/**
 * Created by Jan on 2017-06-29.
 */

public class pagerAdapter extends FragmentStatePagerAdapter{


    public pagerAdapter(android.support.v4.app.FragmentManager fm)
        {
            super(fm);
        }
        @Override
        public android.support.v4.app.Fragment getItem(int position)
        {
            switch(position)
            {
                case 0:
                    return new FirstFragment();
                case 1:

                    return new SecondFragment();

                case 2:
                    return new ThirdFragment();
                default:
                    return null;
            }
        }
        @Override
        public int getCount()
        {
            return 3;
        }
    }




