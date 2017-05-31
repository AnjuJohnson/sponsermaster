package com.cutesys.sponsormasterfullversionnew.Subclasses;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cutesys.sponsermasterlibrary.Button.FloatingActionButton;
import com.cutesys.sponsermasterlibrary.Button.FloatingActionMenu;
import com.cutesys.sponsermasterlibrary.CircularImageView;
import com.cutesys.sponsermasterlibrary.CustomToast;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.Listener;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.SqliteHelper;
import com.cutesys.sponsormasterfullversionnew.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import static android.support.v4.content.ContextCompat.checkSelfPermission;

/**
 * Created by Kris on 3/6/2017.
 */

public class MyProfileFragment extends Fragment implements View.OnClickListener{

    private static final int PERMISSIONS_REQUEST_PHONE_CALL = 100;
    private static String[] PERMISSIONS_PHONECALL = {Manifest.permission.CALL_PHONE};

    private SqliteHelper helper;

    private CollapsingToolbarLayout collapse_toolbar;
    private TextView name, email, phone, address1, address2, office, nationality,
            qatar, issue, expiry;
    private CircularImageView profile;
    private Toolbar toolbar;
    private FloatingActionMenu menu;
    private FloatingActionButton call,mail;

    Listener mListener;

    List<HashMap<String, String>> Data_Item ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.myprofile_fragment, container, false);

        helper = new SqliteHelper(getActivity(), "SponserMaster", null, 1);

        InitIdView(rootView);
        return rootView;
    }

    private void InitIdView(View rootView) {

        mListener = (Listener)getActivity();
        Data_Item = helper.getadmindetails();

        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.mipmap.ic_action_menu);

        menu = (FloatingActionMenu) rootView.findViewById(R.id.menu);
        call = (FloatingActionButton) rootView.findViewById(R.id.call);
        mail = (FloatingActionButton) rootView.findViewById(R.id.mail);
        mail.setOnClickListener(this);
        call.setOnClickListener(this);

        collapse_toolbar = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapse_toolbar);
        collapse_toolbar.setExpandedTitleColor(Color.parseColor("#ffffff"));
        profile   = (CircularImageView) rootView.findViewById(R.id.profile);
        name   = (TextView) rootView.findViewById(R.id.name);
        email   = (TextView) rootView.findViewById(R.id.email);
        phone   = (TextView) rootView.findViewById(R.id.phone);
        address1   = (TextView) rootView.findViewById(R.id.address1);
        address2   = (TextView) rootView.findViewById(R.id.address2);
        office   = (TextView) rootView.findViewById(R.id.office);
        nationality   = (TextView) rootView.findViewById(R.id.nationality);
        qatar   = (TextView) rootView.findViewById(R.id.qatar);
        issue   = (TextView) rootView.findViewById(R.id.issue);
        expiry   = (TextView) rootView.findViewById(R.id.expiry);

        collapse_toolbar.setCollapsedTitleTextColor(Color.WHITE);

        AppBarLayout appBarLayout = (AppBarLayout)rootView.findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapse_toolbar.setTitle(Data_Item.get(0).get("admin_f_name")+" "+
                            Data_Item.get(0).get("admin_l_name"));
                    isShow = true;
                } else if(isShow) {
                    collapse_toolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });

        try {
            Picasso.with(getActivity())
                    .load(Data_Item.get(0).get("admin_img")
                            .replaceAll(" ","%20"))
                    .placeholder(R.drawable.profile)
                    .error(R.drawable.profile)
                    .into(profile);
        }catch (Exception e){
        }


        name.setText(Data_Item.get(0).get("admin_f_name")+""+Data_Item.get(0).get("admin_l_name"));

        if((!Data_Item.get(0).get("admin_email").equals(""))
                && (!Data_Item.get(0).get("admin_email").equals("null"))) {

            email.setText("none");

        } else {
            email.setText(Data_Item.get(0).get("admin_email"));
        }
        if((!Data_Item.get(0).get("admin_phone").equals(""))
                && (!Data_Item.get(0).get("admin_phone").equals("null"))) {

            phone.setText("none");

        } else {
            phone.setText(Data_Item.get(0).get("admin_phone"));
        }

        if((!Data_Item.get(0).get("admin_office").equals(""))
                && (!Data_Item.get(0).get("admin_office").equals("null"))) {

            office.setText("none");

        } else {
            office.setText(Data_Item.get(0).get("admin_office"));
        }

        if((!Data_Item.get(0).get("admin_country").equals(""))
                && (!Data_Item.get(0).get("admin_country").equals("null"))) {

            nationality.setText("none");

        } else {
            nationality.setText(Data_Item.get(0).get("admin_country"));
        }

        if((!Data_Item.get(0).get("admin_qatar_id").equals(""))
                && (!Data_Item.get(0).get("admin_qatar_id").equals("null"))) {

            qatar.setText("none");

        } else {
            qatar.setText(Data_Item.get(0).get("admin_qatar_id"));
        }

        if((!Data_Item.get(0).get("admin_issue").equals(""))
                && (!Data_Item.get(0).get("admin_issue").equals("null"))) {

            issue.setText("none");

        } else {
            issue.setText(Data_Item.get(0).get("admin_issue"));
        }

        if((!Data_Item.get(0).get("admin_expiry").equals(""))
                && (!Data_Item.get(0).get("admin_expiry").equals("null"))) {

            expiry.setText("none");

        } else {
            expiry.setText(Data_Item.get(0).get("admin_expiry"));
        }

        if (Data_Item.get(0).get("admin_address").contains(",")){

            String[] address = Data_Item.get(0).get("admin_address").split(",");
            int val = address.length/2;

            for (int i = 0;i < val ; i++){
                address1.setText(address1.getText().toString()+"\n"+address[i].trim());
            }
            for (int j = val;j < address.length ; j++){
                address2.setText(address2.getText().toString()+"\n"+address[j].trim());
            }
        } else {
            address1.setText(Data_Item.get(0).get("admin_address"));
            address2.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.OpenDrawer();
            }
        });
    }

    @Override
    public void onClick(View view) {

        int buttonId = view.getId();
        switch (buttonId){
            case R.id.call:
                if(phone.getText().toString().trim().equals("none")){
                    CustomToast.info(getActivity(),"Invalid Phone Number").show();

                } else {
                    Action_Call();
                }
                break;
            case R.id.mail:
                if(email.getText().toString().trim().equals("none")){
                    CustomToast.info(getActivity(),"Invalid Mail ID").show();

                } else {
                    Action_Mail();
                }
                break;
        }
    }

    private void Action_Mail() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{email.getText().toString().trim()});
        i.putExtra(Intent.EXTRA_SUBJECT, 0);
        i.putExtra(Intent.EXTRA_TEXT, 0);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
        }
    }

    private void Action_Call() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) !=
                        PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PERMISSIONS_REQUEST_PHONE_CALL);
        } else {
            //Open call function
            String number = new String(phone.getText().toString());
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + number));
            startActivity(intent);
        }
    }
}