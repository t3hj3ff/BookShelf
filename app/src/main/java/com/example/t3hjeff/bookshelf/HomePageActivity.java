package com.example.t3hjeff.bookshelf;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class HomePageActivity extends AppCompatActivity {


    //ზედა მენიუს დეკლარაცია, ფაილები navigation_header.xml და /res/menu/navigation_menu.xml
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    //რესაიკლერ ვიუს დეკლარაცია ნავიგაციისთვის
    private RecyclerView recyclerView;
    //ჰედერ თულბარი
    private Toolbar mToolbar;
    //ექშენბარის ტუგლი
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("მთავარი");

        drawerLayout = (DrawerLayout) findViewById(R.id.drawable_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(HomePageActivity.this, drawerLayout, R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                UserMenuSelector(menuItem);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void UserMenuSelector(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                //გადადის მთავარ გვერდზე;
                break;
            case R.id.nav_booklist:
                //გადადის წიგნების სიაზე (ლისტად უნდა იყოს ჩამოყრილი ახლიდან>ძველი);
                break;
            case R.id.nav_topbooks:
                //წიგნები რეიტინგის მიხედვით;
                break;
            case R.id.nav_parameters:
                //პარამეტრები აპლიკაციის (ვერსია და ყლეობები);
                break;
            case R.id.nav_profile:
                //პროფილის ფეიჯი, ავტორის მიერ დამატებული წიგნები, და წიგნის დამატების ღილაკი;
                break;
            case R.id.nav_logout:
                //ლოგ-აუთი;
                break;
        }
    }
}
