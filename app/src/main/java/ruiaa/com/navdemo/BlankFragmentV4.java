package ruiaa.com.navdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ruiaa on 2017/10/24.
 */

public class BlankFragmentV4 extends Fragment {

    public static BlankFragmentV4 newInstance(@ColorInt int bgColor) {
        BlankFragmentV4 fragment = new BlankFragmentV4();
        Bundle args = new Bundle();
        args.putInt("bgColor", bgColor);
        fragment.setArguments(args);
        return fragment;
    }

    private int color = Color.RED;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            color = getArguments().getInt("bgColor");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        view.findViewById(R.id.fragment_blank).setBackgroundColor(color);
        return view;
    }
}
