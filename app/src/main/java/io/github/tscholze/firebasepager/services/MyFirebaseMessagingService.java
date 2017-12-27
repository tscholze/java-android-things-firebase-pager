package io.github.tscholze.firebasepager.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * The service handles incoming Firebase Messaging notifications by
 * broadcasting an intent with the contained information.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService
{
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    /**
     * Constant string that identifies a "new message income" intent.
     */
    public static final String NEW_MESSAGE_ITENT = "newMessage";

    /**
     * Constant string that identifies the body extra part of a NEW_MESSAGE_ITENT.
     */
    public static final String EXTRA_BODY = "body";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        String body = remoteMessage.getNotification().getBody();

        Intent intent = new Intent(NEW_MESSAGE_ITENT);
        intent.putExtra(EXTRA_BODY, body);
        sendBroadcast(intent);
    }
}
