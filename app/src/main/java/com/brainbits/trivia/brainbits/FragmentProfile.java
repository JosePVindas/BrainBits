package com.brainbits.trivia.brainbits;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentProfile  extends Fragment{

    View view;

    public FragmentProfile() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.profile_info_fragment,container,false);

        final Dialog logOutDialog = new Dialog(getActivity());
        Button profile_logout;
        profile_logout = (Button) view.findViewById(R.id.profile_logout);
        profile_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logOutDialog.setContentView(R.layout.dialog_logout);
                Button dismiss = (Button)logOutDialog.findViewById(R.id.no_btn);
                Button accept = (Button)logOutDialog.findViewById(R.id.yes_btn);

                dismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logOutDialog.dismiss();
                    }
                });

                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Intent i = new Intent(getActivity(), LoginActivity.class);
                        startActivity(i);
                    }
                });

                logOutDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                logOutDialog.show();

            }
        });
        return view;
    }
}
