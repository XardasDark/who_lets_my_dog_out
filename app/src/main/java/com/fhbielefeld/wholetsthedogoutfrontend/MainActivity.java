package com.fhbielefeld.wholetsthedogoutfrontend;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.fhbielefeld.wholetsthedogoutfrontend.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";
    private String currentFragment = "";
    public static MainActivity main;
    public static String targetUser = "";
    public static String picture = "https://i.imgur.com/cqKSBdW.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);

        /**
         * Create a custom ActionBar with custom title
         */
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setTitle(getResources().getString(R.string.app_name_long));
        }

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_activity_main);
        NavController navController = navHostFragment.getNavController();
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.
                Builder(navController.getGraph())
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);


        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        @SuppressWarnings({"UnusedDeclaration"})
        AppBarConfiguration appBarConfiguration2 = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_messages, R.id.navigation_profil)
                .build();
        assert navHostFragment != null;
        NavigationUI.setupWithNavController(navView, navController);
        main = this;
    }

    /**
     * places an instance of a given Fragment.class on a given layout id
     * @param fragmentClass FragmentClass to display
     * @param frameId       layoutFrame to place Fragment on
     */
    public void placeFragment(Class fragmentClass, int frameId) {
        currentFragment = fragmentClass.getName();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // detach fragments
        String simpleName = fragmentClass.getSimpleName();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment f : fragments) {
                if (f != null && !f.isDetached()) {
                    fragmentTransaction.detach(f);
                    if (f instanceof OnManualDetachListener) {
                        ((OnManualDetachListener) f).onManualDetach();
                    }
                }

            }
        }

        // add/attach fragments
        if (fragmentManager.findFragmentByTag(simpleName) != null) {
            fragmentTransaction.attach(fragmentManager.findFragmentByTag(simpleName));
        } else {
            Fragment fragment = null;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                Log.e(LOG_TAG, "Exception creating Fragment instance\n" + e.getMessage());
            }
            fragmentTransaction.add(frameId, fragment, simpleName);
        }
        fragmentTransaction.addToBackStack(simpleName);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

}