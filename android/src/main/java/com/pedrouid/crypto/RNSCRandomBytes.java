package com.pedrouid.crypto;

import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.Callback;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactContextBaseJavaModule;

import java.security.SecureRandom;
import java.util.Map;
import java.util.HashMap;

import android.util.Base64;

class RNSCRandomBytes extends ReactContextBaseJavaModule {
  private static final String SEED_KEY = "seed";

  public RNSCRandomBytes(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  public String getName() {
    return "RNSCRandomBytes";
  }

  @ReactMethod
  public void randomBytes(int size, Promise promise) {
    try {
      promise.resolve(getRandomBytes(size));
    } catch (Exception e) {
      promise.reject("-1", e.getMessage());
    }
  }

  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();
    constants.put(SEED_KEY, getRandomBytes(4096));
    return constants;
  }

  private String getRandomBytes(int size) {
    SecureRandom sr = new SecureRandom();
    byte[] output = new byte[size];
    sr.nextBytes(output);
    return Base64.encodeToString(output, Base64.NO_WRAP);
  }
}
