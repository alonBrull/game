package com.example.game_class;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class FragmentAskName extends Fragment {
    private CallBack_ActivityList callBack_activityList;
    private View view;
    private EditText askname_EDT_name;
    private Button askname_BTN_enter, askname_BTN_check;

    public void setCallBack(CallBack_ActivityList _callBack_activityList) {
        this.callBack_activityList = _callBack_activityList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fram_askname, container, false);
        }
        findViews(view);

        askname_BTN_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = askname_EDT_name.getText().toString();
                askname_EDT_name.setText("");
                askname_EDT_name.setHint(name);
                callBack_activityList.setName(name);
            }
        });

        askname_BTN_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack_activityList.hideFragmentAskName();
            }
        });

        return view;
    }

    private void findViews(View view) {
        askname_EDT_name = view.findViewById(R.id.askname_EDT_name);
        askname_BTN_enter = view.findViewById(R.id.askname_BTN_enter);
        askname_BTN_check = view.findViewById(R.id.askname_BTN_check);
    }

}
