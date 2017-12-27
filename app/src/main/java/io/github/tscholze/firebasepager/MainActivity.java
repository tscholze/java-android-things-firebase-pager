package io.github.tscholze.firebasepager;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import android.os.Handler;
import android.util.Log;

import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * This activity is responsible to display messages from Firebase
 * Cloud Messaging notifications on an alphanumerical display.
 */
public class MainActivity extends Activity implements IRunningTextContext
{
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String TOPIC_IDENTIFIER = "messages";
    private static final String I2C1_BUS_IDENTIFIER = "I2C1";

    private AlphanumericDisplay alphanumericDisplay;
    private List<String> messages = new LinkedList<>();
    private Handler runningTextHandler;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            messages.add(intent.getStringExtra(MyFirebaseMessagingService.EXTRA_BODY));

            if (messages.size() == 1)
            {
                postTextToDisplay();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC_IDENTIFIER);
        Log.d(TAG, "FCM token: " + FirebaseInstanceId.getInstance().getToken());

        setupAlphanumericDisplay();
        runningTextHandler = new Handler();
        IntentFilter intentFilter = new IntentFilter(MyFirebaseMessagingService.NEW_MESSAGE_ITENT);
        registerReceiver(broadcastReceiver, intentFilter);

        messages.add("Teeeeeest");
        postTextToDisplay();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        destroyAlphanumericDisplay();
        FirebaseMessaging.getInstance().unsubscribeFromTopic(TOPIC_IDENTIFIER);
    }

    @Override
    public AlphanumericDisplay getAlphaNumericDisplay()
    {
        return alphanumericDisplay;
    }

    @Override
    public Handler getRunningTextHandler()
    {
        return runningTextHandler;
    }

    @Override
    public void onRunningTextFinished()
    {
        Log.d(TAG, "Running text finished");
        postTextToDisplay();
    }

    private void setupAlphanumericDisplay()
    {
        try
        {
            alphanumericDisplay = new AlphanumericDisplay(I2C1_BUS_IDENTIFIER);
            alphanumericDisplay.setBrightness(1.0f);
            alphanumericDisplay.setEnabled(true);
            alphanumericDisplay.clear();
        }
        catch (IOException e)
        {
            Log.e(TAG, "Error configuring display", e);
        }
    }

    private void destroyAlphanumericDisplay()
    {
        if (alphanumericDisplay != null)
        {
            Log.i(TAG, "Closing display");
            try
            {
                alphanumericDisplay.close();
            }
            catch (IOException e)
            {
                Log.e(TAG, "Error closing display", e);
            }
            finally
            {
                alphanumericDisplay = null;
            }
        }
    }

    private void postTextToDisplay()
    {
        if (messages.isEmpty())
        {
            return;
        }

        String message = messages.get(0);
        Log.d(TAG, "Will post new message: "+ message);

        RunningTextRunnable runnable = new RunningTextRunnable(this, message);
        messages.remove(0);
        runningTextHandler.post(runnable);
    }
}