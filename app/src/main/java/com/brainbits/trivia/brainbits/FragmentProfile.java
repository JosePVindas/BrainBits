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
import android.widget.ImageView;
import android.widget.TextView;

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

        // Vars
        final SessionManager manager = new SessionManager(getActivity());

        // Widgets
        TextView rank;
        ImageView rankImg;
        TextView username;
        TextView email;


        username = (TextView) view.findViewById(R.id.profile_username);
        email = (TextView) view.findViewById(R.id.profile_mail);
        rank = (TextView) view.findViewById(R.id.profile_rank);
        rankImg = (ImageView) view.findViewById(R.id.profile_rank_img);

        String usr = manager.getUsername();
        String mail = manager.getEmail();

        username.setText(usr);
        email.setText(mail);

        final int rank_num = manager.getRank();


        switch (rank_num){

            case 0: {

                rank.setText("Newbie");
                rankImg.setImageResource(R.drawable.rank_newbie);
                break;

            }

            case 1:{

                rank.setText("Private");
                rankImg.setImageResource(R.drawable.rank_private);
                break;

            }

            case 2:{

                rank.setText("Private First Class");
                rankImg.setImageResource(R.drawable.rank_private_first_class);
                break;

            }

            case 3: {

                rank.setText("Sergeant");
                rankImg.setImageResource(R.drawable.rank_sergeant);
                break;

            }

            case 4:{

                rank.setText("Sergeant First class");
                rankImg.setImageResource(R.drawable.rank_sergeant_first_class);
                break;

            }

            case 5:{

                rank.setText("First Sergeant");
                rankImg.setImageResource(R.drawable.rank_sergeant_first);
                break;

            }

            case 7:{

                rank.setText("Sergeant Command Major");
                rankImg.setImageResource(R.drawable.rank_sergeant_command_major);
                break;

            }



        }
    }
}
