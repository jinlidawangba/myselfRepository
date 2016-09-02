package comb.ex.administrator.aidltask;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Administrator on 16-8-25.
 */
public class MyService extends Service {

    private IBinder ibinder = new MyAIDL.Stub() {

        @Override
        public void ok(String info) throws RemoteException {
            Intent intent1 = new Intent("com.ex.main");
            intent1.putExtra("info",info);
            sendBroadcast(intent1);
        }

        @Override
        public void erro() throws RemoteException {

        }
    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return ibinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String title = intent.getStringExtra("title");
        Log.d("========","============"+title);
        return super.onStartCommand(intent, flags, startId);
    }
}
