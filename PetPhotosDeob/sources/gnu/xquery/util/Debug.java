package gnu.xquery.util;

import gnu.mapping.OutPort;
import gnu.xml.XMLPrinter;
import java.io.FileOutputStream;

/* loaded from: classes.dex */
public class Debug {
    public static String tracePrefix = "XQuery-trace: ";
    public static OutPort tracePort = null;
    public static String traceFilename = "XQuery-trace.log";
    public static boolean traceShouldFlush = true;
    public static boolean traceShouldAppend = false;

    public static synchronized Object trace(Object value, Object message) {
        synchronized (Debug.class) {
            OutPort out = tracePort;
            if (out == null) {
                out = new OutPort(new FileOutputStream(traceFilename, traceShouldAppend));
                tracePort = out;
            }
            out.print(tracePrefix);
            out.print(message);
            out.print(' ');
            XMLPrinter xout = new XMLPrinter(out, false);
            xout.writeObject(value);
            out.println();
            if (traceShouldFlush) {
                out.flush();
            }
        }
        return value;
    }
}
