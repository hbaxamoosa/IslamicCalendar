package com.boxofm.islamiccalendar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.icu.util.IslamicCalendar;
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
import java.util.Locale;
import java.util.Objects;

import static android.icu.util.IslamicCalendar.CalculationType.ISLAMIC;

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
        Log.v(TAG, "TESTING JD: " + jd_date);
        double[] islamic_date = Utility.jd_to_islamic(jd_date);
        String[] islamicMonths = getResources().getStringArray(R.array.hijri_months);
        Log.v(TAG, "TESTING Islamic: " + "year is " + islamic_date[0] + " month is " + islamicMonths[(int) (islamic_date[1] - 1)] + " day is " + islamic_date[2]);



        IslamicCalendar islamic = new IslamicCalendar();
        //islamic.set(2010, 3, 20);
        islamic.setCalculationType(ISLAMIC);
        Log.v(TAG, "TESTING islamic.getType(): " + islamic.getType());
        Log.v(TAG, "TESTING islamic.getTime(): " + islamic.getTime());
        Log.v(TAG, "TESTING islamic.get(Calendar.YEAR): " + islamic.get(Calendar.YEAR));
        Log.v(TAG, "TESTING islamic.get(Calendar.MONTH): " + islamic.get(Calendar.MONTH));
        Log.v(TAG, "TESTING islamic.get(Calendar.DAY_OF_MONTH): " + islamic.get(Calendar.DAY_OF_MONTH));

        Calendar MishaBirthDay = Calendar.getInstance(Locale.US);
        MishaBirthDay.set(2010, 3, 20);
        Log.v(TAG, "TESTING date: " + MishaBirthDay);
        IslamicCalendar birthday_misha = new IslamicCalendar();
        birthday_misha.setTime(MishaBirthDay.getTime());
        birthday_misha.setCalculationType(ISLAMIC);
        Log.v(TAG, "TESTING birthday_misha.getType(): " + birthday_misha.getType());
        Log.v(TAG, "TESTING birthday_misha.getTime(): " + birthday_misha.getTime());
        Log.v(TAG, "TESTING birthday_misha.get(Calendar.YEAR): " + birthday_misha.get(Calendar.YEAR));
        Log.v(TAG, "TESTING birthday_misha.get(Calendar.MONTH): " + birthday_misha.get(Calendar.MONTH));
        Log.v(TAG, "TESTING birthday_misha.get(Calendar.DAY_OF_MONTH): " + birthday_misha.get(Calendar.DAY_OF_MONTH));

        Calendar YasmeenBirthDay = Calendar.getInstance(Locale.US);
        YasmeenBirthDay.set(1956, 2, 8);
        Log.v(TAG, "TESTING date: " + YasmeenBirthDay);
        IslamicCalendar birthday_yasmeen = new IslamicCalendar();
        birthday_yasmeen.setTime(YasmeenBirthDay.getTime());
        birthday_yasmeen.setCalculationType(ISLAMIC);
        Log.v(TAG, "TESTING birthday_yasmeen.getType(): " + birthday_yasmeen.getType());
        Log.v(TAG, "TESTING birthday_yasmeen.getTime(): " + birthday_yasmeen.getTime());
        Log.v(TAG, "TESTING birthday_yasmeen.get(Calendar.YEAR): " + birthday_yasmeen.get(Calendar.YEAR));
        Log.v(TAG, "TESTING birthday_yasmeen.get(Calendar.MONTH): " + islamicMonths[(birthday_yasmeen.get(Calendar.MONTH) - 1)]);
        Log.v(TAG, "TESTING birthday_yasmeen.get(Calendar.DAY_OF_MONTH): " + birthday_yasmeen.get(Calendar.DAY_OF_MONTH));

        double meghana_jd_date = Utility.gregorian_to_jd(1980, 12, 19); // 13 Safar-Ul-Muzaffar
        Log.v(TAG, "TESTING meghana JD: " + meghana_jd_date);
        double[] meghana_islamic_date = Utility.jd_to_islamic(meghana_jd_date);
        Log.v(TAG, "TESTING meghana Islamic: " + "year is " + meghana_islamic_date[0] + " month is " + islamicMonths[(int) (meghana_islamic_date[1] - 1)] + " day is " + meghana_islamic_date[2]);

        double misha_jd_date = Utility.gregorian_to_jd(2010, 03, 20); // 05 Rabi-Ul-Akhar
        Log.v(TAG, "TESTING misha JD: " + misha_jd_date);
        double[] misha_islamic_date = Utility.jd_to_islamic(misha_jd_date);
        Log.v(TAG, "TESTING misha Islamic: " + "year is " + misha_islamic_date[0] + " month is " + islamicMonths[(int) (misha_islamic_date[1] - 1)] + " day is " + misha_islamic_date[2]);

        double hasnain_jd_date = Utility.gregorian_to_jd(1982, 02, 18); // 24 Rabi-Ul-Akhar
        Log.v(TAG, "TESTING hasnain JD: " + hasnain_jd_date);
        double[] hasnain_islamic_date = Utility.jd_to_islamic(hasnain_jd_date);
        Log.v(TAG, "TESTING hasnain Islamic: " + "year is " + hasnain_islamic_date[0] + " month is " + islamicMonths[(int) (hasnain_islamic_date[1] - 1)] + " day is " + hasnain_islamic_date[2]);

        double haroun_jd_date = Utility.gregorian_to_jd(2013, 05, 30); // 21 Rajab-Ul-Asab
        Log.v(TAG, "TESTING haroun JD: " + haroun_jd_date);
        double[] haroun_islamic_date = Utility.jd_to_islamic(haroun_jd_date);
        Log.v(TAG, "TESTING haroun Islamic: " + "year is " + haroun_islamic_date[0] + " month is " + islamicMonths[(int) (haroun_islamic_date[1] - 1)] + " day is " + haroun_islamic_date[2]);

        double shabbir_jd_date = Utility.gregorian_to_jd(1945, 04, 13); // 01 Jama-Dil-Ula
        Log.v(TAG, "TESTING Shabbir JD: " + shabbir_jd_date);
        double[] shabbir_islamic_date = Utility.jd_to_islamic(shabbir_jd_date);
        Log.v(TAG, "TESTING Shabbir Islamic: " + "year is " + shabbir_islamic_date[0] + " month is " + islamicMonths[(int) (shabbir_islamic_date[1] - 1)] + " day is " + shabbir_islamic_date[2]);

        double yasmeen_jd_date = Utility.gregorian_to_jd(1956, 02, 8); // 26 Jama-Dil-Ukhra
        Log.v(TAG, "TESTING Yasmeen JD: " + yasmeen_jd_date);
        double[] yasmeen_islamic_date = Utility.jd_to_islamic(yasmeen_jd_date);
        Log.v(TAG, "TESTING Yasmeen Islamic: " + "year is " + yasmeen_islamic_date[0] + " month is " + islamicMonths[(int) (yasmeen_islamic_date[1] - 1)] + " day is " + yasmeen_islamic_date[2]);

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
            return new DatePickerDialog(Objects.requireNonNull(getActivity()), this, year, month, day);
        }

        @SuppressLint("DefaultLocale")
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            // Do something with the date chosen by the user
            TextView date = Objects.requireNonNull(getActivity()).findViewById(R.id.IslamicDate);
            date.setText(String.format("Year: %d Month: %d Day: %d", view.getYear(), view.getMonth(), view.getDayOfMonth()));
        }
    }
}
