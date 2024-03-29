package lk.lankahomes.lankahomeapp.lankahomes;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class navigationDrawerFragment extends android.support.v4.app.Fragment {

    private RecyclerView recyclerView;
    private VivzAdapter adapter;
    public static final String PREF_FILE_Name = "testpref";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    private View containerView;

    public navigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER,  "false"));
        if(savedInstanceState != null){
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);
        adapter = new VivzAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }

    public static List<Information> getData(){
        List<Information> data = new ArrayList<>();
        int[] icons = {R.drawable.lankahomes_logo, R.drawable.lankahomes_logo, R.drawable.lankahomes_logo, R.drawable.lankahomes_logo, };
        String[] titles = {"Sathya Baman", "Baman Sathya", "Kanasalingam", "Satha Palan"};

        for (int k=0; k < icons.length && k < titles.length; k++){
            Information current = new Information();
            current.iconId = icons[k];
            current.title = titles[k];
            data.add(current);
        }
        return data;
    }


    public void setup(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView =getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close ){
            @Override
            public void onDrawerOpened(View drawerView) {

                super.onDrawerOpened(drawerView);
                if(!mUserLearnedDrawer){
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer+"");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {

                super.onDrawerClosed(drawerView);

                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (slideOffset < 0.3) {
                    toolbar.setAlpha(1 - slideOffset);
                }
            }
        };

        // Checking if the user is first time  user and show him we have a drawer
        if (!mUserLearnedDrawer && !mFromSavedInstanceState){
                mDrawerLayout.openDrawer(containerView);
        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // add a hamburger icon to toggle drawer
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    public static void saveToPreferences (Context context, String preferenceName, String preferenceValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_Name, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context, String preferenceName, String defaultValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_Name, context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, defaultValue);
    }
}
