package widget;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by YH on 2017-01-20.
 */

public abstract class BaseRecyclerViewAdapter<T>
    extends RecyclerView.Adapter<BaseRecyclerViewHolder> {
  @Override public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return null;
  }

  @Override public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {

  }

  @Override public int getItemCount() {
    return 0;
  }
}
