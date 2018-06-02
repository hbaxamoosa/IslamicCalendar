package com.boxofm.islamiccalendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView = findViewById(R.id.IslamicDate);
        DatePicker datePicker = findViewById(R.id.datePicker);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        returnDate(year, month, day);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                returnDate(year, monthOfYear, dayOfMonth);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                */
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
    }

    private void returnDate(int year, int monthOfYear, int dayOfMonth) {
        double jd_date = Utility.gregorian_to_jd(year, monthOfYear, dayOfMonth);
        // Log.v(TAG, "JD: " + jd_date);
        double[] islamic_date = Utility.jd_to_islamic(jd_date);
        String[] islamicMonths = getResources().getStringArray(R.array.hijri_months);
        // Log.v(TAG, "Islamic: " + "year is " + islamic_date[0] + " month is " + islamicMonths[(int) islamic_date[1]] + " day is " + islamic_date[2]);
        textView.setText(new StringBuilder().append("JD: ").append(dayOfMonth).append("/")
                .append(monthOfYear).append("/").append(year));
        isItToday(jd_date);
    }

    private void isItToday(double jd_date) {
        // first get today's islamic day and month
        Calendar currentDate = Calendar.getInstance();
        double currentJdDate = Utility.gregorian_to_jd(currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH));
        Log.v(TAG, "currentJdDate: " + currentJdDate);
        double[] currentIslamicDate = Utility.jd_to_islamic(currentJdDate);
        String[] islamicMonths = getResources().getStringArray(R.array.hijri_months);

        // today's Islamic date
        Log.v(TAG, "currentIslamicDate: " + "year is " + currentIslamicDate[0] + " month is " + islamicMonths[(int) currentIslamicDate[1]] + " day is " + currentIslamicDate[2]);

        // selected Islamic date
        double[] selectedIsalmicDate = Utility.jd_to_islamic(jd_date);
        Log.v(TAG, "selectedIsalmicDate: " + "year is " + selectedIsalmicDate[0] + " month is " + islamicMonths[(int) selectedIsalmicDate[1]] + " day is " + selectedIsalmicDate[2]);

        if ((currentIslamicDate[1] == selectedIsalmicDate[1]) && (currentIslamicDate[2] == selectedIsalmicDate[2])) {

            Context context = getApplicationContext();
            CharSequence text = "Islamic Month and Day are the same!";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
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

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            // Do something with the date chosen by the user
            TextView date = getActivity().findViewById(R.id.IslamicDate);
            date.setText("Year: " + view.getYear() + " Month: " + view.getMonth() + " Day: " + view.getDayOfMonth());
        }
    }
}
