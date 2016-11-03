package com.sync.once;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import java.util.concurrent.ExecutionException;

/**
 * Created by YH on 2016-11-03.
 */

public class AsyncSharePreferenceLoader {

  private final Context context;

  private final AsyncTask<String, Void, SharedPreferences> asyncTask =
      new AsyncTask<String, Void, SharedPreferences>() {
        @Override protected SharedPreferences doInBackground(String... params) {
          return context.getSharedPreferences(params[0], Context.MODE_PRIVATE);
        }
      };

  AsyncSharePreferenceLoader(Context context, String name) {
    this.context = context;
    asyncTask.execute(name);
  }

  SharedPreferences get() {
    try {
      return asyncTask.get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
      return null;
    }
  }
}
