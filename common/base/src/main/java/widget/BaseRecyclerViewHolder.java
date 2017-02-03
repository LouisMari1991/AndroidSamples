package widget;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by YH on 2017-01-20.
 */

public class BaseRecyclerViewHolder<T, D extends ViewDataBinding> extends RecyclerView.ViewHolder {
  public BaseRecyclerViewHolder(View itemView) {
    super(itemView);
  }
}
