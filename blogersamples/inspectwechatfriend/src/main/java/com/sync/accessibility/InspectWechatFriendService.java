package com.sync.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by YH on 2016-10-27.
 */

public class InspectWechatFriendService extends AccessibilityService {

  // 群组成员个数
  public static final int GROUP_COUNT = 39;

  public static final String WECHAT_VERSION_25 = "6.3.25";
  public static final String WECHAT_VERSION_27 = "6.3.27";

  public static List<String> nickNameList = new ArrayList<>();
  public static HashSet<String> deleteList = new HashSet<>();
  public static HashSet<String> sortItems = new HashSet<>();

  public static boolean hasComplete;

  public static boolean canCheck;

  @Override public void onAccessibilityEvent(AccessibilityEvent event) {

  }

  @Override public void onInterrupt() {

  }
}
