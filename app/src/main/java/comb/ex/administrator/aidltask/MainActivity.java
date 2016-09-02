package comb.ex.administrator.aidltask;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity {

    private String info = null;
    private MyAIDL aidl = null;

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            aidl = MyAIDL.Stub.asInterface(service);
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private NotificationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent("com.ex.service");
        bindService(intent,conn, Service.BIND_AUTO_CREATE);
        new Button(getApplicationContext()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aidl != null){
                    try {
                        aidl.ok("服务器，你好！");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
    class MainReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Intent intent1 = new Intent("com.ex.service");
            intent1.putExtra("title","");
            info = intent.getStringExtra("info");
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setAutoCancel(true);
            RemoteViews views = new RemoteViews(getPackageName(), R.layout.activity_main);
            views.setTextViewText(R.id.textview,info);
            views.setOnClickPendingIntent(R.id.textview,PendingIntent.getService(getApplicationContext()
                    ,1,intent1,PendingIntent.FLAG_UPDATE_CURRENT));
            builder.setContent(views);
            manager.notify(1,builder.build());

        }
    }
}
