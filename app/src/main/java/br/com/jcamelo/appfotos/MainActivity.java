package br.com.jcamelo.appfotos;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import java.io.File;

import br.com.jcamelo.appfotos.view.UserFragment;

public class MainActivity extends AppCompatActivity {

    private UserFragment userFragment;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onInject();

    }

    private void onInject() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        userFragment = new UserFragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.main_frame_layout, userFragment).commit();

        createFolder();
        requestPermissionCamera();
    }

    private void createFolder() {
        String[] folders = getResources().getStringArray(R.array.folder);
        for (int i = 0; i < folders.length; i++) {
            File file = new File(Environment.getExternalStorageState(), String.valueOf(getExternalFilesDir(folders[i])));
            if (file.exists()) {
            } else {
                file.mkdir();
            }
        }
    }

    private void requestPermissionCamera(){
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.CAMERA}, 1);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}