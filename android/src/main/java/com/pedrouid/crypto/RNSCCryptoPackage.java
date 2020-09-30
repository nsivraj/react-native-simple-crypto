package com.pedrouid.crypto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

public class RNSCCryptoPackage implements ReactPackage {
    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        return Arrays.<NativeModule>asList(
                new RNSCAes(reactContext),
                new RNSCSha(reactContext),
                new RNSCHmac(reactContext),
                new RNSCPbkdf2(reactContext),
                new RNSCRsa(reactContext),
                new RNSCRandomBytes(reactContext)
        );
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Arrays.<ViewManager>asList();
    }

    // Deprecated from RN 0.47.0
    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }
}
