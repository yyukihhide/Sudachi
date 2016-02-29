package com.bazan.mobile10.sudachi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.FrameLayout;
import android.widget.TextView;


/**
 * Created by bzmini02 on 2016/02/23.
 */
public class Description extends AppCompatActivity {
    ScrollView scrollView;
    LinearLayout linearLayout;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description);

        ScrollView scrollView = new ScrollView(this);
        linearLayout = new LinearLayout(this);

        scrollView.addView(linearLayout);

        TextView textView3 = (TextView) findViewById(R.id.textView3);



    }
}