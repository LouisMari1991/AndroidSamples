package com.sync.once;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by YH on 2016-11-03.
 */

public class PersistedMap {

  private static final String DELIMITER = ",";

  private SharedPreferences preferences;
  private Map<String, List<Long>> map = new ConcurrentHashMap<>();
  private final AsyncSharePreferenceLoader preferenceLoader;

  public PersistedMap(Context context, String mapName) {
    String preferenceName = "PersistedMap".concat(mapName);
    preferenceLoader = new AsyncSharePreferenceLoader(context, preferenceName);
  }

  private void waitForLoad() {
    if (preferenceLoader != null) return;



  }
}
