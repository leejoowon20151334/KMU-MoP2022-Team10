package com.mop2022.team10;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;


public class Popup_3_1 extends Activity {
    ImageView imageView_popup;
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
        imageView_popup = (ImageView)findViewById(R.id.imageView_popup);

        Intent intent = getIntent();
        Bitmap img = intent.getParcelableExtra("image");
        String data = intent.getStringExtra("data");
        double count = intent.getDoubleExtra("count",0);

        due_tv.setText(data);
        count_tv.setText(Double.toString(count));
        imageView_popup.setImageBitmap(img);
    }
    public void mOnClose(View v){
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

}
