package org.icegeneral.rrcallbak;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import org.icegeneral.rrcallbak.rxjava.ActivityLifecycle;

import java.util.ArrayList;
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
        List<SubscriberWrapper> removeList = new ArrayList<>();
        for (SubscriberWrapper wrapper : subscribers) {
            if (wrapper.unsubscribeOn == ActivityLifecycle.OnDestroy) {
                wrapper.subscriber.unsubscribe();
                removeList.add(wrapper);
            }
        }
        subscribers.removeAll(removeList);
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        List<SubscriberWrapper> removeList = new ArrayList<>();
        for (SubscriberWrapper wrapper : subscribers) {
            if (wrapper.unsubscribeOn == ActivityLifecycle.OnStop) {
                wrapper.subscriber.unsubscribe();
                removeList.add(wrapper);
            }
        }
        subscribers.removeAll(removeList);
        super.onStop();
    }


    private List<SubscriberWrapper> subscribers;

    public void addSubscriber(Subscriber subscriber, ActivityLifecycle unsubscribeOn) {
        if (subscribers == null) {
            subscribers = new ArrayList<>();
        }
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
