package com.icrg.waterfall;

import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

public final class Save implements Parcelable 
{
	
	public int        id;
	public String     filename = "default";
	
    public boolean    bird_animation;       
    public boolean    butterfly;
    public boolean    rain_animation;
    public boolean    snow;
    public boolean    rainbow;
    public boolean    sunrays;    
    public boolean    thunder_animation; 
    public boolean[]  animation_boolean = {bird_animation, butterfly, rain_animation, snow, rainbow, sunrays, thunder_animation};
    
    public boolean    morning;
    public boolean    bird_ambiance;
    public boolean    evening;
    public boolean    cricket;
    public boolean    rain_ambiance;
    public boolean    forest;
    public boolean    wind;
    public boolean    river;
    public boolean    thunder_ambiance;
    public boolean    night;
    public boolean[]  ambiance_boolean = {morning, bird_ambiance, evening, cricket, rain_ambiance, forest, wind, river, thunder_ambiance, night};
    
    public int instrumental_position = -1;
    
    public static final Parcelable.Creator<Save> CREATOR = new Parcelable.Creator<Save>() 
     {
        public Save createFromParcel(Parcel p) 
        {
            return new Save(p);
        }

        public Save[] newArray(int size) 
        {
            return new Save[size];
        }
     };

    public int describeContents()
    {
        return 0;
    }

    public void writeToParcel(Parcel p, int flags) 
    {
        p.writeInt(id);       
        p.writeString(filename); 
        
        /*p.writeInt(bird_animation ? 1 : 0);
        p.writeInt(butterfly ? 1 : 0);
        p.writeInt(rain_animation ? 1 : 0);
        p.writeInt(snow ? 1 : 0);
        p.writeInt(rainbow ? 1 : 0);
        p.writeInt(sunrays ? 1 : 0);
        p.writeInt(thunder_animation ? 1 : 0);*/
        
        for(int i=0; i<animation_boolean.length; i++)
        {
        	p.writeInt(animation_boolean[i] ? 1 : 0);
        }
        
        /*p.writeInt(morning ? 1 : 0);
        p.writeInt(bird_ambiance ? 1 : 0);
        p.writeInt(evening ? 1 : 0);
        p.writeInt(cricket ? 1 : 0);
        p.writeInt(rain_ambiance ? 1 : 0);
        p.writeInt(forest ? 1 : 0);
        p.writeInt(wind ? 1 : 0);
        p.writeInt(river ? 1 : 0);
        p.writeInt(thunder_ambiance ? 1 : 0);
        p.writeInt(night ? 1 : 0);*/
        
        for(int i=0; i<ambiance_boolean.length; i++)
        {
        	p.writeInt(ambiance_boolean[i] ? 1 : 0);
        }
        
        p.writeInt(instrumental_position);
    }
    
    public static class Columns implements BaseColumns 
    {
        //public static final Uri CONTENT_URI = Uri.parse("content://com.icrg.waterfall.alarm/alarm");
        public static final Uri CONTENT_URI = Uri.parse("content://com.icrg.waterfall/save");
        public static final String FILENAME = "filename";        
        public static final String BIRD_ANIMATION = "bird_animation";
        public static final String BUTTERFLY = "butterfly";
        public static final String RAIN_ANIMATION = "rain_animation";
        public static final String SNOW = "snow";
        public static final String RAINBOW = "rainbow";
        public static final String SUNRAYS = "sunrays";        
        public static final String THUNDER_ANIMATION = "thunder_animation";
        
        public static final String MORNING = "morning";
        public static final String BIRD_AMBIANCE = "bird_ambiance";
        public static final String EVENING = "evening";
        public static final String CRICKET = "cricket";
        public static final String RAIN_AMBIANCE = "rain_ambiance";
        public static final String FOREST = "forest";
        public static final String WIND = "wind";
        public static final String RIVER = "river";
        public static final String THUNDER_AMBIANCE = "thunder_ambiance";
        public static final String NIGHT = "night";
        
        public static final String INSTRUMENTAL_POSITION = "instrumental_position";        

        static final String[] SAVE_QUERY_COLUMNS = 
        {
            _ID, FILENAME,  
            BIRD_ANIMATION, BUTTERFLY, RAIN_ANIMATION, SNOW, RAINBOW, SUNRAYS, THUNDER_ANIMATION, 
            MORNING, BIRD_AMBIANCE, EVENING, CRICKET, RAIN_AMBIANCE, FOREST, WIND, RIVER, THUNDER_AMBIANCE, NIGHT,
            INSTRUMENTAL_POSITION 
        };

        public static final int SAVE_ID_INDEX = 0;        
        public static final int ALARM_FILENAME_INDEX = 1; 
        
        public static final int SAVE_BIRD_ANIMATION_INDEX = 2;
        public static final int SAVE_BUTTERFLY_INDEX = 3;
        public static final int SAVE_RAIN_ANIMATION_INDEX = 4;
        public static final int SAVE_SNOW_INDEX = 5;
        public static final int SAVE_RAINBOW_INDEX = 6;
        public static final int SAVE_SUNRAYS_INDEX = 7;
        public static final int SAVE_THUNDER_ANIMATION_INDEX =8;
        
        public static final int[] animation_index = {SAVE_BIRD_ANIMATION_INDEX, SAVE_BUTTERFLY_INDEX, SAVE_RAIN_ANIMATION_INDEX, SAVE_SNOW_INDEX, SAVE_RAINBOW_INDEX,
        											 SAVE_SUNRAYS_INDEX, SAVE_THUNDER_ANIMATION_INDEX};
        
        public static final int SAVE_MORNING_INDEX = 9;
        public static final int SAVE_BIRD_AMBIANCE_INDEX = 10;
        public static final int SAVE_EVENING_INDEX = 11;
        public static final int SAVE_CRICKET_INDEX = 12;
        public static final int SAVE_RAIN_AMBIANCE_INDEX = 13;
        public static final int SAVE_FOREST_INDEX = 14;
        public static final int SAVE_WIND_INDEX = 15;
        public static final int SAVE_RIVER_INDEX = 16;
        public static final int SAVE_THUNDER_AMBIANCE_INDEX = 17;
        public static final int SAVE_NIGHT_INDEX = 18;
        
        public static final int[] ambience_index = {SAVE_MORNING_INDEX, SAVE_BIRD_AMBIANCE_INDEX, SAVE_EVENING_INDEX, SAVE_CRICKET_INDEX, SAVE_RAIN_AMBIANCE_INDEX,
        											SAVE_FOREST_INDEX, SAVE_WIND_INDEX, SAVE_RIVER_INDEX, SAVE_THUNDER_AMBIANCE_INDEX, SAVE_NIGHT_INDEX};
        
        public static final int SAVE_INSTRUMENTAL_POSITION_INDEX = 19;
        
    }    
    

    public Save(Cursor c) 
    {
        id = c.getInt(Columns.SAVE_ID_INDEX);
        filename = c.getString(Columns.ALARM_FILENAME_INDEX);
        
        /*bird_animation = c.getInt(Columns.SAVE_BIRD_ANIMATION_INDEX) == 1;                
        butterfly = c.getInt(Columns.SAVE_BUTTERFLY_INDEX) == 1;
        rain_animation = c.getInt(Columns.SAVE_RAIN_ANIMATION_INDEX) == 1;
        snow = c.getInt(Columns.SAVE_SNOW_INDEX) == 1;
        rainbow = c.getInt(Columns.SAVE_RAINBOW_INDEX) == 1;
        sunrays = c.getInt(Columns.SAVE_SUNRAYS_INDEX) == 1;
        thunder_animation = c.getInt(Columns.SAVE_THUNDER_ANIMATION_INDEX) == 1;*/
        
        for(int i=0; i<animation_boolean.length; i++)
        {
        	animation_boolean[i] = c.getInt(Columns.animation_index[i]) == 1;
        }
        
        /*morning = c.getInt(Columns.SAVE_MORNING_INDEX) == 1;                
        bird_ambiance = c.getInt(Columns.SAVE_BIRD_AMBIANCE_INDEX) == 1;
        evening = c.getInt(Columns.SAVE_EVENING_INDEX) == 1;
        cricket = c.getInt(Columns.SAVE_CRICKET_INDEX) == 1;
        rain_ambiance = c.getInt(Columns.SAVE_RAIN_AMBIANCE_INDEX) == 1;
        forest = c.getInt(Columns.SAVE_FOREST_INDEX) == 1;
        wind = c.getInt(Columns.SAVE_WIND_INDEX) == 1;
        river = c.getInt(Columns.SAVE_RIVER_INDEX) == 1;
        thunder_ambiance = c.getInt(Columns.SAVE_THUNDER_AMBIANCE_INDEX) == 1;
        night = c.getInt(Columns.SAVE_NIGHT_INDEX) == 1;*/
        
        for(int i=0; i<ambiance_boolean.length; i++)
        {
        	ambiance_boolean[i] = c.getInt(Columns.ambience_index[i]) == 1;
        }
        
        instrumental_position = c.getInt(Columns.SAVE_INSTRUMENTAL_POSITION_INDEX);
        
    }

    public Save(Parcel p) 
    {
        id = p.readInt();        
        filename = p.readString(); 
        
        /*bird_animation = p.readInt() == 1;
        butterfly = p.readInt() == 1;
        rain_animation = p.readInt() == 1;
        snow = p.readInt() == 1;
        rainbow = p.readInt() == 1;
        sunrays = p.readInt() == 1;
        thunder_animation = p.readInt() == 1;*/
        
        for(int i=0; i<animation_boolean.length; i++)
        {
        	animation_boolean[i] = p.readInt() == 1;
        }
        
        /*morning = p.readInt() == 1;
        bird_ambiance = p.readInt() == 1;
        evening = p.readInt() == 1;
        cricket = p.readInt() == 1;
        rain_ambiance = p.readInt() == 1;
        forest = p.readInt() == 1;
        wind = p.readInt() == 1;
        river = p.readInt() == 1;
        thunder_ambiance = p.readInt() == 1;
        night = p.readInt() == 1;*/
        
        for(int i=0; i<ambiance_boolean.length; i++)
        {
        	ambiance_boolean[i] = p.readInt() == 1;
        }
        
        instrumental_position = p.readInt();
    }

   

    
}
