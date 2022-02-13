package com.xiyou3g.information;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.xiyou3g.information.bean.personal_inf;

import org.litepal.LitePal;

import java.util.List;

public class personal_information extends Fragment {

    private View view;

    private EditText edit_male;
    private EditText edit_phone;
    private EditText edit_car;
    private EditText edit_age;
    private EditText edit_location;
    private EditText edit_personality;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_personnal_information, container, false);

        LitePal.getDatabase();

        getEditTextId();

        setEditText();

        return view;
    }

    /**
     * 获取EditTextId
     */
    private void getEditTextId() {
        edit_male = view.findViewById(R.id.male);
        edit_phone = view.findViewById(R.id.phone_num);
        edit_car = view.findViewById(R.id.car_num);
        edit_age = view.findViewById(R.id.car_age);
        edit_location = view.findViewById(R.id.location);
        edit_personality = view.findViewById(R.id.personality);
    }

    private void setEditText() {
        List<personal_inf> dbList = LitePal.findAll(personal_inf.class);
        if (dbList.size() > 0) {
            Log.d("123", dbList.get(0).toString());
            edit_male.setText(dbList.get(0).getMale());
            edit_phone.setText(dbList.get(0).getPhone_member());
            edit_car.setText(dbList.get(0).getCar_member());
            edit_age.setText(dbList.get(0).getCar_age());
            edit_location.setText(dbList.get(0).getLocation());
            edit_personality.setText(dbList.get(0).getPerson_sign());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("123", "onDestroy");
        LitePal.deleteAll(personal_inf.class);
        personal_inf db = new personal_inf();
        db.setMale(String.valueOf(edit_male.getText()));
        db.setPhone_member(String.valueOf(edit_phone.getText()));
        db.setCar_member(String.valueOf(edit_car.getText()));
        db.setCar_age(String.valueOf(edit_age.getText()));
        db.setLocation(String.valueOf(edit_location.getText()));
        db.setPerson_sign(String.valueOf(edit_personality.getText()));
        db.save();
        List<personal_inf> dbList = LitePal.findAll(personal_inf.class);
        Log.d("123", "update" + dbList.get(0).toString());
    }

}