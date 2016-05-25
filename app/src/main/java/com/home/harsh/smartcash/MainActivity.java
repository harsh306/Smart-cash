package com.home.harsh.smartcash;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements OtpTimmer.OnFragmentInteractionListener,BlankForm.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle args= new Bundle();
        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final OtpTimmer otpTimmer = new OtpTimmer();
        final BlankForm blankForm=new BlankForm();
        args.putString("Adding","true");
        otpTimmer.setArguments(args);
        otpTimmer.setHasOptionsMenu(true);
        final FragmentTransaction[] fragTran = new FragmentTransaction[1];

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        fragmentTransaction.add(R.id.fragment_container,blankForm);
        fragmentTransaction.commit();
        //fragmentTransaction.replace(R.id.fragment_container,otpTimmer);
        //fragmentTransaction.commit();

       /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 fragTran[0] = fragmentManager.beginTransaction();
                fragTran[0].replace(R.id.fragment_container,otpTimmer);
                fragTran[0].addToBackStack(null);
                fragTran[0].commit();



                *//*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*//*
            }
        });*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            getFragmentManager().popBackStack();
        }
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


        return super.onOptionsItemSelected(item);//hi



    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
