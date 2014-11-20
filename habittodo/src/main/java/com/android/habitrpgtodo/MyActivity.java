package com.android.habitrpgtodo;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import com.android.habitrpgtodo.adapters.TabsPagerAdapter;
import com.android.habitrpgtodo.provider.MyContentProvider;

public class MyActivity extends FragmentActivity implements ActionBar.TabListener  {

    /**
     * Called when the activity is first created.
     */
    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    private String[] tabs = { "To Do", "Completed" };

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final String ACCOUNT_NAME = "MyApp";
        final String ACCOUNT_TYPE = "habitrpg.com";

        Account appAccount = new Account(ACCOUNT_NAME,ACCOUNT_TYPE);
        AccountManager accountManager = AccountManager.get(getApplicationContext());
        if (accountManager.addAccountExplicitly(appAccount, null, null)) {
            ContentResolver.setIsSyncable(appAccount, MyContentProvider.AUTHORITY, 1);
            ContentResolver.setMasterSyncAutomatically(true);
            ContentResolver.setSyncAutomatically(appAccount, MyContentProvider.AUTHORITY, true);
        }
        ContentResolver.requestSync(appAccount, MyContentProvider.AUTHORITY, new Bundle());

        //Init
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                actionBar.setSelectedNavigationItem(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
