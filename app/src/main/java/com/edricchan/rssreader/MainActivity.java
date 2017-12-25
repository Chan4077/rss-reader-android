package com.edricchan.rssreader;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.edricchan.rssreader.dummy.DummyContent;
import com.edricchan.rssreader.filters.InputFilterMinMax;
import com.edricchan.rssreader.object.NotificationItem;

public class MainActivity extends AppCompatActivity implements RSSItemFragment.OnListFragmentInteractionListener, NotificationFragment.OnListFragmentInteractionListener, ReadLaterFragment.OnListFragmentInteractionListener, ExploreFragment.OnListFragmentInteractionListener {
    final String LOG_TAG = "MyTag";
    SwipeRefreshLayout swipeRefreshLayout;
    CoordinatorLayout coordinatorLayout;
    Menu toolbarMenu;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    changeFragment(new RSSItemFragment());
                    toggleMenuItemVisibility(R.id.menu_configure_rss, true);
                    Log.d(LOG_TAG, "Home was clicked");
                    return true;
                case R.id.navigation_read_later:
                    changeFragment(new ReadLaterFragment());
                    toggleMenuItemVisibility(R.id.menu_configure_rss, false);
                    Log.d(LOG_TAG, "Read later was clicked");
                    return true;
                case R.id.navigation_notifications:
                    changeFragment(new NotificationFragment());
                    toggleMenuItemVisibility(R.id.menu_configure_rss, false);
                    Log.d(LOG_TAG, "Notification was clicked");
                    return true;
                case R.id.navigation_explore:
                    changeFragment(new ExploreFragment());
                    toggleMenuItemVisibility(R.id.menu_configure_rss, false);
                    Log.d(LOG_TAG, "Explore was clicked");
                    return true;
            }
            return false;
        }
    };

    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        Log.d(LOG_TAG, item.toString());
    }

    public void onListFragmentInteraction(NotificationItem item) {
        Log.d(LOG_TAG, item.message);
    }

    /**
     * Changes the fragment to the specified parameter
     *
     * @param fragment The fragment to change to (Must be initialized via the <code>new</code> keyword)
     */
    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_fragmentholder, fragment)
                .commit();
    }

    ;

    /**
     * Toggles a menu item's visibility
     *
     * @param id        The id of the menu item (R.id.*)
     * @param visiblity The visibility to set
     */
    private void toggleMenuItemVisibility(int id, boolean visiblity) {
        toolbarMenu.findItem(id).setVisible(visiblity);
    }
    /**
     * Gets shared preferences used by the app
     * @returns Shared preferences
     */
    private void getAppSharedPreferences () {
        SharedPreferences prefs = this.getSharedPreferences("com.edricchan.rssreader", Context.MODE_PRIVATE);
    }
    /**
     * Opens the configure RSS dialog
     */
    private void configureRSSDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View rssDialogView = inflater.inflate(R.layout.dialog_rss_opts, null);
        EditText postsDisplay = (EditText) rssDialogView.findViewById(R.id.postsDisplay);
        postsDisplay.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "50")});
        // Add an inflated view of the <code>rss_opts</code> layout
        builder.setView(rssDialogView)
                // Sets the title of the dialog
                .setTitle("Configure RSS Feed")
                // Sets the ok button
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // TODO: Implement working functionality for saving
                        dialogInterface.dismiss();
                    }
                })
                // Sets the cancel button
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // TODO: Implement working functionality for canceling
                        dialogInterface.cancel();
                    }
                })
                // Show the dialog!
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        // TODO: Use another colour scheme. Google's colour scheme just looks gorgeous.
        swipeRefreshLayout.setColorSchemeResources(R.color.googleBlue, R.color.googleGreen, R.color.googleRed, R.color.googleYellow);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        changeFragment(new RSSItemFragment());
//        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
//        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
//        layoutParams.setBehavior(new BottomNavigationViewBehaviour());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        toolbarMenu = (Menu) menu;
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_check_updates:
                Log.d(LOG_TAG, "Check updates was clicked");
                return true;
            case R.id.menu_refresh:
                swipeRefreshLayout.setRefreshing(true);
                toolbarMenu.findItem(R.id.menu_refresh).setVisible(false);
                toolbarMenu.findItem(R.id.menu_stop_refreshing).setVisible(true);
                return true;
            case R.id.menu_stop_refreshing:
                swipeRefreshLayout.setRefreshing(false);
                toolbarMenu.findItem(R.id.menu_refresh).setVisible(true);
                toolbarMenu.findItem(R.id.menu_stop_refreshing).setVisible(false);
                return true;
            case R.id.menu_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            case R.id.menu_configure_rss:
                configureRSSDialog();
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

}
