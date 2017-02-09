package com.qianmo.suspendcircle;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static android.os.Build.VERSION_CODES.M;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_start;
    private Button btn_end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_end = (Button) findViewById(R.id.btn_end);
        btn_start.setOnClickListener(this);
        btn_end.setOnClickListener(this);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public void onClick(View v) {
        switch (v.getId()) {
            //启动服务
            case R.id.btn_start:
                Intent intent = new Intent(MainActivity.this, WindowService.class);
                startService(intent);
                break;
            //停止服务
            case R.id.btn_end:
                Intent intent1 = new Intent(MainActivity.this, WindowService.class);
                stopService(intent1);
                break;
        }
    }
}
