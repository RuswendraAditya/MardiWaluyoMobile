package mardiwaluyo.com.mardiwaluyomobile.main.schedule;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import mardiwaluyo.com.mardiwaluyomobile.R;


public class ScheduleActivity extends AppCompatActivity {


    private TabLayout tablayout;
    private ViewPager viewpager;
    private String namaDokter ;
    private String nid ;
    private String tabNames[] = {"Jadwal Klinik", "Jadwal Dokter"};

    public static Drawable setDrawableSelector(Context context, int normal, int selected) {

        Drawable state_normal = ContextCompat.getDrawable(context, normal);

        Drawable state_pressed = ContextCompat.getDrawable(context, selected);

        StateListDrawable drawable = new StateListDrawable();

        drawable.addState(new int[]{android.R.attr.state_selected},
                state_pressed);
        drawable.addState(new int[]{android.R.attr.state_enabled},
                state_normal);

        return drawable;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
    }

    public static ColorStateList setTextselector(int normal, int pressed) {
        ColorStateList colorStates = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_selected},
                        new int[]{}
                },
                new int[]{
                        pressed,
                        normal});
        return colorStates;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        int position = 0;
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            nid =extras.getString("nid");
            namaDokter  = extras.getString("namaDokter");
            position = extras.getInt("viewpager_position");
        }
        initView();


        setupViewPager(viewpager);

        setupTabLayout();

        initTab(position );

        if (position >0)
        {
            viewpager.setCurrentItem(position);

        }

    }

    private void setupTabLayout() {
        tablayout.setupWithViewPager(viewpager);
    }

    private void setupViewPager(ViewPager viewPager) {

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                switch (position) {

                    case 0:
                        return KlinikScheduleFragment.newInstance();

                    case 1:
                        Fragment dokterScheduleFragment = new DokterScheduleFragment();

                        Bundle args = new Bundle();
                        args.putString("namaDokter", namaDokter);
                        args.putString("nid",nid);
                                dokterScheduleFragment.setArguments(args);

                        dokterScheduleFragment.setArguments(args);
                        return  dokterScheduleFragment;


                }
                return null;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return tabNames[position];

            }

            @Override
            public int getCount() {
                return tabNames.length;
            }
        });
    }

    private void initView() {
        tablayout = (TabLayout) findViewById(R.id.tablayout);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
    }

    private void initTab(Integer position) {
        tablayout.getTabAt(0).setCustomView(getTabView(0));
        tablayout.getTabAt(1).setCustomView(getTabView(1));


    }

    private View getTabView(int position) {
        View view = LayoutInflater.from(ScheduleActivity.this).inflate(R.layout.view_tabs, null);


        TextView text = (TextView) view.findViewById(R.id.tab_text);
        text.setText(tabNames[position]);
        text.setTextColor(setTextselector(Color.parseColor("#F2F2F2"), Color.parseColor("#23cec5")));


        return view;
    }


}
