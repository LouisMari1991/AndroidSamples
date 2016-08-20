package com.sync.androidsamples.google.samples.apps.topeka.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.sync.androidsamples.google.samples.apps.topeka.model.Avatar;
import com.sync.androidsamples.google.samples.apps.topeka.model.Player;

/**
 * Created by Administrator on 2016/8/19 0019.
 */
public class PreferencesHelper {

  private static final String PLAY_PREFERENCES = "playPreference";
  private static final String PREFERENCE_FIRST_NAME = PLAY_PREFERENCES + ".firstName";
  private static final String PREFERENCE_LAST_INITIAL = PLAY_PREFERENCES + ".lastInitial";
  private static final String PERFERENCE_AVATAR = PLAY_PREFERENCES + ".avatar";

  private PreferencesHelper() {
    // no instance
  }

  /**
   * Write a {@link com.sync.androidsamples.google.samples.apps.topeka.model.Player} to preferences
   * 2016/8/20 0020 14:13
   */
  public static void writeToPreferences(Context context, Player player) {
    SharedPreferences.Editor editor = getEditor(context);
    editor.putString(PREFERENCE_FIRST_NAME, player.getFirstName());
    editor.putString(PREFERENCE_LAST_INITIAL, player.getLastInitial());
    editor.putString(PERFERENCE_AVATAR, player.getAvatar().name());
    editor.apply();
  }

  /**
   * Retrieves a {@link com.sync.androidsamples.google.samples.apps.topeka.model.Player} from
   * preferences
   * 2016/8/20 0020 14:19
   */
  public static Player getPlayer(Context context) {
    SharedPreferences preferences = getSharedPreferences(context);
    final String firstName = preferences.getString(PREFERENCE_FIRST_NAME, null);
    final String lastInitial = preferences.getString(PREFERENCE_LAST_INITIAL, null);
    final String avatarPreferences = preferences.getString(PERFERENCE_AVATAR, null);
    final Avatar avatar;
    if (null != avatarPreferences) {
      avatar = Avatar.valueOf(avatarPreferences);
    } else {
      avatar = null;
    }
    if (null == firstName || null == lastInitial || null == avatar) {
      return null;
    }
    return new Player(firstName, lastInitial, avatar);
  }

  /**
   * Signs out player by removing all it's data
   * 2016/8/20 0020 14:22
   */
  public static void signOut(Context context) {
    SharedPreferences.Editor editor = getEditor(context);
    editor.remove(PREFERENCE_FIRST_NAME);
    editor.remove(PREFERENCE_LAST_INITIAL);
    editor.remove(PERFERENCE_AVATAR);
    editor.apply();
  }
  
  /**
   * Checks whether a player is currently signed in.
   * 2016/8/20 0020 14:26
   */
  public static boolean isSignedIn(Context context) {
    final SharedPreferences preferences = getSharedPreferences(context);
    return preferences.contains(PREFERENCE_FIRST_NAME) &&
        preferences.contains(PREFERENCE_LAST_INITIAL) && preferences.contains(PERFERENCE_AVATAR);
  }

  /**
   * Checks whether the player's input is valid.
   * 2016/8/20 0020 16:52
   */
  public static boolean isInputDataValid(CharSequence firstName, CharSequence lastInitial) {
    return !TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastInitial);
  }


  private static SharedPreferences.Editor getEditor(Context context) {
    SharedPreferences preference = getSharedPreferences(context);
    return preference.edit();
  }

  private static SharedPreferences getSharedPreferences(Context context) {
    return context.getSharedPreferences(PLAY_PREFERENCES, Context.MODE_PRIVATE);
  }
}
