package com.op.bt.beneficiarypayments.UI;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.op.bt.beneficiarypayments.Data.PrefManager;
import com.op.bt.beneficiarypayments.IntroActivity;
import com.op.bt.beneficiarypayments.R;
import com.op.bt.beneficiarypayments.UI.Adapter.CustomGridViewActivity;
import com.squareup.picasso.Picasso;

public class Meennuu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,ItemListDialogFragment.Listener {

    GridView androidGridView;
    PrefManager pref ;

    String[] gridViewString = {
            "pay beneficiary",
            "Pay Staff",
            "Cash Drops",
            "Register User",
            "Airtime",
            ""


    } ;
    int[] gridViewImageId = {
            R.drawable.payment,
            R.drawable.pay_payment,
            R.drawable.billing,
            R.drawable.airtime,
            R.drawable.ic_launcher_background,
            R.drawable.ic_add_circle_black_24dp

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meennuu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Galaxy Connect");
        setSupportActionBar(toolbar);
        pref = new PrefManager(this);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        TextView username = (TextView)header.findViewById(R.id.nav_username);
        TextView fsize = (TextView)header.findViewById(R.id.tt_fsize);
        ImageView imgvq = (ImageView)header.findViewById(R.id.imageView);
        String photourl  = pref.GetPhotourl();
        username.setText(pref.GetUSer());
        fsize.setTextSize(pref.getFamilySize());

        Picasso.with(this)
                .load(photourl)
                .into(imgvq);

        CustomGridViewActivity adapterViewAndroid = new CustomGridViewActivity(Meennuu.this, gridViewString, gridViewImageId);
        androidGridView=(GridView)findViewById(R.id.grid_view_image_text);
        androidGridView.setAdapter(adapterViewAndroid);
        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {
              //  Toast.makeText(Meennuu.this, "GridView Item: " + gridViewString[+i], Toast.LENGTH_LONG).show();
                    //Todo:Add a nullpointer exception
                switch (i){
                    case 0:
                       // if (pref.Get_locals()){
                           // startActivity(new Intent(Meennuu.this, MainActivity.class));
                           // startActivity(new Intent(Meennuu.this, SyncActivity.class));
                       // }
                        //else{
                        startActivity(new Intent(Meennuu.this, SearchActivity.class));
                   //}
                        break;
                    case 1:
                        // if (pref.Get_locals()){
                        // startActivity(new Intent(Meennuu.this, MainActivity.class));
                        // startActivity(new Intent(Meennuu.this, SyncActivity.class));
                        // }
                        //else{
                        startActivity(new Intent(Meennuu.this, SearchActivity.class));
                        //}
                        break;
                    case 2:
                        // if (pref.Get_locals()){
                        // startActivity(new Intent(Meennuu.this, MainActivity.class));
                        // startActivity(new Intent(Meennuu.this, SyncActivity.class));
                        // }
                        //else{
                        startActivity(new Intent(Meennuu.this, SearchActivity.class));
                        //}
                        break;
                    case 3:
                        // if (pref.Get_locals()){
                        // startActivity(new Intent(Meennuu.this, MainActivity.class));
                        // startActivity(new Intent(Meennuu.this, SyncActivity.class));
                        // }
                        //else{
                        startActivity(new Intent(Meennuu.this, RegisterUser.class));
                        //}
                        break;
                    case 4:
                        //startActivity(new Intent(Meennuu.this, CardActivity.class));
                        break;
                    case 5:
                     //   startActivity(new Intent(Meennuu.this, RegisterUser.class));
                        break;
                }
            }
        });

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.meennuu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            pref.setUser("none","none","none");
            Intent sa = new Intent(Meennuu.this, IntroActivity.class);
            startActivity(sa);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.action_profile){
            Log.e("Bottom Fragment" , "Clecked");

            ItemListDialogFragment.newInstance(4).show(getSupportFragmentManager(),"");
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClicked(int position) {
        Log.e("Clicked", String.valueOf(position));
    }
}
