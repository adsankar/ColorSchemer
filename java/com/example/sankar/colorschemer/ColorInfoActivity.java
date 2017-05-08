package com.example.sankar.colorschemer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class ColorInfoActivity extends Activity {

    private int[] textRGB = {255,255,255};
    private int[] bkgRGB = {255,255,255};
    private int opacity = 100;

    public void updateText(){
        setContentView(R.layout.info_screen);

        ((TextView)findViewById(R.id.textRGB)).setText(toRGB(textRGB[0], textRGB[1], textRGB[2]));
        ((TextView)findViewById(R.id.bkgRGB)).setText(toRGB(bkgRGB[0], bkgRGB[1], bkgRGB[2]));
        ((TextView)findViewById(R.id.textHex)).setText(toHex(textRGB[0], textRGB[1], textRGB[2]));
        ((TextView)findViewById(R.id.bkgHex)).setText(toHex(bkgRGB[0], bkgRGB[1], bkgRGB[2]));
        ((TextView)findViewById(R.id.textHSV)).setText(toHSV(textRGB[0], textRGB[1], textRGB[2]));
        ((TextView)findViewById(R.id.bkgHSV)).setText(toHSV(bkgRGB[0], bkgRGB[1], bkgRGB[2]));
        ((TextView)findViewById(R.id.opacityTextView)).setText("" + opacity+"%");
//        ((TextView)findViewById(R.id.textRGB)).setText("Your Text");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


               /* if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                  .add(R.id.container, new PlaceholderFragment()).commit();
    }*/

        Intent i = getIntent();
        opacity = i.getIntExtra(FullscreenActivity.OPACITY_EXTRA, 100);
        textRGB = i.getIntArrayExtra(FullscreenActivity.TEXT_RGB_EXTRA_MESSAGE);
        bkgRGB = i.getIntArrayExtra(FullscreenActivity.BKG_RGB_EXTRA_MESSAGE);

        updateText();

        //getIntExtra
        //getIntArrayExtra
        setContentView(R.layout.info_screen);
        final View contentView = findViewById(R.id.info_screen_content);
        updateText();

       contentView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               finish();
           }
       });

}

    public void onReturnClick(View view){

        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            finish();

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private String toHSV(int r, int g, int b){
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        float[] HSV = new float[3];
        Color.RGBToHSV(r, g, b, HSV);


        //Color.colorToHSV(Integer.parseInt(toHex(r, g, b), HSV);

        return "("+df.format(HSV[0])+", "+df.format(HSV[1])+", "+df.format(HSV[2])+")";
    }

    private String toHex(int r, int g, int b){
        return String.format("#%02X%02X%02X", r, g, b);
    }

    private String toRGB(int r, int g, int b){
        return "("+ r+", "+ g+ ", "+ b+")";
    }

    //((TextView)findViewById(R.id.fullscreen_content)).setText("Your Text");


}


