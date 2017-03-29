package com.sync.rxjavasamples;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends ListActivity {

  private static final String PATH     = "com.sync.rxjavasamples.PATH";
  private static final String CATEGORY = "com.sync.rxjavasamples.CATEGORY";

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Intent intent = getIntent();
    String path = intent.getStringExtra(PATH);

    if (path == null) {
      path = "";
    }

    setListAdapter(new SimpleAdapter(this, getData(path), android.R.layout.simple_list_item_1, new String[] { "title" },
        new int[] { android.R.id.text1 }));
    getListView().setTextFilterEnabled(true);
  }

  protected List<Map<String, Object>> getData(String prefix) {

    List<Map<String, Object>> datas = new ArrayList<>();

    Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
    mainIntent.addCategory(CATEGORY);

    PackageManager pm = getPackageManager();
    List<ResolveInfo> list = pm.queryIntentActivities(mainIntent, 0);

    if (list == null) {
      return datas;
    }

    String[] prefixPath;
    String prefixWithSlash = prefix;

    if (prefix.equals("")) {
      prefixPath = null;
    } else {
      prefixPath = prefix.split("/");
      prefixWithSlash = prefix + "/";
    }

    int len = list.size();
    Map<String, Boolean> entries = new HashMap<>();

    for (int i = 0; i < len; i++) {
      ResolveInfo info = list.get(i);
      CharSequence lableSeq = info.loadLabel(pm);
      String label = lableSeq != null ? lableSeq.toString() : info.activityInfo.name;

      if (prefixWithSlash.length() == 0 || label.startsWith(prefixWithSlash)) {

        String[] labelPath = label.split("/");

        String nextLabel = prefixPath == null ? labelPath[0] : labelPath[prefixPath.length];

        if ((prefixPath != null ? prefixPath.length : 0) == labelPath.length - 1) {
          addItem(datas, nextLabel,
              activityIntent(info.activityInfo.applicationInfo.packageName, info.activityInfo.name));
        } else {
          if (entries.get(nextLabel) == null) {
            addItem(datas, nextLabel, browseIntent(prefix.equals("") ? nextLabel : prefix + "/" + nextLabel));
            entries.put(nextLabel, true);
          }
        }
      }
    }

    Collections.sort(datas, (map1, map2) -> Collator.getInstance().compare(map1.get("title"), map2.get("title")));
    return datas;
  }

  protected Intent activityIntent(String pkg, String componentName) {
    Intent result = new Intent();
    result.setClassName(pkg, componentName);
    return result;
  }

  protected Intent browseIntent(String path) {
    Intent result = new Intent();
    result.setClass(this, MainActivity.class);
    result.putExtra(PATH, path);
    return result;
  }

  protected void addItem(List<Map<String, Object>> data, String name, Intent intent) {
    Map<String, Object> temp = new HashMap<>();
    temp.put("title", name);
    temp.put("intent", intent);
    data.add(temp);
  }

  @Override protected void onListItemClick(ListView l, View v, int position, long id) {
    Map<String, Object> map = (Map<String, Object>) l.getItemAtPosition(position);
    Intent intent = (Intent) map.get("intent");
    startActivity(intent);
  }
}
