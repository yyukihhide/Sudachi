package com.bazan.mobile10.sudachi;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class LocalSetting extends AppCompatActivity{
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Listの作成
        ArrayList<String> list = new ArrayList<String>();
        // Listにデータを入れる
        list.add("北海道");
        list.add("青森県");
        list.add("岩手県");
        list.add("宮城県");
        list.add("山形県");
        list.add("福島県");
        list.add("茨城県");
        list.add("栃木県");
        list.add("群馬県");
        list.add("埼玉県");
        list.add("千葉県");
        list.add("東京都");
        list.add("志村県");
        list.add("神奈川県");
        list.add("新潟県");
        list.add("富山県");
        list.add("石川県");
        list.add("福井県");
        list.add("山梨県");
        list.add("長野県");
        list.add("岐阜県");
        list.add("静岡県");
        list.add("愛知県");
        list.add("三重県");
        list.add("滋賀県");
        list.add("京都府");
        list.add("大阪府");
        list.add("兵庫県");
        list.add("奈良県");
        list.add("和歌山県");
        list.add("鳥取県");
        list.add("島根県");
        list.add("岡山県");
        list.add("広島県");
        list.add("山口県");
        list.add("徳島");
        list.add("香川");
        list.add("高知");
        list.add("愛媛");
        list.add("福岡県");
        list.add("佐賀県");
        list.add("長崎県");
        list.add("熊本県");
        list.add("大分県");
        list.add("宮崎県");
        list.add("鹿児島県");
        list.add("沖縄県");


        // Adapterの作成
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        // ドロップダウンのレイアウトを指定
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // ListViewにAdapterを関連付ける
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);


        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v == button) {
                    Intent intent = new Intent(LocalSetting.this, Description.class);
                    startActivity(intent);
                }


            }


        });
    }
}