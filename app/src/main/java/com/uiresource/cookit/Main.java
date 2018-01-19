package com.uiresource.cookit;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.MenuInflater;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.uiresource.cookit.utils.AppBarStateChangeListener;
import com.uiresource.cookit.utils.CircleGlide;
import com.uiresource.cookit.utils.CustomTypefaceSpan;

public class Main extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String user;
    DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar(R.id.toolbar, "VIBE CHAT", R.color.colorPink, R.color.colorWhiteTrans, R.drawable.ic_burger);

        FragmentTransaction ft;
        FragmentHome fragmentHome = new FragmentHome();
        ft = getSupportFragmentManager().beginTransaction();
       ft.replace(R.id.frameLayout, fragmentHome).commit();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();






        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu m = navigationView.getMenu();

        for (int i=0;i<m.size();i++) {
            MenuItem mi = m.getItem(i);
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu!=null && subMenu.size() >0 ) {
                for (int j=0; j <subMenu.size();j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }
            applyFontToMenuItem(mi);
        }

        View header = navigationView.getHeaderView(0);
        ImageView imageView = (ImageView) header.findViewById(R.id.imageView);
        Glide.with(this)
                .load(Uri.parse("https://scontent.fdad3-1.fna.fbcdn.net/v/t31.0-8/22538898_1228714313938583_4886922489056926987_o.jpg?oh=0de272b104d2a19dac3adf04818ca4a8&oe=5AE18AC1"))
                .transform(new CircleGlide(this))
                .into(imageView);



        Intent intent = this.getIntent();
        user= intent.getStringExtra("username");
    }
    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/SourceSansPro-Semibold.otf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);



                // OPEN DRAWER
                return true;
        }
    Toast.makeText(Main.this,id+"",Toast.LENGTH_LONG).show();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nofications) {
            // Handle the camera action
        } else if (id == R.id.messages) {
            Intent guidulieu = new Intent(Main.this,Home.class);
            guidulieu.putExtra("username",user);
            startActivity(guidulieu);
        } else if (id == R.id.friends) {

        } else if (id == R.id.account) {

        } else if (id == R.id.setting) {

        }else if(id ==R.id.postSTT){
            Intent guidulieu = new Intent(Main.this,PostSTT.class);
            guidulieu.putExtra("username",user);
            startActivity(guidulieu);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
