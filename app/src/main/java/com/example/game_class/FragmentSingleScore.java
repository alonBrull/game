package com.example.game_class;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class FragmentSingleScore extends Fragment {
    private ScoreActivity scoreActivity;
    private TextView fram_singlescore_name, fram_singlescore_score;
    private View view;


    public void setActivity(ScoreActivity scoreActivity) {
        this.scoreActivity = scoreActivity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(view == null)
        {
            view = inflater.inflate(R.layout.fram_singlescore, container, false);
        }

        findViews(view);

        return view;
    }

    private void findViews(View view) {
        fram_singlescore_name = view.findViewById(R.id.fram_singlescore_name);
        fram_singlescore_score = view.findViewById(R.id.fram_singlescore_score);
    }
}
