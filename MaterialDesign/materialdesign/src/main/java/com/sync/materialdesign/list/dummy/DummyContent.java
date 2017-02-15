package com.sync.materialdesign.list.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author：Administrator on 2017/2/14 0014 23:47
 * Contact：289168296@qq.com
 */
public class DummyContent {

  public static final List<DummyItem> ITEMS = new ArrayList<>();

  public static final Map<String, DummyItem> ITEM_MAP = new HashMap<>();

  private static final int COUNT = 25;

  static {
    
  }

  public static class DummyItem {
    public final String id;
    public final String content;
    public final String details;

    public DummyItem(String id, String content, String details) {
      this.id = id;
      this.content = content;
      this.details = details;
    }

    @Override public String toString() {
      return content;
    }
  }
}
