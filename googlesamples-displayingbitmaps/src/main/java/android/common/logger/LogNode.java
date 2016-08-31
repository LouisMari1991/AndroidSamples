package android.common.logger;

/**
 * Created by sync on 2016/5/20.
 */
public interface LogNode {



  public void println(int priority, String tag, Throwable tr);


}
