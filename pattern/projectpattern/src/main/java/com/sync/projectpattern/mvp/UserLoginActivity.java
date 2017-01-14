package com.sync.projectpattern.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.sync.projectpattern.R;
import com.sync.projectpattern.mvp.presenter.UserLoginPresenter;
import com.sync.projectpattern.mvp.view.IUserLoginView;

/**
 * 架构改为MVP以后，Presenter的出现，将Activity视为View层，Presenter负责完成View层与Model层的交互。现在是这样的：
 * <p>
 * -- View 对应于Activity，负责View的绘制以及与用户交互
 * -- Model 依然是业务逻辑和实体模型
 * -- Presenter 负责完成View于Model间的交互
 * -- ok，先简单了解下，文中会有例子到时候可以直观的感受下。
 * <p>
 * 小总结下，也就是说，之所以让人觉得耳目一新，是因为这次的跳跃是从并不标准的MVC到MVP的一个转变，
 * 减少了Activity的职责，简化了Activity中的代码，将复杂的逻辑代码提取到了Presenter中进行处理。
 * 与之对应的好处就是，耦合度更低，更方便的进行测试。
 */
public class UserLoginActivity extends AppCompatActivity implements IUserLoginView {

  private EditText etUsername, etPassword;
  private Button btLogin, btClear;
  private ProgressBar progress;

  private UserLoginPresenter userLoginPresenter = new UserLoginPresenter(this);

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_login);
    setTitle("用户");
    etUsername = (EditText) findViewById(R.id.et_username);
    etPassword = (EditText) findViewById(R.id.et_password);
    progress = (ProgressBar) findViewById(R.id.progress);
    findViewById(R.id.bt_login).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        userLoginPresenter.login();
      }
    });
    findViewById(R.id.bt_clear).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        userLoginPresenter.clear();
      }
    });
  }

  @Override public String getUserName() {
    return etUsername.getText().toString().trim();
  }

  @Override public String getPassword() {
    return etPassword.getText().toString().trim();
  }

  @Override public void clearUserName() {
    etUsername.setText("");
  }

  @Override public void clearPassword() {
    etPassword.setText("");
  }

  @Override public void showLoading() {
    progress.setVisibility(View.VISIBLE);
  }

  @Override public void hindLoading() {
    progress.setVisibility(View.GONE);
  }

  @Override public void toMainActivity() {
    Toast.makeText(this, "login success , to MainActivity!", Toast.LENGTH_SHORT).show();
  }

  @Override public void showFailedError() {
    Toast.makeText(this, "login failed!", Toast.LENGTH_SHORT).show();
  }
}
