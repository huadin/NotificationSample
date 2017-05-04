package com.snow.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
  private static final String TAG = "MainActivity";
  private RadioGroup rg;
  private Button mBtn4;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    findViewById(R.id.btn1).setOnClickListener(this);
    findViewById(R.id.btn2).setOnClickListener(this);
    findViewById(R.id.btn3).setOnClickListener(this);

    mBtn4 = (Button) findViewById(R.id.btn4);
    mBtn4.setOnClickListener(this);


    rg = (RadioGroup) findViewById(R.id.rg);
  }

  @Override
  public void onClick(View v)
  {
    switch (v.getId())
    {
      case R.id.btn1:
        baseNotify();
        break;
      case R.id.btn2:
        collapsed();
        break;
      case R.id.btn3:
        headsup();
        break;
      case R.id.btn4:
        mBtn4.postDelayed(new Runnable()
        {
          @Override
          public void run()
          {
            grade();
          }
        }, 5000);

        break;
    }
  }

  private void grade()
  {
    Log.i(TAG, "grade: ");

    NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
    builder.setSmallIcon(R.mipmap.ic_launcher_round)
            .setAutoCancel(true)
            .setCategory(Notification.CATEGORY_EMAIL)
            .setDefaults(Notification.DEFAULT_ALL)
            .setContentTitle("等级通知");
    switch (rg.getCheckedRadioButtonId())
    {
      case R.id.rb1:
        builder.setContentText("Public")
                .setVisibility(Notification.VISIBILITY_PUBLIC);
        break;
      case R.id.rb2:
        builder.setContentText("Private")
                .setVisibility(Notification.VISIBILITY_PRIVATE);
        break;
      case R.id.rb3:
        builder.setContentText("Secret")
                .setVisibility(Notification.VISIBILITY_SECRET);
        break;
    }

    Notification notify = builder.setColor(Color.parseColor("#D86758")).build();
    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    manager.notify(0, notify);
  }

  private void headsup()
  {

    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.baidu.com"));
    PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

    NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
    Notification notify = builder.setSmallIcon(R.mipmap.ic_launcher_round)
            .setPriority(Notification.PRIORITY_DEFAULT)  //通知的优先级
            .setCategory(Notification.CATEGORY_MESSAGE)  //通知的类型
            .setContentTitle("通知")
            .setAutoCancel(true)
            .setContentIntent(pi)
            .setContentText("Heads - Up Notification on Android 5.0")
            .setFullScreenIntent(pi, true)              //不设置此项不会悬挂，true,false 不会出现悬挂
            .build();

    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    manager.notify(0, notify);
  }

  private void collapsed()
  {
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.sina.com"));

    PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);

    RemoteViews collapsed = new RemoteViews(getPackageName(), R.layout.collapsed);
    collapsed.setTextViewText(R.id.collapsed_text, "关闭状态");

    RemoteViews show = new RemoteViews(getPackageName(), R.layout.show);


    Notification notify = builder.setAutoCancel(true)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
            .setContentIntent(pi)
            .setContentText("新浪微博")
            .setCustomContentView(collapsed)
            .setCustomBigContentView(show)
            .build();

    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    manager.notify(0, notify);

  }


  private void baseNotify()
  {
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.baidu.com"));
    PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
    Notification notify = builder.setAutoCancel(true)
            .setContentTitle("百度")
//                .setSubText("这是百度网址")
            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
            .setContentIntent(pi)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentText("可以打开百度")
            .setDefaults(Notification.DEFAULT_ALL)
            .build();

    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    manager.notify(0, notify);
  }
}
