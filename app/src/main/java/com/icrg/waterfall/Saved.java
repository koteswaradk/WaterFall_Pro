package com.icrg.waterfall;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class Saved {   

    /**
     * Creates a new Alarm.
     * @param filename 
     */
    public static Uri addItem(ContentResolver contentResolver, String filename) 
    {
        ContentValues values = new ContentValues();
        values.put(Save.Columns.FILENAME, "fname");
        return contentResolver.insert(Save.Columns.CONTENT_URI, values);
    }

    /**
     * Removes an existing Alarm.  If this alarm is snoozing, disables
     * snooze.  Sets next alert.
     */
    public static void deleteItem(Context context, int ItemId) {

        ContentResolver contentResolver = context.getContentResolver();
        /* If alarm is snoozing, lose it */
        //disableSnoozeAlert(context, alarmId);

        Uri uri = ContentUris.withAppendedId(Save.Columns.CONTENT_URI, ItemId);
        contentResolver.delete(uri, "", null);

        //setNextAlert(context);
    }

    /**
     * Queries all alarms
     * @return cursor over all alarms
     */
    public static Cursor getSavedCursor(ContentResolver contentResolver) 
    {
        return contentResolver.query(Save.Columns.CONTENT_URI, Save.Columns.SAVE_QUERY_COLUMNS,
                null, null, null);
    }

    // Private method to get a more limited set of alarms from the database.
    /*private static Cursor getFilteredAlarmsCursor(
            ContentResolver contentResolver) {
        return contentResolver.query(Save.Columns.CONTENT_URI,
        		Save.Columns.SAVE_QUERY_COLUMNS, null,
                null, null);
    }*/

    /**
     * Return an Alarm object representing the alarm id in the database.
     * Returns null if no alarm exists.
     */
    public static Save getSave(ContentResolver contentResolver, int alarmId) {
        Cursor cursor = contentResolver.query(
                ContentUris.withAppendedId(Save.Columns.CONTENT_URI, alarmId),
                Save.Columns.SAVE_QUERY_COLUMNS,
                null, null, null);
        Save alarm = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                alarm = new Save(cursor);
            }
            cursor.close();
        }
        return alarm;
    }


    /**
     * A convenience method to set an alarm in the Alarms
     * content provider.
     *
     * @param id             corresponds to the _id column
     * @param enabled        corresponds to the ENABLED column
     * @param hour           corresponds to the HOUR column
     * @param minutes        corresponds to the MINUTES column
     * @param daysOfWeek     corresponds to the DAYS_OF_WEEK column
     * @param time           corresponds to the ALARM_TIME column
     * @param vibrate        corresponds to the VIBRATE column
     * @param message        corresponds to the MESSAGE column
     * @param alert          corresponds to the ALERT column
     * @return Time when the alarm will fire.
     */
    public static long setItem(Context context, int id, String filename, 
    		                   boolean bird_animation,  boolean butterfly, boolean rain_animation, boolean snow, boolean rainbow, boolean sunrays, boolean thunder_animation,
    		                   boolean morning, boolean bird_ambiance, boolean evening, boolean cricket, boolean rain_ambiance, boolean forest, boolean wind, boolean river, boolean thunder_ambiance, boolean night,
                               int instrumental_position) 
    {

        ContentValues values = new ContentValues(19);
        ContentResolver resolver = context.getContentResolver();
        // Set the alarm_time value if this alarm does not repeat. This will be
        // used later to disable expired alarms.
        long time = 0;
        /*if (!daysOfWeek.isRepeatSet()) {
            //time = calculateAlarm(hour, minutes, daysOfWeek).getTimeInMillis();
        }*/

        /*if (Log.LOGV) Log.v(
                "**  setAlarm * idx " + id + " hour " + hour + " minutes " +
                minutes + " enabled " + enabled + " time " + time);*/

        values.put(Save.Columns.FILENAME, filename);
        
        values.put(Save.Columns.BIRD_ANIMATION, bird_animation ? 1 : 0);
        values.put(Save.Columns.BUTTERFLY, butterfly ? 1 : 0);        
        values.put(Save.Columns.RAIN_ANIMATION, rain_animation ? 1 : 0);
        values.put(Save.Columns.SNOW, snow ? 1 : 0); 
        values.put(Save.Columns.RAINBOW, rainbow ? 1 : 0);
        values.put(Save.Columns.SUNRAYS, sunrays ? 1 : 0);
        values.put(Save.Columns.THUNDER_ANIMATION, thunder_animation ? 1 : 0);
        
        values.put(Save.Columns.MORNING, morning ? 1 : 0);
        values.put(Save.Columns.BIRD_AMBIANCE, bird_ambiance ? 1 : 0);        
        values.put(Save.Columns.EVENING, evening ? 1 : 0);
        values.put(Save.Columns.CRICKET, cricket ? 1 : 0);        
        values.put(Save.Columns.RAIN_AMBIANCE, rain_ambiance ? 1 : 0);
        values.put(Save.Columns.FOREST, forest ? 1 : 0);
        values.put(Save.Columns.WIND, wind ? 1 : 0);
        values.put(Save.Columns.RIVER, river ? 1 : 0);
        values.put(Save.Columns.THUNDER_AMBIANCE, thunder_ambiance ? 1 : 0);
        values.put(Save.Columns.NIGHT, night ? 1 : 0);   
        
        values.put(Save.Columns.INSTRUMENTAL_POSITION, instrumental_position);
        
        resolver.update(ContentUris.withAppendedId(Save.Columns.CONTENT_URI, id),
                        values, null, null);        

        return 1;
    }

    /**
     * A convenience method to enable or disable an alarm.
     *
     * @param id             corresponds to the _id column
     * @param enabled        corresponds to the ENABLED column
     */

    
}
