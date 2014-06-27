package com.mycards.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mycards.api.Download;
import com.mycards.business.Bank;
import com.mycards.business.Card;
import com.mycards.business.Flag;
import com.mycards.business.Model;
import com.mycards003.app.R;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    public static final String PREFS_NAME = "MyPrefsFile";
    private static final String PREFS_IP = "Ip";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,(DrawerLayout) findViewById(R.id.drawer_layout));

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String ip = settings.getString(PREFS_IP,"192.168.0.103");
        Parametros.getInstance().IP = ip;

    }

    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREFS_IP, Parametros.getInstance().IP);
        editor.commit();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, PlaceholderFragment.newInstance(position + 1)).commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean retorno = super.onOptionsItemSelected(item);

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);

            //Toast.makeText(getApplication(),"Opção não implementada", Toast.LENGTH_SHORT).show();
        }
        return retorno;
    }

    public static class PlaceholderFragment extends Fragment {

        private ListView listView;
        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            listView = (ListView)rootView.findViewById(R.id.listView);

            final Integer posicao = getArguments().getInt(ARG_SECTION_NUMBER);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    selectItemLista(posicao, i);
                }
            });
            return rootView;
        }

        @Override
        public void onResume() {
            super.onResume();
            updateListView();
        }

        private void updateListView() {
            final Integer posicao = getArguments().getInt(ARG_SECTION_NUMBER);

            switch (posicao) {
                case 1 : {
                    new Download().execute(new Bank(), getActivity(), listView, null);
                    break;
                }
                case 2: {
                    new Download().execute(new Flag(), getActivity(), listView, null);
                    break;
                }
                case 3: {
                    new Download().execute(new Card(), getActivity(), listView, null);
                    break;
                }
            }
        }

        private void selectItemLista(int position, int position_list) {
            try {
                Intent intent = new Intent(getActivity(), CadActivity.class);
                ListView lv = (ListView)getActivity().findViewById(R.id.listView);
                Parametros.getInstance().model = (Model) lv.getItemAtPosition(position_list);
                switch (position) {
                    case 1: {
                        intent = new Intent(getActivity(), CadBancoActivity.class);
                        break;
                    }
                    case 2: {
                        intent = new Intent(getActivity(), CadBandeiraActivity.class);
                        break;
                    }
                    case 3: {
                        intent = new Intent(getActivity(), CadCartaoActivity.class);
                    }
                }
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }


    }
}
