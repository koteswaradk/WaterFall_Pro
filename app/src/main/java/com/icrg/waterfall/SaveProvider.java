package com.icrg.waterfall;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class SaveProvider extends ContentProvider 
{
    private SQLiteOpenHelper mOpenHelper;

    private static final int SAVED = 1;
    private static final int SAVED_ID = 2;
    private static final UriMatcher sURLMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static 
    {
        //sURLMatcher.addURI("com.icrg.waterfall.alarm", "alarm", ALARMS);
        //sURLMatcher.addURI("com.icrg.waterfall.alarm", "alarm/#", ALARMS_ID);
    	sURLMatcher.addURI("com.icrg.waterfall", "save", SAVED);
        sURLMatcher.addURI("com.icrg.waterfall", "save/#", SAVED_ID);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper 
    {
        private static final String DATABASE_NAME = "saved.db";
        private static final int DATABASE_VERSION = 5;

        public DatabaseHelper(Context context) 
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) 
        {
            db.execSQL("CREATE TABLE saved (" +
                       "_id INTEGER PRIMARY KEY," +                       
                       "filename TEXT, " +
                       
                       "bird_animation INTEGER, " +
                       "butterfly INTEGER, " +
                       "rain_animation INTEGER, " +
                       "snow INTEGER, " + 
                       "rainbow INTEGER, " +
                       "sunrays INTEGER, " +
                       "thunder_animation INTEGER, " +
                       
                       "morning INTEGER, " +
                       "bird_ambiance INTEGER, " +
                       "evening INTEGER, " +
                       "cricket INTEGER, " +
                       "rain_ambiance INTEGER, " +
                       "forest INTEGER, " +
                       "wind INTEGER, " +
                       "river INTEGER, " +
                       "thunder_ambiance INTEGER, " +
                       "night INTEGER, " +                       
                       
                       "instrumental_position INTEGER);");

            // insert default alarms
            String insertMe = "INSERT INTO saved " +
                    "(filename,  " + "bird_animation, butterfly, rain_animation, snow, rainbow, sunrays, thunder_animation, " +
                           "morning, bird_ambiance, evening, cricket, rain_ambiance, forest, wind, river, thunder_ambiance, night, instrumental_position) " +
                    "VALUES ";
            db.execSQL(insertMe + "('Default tracks', 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1);");
            db.execSQL(insertMe + "('Bird', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1);");
            db.execSQL(insertMe + "('Snow', 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1);");
            db.execSQL(insertMe + "('Lightning', 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1);");
            db.execSQL(insertMe + "('Saved tracks', 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int currentVersion) 
        {
            /*if (Log.LOGV) Log.v("Upgrading saved database from version " +
                    oldVersion + " to " + currentVersion +
                    ", which will destroy all old data");*/
            db.execSQL("DROP TABLE IF EXISTS saved");
            onCreate(db);
        }
    }

    public SaveProvider() 
    {
    	
    }

    @Override
    public boolean onCreate() 
    {
        mOpenHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri url, String[] projectionIn, String selection,
            String[] selectionArgs, String sort) 
    {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        // Generate the body of the query
        int match = sURLMatcher.match(url);
        switch (match) 
        {
            case SAVED:
                 qb.setTables("saved");
                 break;
            case SAVED_ID:
                 qb.setTables("saved");
                 qb.appendWhere("_id=");
                 qb.appendWhere(url.getPathSegments().get(1));
                 break;
            default:
                 throw new IllegalArgumentException("Unknown URL " + url);
        }

        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor ret = qb.query(db, projectionIn, selection, selectionArgs,
                              null, null, null);

        if (ret == null) 
        {
           // if (Log.LOGV) Log.v("Saved.query: failed");
        } else {
            ret.setNotificationUri(getContext().getContentResolver(), url);
        }

        return ret;
    }

    @Override
    public String getType(Uri url) 
    {
        int match = sURLMatcher.match(url);
        switch (match) {
            case SAVED:
                return "vnd.android.cursor.dir/saved";
            case SAVED_ID:
                return "vnd.android.cursor.item/saved";
            default:
                throw new IllegalArgumentException("Unknown URL");
        }
    }

    @Override
    public int update(Uri url, ContentValues values, String where, String[] whereArgs) 
    {
        int count;
        long rowId = 0;
        int match = sURLMatcher.match(url);
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        switch (match) 
        {
            case SAVED_ID:
            {
                String segment = url.getPathSegments().get(1);
                rowId = Long.parseLong(segment);
                count = db.update("saved", values, "_id=" + rowId, null);
                break;
            }
            default:
            {
                throw new UnsupportedOperationException("Cannot update URL: " + url);
            }
        }
        //if (Log.LOGV) Log.v("*** notifyChange() rowId: " + rowId + " url " + url);
        getContext().getContentResolver().notifyChange(url, null);
        return count;
    }

    @Override
    public Uri insert(Uri url, ContentValues initialValues) 
    {
        if (sURLMatcher.match(url) != SAVED) 
        {
            throw new IllegalArgumentException("Cannot insert into URL: " + url);
        }

        ContentValues values;
        if (initialValues != null)
            values = new ContentValues(initialValues);
        else
            values = new ContentValues();        
        
        if (!values.containsKey(Save.Columns.FILENAME))
            values.put(Save.Columns.FILENAME, "");        

        if (!values.containsKey(Save.Columns.BIRD_ANIMATION))
            values.put(Save.Columns.BIRD_ANIMATION, 0);
        if (!values.containsKey(Save.Columns.BUTTERFLY))
            values.put(Save.Columns.BUTTERFLY, 0);
        if (!values.containsKey(Save.Columns.RAIN_ANIMATION))
            values.put(Save.Columns.RAIN_ANIMATION, 0);
        if (!values.containsKey(Save.Columns.SNOW))
            values.put(Save.Columns.SNOW, 0);
        if (!values.containsKey(Save.Columns.RAINBOW))
            values.put(Save.Columns.RAINBOW, 0);
        if (!values.containsKey(Save.Columns.SUNRAYS))
            values.put(Save.Columns.SUNRAYS, 0);
        if (!values.containsKey(Save.Columns.THUNDER_ANIMATION))
            values.put(Save.Columns.THUNDER_ANIMATION, 0);
        
        if (!values.containsKey(Save.Columns.MORNING))
            values.put(Save.Columns.MORNING, 0);
        if (!values.containsKey(Save.Columns.BIRD_AMBIANCE))
            values.put(Save.Columns.BIRD_AMBIANCE, 0);
        if (!values.containsKey(Save.Columns.EVENING))
            values.put(Save.Columns.EVENING, 0);
        if (!values.containsKey(Save.Columns.CRICKET))
            values.put(Save.Columns.CRICKET, 0);
        if (!values.containsKey(Save.Columns.RAIN_AMBIANCE))
            values.put(Save.Columns.RAIN_AMBIANCE, 0);
        if (!values.containsKey(Save.Columns.FOREST))
            values.put(Save.Columns.FOREST, 0);
        if (!values.containsKey(Save.Columns.WIND))
            values.put(Save.Columns.WIND, 0);
        if (!values.containsKey(Save.Columns.RIVER))
            values.put(Save.Columns.RIVER, 0);
        if (!values.containsKey(Save.Columns.THUNDER_AMBIANCE))
            values.put(Save.Columns.THUNDER_AMBIANCE, 0);
        if (!values.containsKey(Save.Columns.NIGHT))
            values.put(Save.Columns.NIGHT, 0);        
        
        if (!values.containsKey(Save.Columns.INSTRUMENTAL_POSITION))
            values.put(Save.Columns.INSTRUMENTAL_POSITION, -1);

        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long rowId = db.insert("saved", Save.Columns.FILENAME, values);
        if (rowId < 0) 
        {
            throw new SQLException("Failed to insert row into " + url);
        }
        //if (Log.LOGV) Log.v("Added save rowId = " + rowId);

        Uri newUrl = ContentUris.withAppendedId(Save.Columns.CONTENT_URI, rowId);
        getContext().getContentResolver().notifyChange(newUrl, null);
        
        return newUrl;
    }

    public int delete(Uri url, String where, String[] whereArgs) 
    {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;
        long rowId = 0;
        switch (sURLMatcher.match(url)) 
        {
            case SAVED:
                count = db.delete("saved", where, whereArgs);
                break;
                
            case SAVED_ID:
                String segment = url.getPathSegments().get(1);
                rowId = Long.parseLong(segment);
                if (TextUtils.isEmpty(where)) 
                {
                    where = "_id=" + segment;
                } 
                else 
                {
                    where = "_id=" + segment + " AND (" + where + ")";
                }
                count = db.delete("saved", where, whereArgs);
                break;
                
            default:
                throw new IllegalArgumentException("Cannot delete from URL: " + url);
        }
        getContext().getContentResolver().notifyChange(url, null);
        
        return count;
    }
    
    
}
