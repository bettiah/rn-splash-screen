package com.mehcode.reactnative.splashscreen;

import android.app.Activity;
import android.app.Dialog;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

class SplashScreen extends ReactContextBaseJavaModule implements LifecycleEventListener {
    Dialog mSplashDialog;
    private boolean splashed;

    public SplashScreen(ReactApplicationContext reactContext) {
        super(reactContext);

        reactContext.addLifecycleEventListener(this);
    }

    @Override
    public void onHostResume() {
        if (splashed) return;
        splashed = true;
        show();
    }

    @Override
    public void onHostPause() {
    }

    @Override
    public void onHostDestroy() {
    }

    @Override
    public String getName() {
        return "SplashScreen";
    }

    void show() {
        splashed = true;
        if (mSplashDialog != null && mSplashDialog.isShowing()) {
            // Splash screen is open
            return;
        }

        final Activity activity = getCurrentActivity();
        if (activity == null) {
            return;
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mSplashDialog == null) {
                    mSplashDialog = new Dialog(activity, R.style.SplashTheme);
                    mSplashDialog.setCancelable(false);
                }

                if (!activity.isFinishing()) {
                    mSplashDialog.show();
                }
            }
        });
    }

    /**
     * Close the active splash screen.
     */
    @ReactMethod
    public void hide() {
        if (mSplashDialog == null || !mSplashDialog.isShowing()) {
            // Not showing splash screen
            return;
        }

        final Activity activity = getCurrentActivity();
        if (activity == null) {
            return;
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSplashDialog.dismiss();
            }
        });
    }
}
