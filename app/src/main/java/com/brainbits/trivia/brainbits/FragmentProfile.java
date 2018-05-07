package com.brainbits.trivia.brainbits;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;

public class FragmentProfile  extends Fragment{

    // Vars
    View view;

    public FragmentProfile() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.profile_info_fragment,container,false);

        // Dialogs
        final Dialog logOutDialog = new Dialog(getActivity());

        // Widgets
        Button profile_logout;

        getInfo();


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

    private void getInfo() {

        // Widgets
        TextView rank;
        ImageView rankImg;
        TextView username;
        TextView email;


        username = (TextView) view.findViewById(R.id.profile_username);
        email = (TextView) view.findViewById(R.id.profile_mail);
        rank = (TextView) view.findViewById(R.id.profile_rank);
        rankImg = (ImageView) view.findViewById(R.id.profile_rank_img);

        SharedPreferences preferences = getActivity().getSharedPreferences("Login", MODE_PRIVATE);

        String usr = preferences.getString("usr", null);
        String mail = preferences.getString("email","Unavailable Email");

        username.setText(usr);
        email.setText(mail);

        int rank_num = preferences.getInt("rank",0);

        switch (rank_num){

            case 0: {

                rank.setText("Newbie");
                rankImg.setImageResource(R.drawable.rank_newbie);

            }

            case 1:{

                rank.setText("Private");
                rankImg.setImageResource(R.drawable.rank_private);

            }

            case 2:{

                rank.setText("Private First Class");
                rankImg.setImageResource(R.drawable.rank_private_first_class);

            }

            case 3: {

                rank.setText("Sergeant");
                rankImg.setImageResource(R.drawable.rank_sergeant);

            }

            case 4:{

                rank.setText("Sergeant First class");
                rankImg.setImageResource(R.drawable.rank_sergeant_first_class);

            }

            case 5:{

                rank.setText("First Sergeant");
                rankImg.setImageResource(R.drawable.rank_sergeant_first);

            }

            case 7:{

                rank.setText("Sergeant Command Major");
                rankImg.setImageResource(R.drawable.rank_sergeant_command_major);

            }



        }
    }
}
