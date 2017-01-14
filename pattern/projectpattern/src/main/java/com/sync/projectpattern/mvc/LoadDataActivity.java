package com.sync.projectpattern.mvc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.sync.projectpattern.R;
import com.sync.projectpattern.mvc.bean.Essay;
import com.sync.projectpattern.mvc.model.EssayModel;
import com.sync.projectpattern.mvc.model.MainModel;
import java.util.List;

/**
 * Author：Administrator on 2017/1/14 0014 11:55
 * Contact：289168296@qq.com
 */
public class LoadDataActivity extends AppCompatActivity implements View.OnClickListener {

  private TextView tvView, tvViewUpdate;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_load_data);
    setTitle("MVC");
    findViewById(R.id.btn_mvc).setOnClickListener(this);
    findViewById(R.id.btn_mvc2).setOnClickListener(this);
    tvView = (TextView) findViewById(R.id.tv_view);
    tvViewUpdate = (TextView) findViewById(R.id.tv_view_updata);
  }

  @Override public void onClick(View view) {
    switch (view.getId()) {
      case R.id.btn_mvc:
        MainModel mainModel = new MainModel();
        mainModel.load(new MainModel.MainImpl() {
          @Override public void success(String text) {
            tvView.setText("MVC加载数据：" + text);
          }
        });
        break;
      case R.id.btn_mvc2:
        EssayModel essayModel = new EssayModel(LoadDataActivity.this);
        essayModel.getEssay(3, new EssayModel.OnEssayListener() {
          @Override public void onSuccess(List<Essay> list) {
            if (list != null && list.get(0) != null) {
              tvViewUpdate.setText("MVC 更新数据：" + list.get(0).getTitle());
            }
          }

          @Override public void onError() {

          }
        });
        break;
    }
  }
}
