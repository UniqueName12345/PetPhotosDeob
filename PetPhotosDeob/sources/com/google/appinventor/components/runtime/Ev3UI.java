package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.Ev3BinaryParser;
import com.google.appinventor.components.runtime.util.Ev3Constants;
import kawa.lang.SyntaxForms;

@DesignerComponent(category = ComponentCategory.LEGOMINDSTORMS, description = "A component that provides a high-level interface to a LEGO MINDSTORMS EV3 robot, with functions to draw graphs on EV3 screen.", iconName = "images/legoMindstormsEv3.png", nonVisible = SyntaxForms.DEBUGGING, version = 1)
@SimpleObject
/* loaded from: classes.dex */
public class Ev3UI extends LegoMindstormsEv3Base {
    public Ev3UI(ComponentContainer container) {
        super(container, "Ev3UI");
    }

    @SimpleFunction(description = "Draw a point on the screen.")
    public void DrawPoint(int color, int x, int y) {
        if (color != 0 && color != 1) {
            this.form.dispatchErrorOccurredEvent(this, "DrawPoint", ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, "DrawPoint");
            return;
        }
        byte[] command = Ev3BinaryParser.encodeDirectCommand(Ev3Constants.Opcode.UI_DRAW, false, 0, 0, "cccc", (byte) 2, Byte.valueOf((byte) color), Short.valueOf((short) x), Short.valueOf((short) y));
        sendCommand("DrawPoint", command, false);
        byte[] command2 = Ev3BinaryParser.encodeDirectCommand(Ev3Constants.Opcode.UI_DRAW, false, 0, 0, "c", (byte) 0);
        sendCommand("DrawPoint", command2, false);
    }

    @SimpleFunction(description = "Draw a built-in icon on screen.")
    public void DrawIcon(int color, int x, int y, int type, int no) {
        if (color != 0 && color != 1) {
            this.form.dispatchErrorOccurredEvent(this, "DrawIcon", ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, "DrawIcon");
            return;
        }
        byte[] command = Ev3BinaryParser.encodeDirectCommand(Ev3Constants.Opcode.UI_DRAW, false, 0, 0, "cccccc", (byte) 6, Byte.valueOf((byte) color), Short.valueOf((short) x), Short.valueOf((short) y), Integer.valueOf(type), Integer.valueOf(no));
        sendCommand("DrawIcon", command, false);
        byte[] command2 = Ev3BinaryParser.encodeDirectCommand(Ev3Constants.Opcode.UI_DRAW, false, 0, 0, "c", (byte) 0);
        sendCommand("DrawIcon", command2, false);
    }

    @SimpleFunction(description = "Draw a line on the screen.")
    public void DrawLine(int color, int x1, int y1, int x2, int y2) {
        if (color != 0 && color != 1) {
            this.form.dispatchErrorOccurredEvent(this, "DrawLine", ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, "DrawLine");
            return;
        }
        byte[] command = Ev3BinaryParser.encodeDirectCommand(Ev3Constants.Opcode.UI_DRAW, false, 0, 0, "cccccc", (byte) 3, Byte.valueOf((byte) color), Short.valueOf((short) x1), Short.valueOf((short) y1), Short.valueOf((short) x2), Short.valueOf((short) y2));
        sendCommand("DrawLine", command, false);
        byte[] command2 = Ev3BinaryParser.encodeDirectCommand(Ev3Constants.Opcode.UI_DRAW, false, 0, 0, "c", (byte) 0);
        sendCommand("DrawLine", command2, false);
    }

    @SimpleFunction(description = "Draw a rectangle on the screen.")
    public void DrawRect(int color, int x, int y, int width, int height, boolean fill) {
        if (color != 0 && color != 1) {
            this.form.dispatchErrorOccurredEvent(this, "DrawRect", ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, "DrawRect");
            return;
        }
        Object[] objArr = new Object[6];
        objArr[0] = Byte.valueOf(fill ? (byte) 9 : (byte) 10);
        objArr[1] = Byte.valueOf((byte) color);
        objArr[2] = Short.valueOf((short) x);
        objArr[3] = Short.valueOf((short) y);
        objArr[4] = Short.valueOf((short) width);
        objArr[5] = Short.valueOf((short) height);
        byte[] command = Ev3BinaryParser.encodeDirectCommand(Ev3Constants.Opcode.UI_DRAW, false, 0, 0, "cccccc", objArr);
        sendCommand("DrawRect", command, false);
        byte[] command2 = Ev3BinaryParser.encodeDirectCommand(Ev3Constants.Opcode.UI_DRAW, false, 0, 0, "c", (byte) 0);
        sendCommand("DrawRect", command2, false);
    }

    @SimpleFunction(description = "Draw a circle on the screen.")
    public void DrawCircle(int color, int x, int y, int radius, boolean fill) {
        if ((color != 0 && color != 1) || radius < 0) {
            this.form.dispatchErrorOccurredEvent(this, "DrawCircle", ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, "DrawCircle");
            return;
        }
        Object[] objArr = new Object[5];
        objArr[0] = Byte.valueOf(fill ? (byte) 24 : (byte) 4);
        objArr[1] = Byte.valueOf((byte) color);
        objArr[2] = Short.valueOf((short) x);
        objArr[3] = Short.valueOf((short) y);
        objArr[4] = Short.valueOf((short) radius);
        byte[] command = Ev3BinaryParser.encodeDirectCommand(Ev3Constants.Opcode.UI_DRAW, false, 0, 0, "ccccc", objArr);
        sendCommand("DrawCircle", command, false);
        byte[] command2 = Ev3BinaryParser.encodeDirectCommand(Ev3Constants.Opcode.UI_DRAW, false, 0, 0, "c", (byte) 0);
        sendCommand("DrawCircle", command2, false);
    }

    @SimpleFunction(description = "Fill the screen with a color.")
    public void FillScreen(int color) {
        if (color != 0 && color != 1) {
            this.form.dispatchErrorOccurredEvent(this, "FillScreen", ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, "FillScreen");
            return;
        }
        byte[] command = Ev3BinaryParser.encodeDirectCommand(Ev3Constants.Opcode.UI_DRAW, false, 0, 0, "cccc", (byte) 19, Byte.valueOf((byte) color), (short) 0, (short) 0);
        sendCommand("FillScreen", command, false);
        byte[] command2 = Ev3BinaryParser.encodeDirectCommand(Ev3Constants.Opcode.UI_DRAW, false, 0, 0, "c", (byte) 0);
        sendCommand("FillScreen", command2, false);
    }
}
