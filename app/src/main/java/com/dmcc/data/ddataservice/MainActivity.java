package com.dmcc.data.ddataservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dmcc.data.dataservice.deamon.DmccDataHelperService;
import com.dmcc.data.dataservice.deamon.DmccDataService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, DmccDataService.class));
        startService(new Intent(this, DmccDataHelperService.class));
    }
}
