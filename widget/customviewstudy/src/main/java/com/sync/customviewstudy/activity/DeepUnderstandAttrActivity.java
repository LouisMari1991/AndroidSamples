package com.sync.customviewstudy.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import com.sync.customviewstudy.R;
import com.sync.customviewstudy.utils.CheckNetwork;
import com.sync.customviewstudy.utils.SPUtils;

/**
 * @Description: TypedArray其实是用来简化我们的工作的，比如上例，如果布局中的属性的值是引用类型（比如：@dimen/dp100），
 * 如果使用AttributeSet去获得最终的像素值，那么需要第一步拿到id，第二步再去解析id。
 * 而TypedArray正是帮我们简化了这个过程。
 * 网页可以处理:
 * 点击相应控件:拨打电话、发送短信、发送邮件、上传图片、播放视频
 * 进度条、返回网页上一层、显示网页标题
 * @Author 罗顺翔
 * @date 2017年04月07日 14:14
 */
public class DeepUnderstandAttrActivity extends AppCompatActivity {

  private WebView     mWebView;
  private ProgressBar mProgressBar; // 进度条

  /*进度条是否加载到90%*/
  private boolean progress90;
  /*网页是否加载完成*/
  private boolean pageFinish;

  /*点击上传图片相关*/
  public ValueCallback<Uri>   mUploadMessage;
  public ValueCallback<Uri[]> mUploadMessageForAndroid5;
  public final static int FILECHOOSER_RESULTCODE               = 1;
  public final static int FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 2;

  /*加载视频相关*/
  private MyWebChromeClient                  xwebchromeclient;
  private FrameLayout                        video_fullView;// 全屏时视频加载View
  private View                               xCustomView;
  private WebChromeClient.CustomViewCallback xCustomViewCallback;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_deep_understand_attr);
    setTitle("深入理解Android中的自定义属性");
    initView();
    initWebView();
    mWebView.loadUrl("http://blog.csdn.net/lmj623565791/article/details/45022631");
  }

  private void initView() {
    mWebView = (WebView) findViewById(R.id.activity_deep_understand_att);
    mProgressBar = (ProgressBar) findViewById(R.id.pb_progress);
    mProgressBar.setVisibility(View.VISIBLE);
  }

  private void initWebView() {
    WebSettings settings = mWebView.getSettings();
    // setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
    settings.setLoadWithOverviewMode(true);
    settings.setSavePassword(true);
    settings.setSaveFormData(true);// 保存表单数据
    settings.setSupportZoom(true);
    // 双击缩放
    settings.setBuiltInZoomControls(true);
    settings.setDisplayZoomControls(false);

    // 设置缓存模式
    settings.setAppCacheEnabled(true);
    settings.setCacheMode(WebSettings.LOAD_DEFAULT); // 设置缓存模式
    // 设置此属性，可任意比例缩放
    settings.setUseWideViewPort(true);
    //缩放比例 1
    mWebView.setInitialScale(1);
    settings.setJavaScriptEnabled(true);
    settings.setDomStorageEnabled(true);
    settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);// 排版适应屏幕
    settings.setSupportMultipleWindows(true);// 新加

    /*设置字体默认缩放大小（默认总体为16sp，默认缩放比例为110）*/
    int defaultTextZoom = SPUtils.getInt("UserInfo", "default_textZoom", 110);
    settings.setTextZoom(defaultTextZoom);

    xwebchromeclient = new MyWebChromeClient();
    mWebView.setWebChromeClient(xwebchromeclient);
    mWebView.setWebViewClient(new MyWebViewClient());
  }

  public class MyWebViewClient extends WebViewClient {
    @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
      if (url.startsWith("http://v.youku.com/")) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addCategory("android.intent.category.BROWSABLE");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        startActivity(intent);
        return true;
      } else if (url.startsWith(WebView.SCHEME_TEL) || url.startsWith("sms:") || url.startsWith(
          WebView.SCHEME_MAILTO)) {
        try {
          Intent intent = new Intent(Intent.ACTION_VIEW);
          intent.setData(Uri.parse(url));
          startActivity(intent);
        } catch (android.content.ActivityNotFoundException ignored) {
        }
        return true;
      }
      startProgress90();
      view.loadUrl(url);
      return false;
    }

    @Override public void onPageFinished(WebView view, String url) {
      // html加载完成之后，添加监听图片的点击js函数
      if (progress90) {
        mProgressBar.setVisibility(View.GONE);
      } else {
        pageFinish = true;
      }
      if (!CheckNetwork.isNetworkConnected(DeepUnderstandAttrActivity.this)) {
        mProgressBar.setVisibility(View.GONE);
      }
      addImageClickListener();
      super.onPageFinished(view, url);
    }
  }


  // 注入js函数监听
  private void addImageClickListener() {
    //        Log.d(tag, "==addImageClickListener");
    // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
    mWebView.loadUrl("javascript:(function(){"
        + "var objs = document.getElementsByTagName(\"img\");"
        + "for(var i=0;i<objs.length;i++)"
        + "{"
        +
        //  "objs[i].onclick=function(){alert(this.getAttribute(\"has_link\"));}" +
        "objs[i].onclick=function(){window.injectedObject.imageClick(this.getAttribute(\"src\"),this.getAttribute(\"has_link\"));}"
        + "}"
        + "})()");
  }

  /**
   * 进度条 假装加载到90%
   */
  public void startProgress90() {
    for (int i = 0; i < 900; i++) {
      final int progress = i + 1;
      mProgressBar.postDelayed(new Runnable() {
        @Override public void run() {
          mProgressBar.setProgress(progress);
          if (progress == 900) {
            progress90 = true;
            if (pageFinish) {
              startProgress90to100();
            }
          }
        }
      }, (i + 1) * 2);
    }
  }

  /**
   * 进度条 加载到100%
   */
  public void startProgress90to100() {
    for (int i = 900; i <= 1000; i++) {
      final int progress = i + 1;
      mProgressBar.postDelayed(new Runnable() {
        @Override public void run() {
          mProgressBar.setProgress(progress);
          if (progress == 1000) {
            mProgressBar.setVisibility(View.GONE);
          }
        }
      }, (i + 1) * 2);
    }
  }

  static class FullscreenHolder extends FrameLayout {

    public FullscreenHolder(@NonNull Context context) {
      super(context);
      setBackgroundColor(context.getResources().getColor(android.R.color.black));
    }

    @Override public boolean onTouchEvent(MotionEvent event) {
      return true;
    }
  }

  public class MyWebChromeClient extends WebChromeClient {
    private View xprogressvideo;

    // 播放网络视频时全屏会被调用的方法
    @Override public void onShowCustomView(View view, CustomViewCallback callback) {
      setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
      mWebView.setVisibility(View.INVISIBLE);
      // 如果一个视图已经存在，那么立即终止并新建一个
      if (xCustomView != null) {
        callback.onCustomViewHidden();
        return;
      }

      FrameLayout decor = (FrameLayout) getWindow().getDecorView();
      video_fullView = new FullscreenHolder(DeepUnderstandAttrActivity.this);
      video_fullView.addView(view);
      decor.addView(video_fullView);

      xCustomView = view;
      xCustomViewCallback = callback;
      video_fullView.setVisibility(View.VISIBLE);
    }

    // 视频播放退出全屏会被调用的
    @Override public void onHideCustomView() {
      if (xCustomView == null) { // 不是全屏状态
        return;
      }

      setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
      xCustomView.setVisibility(View.GONE);
      video_fullView.removeView(xCustomView);
      xCustomView = null;
      video_fullView.setVisibility(View.GONE);
      xCustomViewCallback.onCustomViewHidden();
      mWebView.setVisibility(View.VISIBLE);
    }

    // 视屏加载时进程loading
    @Override public View getVideoLoadingProgressView() {
      if (xprogressvideo == null) {
        LayoutInflater inflater = LayoutInflater.from(DeepUnderstandAttrActivity.this);
        xprogressvideo = inflater.inflate(R.layout.video_loading_progress, null);
      }
      return xprogressvideo;
    }

    @Override public void onProgressChanged(WebView view, int newProgress) {
      super.onProgressChanged(view, newProgress);
      if (progress90) {
        int progress = newProgress * 100;
        if (progress > 900) {
          mProgressBar.setProgress(progress);
          if (progress == 1000) {
            mProgressBar.setVisibility(View.GONE);
          }
        }
      }
    }

    @Override public void onReceivedTitle(WebView view, String title) {
      super.onReceivedTitle(view, title);
      // 设置title
      setTitle(title);
    }

    // 扩展浏览器上传文件
    // 3.0++ 版本
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
      openFileChooserImpl(uploadMsg);
    }

    //3.0--版本
    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
      openFileChooserImpl(uploadMsg);
    }

    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
      openFileChooserImpl(uploadMsg);
    }

    // For Android > 5.0
    @Override public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
        FileChooserParams fileChooserParams) {
      openFileChooserImplForAndroid5(filePathCallback);
      return true;
    }
  }

  private void openFileChooserImpl(ValueCallback<Uri> uploadMsg) {
    mUploadMessage = uploadMsg;
    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
    i.addCategory(Intent.CATEGORY_OPENABLE);
    i.setType("image/*");
    startActivityForResult(Intent.createChooser(i, "文件选择"), FILECHOOSER_RESULTCODE);
  }

  private void openFileChooserImplForAndroid5(ValueCallback<Uri[]> uploadMsg) {
    mUploadMessageForAndroid5 = uploadMsg;
    Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
    contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
    contentSelectionIntent.setType("image/*");

    Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
    chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
    chooserIntent.putExtra(Intent.EXTRA_TITLE, "图片选择");

    startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE_FOR_ANDROID_5);
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == FILECHOOSER_RESULTCODE) {
      Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
      mUploadMessage.onReceiveValue(result);
      mUploadMessage = null;
    } else if (requestCode == FILECHOOSER_RESULTCODE_FOR_ANDROID_5) {
      if (null == mUploadMessageForAndroid5) return;
      Uri result = (data == null || resultCode != RESULT_OK) ? null : data.getData();
      if (result != null) {
        mUploadMessageForAndroid5.onReceiveValue(new Uri[] { result });
      } else {
        mUploadMessageForAndroid5.onReceiveValue(new Uri[] {});
      }
      mUploadMessageForAndroid5 = null;
    }
  }

  @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK) {
      if (mWebView.canGoBack()) {
        mWebView.goBack();
        return true;
      } else {
        mWebView.loadUrl("about:blank");
        finish();
      }
    }
    return false;
  }
}
