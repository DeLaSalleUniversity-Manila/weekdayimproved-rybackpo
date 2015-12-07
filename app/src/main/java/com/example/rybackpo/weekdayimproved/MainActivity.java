package com.example.rybackpo.weekdayimproved;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements MyInterface {

    public final static String DEBUG_TAG = "MainActivity.java";

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        private DatePickerDialog dpd;
        private int count;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            count = 0;

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);


            dpd = new DatePickerDialog(getActivity(), this, year, month, day);
            dpd.setTitle("Input the date you want to check: ");
            dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "CHECK DAY", dpd);

            return dpd;
        }


        public void onDateSet(DatePicker v, int year, int month, int day) {

            if(count % 2 == 0) {

                if (v.isShown()) {
                    dpd.updateDate(year, month, day);
                }

                month++;

                if (month == 1 || month == 2) {
                    month += 12;
                    year--;
                }

                int tempyear = ((year / 10) % 10) * 10 + year % 10;
                int century = (int) (year / 100.0);

                Log.d(DEBUG_TAG, "month = " + month);
                Log.d(DEBUG_TAG, "day = " + day);
                Log.d(DEBUG_TAG, "tempyear = " + tempyear + ", " + "century = " + century);

                int dateoutput = (day + (int) (26 * (month + 1) / 10.0) + tempyear + (int) (tempyear / 4.0) + (int) (century / 4.0) + 5 * century) % 7;

                Log.d(DEBUG_TAG, "dateoutput = " + dateoutput);

                setDayMessage(dateoutput);
            }
            count++;
        }


        public void setDayMessage(int day) {
            MyInterface myinterface = (MyInterface) getActivity();
            String daytext;
            switch (day) {
                case 0:
                    daytext = "It's Saturday!";
                    break;
                case 1:
                    daytext = "It's Sunday!";
                    break;
                case 2:
                    daytext = "It's Monday!";
                    break;
                case 3:
                    daytext = "It's Tuesday!";
                    break;
                case 4:
                    daytext = "It's Wednesday!";
                    break;
                case 5:
                    daytext = "It's Thursday!";
                    break;
                case 6:
                    daytext = "It's Friday!";
                    break;
                default:
                    daytext = "Invalid day!";
            }

            myinterface.setText(daytext);

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pickDateDisplayDay();
    }

    public void pickDateDisplayDay() {
        DialogFragment df = new DatePickerFragment();
        df.show(getFragmentManager(), "datePicker");
    }

    public void setText(String message){
        TextView textoutput = (TextView) findViewById(R.id.textViewOut);
        textoutput.setText(message);
        fadeAnimation(textoutput);
        textoutput.setVisibility(View.INVISIBLE);
    }

    public void fadeAnimation(TextView tv){

        Animation fade = AnimationUtils.loadAnimation(this, R.anim.fade);
        tv.startAnimation(fade);

        fade.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                pickDateDisplayDay();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }

        });
    }


}
