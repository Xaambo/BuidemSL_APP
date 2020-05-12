package com.example.buidemsl_app;

import android.os.Bundle;

import com.example.buidemsl_app.ui.main.FragmentKind;
import com.example.buidemsl_app.ui.main.FragmentMaquines;
import com.example.buidemsl_app.ui.main.FragmentZones;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.buidemsl_app.ui.main.SectionsPagerAdapter;

import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fm = getSupportFragmentManager();
    //private adapterGestorArticles scArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        final ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        /*
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
        */

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                        FragmentMaquines fragmentM = (FragmentMaquines) fm.findFragmentByTag("android:switcher:" + R.id.view_pager + ":" + viewPager.getCurrentItem());
                        fragmentM.FABActionListener();
                        break;
                    case 1:
                        FragmentKind fragmentK = (FragmentKind) fm.findFragmentByTag("android:switcher:" + R.id.view_pager + ":" + viewPager.getCurrentItem());
                        fragmentK.FABActionListener();
                        break;
                    case 2:
                        FragmentZones fragmentZ = (FragmentZones) fm.findFragmentByTag("android:switcher:" + R.id.view_pager + ":" + viewPager.getCurrentItem());
                        fragmentZ.FABActionListener();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}