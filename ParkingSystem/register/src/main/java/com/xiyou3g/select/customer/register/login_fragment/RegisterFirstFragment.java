package com.xiyou3g.select.customer.register.login_fragment;

import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.xiyou3g.select.customer.register.R;
import com.xiyou3g.select.customer.register.util.NumberMatch;

public class RegisterFirstFragment extends Fragment {

    private String text;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forget_password_layout, container, false);
        Bundle bundle = getArguments();
        assert bundle != null;
        text = bundle.getString("text");
        TextView title = view.findViewById(R.id.text_title);
        title.setText(text);
        Toolbar toolbar = view.findViewById(R.id.top_toolbar);
        toolbar.setNavigationOnClickListener(view1 -> requireActivity().finish());
        toolbar.setForegroundGravity(Gravity.CENTER);
        TextView textView = view.findViewById(R.id.text_high);
        textView.setText(text);
        EditText editText = view.findViewById(R.id.phone_number_find);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        Button button = view.findViewById(R.id.next_button);
        button.setOnClickListener(view12 -> accountMatch(editText.getText().toString()));

        return view;
    }

    private void replace_fragment() {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        RegisterSecondFragment secondFragment = new RegisterSecondFragment();
        Bundle bundle = new Bundle();
        bundle.putString("text", text);
        secondFragment.setArguments(bundle);
        transaction.replace(R.id.frame_layout, secondFragment).commit();
    }

    public void accountMatch (String phone_number) {
        if (!NumberMatch.number(phone_number)) {
            Toast.makeText(getContext(), "请填写正确的手机号", Toast.LENGTH_SHORT).show();
        } else {
            replace_fragment();
        }
    }

}
