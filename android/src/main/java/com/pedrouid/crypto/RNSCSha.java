package com.pedrouid.crypto;

import android.widget.Toast;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.UUID;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.InvalidKeyException;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.Mac;

import org.spongycastle.crypto.ExtendedDigest;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.digests.SHA224Digest;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.digests.SHA384Digest;
import org.spongycastle.crypto.digests.SHA512Digest;
import org.spongycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.spongycastle.crypto.PBEParametersGenerator;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.util.encoders.Hex;

import android.util.Base64;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class RNSCSha extends ReactContextBaseJavaModule {

    public RNSCSha(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    private static ArrayList<String> algorithms = new ArrayList<String>(
            Arrays.asList("SHA-1",
                    "SHA-256",
                    "SHA-512"));

    @Override
    public String getName() {
        return "RNSCSha";
    }

    private byte[] sha(byte[] data, String algorithm) throws Exception {
        if (!algorithms.contains(algorithm)) {
            throw new Exception("Invalid algorithm");
        }

        MessageDigest md = MessageDigest.getInstance(algorithm);
        md.update(data);
        return md.digest();
    }

    @ReactMethod
    public void shaBase64(String data, String algorithm, Promise promise) throws Exception {
        try {
            byte[] digest = this.sha(Base64.decode(data, Base64.NO_WRAP), algorithm);
            promise.resolve(Base64.encodeToString(digest, Base64.NO_WRAP));
        } catch (Exception e) {
            promise.reject("-1", e.getMessage());
        }
    }

    @ReactMethod
    public void shaUtf8(String data, String algorithm, Promise promise) throws Exception {
        try {
            byte[] digest = this.sha(data.getBytes(), algorithm);
            promise.resolve(Util.bytesToHex(digest));
        } catch (Exception e) {
            promise.reject("-1", e.getMessage());
        }
    }
}
