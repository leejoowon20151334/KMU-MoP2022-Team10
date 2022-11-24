package com.mop2022.team10;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;


public class Popup_3_1 extends Activity {
    TextView due_tv;
    TextView count_tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_3_1);
        due_tv = (TextView) findViewById(R.id.dialog_due);
        count_tv = (TextView) findViewById(R.id.dialog_count);

        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        due_tv.setText(data);
    }
    public void mOnClose(View v){
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

}
