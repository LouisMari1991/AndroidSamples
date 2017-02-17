package com.sync.coolweather;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.sync.coolweather.db.Province;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by YH on 2017-02-17.
 */

public class ChooseAreaFragment extends Fragment {

  public static final int LEVEL_PROVINCE = 0;
  public static final int LEVEL_CITY = 1;
  public static final int LEVEL_COUNTY = 2;

  private ProgressDialog progressDialog;
  private TextView titleView;
  private Button backButton;
  private ListView listView;
  private ArrayAdapter<String> adapter;
  private List<String> dataList = new ArrayList<>();

  private List<Province> provinceList;


}
