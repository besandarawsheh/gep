package com.example.hp.gep;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.Toast;

public class mainpage extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    NavigationView navigationView ;

    private void setFirstItemNavigationView() {
        navigationView.setCheckedItem(R.id.nav_event);
        navigationView.getMenu().performIdentifierAction(R.id.nav_event, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawarLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.Open, R.string.Close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);
        setFirstItemNavigationView();
        initInstances();
    }

    private void initInstances() {


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
        @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                    switch (id) {
                        case R.id.nav_event:
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                            mDrawerLayout.closeDrawers();
                            break;
                        case R.id.nav_friend:
                            Intent in = new Intent(getApplicationContext(), Friend.class);
                            startActivity(in);
                            mDrawerLayout.closeDrawers();
                            //Do some thing here
                            // add navigation drawer item onclick method here
                            break;
                        case R.id.nav_home:

                            Intent intent= new Intent(getApplicationContext(), homepage.class);
                           Email.setANum(0);

                            startActivity(intent);
                            mDrawerLayout.closeDrawers();
                            //Do some thing here
                            // add navigation drawer item onclick method here
                            break;
                        case R.id.nav_frRequest:
                            Intent r= new Intent(getApplicationContext(), friendsRequests.class);
                            startActivity(r);
                            mDrawerLayout.closeDrawers();
                            //Do some thing here
                            // add navigation drawer item onclick method here
                            break;
                        case R.id.nav_FRList:
                            Intent l= new Intent(getApplicationContext(), listOfFriends.class);
                            startActivity(l);
                            mDrawerLayout.closeDrawers();
                            //Do some thing here
                            // add navigation drawer item onclick method here
                            break;




                    }
                int i =menuItem.getItemId();
                String t;
                t= Integer.toString(i);
                Toast.makeText(mainpage.this, t, Toast.LENGTH_LONG).show();

return false;
       }
                 });



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {




        if(mToggle.onOptionsItemSelected(item)){
            return true;}
        /*int i =item.getItemId();
        String t;
        t= Integer.toString(i);
        Toast.makeText(mainpage.this, t, Toast.LENGTH_LONG).show();*/
        return super.onOptionsItemSelected(item);

    }


}