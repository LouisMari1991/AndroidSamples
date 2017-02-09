package com.sync.capturingphotos;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import com.sync.capturingphotos.databinding.ActivityMainBinding;
import com.sync.logger.Logger;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

  private static final int REQ_THUMB = 222;
  private static final int REQ_GALLERY = 333;
  private static final int REQ_TAKE_PHOTO = 444;

  private String mCurrentPhotoPath;
  private String mPublicPhotoPath;

  private ActivityMainBinding mBinding;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      requestPermissions(new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, 200);
    }
    mBinding.thumbnail.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {//判断是否有相机应用
          startActivityForResult(takePictureIntent, REQ_THUMB);
        }
      }
    });
    mBinding.fullSize.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {//判断是否有相机应用
          File photoFile = null;
          try {
            photoFile = createImageFile();
          } catch (IOException e) {
            e.printStackTrace();
          }
          // Continue only if the File was successfully created
          if (photoFile != null) {
            //FileProvider 是一个特殊的 ContentProvider 的子类，
            //它使用 content:// Uri 代替了 file:/// Uri. ，更便利而且安全的为另一个app分享文件
            Uri photoURI = FileProvider.getUriForFile(MainActivity.this, "com.sync.fileprovider", photoFile);
            Logger.i("photoURI:" + photoURI.toString() + ", filePath=" + mCurrentPhotoPath);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, REQ_TAKE_PHOTO);
          }
        }
      }
    });

    mBinding.addGallery.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {//判断是否有相机应用
          // Create the File where the photo should go
          File photoFile = null;
          try {
            photoFile = createPublicImageFile();//创建临时图片文件
          } catch (IOException ex) {
            ex.printStackTrace();
          }
          // Continue only if the File was successfully created
          if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(MainActivity.this, "com.sync.fileprovider", photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, REQ_GALLERY);
          }
        }
      }
    });
  }

  private File createImageFile() throws IOException {
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
    String imageFileName = "JPEG_" + timeStamp + "_";
    //.getExternalFilesDir()方法可以获取到 SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
    File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    //创建临时文件,文件前缀不能少于三个字符,后缀如果为空默认未".tmp"
    File image = File.createTempFile(imageFileName,  /* 前缀 */
        ".jpg",         /* 后缀 */
        storageDir      /* 文件夹 */);
    mCurrentPhotoPath = image.getAbsolutePath();
    return image;
  }

  private File createPublicImageFile() throws IOException {
    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    // Create an image file name
    Logger.i("path:" + path.getAbsolutePath());
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
    String imageFileName = "JPEG_" + timeStamp;
    File image = File.createTempFile(imageFileName,  /* 前缀 */
        ".jpg",         /* 后缀 */
        path      /* 文件夹 */);
    mPublicPhotoPath = image.getAbsolutePath();
    return image;
  }

  private void galleryAddPic(String path) {
    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
    File f = new File(path);
    Uri contentUri = Uri.fromFile(f);
    mediaScanIntent.setData(contentUri);
    sendBroadcast(mediaScanIntent);
  }

  private void showImage(ImageView imageView, String srcPath, String text) {

    // Get the dimensions of the View
    int targetW = imageView.getWidth();
    int targetH = imageView.getHeight();

    // Get the dimensions of the bitmap
    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
    bmOptions.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(srcPath, bmOptions);
    int photoW = bmOptions.outWidth;
    int photoH = bmOptions.outHeight;

    // Determine how much to scale down the image
    int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

    // Decode the image file into a Bitmap sized to fill the View
    bmOptions.inJustDecodeBounds = false;
    bmOptions.inSampleSize = scaleFactor;
    bmOptions.inPurgeable = true;

    Bitmap srcBitmap = BitmapFactory.decodeFile(srcPath, bmOptions);

    int w = srcBitmap.getWidth();
    int h = srcBitmap.getHeight();

    Bitmap destBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(destBitmap);

    Paint p = new Paint();

    // 水印的颜色
    p.setColor(Color.RED);

    // 水印的字体大小
    p.setTextSize(getResources().getDimensionPixelOffset(R.dimen.pic_text));

    p.setAntiAlias(true);// 去锯齿

    canvas.drawBitmap(srcBitmap, 0, 0, p);

    // 在左边的中间位置开始添加水印
    canvas.drawText(text, 0, h / 2, p);

    canvas.save(Canvas.ALL_SAVE_FLAG);

    canvas.restore();

    imageView.setImageBitmap(destBitmap);

    // 首先保存图片
    File file = new File(srcPath);
    if (file.exists()) {
      file.delete();
    }
    try {
      FileOutputStream fos = new FileOutputStream(file);
      destBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
      fos.flush();
      fos.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    galleryAddPic(srcPath);

  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode != Activity.RESULT_OK) return;
    switch (requestCode) {
      case REQ_THUMB: {
        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");
        mBinding.imageView.setImageBitmap(imageBitmap);
        break;
      }
      case REQ_TAKE_PHOTO: {
        showImage(mBinding.imageView, mCurrentPhotoPath, mCurrentPhotoPath);
        break;
      }
      case REQ_GALLERY: {
        showImage(mBinding.imageView, mPublicPhotoPath, mPublicPhotoPath);
        break;
      }
    }
  }
}
