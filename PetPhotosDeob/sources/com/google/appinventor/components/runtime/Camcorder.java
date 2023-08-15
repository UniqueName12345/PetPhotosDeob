package com.google.appinventor.components.runtime;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.FileUtil;
import kawa.lang.SyntaxForms;

@UsesPermissions({"android.permission.CAMERA"})
@DesignerComponent(category = ComponentCategory.MEDIA, description = "A component to record a video using the device's camcorder.After the video is recorded, the name of the file on the phone containing the clip is available as an argument to the AfterRecording event. The file name can be used, for example, to set the source property of a VideoPlayer component.", iconName = "images/camcorder.png", nonVisible = SyntaxForms.DEBUGGING, version = 1)
@SimpleObject
/* loaded from: classes.dex */
public class Camcorder extends AndroidNonvisibleComponent implements ActivityResultListener, Component {
    private static final String CAMCORDER_INTENT = "android.media.action.VIDEO_CAPTURE";
    private final ComponentContainer container;
    private boolean havePermission;
    private int requestCode;

    public Camcorder(ComponentContainer container) {
        super(container.$form());
        this.havePermission = false;
        this.container = container;
    }

    public void Initialize() {
        this.havePermission = !this.form.isDeniedPermission("android.permission.CAMERA");
        if (FileUtil.needsWritePermission(this.form.DefaultFileScope())) {
            this.havePermission &= this.form.isDeniedPermission("android.permission.WRITE_EXTERNAL_STORAGE") ? false : true;
        }
    }

    @SimpleFunction
    public void RecordVideo() {
        String state = Environment.getExternalStorageState();
        if (!this.havePermission) {
            this.form.runOnUiThread(new Runnable() { // from class: com.google.appinventor.components.runtime.Camcorder.1
                @Override // java.lang.Runnable
                public void run() {
                    Camcorder.this.form.askPermission("android.permission.CAMERA", new PermissionResultHandler() { // from class: com.google.appinventor.components.runtime.Camcorder.1.1
                        @Override // com.google.appinventor.components.runtime.PermissionResultHandler
                        public void HandlePermissionResponse(String permission, boolean granted) {
                            if (granted) {
                                me.havePermission = true;
                                me.RecordVideo();
                                return;
                            }
                            Camcorder.this.form.dispatchPermissionDeniedEvent(me, "RecordVideo", "android.permission.CAMERA");
                        }
                    });
                }
            });
        } else if ("mounted".equals(state)) {
            Log.i("CamcorderComponent", "External storage is available and writable");
            if (this.requestCode == 0) {
                this.requestCode = this.form.registerForActivityResult(this);
            }
            Intent intent = new Intent(CAMCORDER_INTENT);
            this.container.$context().startActivityForResult(intent, this.requestCode);
        } else if ("mounted_ro".equals(state)) {
            this.form.dispatchErrorOccurredEvent(this, "RecordVideo", ErrorMessages.ERROR_MEDIA_EXTERNAL_STORAGE_READONLY, new Object[0]);
        } else {
            this.form.dispatchErrorOccurredEvent(this, "RecordVideo", ErrorMessages.ERROR_MEDIA_EXTERNAL_STORAGE_NOT_AVAILABLE, new Object[0]);
        }
    }

    @Override // com.google.appinventor.components.runtime.ActivityResultListener
    public void resultReturned(int requestCode, int resultCode, Intent data) {
        Log.i("CamcorderComponent", "Returning result. Request code = " + requestCode + ", result code = " + resultCode);
        if (requestCode == this.requestCode && resultCode == -1) {
            if (data != null && data.getData() != null) {
                Uri tryClipUri = data.getData();
                Log.i("CamcorderComponent", "Calling Camcorder.AfterPicture with clip path " + tryClipUri.toString());
                AfterRecording(tryClipUri.toString());
                return;
            }
            Log.i("CamcorderComponent", "Couldn't find a clip file from the Camcorder result");
            this.form.dispatchErrorOccurredEvent(this, "TakeVideo", ErrorMessages.ERROR_CAMCORDER_NO_CLIP_RETURNED, new Object[0]);
            return;
        }
        Log.i("CamcorderComponent", "No clip filed rerturn; request failed");
        this.form.dispatchErrorOccurredEvent(this, "TakeVideo", ErrorMessages.ERROR_CAMCORDER_NO_CLIP_RETURNED, new Object[0]);
    }

    private void deleteFile(Uri fileUri) {
        java.io.File fileToDelete = new java.io.File(fileUri.getPath());
        try {
            if (fileToDelete.delete()) {
                Log.i("CamcorderComponent", "Deleted file " + fileUri.toString());
            } else {
                Log.i("CamcorderComponent", "Could not delete file " + fileUri.toString());
            }
        } catch (SecurityException e) {
            Log.i("CamcorderComponent", "Got security exception trying to delete file " + fileUri.toString());
        }
    }

    @SimpleEvent
    public void AfterRecording(String clip) {
        EventDispatcher.dispatchEvent(this, "AfterRecording", clip);
    }
}
