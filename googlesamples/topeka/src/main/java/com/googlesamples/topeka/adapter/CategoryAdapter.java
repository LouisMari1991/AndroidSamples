package com.googlesamples.topeka.adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.googlesamples.topeka.R;
import com.googlesamples.topeka.databinding.ItemCategoryBinding;
import com.googlesamples.topeka.helper.ApiLevelHelper;
import com.googlesamples.topeka.model.Category;
import com.googlesamples.topeka.persistence.TopekaDatabaseHelper;
import com.sync.logger.Logger;
import java.util.List;

/**
 * Author：Administrator on 2016/8/29 0029 21:48
 * Contact：289168296@qq.com
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

  public static final String DRAWABLE = "drawable";
  private static final String ICON_CATEGORY = "icon_category_";
  private final Resources mResources;
  private final String mPackageName;
  private final LayoutInflater mLayoutInflater;
  private final Activity mActivity;
  private List<Category> mCategories;

  private OnItemClickListener mOnItemClickListener;

  public interface OnItemClickListener {
    void onCLick(View view, int position);
  }


  public CategoryAdapter(Activity activity) {
    this.mActivity = activity;
    this.mResources = mActivity.getResources();
    this.mPackageName = mActivity.getPackageName();
    this.mLayoutInflater = LayoutInflater.from(activity.getApplicationContext());
    updateCategory(activity);
  }

  @Override public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(
        (ItemCategoryBinding) DataBindingUtil.inflate(mLayoutInflater, R.layout.item_category,
            parent, false));
  }

  @Override public void onBindViewHolder(final ViewHolder holder, final int position) {
    ItemCategoryBinding binding = holder.getBinding();
    Category category = mCategories.get(position);
    binding.setCategory(category);
    /**
     * 直接Binding
     * 当一个variable或observable变化时，binding会在下一帧之前被计划要改变。
     * 有很多次，但是在Binding时必须立即执行。
     * 要强制执行，使用executePendingBindings()方法。
     */
    binding.executePendingBindings();
    setCategoryIcon(category, binding.categoryIcon);
    holder.itemView.setBackgroundColor(getColor(category.getTheme().getWindowBackgroundColor()));
    binding.categoryTitle.setTextColor(getColor(category.getTheme().getTextPrimaryColor()));
    binding.categoryTitle.setBackgroundColor(getColor(category.getTheme().getPrimaryColor()));
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        mOnItemClickListener.onCLick(view, holder.getAdapterPosition());
      }
    });
  }

  @Override public long getItemId(int position) {
    return mCategories.get(position).getId().hashCode();
  }

  @Override public int getItemCount() {
    return mCategories.size();
  }

  public Category getItem(int position) {
    return mCategories.get(position);
  }

  /**
   * @see android.support.v7.widget.RecyclerView.Adapter#notifyItemChanged(int)
   * @param id Id of changed category.
   */
  public final void notifyItemChanged(String id) {
    updateCategory(mActivity);
    notifyItemChanged(getItemPositionById(id));
  }

  private int getItemPositionById(String id) {
    for (int i = 0; i < mCategories.size(); i ++) {
      if (mCategories.get(i).getId().equals(id)) {
        return i;
      }
    }
    return -1;
  }

  public void setOnItemClicklistener(OnItemClickListener onItemTouchListener) {
    mOnItemClickListener = onItemTouchListener;
  }

  private void setCategoryIcon(Category category, ImageView icon) {
    final int categoryImageResource = mResources.getIdentifier(
        ICON_CATEGORY + category.getId(), DRAWABLE, mPackageName);
    Logger.i(" categoryImageResource : " + category.getId());
    final boolean solved = category.isSolved();
    if (solved) {
      Drawable solvedIcon = loadSolvedIcon(category, categoryImageResource);
      icon.setImageDrawable(solvedIcon);
    } else {
      icon.setImageResource(categoryImageResource);
    }
  }

  private void updateCategory(Activity activity) {
    mCategories = TopekaDatabaseHelper.getCategories(activity, true);
  }

  /**
   * Loads an icon that indicates that a category has already been solved.
   *  加载一个图标表明一个类型已经解决。
   *
   * @param category The solved category to display.
   * @param categoryImageResource The category's identifying image.
   * @return The icon indicating that the category has been solved.
   */
  private Drawable loadSolvedIcon(Category category, int categoryImageResource) {
    if (ApiLevelHelper.isAtLeast(Build.VERSION_CODES.LOLLIPOP)) {
      return loadSolvedIconLollipop(category, categoryImageResource);
    }
    return loadSolvedIconPreLollipop(category, categoryImageResource);
  }

  @NonNull
  private LayerDrawable loadSolvedIconLollipop(Category category, int categoryImageResource) {
    final Drawable done = loadTintedDoneDrawable();
    final Drawable categoryIcon = loadTintedCategoryDrawable(category, categoryImageResource);
    Drawable[] layers = new Drawable[]{categoryIcon, done};
    return new LayerDrawable(layers);
  }

  private Drawable loadSolvedIconPreLollipop(Category category, int categoryImageResource) {
    return loadTintedCategoryDrawable(category, categoryImageResource);
  }

  /**
   * Loads and tints a drawable.
   *
   * @param category The category providing the tint color
   * @param categoryImageResource The image resource to tint
   * @return The tinted resource
   */
  private Drawable loadTintedCategoryDrawable(Category category, int categoryImageResource) {
    final Drawable categoryIcon = ContextCompat
        .getDrawable(mActivity, categoryImageResource).mutate();
    return wrapAndTint(categoryIcon, category.getTheme().getPrimaryColor());
  }

  /**
   * Loads and tints a check mark.
   *
   * @return The tinted check marl.
   */
  private Drawable loadTintedDoneDrawable() {
    final Drawable done = ContextCompat.getDrawable(mActivity, R.drawable.ic_tick);
    return wrapAndTint(done, android.R.color.white);
  }

  /**
   *  着色器
   * @param done 着色之前的Drawable
   * @param color 颜色ResId
   * @return 着色之后的Drawable
   */
  private Drawable wrapAndTint(Drawable done, @ColorRes int color) {
    Drawable compatDrawable = DrawableCompat.wrap(done);
    DrawableCompat.setTint(compatDrawable, getColor(color));
    return compatDrawable;
  }

  /**
   * Convenience method for color loading.
   *
   * @param colorRes The resource id of the color to load.
   * @return The loaded color.
   *
   * 2016/8/30 0030 21:38
   */
  private int getColor(@ColorRes int colorRes) {
    return ContextCompat.getColor(mActivity, colorRes);
  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    private ItemCategoryBinding mBinding;

    public ViewHolder(ItemCategoryBinding binding) {
      super(binding.getRoot());
      this.mBinding = binding;
    }

    public ItemCategoryBinding getBinding() {
      return mBinding;
    }
  }
}
