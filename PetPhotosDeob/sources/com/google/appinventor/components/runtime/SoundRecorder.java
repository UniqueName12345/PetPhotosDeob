package com.google.appinventor.components.runtime;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.errors.PermissionException;
import com.google.appinventor.components.runtime.util.BulkPermissionRequest;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.FileUtil;
import java.io.IOException;
import kawa.lang.SyntaxForms;

@UsesPermissions({"android.permission.RECORD_AUDIO"})
@DesignerComponent(category = ComponentCategory.MEDIA, description = "<p>Multimedia component that records audio.</p>", iconName = "images/soundRecorder.png", nonVisible = SyntaxForms.DEBUGGING, version = 2)
@SimpleObject
/* loaded from: classes.dex */
public final class SoundRecorder extends AndroidNonvisibleComponent implements Component, MediaRecorder.OnErrorListener, MediaRecorder.OnInfoListener {
    private static final String TAG = "SoundRecorder";
    private RecordingController controller;
    private boolean havePermission;
    private String savedRecording;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class RecordingController {
        final String file;
        final MediaRecorder recorder;

        RecordingController(String savedRecording) throws IOException {
            this.file = savedRecording.equals("") ? FileUtil.getRecordingFile(SoundRecorder.this.form, "3gp").getAbsolutePath() : savedRecording;
            this.recorder = new MediaRecorder();
            this.recorder.setAudioSource(1);
            this.recorder.setOutputFormat(1);
            this.recorder.setAudioEncoder(1);
            Log.i(SoundRecorder.TAG, "Setting output file to " + this.file);
            this.recorder.setOutputFile(this.file);
            Log.i(SoundRecorder.TAG, "preparing");
            this.recorder.prepare();
            this.recorder.setOnErrorListener(SoundRecorder.this);
            this.recorder.setOnInfoListener(SoundRecorder.this);
        }

        void start() throws IllegalStateException {
            Log.i(SoundRecorder.TAG, "starting");
            try {
                this.recorder.start();
            } catch (IllegalStateException e) {
                Log.e(SoundRecorder.TAG, "got IllegalStateException. Are there two recorders running?", e);
                throw new IllegalStateException("Is there another recording running?");
            }
        }

        void stop() {
            this.recorder.setOnErrorListener(null);
            this.recorder.setOnInfoListener(null);
            this.recorder.stop();
            this.recorder.reset();
            this.recorder.release();
        }
    }

    public SoundRecorder(ComponentContainer container) {
        super(container.$form());
        this.savedRecording = "";
        this.havePermission = false;
    }

    public void Initialize() {
        this.havePermission = !this.form.isDeniedPermission("android.permission.RECORD_AUDIO");
        if (FileUtil.needsWritePermission(this.form.DefaultFileScope())) {
            this.havePermission &= this.form.isDeniedPermission("android.permission.WRITE_EXTERNAL_STORAGE") ? false : true;
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Specifies the path to the file where the recording should be stored. If this property is the empty string, then starting a recording will create a file in an appropriate location.  If the property is not the empty string, it should specify a complete path to a file in an existing directory, including a file name with the extension .3gp.")
    public String SavedRecording() {
        return this.savedRecording;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "", editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING)
    public void SavedRecording(String pathName) {
        this.savedRecording = pathName;
    }

    @SimpleFunction
    public void Start() {
        String uri = FileUtil.resolveFileName(this.form, this.savedRecording, this.form.DefaultFileScope());
        if (!this.havePermission) {
            final String[] neededPermissions = FileUtil.needsPermission(this.form, uri) ? new String[]{"android.permission.RECORD_AUDIO", "android.permission.WRITE_EXTERNAL_STORAGE"} : new String[]{"android.permission.RECORD_AUDIO"};
            this.form.runOnUiThread(new Runnable() { // from class: com.google.appinventor.components.runtime.SoundRecorder.1
                @Override // java.lang.Runnable
                public void run() {
                    SoundRecorder.this.form.askPermission(new BulkPermissionRequest(me, "Start", neededPermissions) { // from class: com.google.appinventor.components.runtime.SoundRecorder.1.1
                        @Override // com.google.appinventor.components.runtime.util.BulkPermissionRequest
                        public void onGranted() {
                            me.havePermission = true;
                            me.Start();
                        }
                    });
                }
            });
        } else if (this.controller != null) {
            Log.i(TAG, "Start() called, but already recording to " + this.controller.file);
        } else {
            Log.i(TAG, "Start() called");
            if (FileUtil.isExternalStorageUri(this.form, uri) && !Environment.getExternalStorageState().equals("mounted")) {
                this.form.dispatchErrorOccurredEvent(this, "Start", ErrorMessages.ERROR_MEDIA_EXTERNAL_STORAGE_NOT_AVAILABLE, new Object[0]);
                return;
            }
            try {
                this.controller = new RecordingController(this.savedRecording);
                try {
                    this.controller.start();
                    StartedRecording();
                } catch (Throwable t) {
                    this.controller = null;
                    Log.e(TAG, "Cannot record sound", t);
                    this.form.dispatchErrorOccurredEvent(this, "Start", ErrorMessages.ERROR_SOUND_RECORDER_CANNOT_CREATE, t.getMessage());
                }
            } catch (PermissionException e) {
                this.form.dispatchPermissionDeniedEvent(this, "Start", e);
            } catch (Throwable t2) {
                Log.e(TAG, "Cannot record sound", t2);
                this.form.dispatchErrorOccurredEvent(this, "Start", ErrorMessages.ERROR_SOUND_RECORDER_CANNOT_CREATE, t2.getMessage());
            }
        }
    }

    @Override // android.media.MediaRecorder.OnErrorListener
    public void onError(MediaRecorder affectedRecorder, int what, int extra) {
        if (this.controller == null || affectedRecorder != this.controller.recorder) {
            Log.w(TAG, "onError called with wrong recorder. Ignoring.");
            return;
        }
        this.form.dispatchErrorOccurredEvent(this, "onError", ErrorMessages.ERROR_SOUND_RECORDER, new Object[0]);
        try {
            this.controller.stop();
        } catch (Throwable e) {
            try {
                Log.w(TAG, e.getMessage());
            } finally {
                this.controller = null;
                StoppedRecording();
            }
        }
    }

    @Override // android.media.MediaRecorder.OnInfoListener
    public void onInfo(MediaRecorder affectedRecorder, int what, int extra) {
        if (this.controller == null || affectedRecorder != this.controller.recorder) {
            Log.w(TAG, "onInfo called with wrong recorder. Ignoring.");
            return;
        }
        switch (what) {
            case 1:
                this.form.dispatchErrorOccurredEvent(this, "recording", ErrorMessages.ERROR_SOUND_RECORDER, new Object[0]);
                break;
            case 800:
                this.form.dispatchErrorOccurredEvent(this, "recording", ErrorMessages.ERROR_SOUND_RECORDER_MAX_DURATION_REACHED, new Object[0]);
                break;
            case ErrorMessages.ERROR_SOUND_RECORDER /* 801 */:
                this.form.dispatchErrorOccurredEvent(this, "recording", ErrorMessages.ERROR_SOUND_RECORDER_MAX_FILESIZE_REACHED, new Object[0]);
                break;
            default:
                return;
        }
        try {
            Log.i(TAG, "Recoverable condition while recording. Will attempt to stop normally.");
            this.controller.recorder.stop();
        } catch (IllegalStateException e) {
            Log.i(TAG, "SoundRecorder was not in a recording state.", e);
            this.form.dispatchErrorOccurredEventDialog(this, "Stop", ErrorMessages.ERROR_SOUND_RECORDER_ILLEGAL_STOP, new Object[0]);
        } finally {
            this.controller = null;
            StoppedRecording();
        }
    }

    @SimpleFunction
    public void Stop() {
        if (this.controller == null) {
            Log.i(TAG, "Stop() called, but already stopped.");
            return;
        }
        try {
            Log.i(TAG, "Stop() called");
            Log.i(TAG, "stopping");
            this.controller.stop();
            Log.i(TAG, "Firing AfterSoundRecorded with " + this.controller.file);
            AfterSoundRecorded(this.controller.file);
        } catch (Throwable th) {
            try {
                this.form.dispatchErrorOccurredEvent(this, "Stop", ErrorMessages.ERROR_SOUND_RECORDER, new Object[0]);
            } finally {
                this.controller = null;
                StoppedRecording();
            }
        }
    }

    @SimpleEvent(description = "Provides the location of the newly created sound.")
    public void AfterSoundRecorded(String sound) {
        EventDispatcher.dispatchEvent(this, "AfterSoundRecorded", sound);
    }

    @SimpleEvent(description = "Indicates that the recorder has started, and can be stopped.")
    public void StartedRecording() {
        EventDispatcher.dispatchEvent(this, "StartedRecording", new Object[0]);
    }

    @SimpleEvent(description = "Indicates that the recorder has stopped, and can be started again.")
    public void StoppedRecording() {
        EventDispatcher.dispatchEvent(this, "StoppedRecording", new Object[0]);
    }
}
