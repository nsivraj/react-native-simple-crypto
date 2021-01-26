package com.pedrouid.crypto;

import android.os.AsyncTask;

import com.facebook.react.bridge.NoSuchKeyException;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.bridge.Promise;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class RNSCRsa extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNSCRsa(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNSCRsa";
  }

  private String getAlgorithmFromHash(final String hash) {
    if (hash.equals("Raw")) {
        return "NONEwithRSA";
    } else if (hash.equals("SHA1")) {
        return "SHA1withRSA";
    } else if (hash.equals("SHA224")) {
        return "SHA224withRSA";
    } else if (hash.equals("SHA256")) {
        return "SHA256withRSA";
    } else if (hash.equals("SHA384")) {
        return "SHA384withRSA";
    } else if (hash.equals("SHA512")) {
        return "SHA512withRSA";
    } else {
        return "SHA512withRSA";
    }
}

  @ReactMethod
  public void generate(final Promise promise) {
    this.generateKeys(2048, promise);
  }

  @ReactMethod
  public void generateKeys(final int keySize, final Promise promise) {
    AsyncTask.execute(new Runnable() {
      @Override
      public void run() {
        WritableNativeMap keys = new WritableNativeMap();

        try {
          RSA rsa = new RSA();
          rsa.generate(keySize);
          keys.putString("public", rsa.getPublicKey());
          keys.putString("private", rsa.getPrivateKey());
          promise.resolve(keys);
        } catch (NoSuchAlgorithmException e) {
          promise.reject("Error", e.getMessage());
        } catch (Exception e) {
          promise.reject("Error", e.getMessage());
        }
      }
    });
  }
  
  @ReactMethod
  public void generateKeysWithSeed(final int keySize, final String seedBase64, final Promise promise) {
    AsyncTask.execute(new Runnable() {
      @Override
      public void run() {
        WritableNativeMap keys = new WritableNativeMap();

        try {
          byte[] seedBytes = Base64.decode(seedBase64, Base64.DEFAULT);
          RSA rsa = new RSA();
          rsa.generateWithSeed(keySize, seedBytes);
          keys.putString("public", rsa.getPublicKey());
          keys.putString("private", rsa.getPrivateKey());
          promise.resolve(keys);
        } catch (NoSuchAlgorithmException e) {
          promise.reject("Error", e.getMessage());
        } catch (Exception e) {
          promise.reject("Error", e.getMessage());
        }
      }
    });
  }

  @ReactMethod
  public void encrypt(final String message, final String publicKeyString, final Promise promise) {
    AsyncTask.execute(new Runnable() {
      @Override
      public void run() {
        try {
          RSA rsa = new RSA();
          rsa.setPublicKey(publicKeyString);
          String encodedMessage = rsa.encrypt(message);
          promise.resolve(encodedMessage);
        } catch (Exception e) {
          promise.reject("Error", e.getMessage());
        }
      }
    });
  }

  @ReactMethod
  public void encrypt64(final String message, final String publicKeyString, final Promise promise) {
    AsyncTask.execute(new Runnable() {
      @Override
      public void run() {
        try {
          RSA rsa = new RSA();
          rsa.setPublicKey(publicKeyString);
          String encodedMessage = rsa.encrypt64(message);
          promise.resolve(encodedMessage);
        } catch (Exception e) {
          promise.reject("Error", e.getMessage());
        }
      }
    });
  }

  @ReactMethod
  public void decrypt(final String encodedMessage, final String privateKeyString, final Promise promise) {
    AsyncTask.execute(new Runnable() {
      @Override
      public void run() {
        try {
          RSA rsa = new RSA();
          rsa.setPrivateKey(privateKeyString);
          String message = rsa.decrypt(encodedMessage);
          promise.resolve(message);

        } catch (Exception e) {
          promise.reject("Error", e.getMessage());
        }
      }
    });
  }

  @ReactMethod
  public void decrypt64(final String encodedMessage, final String privateKeyString, final Promise promise) {
    AsyncTask.execute(new Runnable() {
      @Override
      public void run() {
        try {
          RSA rsa = new RSA();
          rsa.setPrivateKey(privateKeyString);
          String message = rsa.decrypt64(encodedMessage);
          promise.resolve(message);

        } catch (Exception e) {
          promise.reject("Error", e.getMessage());
        }
      }
    });
  }

  @ReactMethod
  public void sign(final String message, final String privateKeyString, final String hash, final Promise promise) {
    AsyncTask.execute(new Runnable() {
      @Override
      public void run() {
        try {
          RSA rsa = new RSA();
          rsa.setPrivateKey(privateKeyString);
          String signature = rsa.sign(message, getAlgorithmFromHash(hash));
          promise.resolve(signature);

        } catch (Exception e) {
          promise.reject("Error", e.getMessage());
        }
      }
    });
  }

  @ReactMethod
  public void sign64(final String message, final String privateKeyString, final String hash, final Promise promise) {
    AsyncTask.execute(new Runnable() {
      @Override
      public void run() {
        try {
          RSA rsa = new RSA();
          rsa.setPrivateKey(privateKeyString);
          String signature = rsa.sign64(message, getAlgorithmFromHash(hash));
          promise.resolve(signature);

        } catch (Exception e) {
          promise.reject("Error", e.getMessage());
        }
      }
    });
  }

  @ReactMethod
  public void verify(final String signature, final String message, final String publicKeyString, final String hash,
      final Promise promise) {
    AsyncTask.execute(new Runnable() {
      @Override
      public void run() {
        try {
          RSA rsa = new RSA();
          rsa.setPublicKey(publicKeyString);
          boolean verified = rsa.verify(signature, message, getAlgorithmFromHash(hash));
          promise.resolve(verified);

        } catch (Exception e) {
          promise.reject("Error", e.getMessage());
        }
      }
    });
  }

  @ReactMethod
  public void verify64(final String signature, final String message, final String publicKeyString, final String hash,
      final Promise promise) {
    AsyncTask.execute(new Runnable() {
      @Override
      public void run() {
        try {
          RSA rsa = new RSA();
          rsa.setPublicKey(publicKeyString);
          boolean verified = rsa.verify64(signature, message, getAlgorithmFromHash(hash));
          promise.resolve(verified);

        } catch (Exception e) {
          promise.reject("Error", e.getMessage());
        }
      }
    });
  }
}
