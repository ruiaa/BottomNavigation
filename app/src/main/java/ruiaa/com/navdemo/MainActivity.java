package ruiaa.com.navdemo;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ruiaa.bottomnavigation.BottomBarView;
import com.ruiaa.bottomnavigation.ItemView;
import com.ruiaa.bottomnavigation.OnSelectListener;
import com.ruiaa.bottomnavigation.ScrollFrameView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ScrollFrameView scrollFrameView;
    private BottomBarView bottomBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scrollFrameView=(ScrollFrameView)findViewById(R.id.container);
        bottomBarView=(BottomBarView)findViewById(R.id.bar);

        //设置 item 默认样式
        //bottomBarView.setTextColor(Color.RED,Color.GRAY).setTextSize(14).setImgSize(36,30).setTextImgDistance(6);
        //设置小圆点默认样式
        //bottomBarView.setDotSize(20,20).setDotBgColor(Color.GREEN).setDotTextSize(14).setDotTextColor(Color.MAGENTA).setDotOffset(30,20);
        bottomBarView
                .addItem(new ItemView(bottomBarView).setContent("msg",R.drawable.msg_active,R.drawable.msg_inactive))
                .addItem(new ItemView(bottomBarView).setContent("music",R.drawable.music_active,R.drawable.music_inactive))
                .addItem(new ItemView(bottomBarView).setContent("movie",R.drawable.movie_active,R.drawable.movie_inactive))
                .addItem(new ItemView(bottomBarView).setContent("sport",R.drawable.sport_active,R.drawable.sport_inactive))
                .addItem(new ItemView(bottomBarView).setContent("mine",R.drawable.mine_active,R.drawable.mine_inactive))
                .init();

        //scrollFrameView.setFragmentList(getFragmentManager(),getFragmentList());
        scrollFrameView.setFragmentList(getSupportFragmentManager(),getFragmentV4List());
        scrollFrameView.setBottomBarView(bottomBarView);

        bottomBarView.setOnSelectListener(new OnSelectListener() {
            @Override
            public void onSelect(int position) {
                Log.e("ruiaa","select"+position);
            }
        });
        //scrollFrameView.setCurrentItem(2);
        bottomBarView.setSelectWithFrame(2);

        bottomBarView.getItem(4).setDotText("?").setDotVisible(true);
    }

    private List<Fragment> getFragmentList(){
        List<Fragment> list=new ArrayList<>();
        list.add(BlankFragment.newInstance(Color.RED));
        list.add(BlankFragment.newInstance(Color.BLUE));
        list.add(BlankFragment.newInstance(Color.YELLOW));
        list.add(BlankFragment.newInstance(Color.GREEN));
        list.add(BlankFragment.newInstance(Color.GRAY));
        return list;
    }

    private List<android.support.v4.app.Fragment> getFragmentV4List(){
        List<android.support.v4.app.Fragment> list=new ArrayList<>();
        list.add(BlankFragmentV4.newInstance(Color.RED));
        list.add(BlankFragmentV4.newInstance(Color.BLUE));
        list.add(BlankFragmentV4.newInstance(Color.YELLOW));
        list.add(BlankFragmentV4.newInstance(Color.GREEN));
        list.add(BlankFragmentV4.newInstance(Color.GRAY));
        return list;
    }

}
