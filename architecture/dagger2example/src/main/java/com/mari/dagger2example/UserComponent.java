package com.mari.dagger2example;

/**
 * inject 方法接收父类型参数，而调用时传入的是子类型对象则无法注入。比如你想作用 BaseActivity，inject() 就传入 BaseActivity,但是只能作用 BaseActivity 不能作用子类MainActivity。反之亦然；
 */
@dagger.Component(modules = {UserModule.class})
public interface UserComponent {
  void inject(MainActivity activity);
}
