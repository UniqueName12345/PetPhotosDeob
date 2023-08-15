package com.google.appinventor.components.runtime;

import android.os.Handler;
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
import com.google.appinventor.components.runtime.util.AsyncCallbackPair;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import kawa.lang.SyntaxForms;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

@SimpleObject
@UsesPermissions(permissionNames = "android.permission.INTERNET")
@DesignerComponent(category = ComponentCategory.INTERNAL, description = "Non-visible component that communicates with a Web service and stores media files.", iconName = "images/mediastore.png", nonVisible = SyntaxForms.DEBUGGING, version = 1)
@UsesLibraries({"httpmime.jar"})
/* loaded from: classes.dex */
public final class MediaStore extends AndroidNonvisibleComponent implements Component {
    private static final String LOG_TAG_COMPONENT = "MediaStore: ";
    private Handler androidUIHandler;
    protected final ComponentContainer componentContainer;
    private String serviceURL;

    public MediaStore(ComponentContainer container) {
        super(container.$form());
        this.componentContainer = container;
        this.androidUIHandler = new Handler();
        this.serviceURL = "http://ai-mediaservice.appspot.com";
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String ServiceURL() {
        return this.serviceURL;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "http://ai-mediaservice.appspot.com", editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING)
    public void ServiceURL(String url) {
        this.serviceURL = url;
    }

    @SimpleFunction
    public void PostMedia(String mediafile) throws FileNotFoundException {
        String newMediaPath;
        AsyncCallbackPair<String> myCallback = new AsyncCallbackPair<String>() { // from class: com.google.appinventor.components.runtime.MediaStore.1
            @Override // com.google.appinventor.components.runtime.util.AsyncCallbackPair
            public void onSuccess(final String response) {
                MediaStore.this.androidUIHandler.post(new Runnable() { // from class: com.google.appinventor.components.runtime.MediaStore.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        MediaStore.this.MediaStored(response);
                    }
                });
            }

            @Override // com.google.appinventor.components.runtime.util.AsyncCallbackPair
            public void onFailure(final String message) {
                MediaStore.this.androidUIHandler.post(new Runnable() { // from class: com.google.appinventor.components.runtime.MediaStore.1.2
                    @Override // java.lang.Runnable
                    public void run() {
                        MediaStore.this.WebServiceError(message);
                    }
                });
            }
        };
        try {
            DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
            entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            String[] pathtokens = mediafile.split("/");
            if (pathtokens[0].equals("file:")) {
                newMediaPath = new java.io.File(new URL(mediafile).toURI()).getAbsolutePath();
            } else {
                newMediaPath = mediafile;
            }
            java.io.File media = new java.io.File(newMediaPath);
            entityBuilder.addPart("file", new FileBody(media));
            HttpEntity entity = entityBuilder.build();
            String uploadURL = getUploadUrl();
            HttpPost post = new HttpPost(uploadURL);
            post.setEntity(entity);
            HttpResponse response = defaultHttpClient.execute(post);
            HttpEntity httpEntity = response.getEntity();
            String result = EntityUtils.toString(httpEntity);
            myCallback.onSuccess(result);
        } catch (Exception e) {
            e.printStackTrace();
            myCallback.onFailure(e.getMessage());
        }
    }

    private String getUploadUrl() {
        try {
            String url = this.serviceURL;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "AppInventor");
            con.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder response = new StringBuilder();
            while (true) {
                String inputLine = in.readLine();
                if (inputLine != null) {
                    response.append(inputLine);
                } else {
                    in.close();
                    return response.toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @SimpleEvent
    public void MediaStored(String url) {
        EventDispatcher.dispatchEvent(this, "MediaStored", url);
    }

    @SimpleEvent
    public void WebServiceError(String message) {
        EventDispatcher.dispatchEvent(this, "WebServiceError", message);
    }
}
