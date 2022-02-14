package com.xiyou3g.select.customer.register.login_fragment;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.xiyou3g.select.customer.register.R;
import com.xiyou3g.select.customer.register.util.NumberMatch;
import com.xiyou3g.select.customer.register.util.TimeCountUtil;

public class RegisterSecondFragment extends Fragment {

    private String text;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.get_code_layout, container, false);
        Bundle bundle = getArguments();
        assert bundle != null;
        text = bundle.getString("text");
        TextView textView = view.findViewById(R.id.get_code_text_title);
        textView.setText("获取验证码");
        Toolbar toolbar = view.findViewById(R.id.top_toolbar);
        toolbar.setNavigationOnClickListener(view1 -> {
            FragmentManager parentFragmentManager = getParentFragmentManager();
            FragmentTransaction transaction = parentFragmentManager.beginTransaction();
            RegisterFirstFragment firstFragment = new RegisterFirstFragment();
            Bundle bundle1 = new Bundle();
            bundle1.putString("text", text);
            firstFragment.setArguments(bundle1);
            transaction.replace(R.id.frame_layout, firstFragment).commit();
        });
        Button get_code_button = view.findViewById(R.id.get_code_button);
        get_code_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeCountUtil timeCountUtil = new TimeCountUtil(60000L, 1000L, getActivity(), get_code_button);
                timeCountUtil.start();
            }
        });
        EditText editText = view.findViewById(R.id.get_code_edit);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        return view;
    }
}
