package com.codecamp.prashant.moviebx.Preference;

import android.content.Context;
import android.content.SharedPreferences;

public class helpScreenPreference {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;

    int PRIVATE_MODE=0;
    private static final String PREF_NAME="helpScreen";
    private static final String FIRST_TIME_LAUNCH="is_first_time";

    public helpScreenPreference(Context context) {
        this.context = context;
        preferences=context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor=preferences.edit();
    }

    public void setFirstTimeLaunch(boolean firstTimeLaunch){
        editor.putBoolean(FIRST_TIME_LAUNCH,firstTimeLaunch);
        editor.commit();
    }
    public boolean getFistTimelaunch(){
        return preferences.getBoolean(FIRST_TIME_LAUNCH,true);
    }
}
