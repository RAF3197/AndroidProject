package com.raf.jedi.project;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
import android.widget.Chronometer;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.io.File;
import java.io.FileDescriptor;
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
    private MyAdapter mAdapter;
    private ModelContainer mModel;
    private Boolean recording = false;
    private String fileName = null;
    private TextView cron;
    private Chronometer chronometer;

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static final int REQ_PERM = 42;
    public static final String ACTION_PERMISSION_GRANTED = "action_permission_granted";
    private String mFileName;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
        chronometer = (Chronometer) findViewById(R.id.chronometerTimer);
        chronometer.setBase(SystemClock.elapsedRealtime());


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
        else if (id == R.id.author) {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            builder.setTitle("Ricard Abril")
                    .setMessage("ricard.abril.ferreres@est.fib.upc.edu")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void startRecording() {

       /* mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);*/


        mCurrentRecording = new AudioModel();

        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        DateFormat formDate = new SimpleDateFormat("dd:MM:yyy");
        DateFormat formHour = new SimpleDateFormat("HH:mm:ss");


        /*mCurrentRecording.creationTime = (String.valueOf(formDate.format(date)) +"_" + String.valueOf(formHour.format(date)));
        mFileName = getExternalCacheDir().getAbsolutePath();
        File a = new File(mFileName + "/" + String.valueOf(formDate.format(date)) + "_" +  String.valueOf(formHour.format(date)) + ".3gp");
        a.setReadable(true);
        //String auxFileName = a.getAbsolutePath() + "/" + String.valueOf(formDate.format(date)) + "_" +  String.valueOf(formHour.format(date)) + ".3gp";
        Log.d("THE_PATH ->",a.getAbsolutePath());
        mRecorder.setOutputFile(a);
        mCurrentRecording.myAudio=a;
        mCurrentRecording.path = a.getAbsolutePath();
        Log.d("name",mCurrentRecording.creationTime);*/
        /*String outputFile = Environment.getExternalStorageDirectory().getAbsolutePath()  + "/" + String.valueOf(formDate.format(date)) + "_" +  String.valueOf(formHour.format(date)) + ".3gp";
        mRecorder.setOutputFile(outputFile);
        mCurrentRecording.path = outputFile;
        mCurrentRecording.creationTime = (String.valueOf(formDate.format(date)) +"_" + String.valueOf(formHour.format(date)));

        try{
            mRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }


        mRecorder.start();*/

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        File root = android.os.Environment.getExternalStorageDirectory();
        File file = new File(root.getAbsolutePath() + "/VoiceRecorder/Audios");
        if (!file.exists()) {
            file.mkdirs();
        }

        fileName =  root.getAbsolutePath() + "/VoiceRecorder/Audios/" + String.valueOf(System.currentTimeMillis() + ".mp3");
        Log.d("filename",fileName);
        mRecorder.setOutputFile(fileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mCurrentRecording.path = fileName;
        mCurrentRecording.creationTime = String.valueOf(formDate.format(date)) + "_" +  String.valueOf(formHour.format(date));
        try {
            mRecorder.prepare();
            mRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        chronometer.setVisibility(View.VISIBLE);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        chronometer.stop();
        chronometer.setBase(SystemClock.elapsedRealtime());
        mRecorder = null;
        chronometer.setVisibility(View.GONE);
        Duration();
        mModel.audios.add(mCurrentRecording);
        mCurrentRecording = null;
        mModel.print();
        mModel.save(this);
        onResume();
    }

    private void Duration() {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(mCurrentRecording.path);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCurrentRecording.duration = mediaPlayer.getDuration();
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
                playRecord(pos);
            }



        });

        mRecycleView.setAdapter(mAdapter);
    }

    private void playRecord(int pos) {
        Intent i = new Intent(this,PlayRecord.class);
        i.putExtra("pos",pos);
        startActivity(i);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mModel.save(this);
    }
    private void getPermision() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 200);
            }
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        42);
            }
        }
    }




}
