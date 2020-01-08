package com.example.game_class;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.LatLng;

public class FragmentAskName extends Fragment {
    private CallBack_ActivityList callBack_activityList;
    private CallBack_Location callBack_location;
    private View view;
    private EditText askname_EDT_name, askname_EDT_address;
    private Button askname_BTN_enter, askname_BTN_check, askname_BTN_location;
    private String name, address;
    private LatLng latLng;
    private final String STRING_CURRENT = "current location";

    public void setCallBackList(CallBack_ActivityList _callBack_activityList) {
        this.callBack_activityList = _callBack_activityList;
    }

    public void setCallBackLocation(CallBack_Location callBack_location) {
        this.callBack_location = callBack_location;
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

        setOnClickListeners();

        return view;
    }

    private void setOnClickListeners() {
        askname_BTN_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = askname_EDT_name.getText().toString();
                address = askname_EDT_address.getText().toString();
                askname_EDT_name.setText("");
                askname_EDT_name.setHint(name);
                if(askname_EDT_address.getText().toString().equals(STRING_CURRENT)) {
                    askname_EDT_address.setText("");
                    askname_EDT_address.setHint(STRING_CURRENT);
                } else {
                    askname_EDT_address.setText("");
                    latLng = callBack_location.getLatLngFromAddress(address);
                }
            }
        });

        askname_BTN_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.equals("")) {
                    Toast.makeText(getContext(), "please enter a name", Toast.LENGTH_SHORT).show();
                } else if (latLng.equals(new LatLng(0, 0))) {
                    Toast.makeText(getContext(),   "\"" + address + "\"" + " is not a valid address", Toast.LENGTH_SHORT).show();
                } else {
                    callBack_activityList.setLocation(latLng);
                    callBack_activityList.setName(name);
                    callBack_activityList.hideFragmentAskName();
                }
            }
        });

        askname_BTN_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack_location.setCurrentLocation();
                latLng = callBack_location.getLatlng();
                askname_EDT_address.setText(STRING_CURRENT);
            }
        });
    }

    private void findViews(View view) {
        askname_EDT_name = view.findViewById(R.id.askname_EDT_name);
        askname_EDT_address = view.findViewById(R.id.askname_EDT_address);
        askname_BTN_enter = view.findViewById(R.id.askname_BTN_enter);
        askname_BTN_check = view.findViewById(R.id.askname_BTN_check);
        askname_BTN_location = view.findViewById(R.id.askname_BTN_location);
    }

}
