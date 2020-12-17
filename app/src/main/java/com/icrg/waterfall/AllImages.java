package com.icrg.waterfall;

import java.util.ArrayList;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

public class AllImages 
{
	Context context = null;	
	LayoutInflater layoutInflater = null;
	View view = null;
	ArrayList<MediaPlayer> soundPool_ambience = null,  soundPool_binauralBeats = null, soundPool_instrumentalMusic = null;
	ArrayList<Integer[]> allImages = null, allImages_hvr = null;
	ArrayList<Integer> animaton_files = null;
	ArrayList<Integer> animation_patches = null;
	
	ArrayList<MediaPlayer> fall_list = null;	
	ArrayList<Boolean> isAnimationSelected = null, isAmbienceSelected = null, animationActual_boolean = null, ambienceActual_boolean = null, modesBoolean = null;
	
	Integer[] thumbs = {R.drawable.clock_1, R.drawable.clock_2, R.drawable.clock_3, R.drawable.clock_4, R.drawable.clock_5,
	                    R.drawable.clock_6};
	
	Integer[] themeThumbs = {R.drawable.thumbs_1, R.drawable.thumbs_2, R.drawable.thumbs_3, R.drawable.thumbs_4};
	Integer[] themeThumbs_hvr = {R.drawable.thumbs_1_hvr, R.drawable.thumbs_2_hvr, R.drawable.thumbs_3_hvr, R.drawable.thumbs_4_hvr};

	Integer[] animations = {R.drawable.bird_icon, R.drawable.butterfly_icon, R.drawable.rain_icon, R.drawable.snow_icon,
                            R.drawable.rainbow_icon, R.drawable.flower_icon, R.drawable.thunder_icon};
	Integer[] animations_hvr = {R.drawable.bird_icon_hvr, R.drawable.butterfly_icon_hvr, R.drawable.rain_icon_hvr, 
			                    R.drawable.snow_icon_hvr, R.drawable.rainbow_icon_hvr, R.drawable.flower_icon_hvr, R.drawable.thunder_icon_hvr};

	Integer[] ambience = {R.drawable.beach_icon, R.drawable.bird_icon, R.drawable.evening_icon, R.drawable.insects_icon, R.drawable.rain_icon, R.drawable.frog_icon,
						  R.drawable.wind_icon, R.drawable.river_icon, R.drawable.thunder_icon,  R.drawable.cicidas_icon,};
	Integer[] ambience_hvr = {R.drawable.beach_icon_hvr, R.drawable.bird_icon_hvr, R.drawable.evening_icon_hvr, R.drawable.insects_icon_hvr, R.drawable.rain_icon_hvr, R.drawable.frog_icon_hvr,
            				  R.drawable.wind_icon_hvr, R.drawable.river_icon_hvr, R.drawable.thunder_icon_hvr,  R.drawable.cicidas_icon_hvr};
	
	Integer[] binaural_beats_raw = {R.raw.alpha, R.raw.beta, R.raw.delta, R.raw.meditation, R.raw.powernap, R.raw.beach1, R.raw.theata, R.raw.sleep};

	Integer[] instrumental_music = {R.drawable.floute_icon, R.drawable.xylophone_icon, R.drawable.saxophone_icon, R.drawable.sitar_icon, R.drawable.violin_icon,
                                    R.drawable.piano_icon, R.drawable.guittar_icon,};
	Integer[] instrumental_music_hvr = {R.drawable.floute_icon_hvr, R.drawable.xylophone_icon_hvr, R.drawable.saxophone_icon_hvr, R.drawable.sitar_icon_hvr, R.drawable.violin_icon_hvr,
				                        R.drawable.piano_icon_hvr, R.drawable.guittar_icon_hvr};

	Integer[] modes = {R.drawable.evening_icon, R.drawable.day_icon, R.drawable.night_icon, R.drawable.night_icon_with_color};
	Integer[] modes_hvr = {R.drawable.evening_icon_hvr, R.drawable.day_icon_hvr, R.drawable.night_icon_hvr, R.drawable.night_icon_with_color_hvr};

	Integer[] mist_hvr = {/*R.drawable.on_bt,*/ R.drawable.off_bt};	
	ArrayList<ArrayList<ImageButton>> allButtons;
	Integer[] tabtheme_hover = {R.drawable.theme_hvr, R.drawable.animation_hvr, R.drawable.ambiance_hvr, 
			                    R.drawable.instrumental_musics_hvr, R.drawable.mode_hvr};
	
	public AllImages(Context ctxt) 
	{
		// TODO Auto-generated constructor stub
		context = ctxt;
		layoutInflater = LayoutInflater.from(context);		
		allImages = mainGallery();        
        allImages_hvr = addHovers();        
        view = layoutInflater.inflate(R.layout.feelit, null);   
        
        isAnimationSelected = addAnimationBooleans();
        isAmbienceSelected = addAmbienceBooleans(); 
        animationActual_boolean = addanimationActualBooleans();
        ambienceActual_boolean = addambienceActualBooleans();
        modesBoolean = addModeBoolean();
        
	}

	public ArrayList<Integer[]> mainGallery() 
    {
		// TODO Auto-generated method stub
    	ArrayList<Integer[]> allImages = new ArrayList<Integer[]>();
    	allImages.add(themeThumbs);
        allImages.add(animations);
        allImages.add(ambience);
        allImages.add(instrumental_music);
        allImages.add(modes);
		return allImages;
	}

	public ArrayList<Integer[]> addHovers() 
    {
		// TODO Auto-generated method stub
    	ArrayList<Integer[]> allImages_hvr = new ArrayList<Integer[]>();
    	allImages_hvr.add(themeThumbs_hvr);
        allImages_hvr.add(animations_hvr);
        allImages_hvr.add(ambience_hvr);
        allImages_hvr.add(instrumental_music_hvr);
        allImages_hvr.add(modes_hvr);
		return allImages_hvr;
	}
	
	
	
	private ArrayList<Boolean> addAnimationBooleans() 
	{
		// TODO Auto-generated method stub
		ArrayList<Boolean> arrbut = new ArrayList<Boolean>();
    	arrbut.add(false);
    	arrbut.add(false);
    	arrbut.add(false);
    	arrbut.add(false);
    	arrbut.add(false);
    	arrbut.add(false);
    	arrbut.add(false);
    	
		return arrbut;
	}

	public ArrayList<Boolean> addAmbienceBooleans() 
	    {
			// TODO Auto-generated method stub
	    	ArrayList<Boolean> arrbut = new ArrayList<Boolean>();
	    	arrbut.add(false);
	    	arrbut.add(false);
	    	arrbut.add(false);
	    	arrbut.add(false);
	    	arrbut.add(false);
	    	arrbut.add(false);
	    	arrbut.add(false);
	    	arrbut.add(false);
	    	arrbut.add(false);
	    	arrbut.add(false);
			return arrbut;
		}
	
	private ArrayList<Boolean> addanimationActualBooleans() 
	{
		// TODO Auto-generated method stub
		ArrayList<Boolean> arrbut = new ArrayList<Boolean>();
    	arrbut.add(false);
    	arrbut.add(false);
    	arrbut.add(false);
    	arrbut.add(false);
    	arrbut.add(false);
    	arrbut.add(false);
    	arrbut.add(false);
    	arrbut.add(false);
    	arrbut.add(false);
    	arrbut.add(false);
		return arrbut;
	}
	
	private ArrayList<Boolean> addambienceActualBooleans() 
	{
		// TODO Auto-generated method stub
		ArrayList<Boolean> arrbut = new ArrayList<Boolean>();
    	arrbut.add(false);
    	arrbut.add(false);
    	arrbut.add(false);
    	arrbut.add(false);
    	arrbut.add(false);
    	arrbut.add(false);
    	arrbut.add(false);
    	arrbut.add(false);
    	arrbut.add(false);
    	arrbut.add(false);
    	
		return arrbut;
	}	
	
	private ArrayList<Boolean> addModeBoolean() {
		// TODO Auto-generated method stub
		ArrayList<Boolean> arrbut = new ArrayList<Boolean>();
    	arrbut.add(false);
    	arrbut.add(false);
    	arrbut.add(false);
    	arrbut.add(false);    	
    	
		return arrbut;
	}

}
