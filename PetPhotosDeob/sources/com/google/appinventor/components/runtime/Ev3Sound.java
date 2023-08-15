package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.Ev3BinaryParser;
import kawa.lang.SyntaxForms;

@DesignerComponent(category = ComponentCategory.LEGOMINDSTORMS, description = "A component that provides a high-level interface to sound functionalities on LEGO MINDSTORMS EV3 robot.", iconName = "images/legoMindstormsEv3.png", nonVisible = SyntaxForms.DEBUGGING, version = 1)
@SimpleObject
/* loaded from: classes.dex */
public class Ev3Sound extends LegoMindstormsEv3Base {
    public Ev3Sound(ComponentContainer container) {
        super(container, "Ev3Sound");
    }

    @SimpleFunction(description = "Make the robot play a tone.")
    public void PlayTone(int volume, int frequency, int milliseconds) {
        String functionName = Thread.currentThread().getStackTrace()[1].getMethodName();
        if (volume < 0 || volume > 100 || frequency < 250 || frequency > 10000 || milliseconds < 0 || milliseconds > 65535) {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, functionName);
            return;
        }
        byte[] command = Ev3BinaryParser.encodeDirectCommand((byte) -108, true, 0, 0, "cccc", (byte) 1, Byte.valueOf((byte) volume), Short.valueOf((short) frequency), Short.valueOf((short) milliseconds));
        sendCommand(functionName, command, true);
    }

    @SimpleFunction(description = "Stop any sound on the robot.")
    public void StopSound() {
        String functionName = Thread.currentThread().getStackTrace()[1].getMethodName();
        byte[] command = Ev3BinaryParser.encodeDirectCommand((byte) -108, false, 0, 0, "c", (byte) 0);
        sendCommand(functionName, command, false);
    }
}
