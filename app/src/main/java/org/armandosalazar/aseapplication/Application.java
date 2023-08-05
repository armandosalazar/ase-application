package org.armandosalazar.aseapplication;

import android.content.Context;

public class Application extends android.app.Application {
    private static Application instance;

    public Application() {
        instance = this;
    }

    public static Application getInstance() {
        return instance;
    }

}
