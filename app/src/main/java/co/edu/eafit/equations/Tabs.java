package co.edu.eafit.equations;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import co.edu.eafit.equations.helps.Help;
import co.edu.eafit.equations.inputs.InputBisection;
import co.edu.eafit.equations.inputs.InputFalsePosition;
import co.edu.eafit.equations.inputs.InputFixedPoint;
import co.edu.eafit.equations.inputs.InputIncrementalSearches;
import co.edu.eafit.equations.inputs.InputMultipleRoots;
import co.edu.eafit.equations.inputs.InputNewton;
import co.edu.eafit.equations.inputs.InputSecant;
import co.edu.eafit.equations.tables.TableBisection;
import co.edu.eafit.equations.tables.TableFalsePosition;
import co.edu.eafit.equations.tables.TableFixedPoint;
import co.edu.eafit.equations.tables.TableIncrementalSearches;
import co.edu.eafit.equations.tables.TableMultipleRoots;
import co.edu.eafit.equations.tables.TableNewton;
import co.edu.eafit.equations.tables.TableSecant;

public class Tabs extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        String nameToolbar = getIntent().getExtras().getString("type");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(nameToolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tabs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment tab = null;
            int type = getIntent().getExtras().getInt("id");
            switch (position) {
                case 0:
                    switch (type){
                        case 0:
                            tab = InputIncrementalSearches.newInstance(position);
                            break;
                        case 1:
                            tab = InputBisection.newInstance(position);
                            break;
                        case 2:
                            tab = InputFalsePosition.newInstance(position);
                            break;
                        case 3:
                            tab = InputFixedPoint.newInstance(position);
                            break;
                        case 4:
                            tab = InputNewton.newInstance(position);
                            break;
                        case 5:
                            tab = InputSecant.newInstance(position);
                            break;
                        case 6:
                            tab = InputMultipleRoots.newInstance(position);
                            break;
                        default:
                            tab = null;
                            break;
                    }
                    break;
                case 1:
                    switch (type){
                        case 0:
                            tab = TableIncrementalSearches.newInstance(position);
                            break;
                        case 1:
                            tab = TableBisection.newInstance(position);
                            break;
                        case 2:
                            tab = TableFalsePosition.newInstance(position);
                            break;
                        case 3:
                            tab = TableFixedPoint.newInstance(position);
                            break;
                        case 4:
                            tab = TableNewton.newInstance(position);
                            break;
                        case 5:
                            tab = TableSecant.newInstance(position);
                            break;
                        case 6:
                            tab = TableMultipleRoots.newInstance(position);
                            break;
                        default:
                            tab = null;
                            break;
                    }
                    ;
                    break;
                case 2:
                    switch (type){
                        //Usar por si nesecita un help en especifico
                        default:
                            tab =  Help.newInstance(position);
                            break;
                    }
                    break;
                default:
                    Log.i("ErrorMenu:","Creacion de Frament Invalida");
            }
            return tab;
        }

        @Override
        public int getCount() { //Numero de paginas
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Entrada";
                case 1:
                    return "Tabla";
                case 2:
                    return "Ayuda";
            }
            return "Default";
        }
    }

}
