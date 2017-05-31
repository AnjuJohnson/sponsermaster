package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.cutesys.sponsermasterlibrary.Button.FloatingActionButton;
import com.cutesys.sponsermasterlibrary.CircularImageView;
import com.cutesys.sponsermasterlibrary.CustomToast;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.ListItem;
import com.cutesys.sponsormasterfullversionnew.R;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Athira on 3/2/2017.
 */
public class OtherProfileDocumentAdapter extends RecyclerView.Adapter {

    boolean temp = true;
    int clickedPos = -1;
    private Activity mContext;

    private ArrayList<ListItem> mListItem;
    private ListItem item;
    private int mMaxProgress = 100;

    String mStatus;
    int widthPixels , heightPixels;
    float scaleFactor;

    public String[] mColors = {
            "#01AFDA",
            "#4CAF50",
            "#F7C638",
            "#E23D34"
    };
    public OtherProfileDocumentAdapter(Activity context, ArrayList<ListItem> listitem){
        mContext = context;
        mListItem = listitem;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        CircularImageView circularprofile;
        FloatingActionButton img, mDownload;

        public ViewHolder(View view) {
            super(view);

            img = (FloatingActionButton) view.findViewById(R.id.img);
            mDownload = (FloatingActionButton) view.findViewById(R.id.mDownload);
            name = (TextView) view.findViewById(R.id.name);
            circularprofile = (CircularImageView) view.findViewById(R.id.circularprofile);
            mDownload.setMax(mMaxProgress);
            LoadSize();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View rootView = LayoutInflater.
                from(mContext).inflate(R.layout.documentitem, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        item = mListItem.get(position);
        final ViewHolder mViewHolder = (ViewHolder) viewHolder;

        mViewHolder.name.setText(item.get_name());
        Random randomGenerator = new Random();
        int randomNumber = randomGenerator.nextInt(mColors.length);

        if ((mListItem.get(position).get_email().contains(".jpeg"))
                || (mListItem.get(position).get_email().contains(".jpg"))
                || (mListItem.get(position).get_email().contains(".png"))) {

            mViewHolder.circularprofile.setVisibility(View.VISIBLE);
            mViewHolder.img.setVisibility(View.GONE);

            try {
                Picasso.with(mContext)
                        .load(mListItem.get(position).get_email().replaceAll(" ","%20"))
                        .placeholder(R.drawable.profile)
                        .error(R.drawable.profile)
                        .into(mViewHolder.circularprofile);
            }catch (Exception e){
            }
        }else {
            mViewHolder.circularprofile.setVisibility(View.GONE);
            mViewHolder.img.setVisibility(View.VISIBLE);
            if (mListItem.get(position).get_email().contains(".pdf")) {
                mViewHolder.img.setImageResource(R.mipmap.ic_pdf);
            } else {
                mViewHolder.img.setImageResource(R.mipmap.ic_doc);
            }
            mViewHolder.img.setColorNormal(Color.parseColor(mColors[randomNumber]));

        }
        mViewHolder.itemView.findViewById(R.id.item_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((mListItem.get(position).get_email().contains(".jpeg"))
                        || (mListItem.get(position).get_email().contains(".jpg"))
                        || (mListItem.get(position).get_email().contains(".png"))) {

                    Dialog mDialog = new Dialog(mContext,R.style.Dialog);
                    mDialog.setContentView(R.layout.imagedialog);

                    mDialog.setTitle(""+mListItem.get(position).get_name());
                    ImageView loadimage = (ImageView)mDialog.findViewById(R.id.loadimage);

                    if (isTablet(mContext)) {
                        loadimage.getLayoutParams().height = (Math.round(heightPixels / scaleFactor)-300);
                    } else {
                        loadimage.getLayoutParams().height = (Math.round(heightPixels / scaleFactor));
                    }
                    //loadimage.getLayoutParams().width = Math.round(widthPixels / scaleFactor);
                    loadimage.requestLayout();
                    mDialog.show();

                    try {
                        Picasso.with(mContext)
                                .load(mListItem.get(position).get_email().replaceAll(" ","%20"))
                                .placeholder(R.drawable.profile)
                                .error(R.drawable.profile)
                                .into(loadimage);
                    }catch (Exception e){
                    }

                } else if ((mListItem.get(position).get_email().contains(".pdf"))
                        || (mListItem.get(position).get_email().contains(".txt"))
                        || (mListItem.get(position).get_email().contains(".docx"))
                        || (mListItem.get(position).get_email().contains(".doc"))) {

                    Dialog mDialog = new Dialog(mContext,R.style.Dialog);
                    mDialog.setContentView(R.layout.documentdialog);

                    mDialog.setTitle(""+mListItem.get(position).get_name());
                    WebView loaddoc = (WebView)mDialog.findViewById(R.id.loaddoc);

                    if (isTablet(mContext)) {
                        loaddoc.getLayoutParams().height = (Math.round(heightPixels / scaleFactor)-300);
                    } else {
                        loaddoc.getLayoutParams().height = (Math.round(heightPixels / scaleFactor));
                    }

                    loaddoc.requestLayout();
                    mDialog.show();

                    Uri uri = Uri.parse(mListItem.get(position).get_email().replaceAll(" ","%20"));
                    loaddoc.setWebViewClient(new Callback());
                    loaddoc.getSettings().setJavaScriptEnabled(true);
                    WebSettings webSettings = loaddoc.getSettings();
                    webSettings.setBuiltInZoomControls(true);
                    loaddoc.getSettings().setLoadWithOverviewMode(true);
                    loaddoc.getSettings().setUseWideViewPort(true);
                    loaddoc.loadUrl(
                            "http://docs.google.com/gview?embedded=true&url=" + uri);

                } else {
                    Dialog mDialog = new Dialog(mContext,R.style.Dialog);
                    mDialog.setContentView(R.layout.documentdialog);

                    mDialog.setTitle(""+mListItem.get(position).get_name());
                    WebView loaddoc = (WebView)mDialog.findViewById(R.id.loaddoc);
                    if (isTablet(mContext)) {
                        loaddoc.getLayoutParams().height = (Math.round(heightPixels / scaleFactor)-300);
                    } else {
                        loaddoc.getLayoutParams().height = (Math.round(heightPixels / scaleFactor));
                    }
                    loaddoc.requestLayout();
                    mDialog.show();

                    Uri uri = Uri.parse(mListItem.get(position).get_email().replaceAll(" ","%20"));
                    loaddoc.setWebViewClient(new Callback());
                    loaddoc.getSettings().setJavaScriptEnabled(true);
                    WebSettings webSettings = loaddoc.getSettings();
                    webSettings.setBuiltInZoomControls(true);
                    loaddoc.getSettings().setLoadWithOverviewMode(true);
                    loaddoc.getSettings().setUseWideViewPort(true);
                    loaddoc.loadUrl(
                            "http://docs.google.com/gview?embedded=true&url=" + uri);
                }
            }
        });

        mViewHolder.mDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                clickedPos = position;
                notifyDataSetChanged();
            }
        });

        if (clickedPos == position) {
            if(temp == true) {
                temp = false;
                mViewHolder.mDownload.setShowProgressBackground(false);
                mViewHolder.mDownload.setIndeterminate(true);
                new DownloadFileFromURL(mViewHolder.mDownload,
                        mListItem.get(position).get_name())
                        .execute(mListItem.get(position).get_email().replaceAll(" ","%20"));
            } else {
                CustomToast.info(mContext,"Please wait while we process your request").show();
            }
        }
    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }

    private void LoadSize(){
        DisplayMetrics metrics = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        widthPixels = metrics.widthPixels;
        heightPixels = metrics.heightPixels;
        scaleFactor = metrics.density;
    }

    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return(false);
        }
    }

    public boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        String extension = "";
        String mName;
        FloatingActionButton mFloatingActionButton;

        DownloadFileFromURL(FloatingActionButton mButton, String name){

            mFloatingActionButton = mButton;
            mName = name;
        }

        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return null;
                            }

                            public void checkClientTrusted(
                                    java.security.cert.X509Certificate[] certs, String authType) {
                            }

                            public void checkServerTrusted(
                                    java.security.cert.X509Certificate[] certs, String authType) {
                            }
                        }
                };

                try {
                    SSLContext sc = SSLContext.getInstance("SSL");
                    sc.init(null, trustAllCerts, new java.security.SecureRandom());
                    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                } catch (Exception e) {
                }
                try {
                    URL url = new URL(f_url[0]);
                    URLConnection connection = url.openConnection();
                    connection.connect();
                    int lengthOfFile = connection.getContentLength();

                    if (f_url[0].contains(".jpeg")) {
                        extension = ".jpeg";
                    } else if (f_url[0].contains(".jpg")) {
                        extension = ".jpg";
                    } else if (f_url[0].contains(".png")) {
                        extension = ".png";
                    } else if (f_url[0].contains(".pdf")) {
                        extension = ".pdf";
                    } else if (f_url[0].contains(".txt")) {
                        extension = ".txt";
                    } else if (f_url[0].contains(".docx")) {
                        extension = ".docx";
                    } else if (f_url[0].contains(".doc")) {
                        extension = ".doc";
                    }

                    // download the file
                    InputStream input = new BufferedInputStream(url.openStream(), 8192);
                    File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "Sponsor Master");
                    if (!mediaStorageDir.exists()) {
                        if (!mediaStorageDir.mkdirs()) {
                            Log.d("App", "failed to create directory");
                        }
                    }

                    // Output stream
                    OutputStream output = new FileOutputStream("/sdcard/Sponsor Master/" + mName + "" + extension);

                    byte data[] = new byte[1024];

                    long total = 0;

                    while ((count = input.read(data)) != -1) {
                        total += count;


                        // writing data to file
                        output.write(data, 0, count);
                    }

                    // flushing output
                    output.flush();

                    // closing streams
                    output.close();
                    input.close();

                } catch (Exception e) {
                    Log.e("Error: ", e.getMessage());
                }
            }catch (Exception e) {
                CustomToast.error(mContext,"File not found").show();
            }
            return null;
        }

        /**
         * After completing background task
         * Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {

            mFloatingActionButton.hideProgress();
            temp = true;
            CustomToast.success(mContext,"Download Complete").show();
//            ShowNotification("/sdcard/"+mName+""+extension);
            addNotification();
        }
    }
    private void addNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(mContext);
                        builder.setSmallIcon(R.mipmap.ic_launcher);
                        builder.setContentTitle("Notifications Example");
                        builder.setContentText("This is a test notification");

        Intent notificationIntent = new Intent(mContext, OtherProfileDocumentAdapter.class);
        PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

//    private void ShowNotification(String url){
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
//        builder.setSmallIcon(R.mipmap.ic_launcher);
//       // File pdfFile = new File("file://" + "/sdcard/download2.jpg","image");
//    }
}