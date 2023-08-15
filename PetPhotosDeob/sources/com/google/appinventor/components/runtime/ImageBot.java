package com.google.appinventor.components.runtime;

import android.graphics.Bitmap;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesLibraries;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.errors.StopBlocksExecution;
import com.google.appinventor.components.runtime.imagebot.ImageBotToken;
import com.google.appinventor.components.runtime.util.AsynchUtil;
import com.google.appinventor.components.runtime.util.Base58Util;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.FileUtil;
import com.google.appinventor.components.runtime.util.IOUtils;
import com.google.appinventor.components.runtime.util.MediaUtil;
import com.google.protobuf.ByteString;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import kawa.lang.SyntaxForms;

@SimpleObject
@UsesPermissions(permissionNames = "android.permission.INTERNET")
@DesignerComponent(androidMinSdk = 9, category = ComponentCategory.EXPERIMENTAL, iconName = "images/paintpalette.png", nonVisible = SyntaxForms.DEBUGGING, version = 1)
@UsesLibraries(libraries = "protobuf-java-3.0.0.jar")
/* loaded from: classes.dex */
public class ImageBot extends AndroidNonvisibleComponent {
    private static final String IMAGEBOT_SERVICE_URL = "https://chatbot.appinventor.mit.edu/image/v1";
    public static final String LOG_TAG = ImageBot.class.getSimpleName();
    private String apiKey;
    private boolean invert;
    private int size;
    private String token;

    public ImageBot(ComponentContainer container) {
        super(container.$form());
        this.apiKey = "";
        this.invert = true;
        this.size = 256;
    }

    @SimpleProperty(description = "The MIT Access token to use. MIT App Inventor will automatically fill this value in. You should not need to change it.", userVisible = SyntaxForms.DEBUGGING)
    @DesignerProperty(defaultValue = "", editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING)
    public void Token(String token) {
        this.token = token;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public void ApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    @DesignerProperty(defaultValue = "True", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void InvertMask(boolean invert) {
        this.invert = invert;
    }

    @SimpleProperty
    public boolean InvertMask() {
        return this.invert;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "256", editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER)
    public void Size(int size) {
        this.size = size;
    }

    @SimpleProperty
    public int Size() {
        return this.size;
    }

    @SimpleFunction
    public void CreateImage(final String description) {
        AsynchUtil.runAsynchronously(new Runnable() { // from class: com.google.appinventor.components.runtime.ImageBot.1
            @Override // java.lang.Runnable
            public void run() {
                ImageBot.this.doCreateImage(description);
            }
        });
    }

    @SimpleFunction
    public void EditImage(Object source, final String description) {
        try {
            final Bitmap bitmap = loadImage(source);
            if (bitmap != null) {
                AsynchUtil.runAsynchronously(new Runnable() { // from class: com.google.appinventor.components.runtime.ImageBot.2
                    @Override // java.lang.Runnable
                    public void run() {
                        ImageBot.this.doEditImage(bitmap, null, description);
                    }
                });
            } else {
                this.form.androidUIHandler.postDelayed(new Runnable() { // from class: com.google.appinventor.components.runtime.ImageBot.3
                    @Override // java.lang.Runnable
                    public void run() {
                        ImageBot.this.ErrorOccurred(-1, "Invalid input to EditImage");
                    }
                }, 0L);
                throw new StopBlocksExecution();
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Unable to read source image", e);
        }
    }

    @SimpleFunction
    public void EditImageWithMask(Object imageSource, Object maskSource, final String prompt) {
        try {
            final Bitmap bitmap = loadImage(imageSource);
            final Bitmap mask = loadMask(maskSource);
            if (bitmap != null && mask != null) {
                AsynchUtil.runAsynchronously(new Runnable() { // from class: com.google.appinventor.components.runtime.ImageBot.4
                    @Override // java.lang.Runnable
                    public void run() {
                        ImageBot.this.doEditImage(bitmap, mask, prompt);
                    }
                });
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Unable to read source image", e);
        }
    }

    @SimpleEvent
    public void ImageCreated(String fileName) {
        EventDispatcher.dispatchEvent(this, "ImageCreated", fileName);
    }

    @SimpleEvent
    public void ImageEdited(String fileName) {
        EventDispatcher.dispatchEvent(this, "ImageEdited", fileName);
    }

    @SimpleEvent
    public void ErrorOccurred(int responseCode, String responseText) {
        if (!EventDispatcher.dispatchEvent(this, "ErrorOccurred", Integer.valueOf(responseCode), responseText)) {
            this.form.dispatchErrorOccurredEvent(this, "ErrorOccurred", ErrorMessages.ERROR_IMAGEBOT_ERROR, Integer.valueOf(responseCode), responseText);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class ImageException extends IOException {
        private int code;
        private String description;

        private ImageException(int code, String description) {
            this.code = code;
            this.description = description;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public String getResponseMessage() {
            return this.description;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getResponseCode() {
            return this.code;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doCreateImage(String prompt) {
        String iToken;
        try {
            if (this.token != null && !this.token.equals("") && this.token.substring(0, 1).equals("%")) {
                iToken = this.token.substring(1);
            } else {
                iToken = this.token;
            }
            byte[] decodedToken = Base58Util.decode(iToken);
            ImageBotToken.token token = ImageBotToken.token.parseFrom(decodedToken);
            ImageBotToken.request.Builder builder = ImageBotToken.request.newBuilder().setToken(token).setSize("" + this.size).setOperation(ImageBotToken.request.OperationType.CREATE).setPrompt(prompt);
            if (this.apiKey != null && !this.apiKey.equals("")) {
                builder = builder.setApikey(this.apiKey);
            }
            ImageBotToken.request request = builder.m201build();
            try {
                final String response = sendRequest(request);
                this.form.runOnUiThread(new Runnable() { // from class: com.google.appinventor.components.runtime.ImageBot.5
                    @Override // java.lang.Runnable
                    public void run() {
                        ImageBot.this.ImageCreated(response);
                    }
                });
            } catch (ImageException e) {
                this.form.runOnUiThread(new Runnable() { // from class: com.google.appinventor.components.runtime.ImageBot.6
                    @Override // java.lang.Runnable
                    public void run() {
                        ImageBot.this.ErrorOccurred(e.getResponseCode(), e.getResponseMessage());
                    }
                });
            }
        } catch (Exception e2) {
            this.form.runOnUiThread(new Runnable() { // from class: com.google.appinventor.components.runtime.ImageBot.7
                @Override // java.lang.Runnable
                public void run() {
                    ImageBot.this.ErrorOccurred(404, e2.toString());
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doEditImage(Bitmap source, Bitmap mask, String description) {
        String iToken;
        ByteArrayOutputStream sourceBuffer = new ByteArrayOutputStream();
        source.compress(Bitmap.CompressFormat.PNG, 100, sourceBuffer);
        ByteString sourceString = ByteString.copyFrom(sourceBuffer.toByteArray());
        ByteArrayOutputStream maskBuffer = new ByteArrayOutputStream();
        mask.compress(Bitmap.CompressFormat.PNG, 100, maskBuffer);
        ByteString maskString = ByteString.copyFrom(maskBuffer.toByteArray());
        if (this.token != null && !this.token.equals("") && this.token.substring(0, 1).equals("%")) {
            iToken = this.token.substring(1);
        } else {
            iToken = this.token;
        }
        try {
            byte[] decodedToken = Base58Util.decode(iToken);
            ImageBotToken.token token = ImageBotToken.token.parseFrom(decodedToken);
            ImageBotToken.request.Builder builder = ImageBotToken.request.newBuilder().setToken(token).setSource(sourceString).setMask(maskString).setOperation(ImageBotToken.request.OperationType.EDIT).setSize("" + this.size).setPrompt(description);
            if (this.apiKey != null && !this.apiKey.equals("")) {
                builder = builder.setApikey(this.apiKey);
            }
            ImageBotToken.request request = builder.m201build();
            try {
                final String response = sendRequest(request);
                this.form.runOnUiThread(new Runnable() { // from class: com.google.appinventor.components.runtime.ImageBot.9
                    @Override // java.lang.Runnable
                    public void run() {
                        ImageBot.this.ImageEdited(response);
                    }
                });
            } catch (ImageException e) {
                this.form.runOnUiThread(new Runnable() { // from class: com.google.appinventor.components.runtime.ImageBot.10
                    @Override // java.lang.Runnable
                    public void run() {
                        ImageBot.this.ErrorOccurred(e.getResponseCode(), e.getResponseMessage());
                    }
                });
            }
        } catch (IOException e2) {
            this.form.runOnUiThread(new Runnable() { // from class: com.google.appinventor.components.runtime.ImageBot.8
                @Override // java.lang.Runnable
                public void run() {
                    ImageBot.this.ErrorOccurred(ErrorMessages.ERROR_NXT_INVALID_RETURN_PACKAGE, "Invalid Token");
                }
            });
        }
    }

    private String sendRequest(ImageBotToken.request request) throws ImageException {
        HttpsURLConnection connection = null;
        try {
            try {
                URL url = new URL(IMAGEBOT_SERVICE_URL);
                connection = (HttpsURLConnection) url.openConnection();
                if (connection != null) {
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    request.writeTo(connection.getOutputStream());
                    int responseCode = connection.getResponseCode();
                    ImageBotToken.response response = ImageBotToken.response.parseFrom(connection.getInputStream());
                    if (responseCode == 200) {
                        byte[] imageData = response.getImage().toByteArray();
                        java.io.File outFile = getOutputFile();
                        FileOutputStream out = new FileOutputStream(outFile);
                        out.write(imageData);
                        out.flush();
                        out.close();
                        String result = Uri.fromFile(outFile).toString();
                        return result;
                    }
                    String errorMessage = IOUtils.readStreamAsString(connection.getErrorStream());
                    throw new ImageException(responseCode, errorMessage);
                }
                connection.disconnect();
                throw new ImageException(404, "Could not connect to proxy server");
            } catch (IOException e) {
                throw new ImageException(404, e.toString());
            }
        } finally {
            connection.disconnect();
        }
    }

    private Bitmap loadImage(Object source) throws IOException {
        Bitmap bitmap;
        Log.d(LOG_TAG, "loadImage source = " + source);
        if (source instanceof Canvas) {
            bitmap = ((Canvas) source).getBitmap();
        } else if (source instanceof Image) {
            bitmap = ((BitmapDrawable) ((Image) source).getView().getBackground()).getBitmap();
        } else {
            String sourceStr = source.toString();
            bitmap = MediaUtil.getBitmapDrawable(this.form, sourceStr).getBitmap();
        }
        if (bitmap != null) {
            if (bitmap.getWidth() == this.size && bitmap.getHeight() == this.size) {
                return bitmap;
            }
            bitmap = Bitmap.createScaledBitmap(bitmap, this.size, this.size, false);
        }
        return bitmap;
    }

    private Bitmap loadMask(Object mask) throws IOException {
        Bitmap bitmap = loadImage(mask);
        if (this.invert) {
            ColorMatrix transform = new ColorMatrix(new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.0f, 255.0f});
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(transform);
            Paint paint = new Paint();
            paint.setColorFilter(filter);
            Bitmap newBitmap = Bitmap.createBitmap(this.size, this.size, Bitmap.Config.ARGB_8888);
            android.graphics.Canvas canvas = new android.graphics.Canvas(newBitmap);
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
            return newBitmap;
        }
        return bitmap;
    }

    private java.io.File getOutputFile() throws IOException {
        String tempdir = FileUtil.resolveFileName(this.form, "", this.form.DefaultFileScope());
        if (tempdir.startsWith("file://")) {
            tempdir = tempdir.substring(7);
        } else if (tempdir.startsWith("file:")) {
            tempdir = tempdir.substring(5);
        }
        Log.d(LOG_TAG, "tempdir = " + tempdir);
        java.io.File outFile = java.io.File.createTempFile("ImageBot", ".png", new java.io.File(tempdir));
        Log.d(LOG_TAG, "outfile = " + outFile);
        return outFile;
    }
}
