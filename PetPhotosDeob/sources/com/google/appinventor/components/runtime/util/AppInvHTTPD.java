package com.google.appinventor.components.runtime.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.google.appinventor.components.common.YaVersion;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.PhoneStatus;
import com.google.appinventor.components.runtime.ReplForm;
import com.google.appinventor.components.runtime.util.NanoHTTPD;
import gnu.expr.Language;
import gnu.expr.ModuleExp;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.List;
import java.util.Properties;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import kawa.standard.Scheme;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class AppInvHTTPD extends NanoHTTPD {
    private static final String LOG_TAG = "AppInvHTTPD";
    private static final String MIME_JSON = "application/json";
    private static final int YAV_SKEW_BACKWARD = 4;
    private static final int YAV_SKEW_FORWARD = 1;
    private static byte[] hmacKey;
    private static int seq;
    private final Handler androidUIHandler;
    private ReplForm form;
    private File rootDir;
    private Language scheme;
    private boolean secure;

    public AppInvHTTPD(int port, File wwwroot, boolean secure, ReplForm form) throws IOException {
        super(port, wwwroot);
        this.androidUIHandler = new Handler();
        this.rootDir = wwwroot;
        this.scheme = Scheme.getInstance("scheme");
        this.form = form;
        this.secure = secure;
        ModuleExp.mustNeverCompile();
    }

    @Override // com.google.appinventor.components.runtime.util.NanoHTTPD
    public NanoHTTPD.Response serve(String uri, String method, Properties header, Properties parms, Properties files, Socket mySocket) {
        NanoHTTPD.Response res;
        String installer;
        NanoHTTPD.Response res2;
        Log.d(LOG_TAG, method + " '" + uri + "' ");
        if (this.secure) {
            InetAddress myAddress = mySocket.getInetAddress();
            String hostAddress = myAddress.getHostAddress();
            if (!hostAddress.equals("127.0.0.1")) {
                Log.d(LOG_TAG, "Debug: hostAddress = " + hostAddress + " while in secure mode, closing connection.");
                NanoHTTPD.Response res3 = new NanoHTTPD.Response(NanoHTTPD.HTTP_OK, MIME_JSON, "{\"status\" : \"BAD\", \"message\" : \"Security Error: Invalid Source Location " + hostAddress + "\"}");
                res3.addHeader("Access-Control-Allow-Origin", "*");
                res3.addHeader("Access-Control-Allow-Headers", "origin, content-type");
                res3.addHeader("Access-Control-Allow-Methods", "POST,OPTIONS,GET,HEAD,PUT");
                res3.addHeader("Allow", "POST,OPTIONS,GET,HEAD,PUT");
                return res3;
            }
        }
        if (method.equals("OPTIONS")) {
            Enumeration e = header.propertyNames();
            while (e.hasMoreElements()) {
                String value = (String) e.nextElement();
                Log.d(LOG_TAG, "  HDR: '" + value + "' = '" + header.getProperty(value) + "'");
            }
            NanoHTTPD.Response res4 = new NanoHTTPD.Response(NanoHTTPD.HTTP_OK, NanoHTTPD.MIME_PLAINTEXT, "OK");
            res4.addHeader("Access-Control-Allow-Origin", "*");
            res4.addHeader("Access-Control-Allow-Headers", "origin, content-type");
            res4.addHeader("Access-Control-Allow-Methods", "POST,OPTIONS,GET,HEAD,PUT");
            res4.addHeader("Allow", "POST,OPTIONS,GET,HEAD,PUT");
            return res4;
        } else if (uri.equals("/_newblocks")) {
            adoptMainThreadClassLoader();
            String inSeq = parms.getProperty("seq", Component.TYPEFACE_DEFAULT);
            int iseq = Integer.parseInt(inSeq);
            String blockid = parms.getProperty("blockid");
            String code = parms.getProperty("code");
            String inMac = parms.getProperty("mac", "no key provided");
            if (hmacKey != null) {
                try {
                    Mac hmacSha1 = Mac.getInstance("HmacSHA1");
                    SecretKeySpec key = new SecretKeySpec(hmacKey, "RAW");
                    hmacSha1.init(key);
                    byte[] tmpMac = hmacSha1.doFinal((code + inSeq + blockid).getBytes());
                    StringBuffer sb = new StringBuffer(tmpMac.length * 2);
                    Formatter formatter = new Formatter(sb);
                    for (byte b : tmpMac) {
                        formatter.format("%02x", Byte.valueOf(b));
                    }
                    String compMac = sb.toString();
                    Log.d(LOG_TAG, "Incoming Mac = " + inMac);
                    Log.d(LOG_TAG, "Computed Mac = " + compMac);
                    Log.d(LOG_TAG, "Incoming seq = " + inSeq);
                    Log.d(LOG_TAG, "Computed seq = " + seq);
                    Log.d(LOG_TAG, "blockid = " + blockid);
                    if (!inMac.equals(compMac)) {
                        Log.e(LOG_TAG, "Hmac does not match");
                        this.form.dispatchErrorOccurredEvent(this.form, LOG_TAG, ErrorMessages.ERROR_REPL_SECURITY_ERROR, "Invalid HMAC");
                        NanoHTTPD.Response res5 = new NanoHTTPD.Response(NanoHTTPD.HTTP_OK, MIME_JSON, "{\"status\" : \"BAD\", \"message\" : \"Security Error: Invalid MAC\"}");
                        return res5;
                    } else if (seq != iseq && seq != iseq + 1) {
                        Log.e(LOG_TAG, "Seq does not match");
                        this.form.dispatchErrorOccurredEvent(this.form, LOG_TAG, ErrorMessages.ERROR_REPL_SECURITY_ERROR, "Invalid Seq");
                        NanoHTTPD.Response res6 = new NanoHTTPD.Response(NanoHTTPD.HTTP_OK, MIME_JSON, "{\"status\" : \"BAD\", \"message\" : \"Security Error: Invalid Seq\"}");
                        return res6;
                    } else {
                        if (seq == iseq + 1) {
                            Log.e(LOG_TAG, "Seq Fixup Invoked");
                        }
                        seq = iseq + 1;
                        String code2 = "(begin (require <com.google.youngandroid.runtime>) (process-repl-input " + blockid + " (begin " + code + " )))";
                        Log.d(LOG_TAG, "To Eval: " + code2);
                        try {
                            if (code.equals("#f")) {
                                Log.e(LOG_TAG, "Skipping evaluation of #f");
                            } else {
                                this.scheme.eval(code2);
                            }
                            res2 = new NanoHTTPD.Response(NanoHTTPD.HTTP_OK, MIME_JSON, RetValManager.fetch(false));
                        } catch (Throwable ex) {
                            Log.e(LOG_TAG, "newblocks: Scheme Failure", ex);
                            RetValManager.appendReturnValue(blockid, "BAD", ex.toString());
                            res2 = new NanoHTTPD.Response(NanoHTTPD.HTTP_OK, MIME_JSON, RetValManager.fetch(false));
                        }
                        res2.addHeader("Access-Control-Allow-Origin", "*");
                        res2.addHeader("Access-Control-Allow-Headers", "origin, content-type");
                        res2.addHeader("Access-Control-Allow-Methods", "POST,OPTIONS,GET,HEAD,PUT");
                        res2.addHeader("Allow", "POST,OPTIONS,GET,HEAD,PUT");
                        return res2;
                    }
                } catch (Exception e2) {
                    Log.e(LOG_TAG, "Error working with hmac", e2);
                    this.form.dispatchErrorOccurredEvent(this.form, LOG_TAG, ErrorMessages.ERROR_REPL_SECURITY_ERROR, "Exception working on HMAC");
                    NanoHTTPD.Response res7 = new NanoHTTPD.Response(NanoHTTPD.HTTP_OK, NanoHTTPD.MIME_PLAINTEXT, "NOT");
                    return res7;
                }
            }
            Log.e(LOG_TAG, "No HMAC Key");
            this.form.dispatchErrorOccurredEvent(this.form, LOG_TAG, ErrorMessages.ERROR_REPL_SECURITY_ERROR, "No HMAC Key");
            NanoHTTPD.Response res8 = new NanoHTTPD.Response(NanoHTTPD.HTTP_OK, MIME_JSON, "{\"status\" : \"BAD\", \"message\" : \"Security Error: No HMAC Key\"}");
            return res8;
        } else if (uri.equals("/_values")) {
            NanoHTTPD.Response res9 = new NanoHTTPD.Response(NanoHTTPD.HTTP_OK, MIME_JSON, RetValManager.fetch(true));
            res9.addHeader("Access-Control-Allow-Origin", "*");
            res9.addHeader("Access-Control-Allow-Headers", "origin, content-type");
            res9.addHeader("Access-Control-Allow-Methods", "POST,OPTIONS,GET,HEAD,PUT");
            res9.addHeader("Allow", "POST,OPTIONS,GET,HEAD,PUT");
            return res9;
        } else if (uri.equals("/_getversion")) {
            try {
                String packageName = this.form.getPackageName();
                PackageInfo pInfo = this.form.getPackageManager().getPackageInfo(packageName, 0);
                if (SdkLevel.getLevel() >= 5) {
                    installer = EclairUtil.getInstallerPackageName(YaVersion.ACCEPTABLE_COMPANION_PACKAGE, this.form);
                } else {
                    installer = "Not Known";
                }
                String versionName = pInfo.versionName;
                if (installer == null) {
                    installer = "Not Known";
                }
                res = new NanoHTTPD.Response(NanoHTTPD.HTTP_OK, MIME_JSON, "{\"version\" : \"" + versionName + "\", \"fingerprint\" : \"" + Build.FINGERPRINT + "\", \"installer\" : \"" + installer + "\", \"package\" : \"" + packageName + "\", \"fqcn\" : true }");
            } catch (PackageManager.NameNotFoundException n) {
                n.printStackTrace();
                res = new NanoHTTPD.Response(NanoHTTPD.HTTP_OK, MIME_JSON, "{\"verison\" : \"Unknown\"");
            }
            res.addHeader("Access-Control-Allow-Origin", "*");
            res.addHeader("Access-Control-Allow-Headers", "origin, content-type");
            res.addHeader("Access-Control-Allow-Methods", "POST,OPTIONS,GET,HEAD,PUT");
            res.addHeader("Allow", "POST,OPTIONS,GET,HEAD,PUT");
            if (this.secure) {
                seq = 1;
                this.androidUIHandler.post(new Runnable() { // from class: com.google.appinventor.components.runtime.util.AppInvHTTPD.1
                    @Override // java.lang.Runnable
                    public void run() {
                        AppInvHTTPD.this.form.clear();
                    }
                });
                return res;
            }
            return res;
        } else if (uri.equals("/_extensions")) {
            NanoHTTPD.Response res10 = processLoadExtensionsRequest(parms);
            return res10;
        } else if (uri.equals("/_proxy")) {
            String popup = PhoneStatus.getPopup();
            NanoHTTPD.Response res11 = new NanoHTTPD.Response(NanoHTTPD.HTTP_OK, NanoHTTPD.MIME_HTML, popup);
            res11.addHeader("Access-Control-Allow-Origin", "*");
            res11.addHeader("Access-Control-Allow-Headers", "origin, content-type");
            res11.addHeader("Access-Control-Allow-Methods", "POST,OPTIONS,GET,HEAD,PUT");
            res11.addHeader("Allow", "POST,OPTIONS,GET,HEAD,PUT");
            return res11;
        } else if (method.equals("PUT")) {
            Boolean error = false;
            String tmpFileName = files.getProperty("content", null);
            if (tmpFileName != null) {
                File fileFrom = new File(tmpFileName);
                String filename = parms.getProperty("filename", null);
                if (filename != null && (filename.startsWith("..") || filename.endsWith("..") || filename.indexOf("../") >= 0)) {
                    Log.d(LOG_TAG, " Ignoring invalid filename: " + filename);
                    filename = null;
                }
                if (filename != null) {
                    File fileTo = new File(this.rootDir + "/" + filename);
                    File parentFileTo = fileTo.getParentFile();
                    if (!parentFileTo.exists()) {
                        parentFileTo.mkdirs();
                    }
                    if (!fileFrom.renameTo(fileTo)) {
                        error = Boolean.valueOf(copyFile(fileFrom, fileTo));
                        fileFrom.delete();
                    }
                } else {
                    fileFrom.delete();
                    Log.e(LOG_TAG, "Received content without a file name!");
                    error = true;
                }
            } else {
                Log.e(LOG_TAG, "Received PUT without content.");
                error = true;
            }
            if (error.booleanValue()) {
                NanoHTTPD.Response res12 = new NanoHTTPD.Response(NanoHTTPD.HTTP_INTERNALERROR, NanoHTTPD.MIME_PLAINTEXT, "NOTOK");
                res12.addHeader("Access-Control-Allow-Origin", "*");
                res12.addHeader("Access-Control-Allow-Headers", "origin, content-type");
                res12.addHeader("Access-Control-Allow-Methods", "POST,OPTIONS,GET,HEAD,PUT");
                res12.addHeader("Allow", "POST,OPTIONS,GET,HEAD,PUT");
                return res12;
            }
            NanoHTTPD.Response res13 = new NanoHTTPD.Response(NanoHTTPD.HTTP_OK, NanoHTTPD.MIME_PLAINTEXT, "OK");
            res13.addHeader("Access-Control-Allow-Origin", "*");
            res13.addHeader("Access-Control-Allow-Headers", "origin, content-type");
            res13.addHeader("Access-Control-Allow-Methods", "POST,OPTIONS,GET,HEAD,PUT");
            res13.addHeader("Allow", "POST,OPTIONS,GET,HEAD,PUT");
            return res13;
        } else {
            NanoHTTPD.Response res14 = serveFile(uri, header, this.rootDir, true);
            return res14;
        }
    }

    private boolean copyFile(File infile, File outfile) {
        try {
            FileInputStream in = new FileInputStream(infile);
            FileOutputStream out = new FileOutputStream(outfile);
            byte[] buffer = new byte[32768];
            while (true) {
                int len = in.read(buffer);
                if (len > 0) {
                    out.write(buffer, 0, len);
                } else {
                    in.close();
                    out.close();
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
    }

    private NanoHTTPD.Response processLoadExtensionsRequest(Properties parms) {
        try {
            JSONArray array = new JSONArray(parms.getProperty("extensions", "[]"));
            List<String> extensionsToLoad = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                String extensionName = array.optString(i);
                if (extensionName != null) {
                    extensionsToLoad.add(extensionName);
                } else {
                    return error("Invalid JSON content at index " + i);
                }
            }
            try {
                this.form.loadComponents(extensionsToLoad);
                return message("OK");
            } catch (Exception e) {
                return error(e);
            }
        } catch (JSONException e2) {
            return error(e2);
        }
    }

    private void adoptMainThreadClassLoader() {
        ClassLoader mainClassLoader = Looper.getMainLooper().getThread().getContextClassLoader();
        Thread myThread = Thread.currentThread();
        if (myThread.getContextClassLoader() != mainClassLoader) {
            myThread.setContextClassLoader(mainClassLoader);
        }
    }

    private NanoHTTPD.Response message(String txt) {
        return addHeaders(new NanoHTTPD.Response(NanoHTTPD.HTTP_OK, NanoHTTPD.MIME_PLAINTEXT, txt));
    }

    private NanoHTTPD.Response json(String json) {
        return addHeaders(new NanoHTTPD.Response(NanoHTTPD.HTTP_OK, MIME_JSON, json));
    }

    private NanoHTTPD.Response error(String msg) {
        JSONObject result = new JSONObject();
        try {
            result.put(NotificationCompat.CATEGORY_STATUS, "BAD");
            result.put("message", msg);
        } catch (JSONException e) {
            Log.wtf(LOG_TAG, "Unable to write basic JSON content", e);
        }
        return addHeaders(new NanoHTTPD.Response(NanoHTTPD.HTTP_OK, MIME_JSON, result.toString()));
    }

    private NanoHTTPD.Response error(Throwable t) {
        return error(t.toString());
    }

    private NanoHTTPD.Response addHeaders(NanoHTTPD.Response res) {
        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Headers", "origin, content-type");
        res.addHeader("Access-Control-Allow-Methods", "POST,OPTIONS,GET,HEAD,PUT");
        res.addHeader("Allow", "POST,OPTIONS,GET,HEAD,PUT");
        return res;
    }

    public static void setHmacKey(String inputKey) {
        hmacKey = inputKey.getBytes();
        seq = 1;
    }

    public void resetSeq() {
        seq = 1;
    }
}
