package bethesda.com.bethesdahospitalmobile.main.schedule;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import bethesda.com.bethesdahospitalmobile.R;


public class ScheduleActivity extends AppCompatActivity {


    private TabLayout tablayout;
    private ViewPager viewpager;

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

        initView();


        setupViewPager(viewpager);

        setupTabLayout();

        initTab();


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
                        return DokterScheduleFragment.newInstance();

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

    private void initTab() {
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
