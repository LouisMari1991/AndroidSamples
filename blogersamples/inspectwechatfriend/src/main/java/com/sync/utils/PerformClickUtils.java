package com.sync.utils;

import android.accessibilityservice.AccessibilityService;
import android.os.Build;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.List;

/**
 * Created by YH on 2016-10-29.
 */

public class PerformClickUtils {

  /**
   * 在当前页面查找文字并点击
   *
   * @author YH
   * @time 2016-10-29 16:02
   */
  public static void findTextAndClick(AccessibilityService accessibilityService, String text) {
    AccessibilityNodeInfo accessibilityNodeInfo = accessibilityService.getRootInActiveWindow();
    if (accessibilityNodeInfo == null) {
      return;
    }

    List<AccessibilityNodeInfo> nodeInfoList =
        accessibilityNodeInfo.findAccessibilityNodeInfosByText(text);

    if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
      for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
        if (nodeInfo != null) {
          preformClick(nodeInfo);
          break;
        }
      }
    }
  }

  /**
   * 检查viewId进行点击
   *
   * @author YH
   * @time 2016-10-29 16:08
   */
  public static void findViewIdAndClick(AccessibilityService accessibilityService, String id) {

    AccessibilityNodeInfo accessibilityNodeInfo = accessibilityService.getRootInActiveWindow();

    if (accessibilityNodeInfo == null) {
      return;
    }

    List<AccessibilityNodeInfo> nodeInfoList =
        accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(id);
    if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
      for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
        if (nodeInfo != null) {
          preformClick(nodeInfo);
          break;
        }
      }
    }
  }

  /**
   * 在当前页面查找对话框文字内容并点击
   * @param text1 默认点击text1
   * @param text2
   * @author YH
   * @time 2016-10-29 16:15
   */
  public static void findDialogAndClick(AccessibilityService accessibilityService, String text1,
      String text2) {

    AccessibilityNodeInfo accessibilityNodeInfo = accessibilityService.getRootInActiveWindow();
    if (accessibilityNodeInfo == null) {
      return;
    }

    List<AccessibilityNodeInfo> dialogWait =
        accessibilityNodeInfo.findAccessibilityNodeInfosByText(text1);
    List<AccessibilityNodeInfo> dialogConfirm =
        accessibilityNodeInfo.findAccessibilityNodeInfosByText(text2);

    if (!dialogWait.isEmpty() && !dialogConfirm.isEmpty()) {
      for (AccessibilityNodeInfo nodeInfo : dialogWait) {
        if (nodeInfo != null && text1.equals(nodeInfo.getText())) {
          preformClick(nodeInfo);
          break;
        }
      }
    }
  }

  /**
   * 模拟点击事件
   * @author YH
   * @time 2016-10-29 16:17
   */
  public static void preformClick(AccessibilityNodeInfo nodeInfo) {
    if (nodeInfo == null) {
      return;
    }
    if (nodeInfo.isCheckable()) {
      nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
    } else {
      preformClick(nodeInfo.getParent());
    }
  }

  /**
   * 模拟返回事件
   * @param service
   */
  public static void performBack(AccessibilityService service) {
    if (service == null) {
      return;
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
      try {
        Thread.sleep(200);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
    }


  }

}
