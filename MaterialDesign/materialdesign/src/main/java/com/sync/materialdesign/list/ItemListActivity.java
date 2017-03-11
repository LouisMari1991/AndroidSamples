package com.sync.materialdesign.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.sync.materialdesign.R;
import com.sync.materialdesign.list.dummy.DummyContent;
import java.util.List;

/**
 * Author：Administrator on 2017/3/10 0010 21:48
 * Contact：289168296@qq.com
 */
public class ItemListActivity extends AppCompatActivity {

  /**
   * Whether or not the activity is in two-pane mode, i.e. running on a tablet
   * device.
   */
  private boolean mTwoPane;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_item_list);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    toolbar.setTitle(getTitle());

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
      }
    });

    View recyclerView = findViewById(R.id.item_list);
    assert recyclerView != null;
    setupRecyclerView((RecyclerView) recyclerView);
    if (findViewById(R.id.item_detail_container) != null) {
      // The detail container view will be present only in the
      // large-screen layouts (res/values-w900dp).
      // If this view is present, then the
      // activity should be in two-pane mode.
      mTwoPane = true;
    }

  }

  private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
    recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(DummyContent.ITEMS));
  }

  public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

    private final List<DummyContent.DummyItem> mValues;

    public SimpleItemRecyclerViewAdapter(List<DummyContent.DummyItem> values) {
      mValues = values;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_content_right, parent, false);
      return new ViewHolder(view);
    }

    @Override public void onBindViewHolder(final ViewHolder holder, int position) {
      holder.mItem = mValues.get(position);
      holder.mContentView.setText("测试：加班宝 " + position);

      holder.mView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(ItemDetailFragment.ARG_ITEM_ID, holder.mItem.id);
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.item_detail_container, fragment)
                .commit();
          } else {
            Context context = view.getContext();
            Intent intent = new Intent(context, ItemDetailActivity.class);
            intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, holder.mItem.id);
            ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(ItemListActivity.this,
                    holder.mIdView, getString(R.string.transition_news_img));//与xml文件对应

            ActivityCompat.startActivity(ItemListActivity.this, intent, options.toBundle());
          }
        }
      });
    }

    @Override public int getItemCount() {
      return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

      public final View mView;
      public final ImageView mIdView;
      public final TextView mContentView;
      public DummyContent.DummyItem mItem;

      public ViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mIdView = (ImageView) mView.findViewById(R.id.image_id);
        mContentView = (TextView) mView.findViewById(R.id.content);
      }

      @Override public String toString() {
        return super.toString() + " '" + mContentView.getText() + "'";
      }
    }
  }
}
