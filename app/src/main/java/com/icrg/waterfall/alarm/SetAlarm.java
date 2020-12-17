package com.icrg.waterfall.alarm;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;

import com.icrg.waterfall.R;
public class SetAlarm extends PreferenceActivity implements TimePickerDialog.OnTimeSetListener 
{
    //private EditTextPreference mLabel;
    private Preference mTimePref;
    private AlarmPreference mAlarmPref;
    private CheckBoxPreference mVibratePref;
    private RepeatPreference mRepeatPref;
    private MenuItem mDeleteAlarmItem;
    private MenuItem mTestAlarmItem;

    private int     mId;
    private boolean mEnabled;
    private int     mHour;
    private int     mMinutes;
    private ImageView img;
    /**
     * Set an alarm.  Requires an Alarms.ALARM_ID to be passed in as an
     * extra. FIXME: Pass an Alarm object like every other Activity.
     */
    @Override
    protected void onCreate(Bundle icicle) 
    {
        super.onCreate(icicle);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        addPreferencesFromResource(R.xml.alarm_prefs);
        //setContentView(R.layout.customlistview);
        
        img = new ImageView(SetAlarm.this);
        img.setBackgroundResource(R.drawable.bg);

        // Get each preference so we can retrieve the value later.
        /*mLabel = (EditTextPreference) findPreference("label");
        mLabel.setOnPreferenceChangeListener(
                new Preference.OnPreferenceChangeListener() {
                    public boolean onPreferenceChange(Preference p,
                            Object newValue) {
                        // Set the summary based on the new label.
                        p.setSummary((String) newValue);
                        return true;
                    }
                });*/
        mTimePref = findPreference("time");
        mAlarmPref = (AlarmPreference) findPreference("alarm");
        mVibratePref = (CheckBoxPreference) findPreference("vibrate");
        mRepeatPref = (RepeatPreference) findPreference("setRepeat");

        Intent i = getIntent();
        mId = i.getIntExtra(Alarms.ALARM_ID, -1);
        if (Log.LOGV) 
        {
            Log.v("In SetAlarm, alarm id = " + mId);
        }

        /* load alarm details from database */
        Alarm alarm = Alarms.getAlarm(getContentResolver(), mId);
        // Bad alarm, bail to avoid a NPE.
        if (alarm == null) 
        {
            finish();
            return;
        }
        mEnabled = alarm.enabled;
        //mLabel.setText(alarm.label);
        //mLabel.setSummary(alarm.label);
        mHour = alarm.hour;
        mMinutes = alarm.minutes;
        mRepeatPref.setDaysOfWeek(alarm.daysOfWeek);
        mVibratePref.setChecked(alarm.vibrate);
        // Give the alert uri to the preference.
        mAlarmPref.setAlert(alarm.alert);
        updateTime();

        // We have to do this to get the save/cancel buttons to highlight on
        // their own.
        getListView().setItemsCanFocus(true);

        // Grab the content view so we can modify it.
        FrameLayout content = (FrameLayout) getWindow().getDecorView()
                .findViewById(android.R.id.content);

        // Get the main ListView and remove it from the content view.
        ListView lv = getListView();
        content.removeView(lv);

        // Create the new LinearLayout that will become the content view and
        // make it vertical.
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setBackgroundResource(R.drawable.bg);
        ll.bringToFront();

        // Have the ListView expand to fill the screen minus the save/cancel
        // buttons.
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,  LayoutParams.WRAP_CONTENT);
                
        lp.weight = 1;        
        ll.addView(lv, lp);        

        // Inflate the buttons onto the LinearLayout.
        View v = LayoutInflater.from(this).inflate(R.layout.save_cancel_alarm, ll);
        //View v1 = LayoutInflater.from(this).inflate(R.layout.customlistview, ll);

        // Attach actions to each button.
        Button b = (Button) v.findViewById(R.id.alarm_save);
        b.setOnClickListener(new View.OnClickListener() 
        {
                public void onClick(View v) 
                {
                    // Enable the alarm when clicking "Done"
                    mEnabled = true;
                    saveAlarm();
                    finish();
                }
        });
        b = (Button) v.findViewById(R.id.alarm_cancel);
        b.setOnClickListener(new View.OnClickListener() 
        {
                public void onClick(View v) 
                {
                    finish();
                }
        });

        // Replace the old content view with our new one.
        setContentView(ll);
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) 
    {
        if (preference == mTimePref) 
        {
            new TimePickerDialog(this, this, mHour, mMinutes, DateFormat.is24HourFormat(this)).show();
        }

        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    @Override
    public void onBackPressed() 
    {
        saveAlarm();
        finish();
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) 
    {
        mHour = hourOfDay;
        mMinutes = minute;
        updateTime();
        // If the time has been changed, enable the alarm.
        mEnabled = true;
    }

    private void updateTime() 
    {
        if (Log.LOGV) 
        {
            Log.v("updateTime " + mId);
        }
        mTimePref.setSummary(Alarms.formatTime(this, mHour, mMinutes, mRepeatPref.getDaysOfWeek()));
    }

    private void saveAlarm() 
    {
        final String alert = mAlarmPref.getAlertString();
        long time = Alarms.setAlarm(this, mId, mEnabled, mHour, mMinutes,
                mRepeatPref.getDaysOfWeek(), mVibratePref.isChecked(),
                "", alert);       
    }

    /**
     * Write alarm out to persistent store and pops toast if alarm
     * enabled.
     * Used only in test code.
     */
    private static void saveAlarm(
            Context context, int id, boolean enabled, int hour, int minute,
            Alarm.DaysOfWeek daysOfWeek, boolean vibrate, String label,
            String alert, boolean popToast) {
        if (Log.LOGV) Log.v("** saveAlarm " + id + " " + label + " " + enabled
                + " " + hour + " " + minute + " vibe " + vibrate);

        // Fix alert string first
        long time = Alarms.setAlarm(context, id, enabled, hour, minute, daysOfWeek, vibrate, label, alert);
        
    }

    

    public boolean onCreateOptionsMenu(Menu menu) 
    {
        super.onCreateOptionsMenu(menu);

        mDeleteAlarmItem = menu.add(0, 0, 0, R.string.delete_alarm);
        mDeleteAlarmItem.setIcon(android.R.drawable.ic_menu_delete);

        if (AlarmClock.DEBUG) 
        {
            mTestAlarmItem = menu.add(0, 0, 0, "test alarm");
        }

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) 
    {
        if (item == mDeleteAlarmItem) 
        {
            Alarms.deleteAlarm(this, mId);
            finish();
            return true;
        }
        if (AlarmClock.DEBUG) 
        {
            if (item == mTestAlarmItem) 
            {
                setTestAlarm();
                return true;
            }
        }
        return false;
    }


    /**
     * Test code: this is disabled for production build.  Sets
     * this alarm to go off on the next minute
     */
    void setTestAlarm() 
    {
    	// start with now
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());

        int nowHour = c.get(java.util.Calendar.HOUR_OF_DAY);
        int nowMinute = c.get(java.util.Calendar.MINUTE);

        int minutes = (nowMinute + 1) % 60;
        int hour = nowHour + (nowMinute == 0 ? 1 : 0);

        saveAlarm(this, mId, true, hour, minutes, mRepeatPref.getDaysOfWeek(),
                true, "", mAlarmPref.getAlertString(), true);
    }

}
