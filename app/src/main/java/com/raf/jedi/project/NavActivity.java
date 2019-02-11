package com.raf.jedi.project;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NavActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private MediaRecorder mRecorder = null;
    private AudioModel mCurrentRecording;
    private RecyclerView mRecycleView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private ModelContainer mModel;
    private Boolean recording = false;

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    public static final String ACTION_PERMISSION_GRANTED = "action_permission_granted";
    private String mFileName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton rec = (FloatingActionButton) findViewById(R.id.recordButton);
        rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recording) {
                    stopRecording();
                }
                else {
                    startRecording();
                }
                recording = !recording;
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        setTitle("Recordings");
        mRecycleView = findViewById(R.id.listRecycleView);
        mRecycleView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(mLayoutManager);

        mFileName = getExternalCacheDir().getAbsolutePath();

        getPermision();

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void startRecording() {

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);


        mCurrentRecording = new AudioModel();

        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        DateFormat formDate = new SimpleDateFormat("dd:MM:yyy");
        DateFormat formHour = new SimpleDateFormat("HH:mm:ss");


        mCurrentRecording.creationTime = (String.valueOf(formDate.format(date)) +"_" + String.valueOf(formHour.format(date)));
        String auxFileName = mFileName + "/" + String.valueOf(formDate.format(date)) + "_" +  String.valueOf(formHour.format(date)) + ".3gp";
        Log.d("File name", auxFileName);
        mRecorder.setOutputFile(auxFileName);
        mCurrentRecording.path = auxFileName;

        Log.d("name",mCurrentRecording.creationTime);

        try{
            mRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }


        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        mModel.audios.add(mCurrentRecording);
        mCurrentRecording = null;
        mModel.print();
        mModel.save(this);
        onResume();
    }

    @Override
    protected void onResume() {
        super.onResume();


        mModel = ModelContainer.load(this);
        mModel.print();

        mAdapter = new MyAdapter(mModel.audios, new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                Log.d("Record click", "Click item " + String.valueOf(pos));
                AudioModel mi = mModel.audios.get(pos);

            }
        });

        mRecycleView.setAdapter(mAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mModel.save(this);
    }
    private void getPermision(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.RECORD_AUDIO)){
            }
            else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},REQUEST_RECORD_AUDIO_PERMISSION);
            }
        }
        else {
            onPermissionGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],int[] grantResults) {
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            onPermissionGranted();
        }
    }

    public void onPermissionGranted() {
        Log.d("Permission","Granted");

        return;

       /* Intent i = new Intent(this, NavActivity.class);
        i.setAction(NavActivity.ACTION_PERMISSION_GRANTED);
        startService(i);*/
    }
}
