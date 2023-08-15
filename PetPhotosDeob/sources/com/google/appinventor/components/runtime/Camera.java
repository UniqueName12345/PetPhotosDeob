package com.google.appinventor.components.runtime;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.BulkPermissionRequest;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.FileUtil;
import com.google.appinventor.components.runtime.util.NougatUtil;
import com.google.appinventor.components.runtime.util.ScopedFile;
import java.net.URI;
import kawa.lang.SyntaxForms;

@UsesPermissions({"android.permission.CAMERA"})
@DesignerComponent(category = ComponentCategory.MEDIA, description = "A component to take a picture using the device's camera. After the picture is taken, the name of the file on the phone containing the picture is available as an argument to the AfterPicture event. The file name can be used, for example, to set the Picture property of an Image component.", iconName = "images/camera.png", nonVisible = SyntaxForms.DEBUGGING, version = 3)
@SimpleObject
/* loaded from: classes.dex */
public class Camera extends AndroidNonvisibleComponent implements ActivityResultListener, Component {
    private static final String CAMERA_INTENT = "android.media.action.IMAGE_CAPTURE";
    private static final String CAMERA_OUTPUT = "output";
    private final ComponentContainer container;
    private boolean havePermission;
    private Uri imageFile;
    private int requestCode;
    private boolean useFront;

    public Camera(ComponentContainer container) {
        super(container.$form());
        this.havePermission = false;
        this.container = container;
        UseFront(false);
    }

    public void Initialize() {
        this.havePermission = !this.form.isDeniedPermission("android.permission.CAMERA");
        if (FileUtil.needsWritePermission(this.form.DefaultFileScope())) {
            this.havePermission &= this.form.isDeniedPermission("android.permission.WRITE_EXTERNAL_STORAGE") ? false : true;
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    @Deprecated
    public boolean UseFront() {
        return this.useFront;
    }

    @SimpleProperty(description = "Specifies whether the front-facing camera should be used (when available). If the device does not have a front-facing camera, this option will be ignored and the camera will open normally.")
    @Deprecated
    public void UseFront(boolean front) {
        this.useFront = front;
    }

    @SimpleFunction
    public void TakePicture() {
        ScopedFile target = FileUtil.getScopedPictureFile(this.form, "jpg");
        if (!this.havePermission) {
            String[] permissions = FileUtil.needsWritePermission(target) ? new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"} : new String[]{"android.permission.CAMERA"};
            this.form.askPermission(new BulkPermissionRequest(this, "TakePicture", permissions) { // from class: com.google.appinventor.components.runtime.Camera.1
                @Override // com.google.appinventor.components.runtime.util.BulkPermissionRequest
                public void onGranted() {
                    Camera.this.havePermission = true;
                    Camera.this.TakePicture();
                }
            });
            return;
        }
        String state = Environment.getExternalStorageState();
        if (!FileUtil.needsExternalStorage(this.form, target) || "mounted".equals(state)) {
            Log.i("CameraComponent", "External storage is available and writable");
            capturePicture(target);
        } else if ("mounted_ro".equals(state)) {
            this.form.dispatchErrorOccurredEvent(this, "TakePicture", ErrorMessages.ERROR_MEDIA_EXTERNAL_STORAGE_READONLY, new Object[0]);
        } else {
            this.form.dispatchErrorOccurredEvent(this, "TakePicture", ErrorMessages.ERROR_MEDIA_EXTERNAL_STORAGE_NOT_AVAILABLE, new Object[0]);
        }
    }

    private void capturePicture(ScopedFile target) {
        Uri imageUri;
        java.io.File path = new java.io.File(URI.create(FileUtil.resolveFileName(this.form, target.getFileName(), target.getScope())));
        java.io.File directory = path.getParentFile();
        if (!directory.exists()) {
            directory.mkdirs();
        }
        this.imageFile = Uri.fromFile(path);
        ContentValues values = new ContentValues();
        values.put("_data", this.imageFile.getPath());
        values.put("mime_type", "image/jpeg");
        values.put("title", this.imageFile.getLastPathSegment());
        if (this.requestCode == 0) {
            this.requestCode = this.form.registerForActivityResult(this);
        }
        if (Build.VERSION.SDK_INT < 24) {
            imageUri = this.container.$context().getContentResolver().insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI, values);
        } else {
            imageUri = NougatUtil.getPackageUri(this.form, path);
        }
        Intent intent = new Intent(CAMERA_INTENT);
        intent.putExtra(CAMERA_OUTPUT, imageUri);
        if (this.useFront) {
            intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
        }
        this.container.$context().startActivityForResult(intent, this.requestCode);
    }

    @Override // com.google.appinventor.components.runtime.ActivityResultListener
    public void resultReturned(int requestCode, int resultCode, Intent data) {
        Log.i("CameraComponent", "Returning result. Request code = " + requestCode + ", result code = " + resultCode);
        if (requestCode == this.requestCode && resultCode == -1) {
            java.io.File image = new java.io.File(this.imageFile.getPath());
            if (image.length() != 0) {
                scanFileToAdd(image);
                AfterPicture(this.imageFile.toString());
                return;
            }
            deleteFile(this.imageFile);
            if (data != null && data.getData() != null) {
                Uri tryImageUri = data.getData();
                Log.i("CameraComponent", "Calling Camera.AfterPicture with image path " + tryImageUri.toString());
                AfterPicture(tryImageUri.toString());
                return;
            }
            Log.i("CameraComponent", "Couldn't find an image file from the Camera result");
            this.form.dispatchErrorOccurredEvent(this, "TakePicture", ErrorMessages.ERROR_CAMERA_NO_IMAGE_RETURNED, new Object[0]);
            return;
        }
        deleteFile(this.imageFile);
    }

    private void scanFileToAdd(java.io.File image) {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        Uri contentUri = NougatUtil.getPackageUri(this.form, image);
        mediaScanIntent.setData(contentUri);
        this.container.$context().getApplicationContext().sendBroadcast(mediaScanIntent);
    }

    private void deleteFile(Uri fileUri) {
        java.io.File fileToDelete = new java.io.File(fileUri.getPath());
        try {
            if (fileToDelete.delete()) {
                Log.i("CameraComponent", "Deleted file " + fileUri.toString());
            } else {
                Log.i("CameraComponent", "Could not delete file " + fileUri.toString());
            }
        } catch (SecurityException e) {
            Log.i("CameraComponent", "Got security exception trying to delete file " + fileUri.toString());
        }
    }

    @SimpleEvent
    public void AfterPicture(String image) {
        EventDispatcher.dispatchEvent(this, "AfterPicture", image);
    }
}
