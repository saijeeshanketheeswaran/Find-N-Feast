package ca.csci4100.uoit.project;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle mToggle;
    Toolbar toolbar;
    public static int GET_LOGIN_INTENT = 1;
    NavigationView navigationView;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();

        // load at application start up
        fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container,new NearbyFragment());
        fragmentTransaction.commit();

        navigationView= (NavigationView) findViewById(R.id.navigationView);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container,
                    new NearbyFragment()).commit();
            navigationView.setCheckedItem(R.id.nearby);
        }
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nearby:
                fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container,new NearbyFragment());
                fragmentTransaction.commit();
                break;

            case R.id.search:
                fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container,new SearchFragment());
                fragmentTransaction.commit();
                break;

            case R.id.logout:
                Intent getLoggoutIntent = new Intent(
                        MainActivity.this,
                        LoginActivity.class
                );
                startActivity(getLoggoutIntent);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }



    private void setUpToolbar() {
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



        @Override
    public void onActivityResult(int requestCode,
                                 int resultCode,
                                 Intent resultIntent) {
        if(requestCode == GET_LOGIN_INTENT) {
            if(resultCode == Activity.RESULT_OK){
                //correct password
                Toast toast = Toast.makeText(MainActivity.this, " Login Success", Toast.LENGTH_SHORT);
                toast.show();

            }
        }
        // TODO: handle the province select event
    }

}
