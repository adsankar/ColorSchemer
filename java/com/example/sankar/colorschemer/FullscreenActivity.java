package com.example.sankar.colorschemer;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sankar.colorschemer.util.SystemUiHider;

import java.util.Random;

import static com.example.sankar.colorschemer.R.id;
import static com.example.sankar.colorschemer.R.layout;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class FullscreenActivity extends Activity {

    public final static String OPACITY_EXTRA = "com.example.sankar.colorschemer.OPACITY_MESSAGE";
    public final static String BKG_RGB_EXTRA_MESSAGE = "com.example.sankar.colorschemer.BKG_RGB_MESSAGE";
    public final static String TEXT_RGB_EXTRA_MESSAGE = "com.example.sankar.colorschemer.TEXT_RGB_MESSAGE";



    private static int opacity = 100;
    private static int[] bkgRGB = {0,153,204};
    private static int[] textRGB = {51, 81,229};
    private static int caseNumber = 1;
    private static Random r = new Random(465);
    private int backButtonCount = 0;

    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = false;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(layout.activity_fullscreen);

        final View controlsView = findViewById(id.fullscreen_content_controls);
        final View contentView = findViewById(id.fullscreen_content);

        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider
                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    // Cached values.
                    int mControlsHeight;
                    int mShortAnimTime;

                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    public void onVisibilityChange(boolean visible) {
                        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                            // If the ViewPropertyAnimator API is available
                            // (Honeycomb MR2 and later), use it to animate the
                            // in-layout UI controls at the bottom of the
                            // screen.
                            if (mControlsHeight == 0) {
                                mControlsHeight = controlsView.getHeight();
                            }
                            if (mShortAnimTime == 0) {
                                mShortAnimTime = getResources().getInteger(
                                        android.R.integer.config_shortAnimTime);
                            }
                            controlsView.animate()
                                    .translationY(visible ? 0 : mControlsHeight)
                                    .setDuration(mShortAnimTime);
                        } else {
                            // If the ViewPropertyAnimator APIs aren't
                            // available, simply show or hide the in-layout UI
                            // controls.*/
                          //  controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
                        //}

                     /*   if (visible && AUTO_HIDE) {
                            // Schedule a hide().
                            delayedHide(AUTO_HIDE_DELAY_MILLIS);
                        }*/
                    }
                });

        // Set up the user interaction to manually show or hide the system UI.
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generate(caseNumber);
                /*if (TOGGLE_ON_CLICK) {
                    mSystemUiHider.toggle();
                } else {
                    mSystemUiHider.show();
                }*/
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
      //  findViewById(R.id.generate_button).setOnTouchListener(mDelayHideTouchListener);

        SeekBar seekBar = (SeekBar)findViewById(id.seekBar);

        seekBar.setMax(100);
        seekBar.setProgress(100);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                opacity =  progress;
                update();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // nothing here
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // nothing here
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
       // delayedHide(100);
    }


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }

            return false;
        }
    };

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }


    /**
     * My stuff here onward
     */

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case id.radioButton1:
                if (checked)
                    caseNumber = 1;
                    break;
            case id.radioButton2 :
                if (checked)
                    caseNumber = 2;
                    break;
            case id.radioButton3 :
                if (checked)
                    caseNumber = 3;
                break;
        }
    }



    private int[] newColor(){
        int[] RGB = new int[3];
        RGB[0] = r.nextInt(256);
        RGB[1] = r.nextInt(256);
        RGB[2] = r.nextInt(256);
        return RGB;
    }

    public void onInfoClick(View view){
        //more goes here

       // setContentView(layout.info_screen);
        //Starting a new Intent
        Intent nextScreen = new Intent(this, ColorInfoActivity.class);


        //Sending data to another Activity
        nextScreen.putExtra(TEXT_RGB_EXTRA_MESSAGE, textRGB);
        nextScreen.putExtra(BKG_RGB_EXTRA_MESSAGE, bkgRGB);
        nextScreen.putExtra(OPACITY_EXTRA, opacity);


        startActivity(nextScreen);

    }

    private void generate(int caseNumber){
        switch (caseNumber){
            case 1://change text and background
                bkgRGB = newColor();
                textRGB = newColor();
                update();
                break;
            case 2://change only background color (keep text color)
                bkgRGB = newColor();
                update();
                break;
            case 3://change only text color (keep background color)
                textRGB = newColor();
                update();
                break;
        }
    }


    private void update(){

        findViewById(id.fullscreen_content).setBackgroundColor(Color.argb( 255, bkgRGB[0], bkgRGB[1], bkgRGB[2]));//our colors go here
        TextView tv = (TextView)findViewById( id.fullscreen_content);
        tv.setTextColor(Color.argb((int)(opacity*2.55), textRGB[0], textRGB[1], textRGB[2]));

    }

    /**
     * Back button listener.
     * Will close the application if the back button pressed twice.
     */
    public void onBackPressed()
    {
        if(backButtonCount >= 1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Press back again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }

}
