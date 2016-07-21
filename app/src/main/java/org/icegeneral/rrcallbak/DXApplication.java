package org.icegeneral.rrcallbak;

import android.app.Application;

import org.icegeneral.rrcallbak.retrofit.DX168API;

/**
 * Created by iceGeneral on 16/7/20.
 */

public class DXApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DX168API.init(true);
    }
}
