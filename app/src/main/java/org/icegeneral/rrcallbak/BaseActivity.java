package org.icegeneral.rrcallbak;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import org.icegeneral.rrcallbak.rxjava.ActivityLifecycle;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by iceGeneral on 16/7/20.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onDestroy() {
        Iterator<SubscriberWrapper> it = subscribers.iterator();
        while (it.hasNext()) {
            SubscriberWrapper subscriberWrapper = it.next();
            if (subscriberWrapper.unsubscribeOn == ActivityLifecycle.OnDestroy) {
                subscriberWrapper.subscriber.unsubscribe();
                it.remove();
            }
        }
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Iterator<SubscriberWrapper> it = subscribers.iterator();
        while (it.hasNext()) {
            SubscriberWrapper subscriberWrapper = it.next();
            if (subscriberWrapper.unsubscribeOn == ActivityLifecycle.OnStop) {
                subscriberWrapper.subscriber.unsubscribe();
                it.remove();
            }
        }
        super.onStop();
    }


    private List<SubscriberWrapper> subscribers = new LinkedList<>();

    public void addSubscriber(Subscriber subscriber, ActivityLifecycle unsubscribeOn) {
        subscribers.add(new SubscriberWrapper(subscriber, unsubscribeOn));
    }

    private class SubscriberWrapper {
        Subscriber subscriber;
        ActivityLifecycle unsubscribeOn;

        public SubscriberWrapper(Subscriber subscriber, ActivityLifecycle unsubscribeOn) {
            this.subscriber = subscriber;
            this.unsubscribeOn = unsubscribeOn;
        }
    }
}
