package com.google.appinventor.components.runtime.util;

import android.util.Log;
import gnu.expr.Language;
import gnu.mapping.Environment;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure0;
import gnu.mapping.TtyInPort;
import gnu.mapping.Values;
import gnu.text.FilePath;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import kawa.Shell;
import kawa.Telnet;

/* loaded from: classes.dex */
public class TelnetRepl extends Procedure0 {
    private static final int REPL_STACK_SIZE = 262144;
    Language language;
    Socket socket;

    public TelnetRepl(Language language, Socket socket) {
        this.language = language;
        this.socket = socket;
    }

    @Override // gnu.mapping.Procedure0, gnu.mapping.Procedure
    public Object apply0() {
        Thread thread = Thread.currentThread();
        ClassLoader contextClassLoader = thread.getContextClassLoader();
        if (contextClassLoader == null) {
            thread.setContextClassLoader(Telnet.class.getClassLoader());
        }
        try {
            try {
                Shell.run(this.language, Environment.getCurrent());
                return Values.empty;
            } catch (RuntimeException e) {
                Log.d("TelnetRepl", "Repl is exiting with error " + e.getMessage());
                e.printStackTrace();
                throw e;
            }
        } finally {
            try {
                this.socket.close();
            } catch (IOException e2) {
            }
        }
    }

    public static Thread serve(Language language, Socket client) throws IOException {
        Telnet conn = new Telnet(client, true);
        OutputStream sout = conn.getOutputStream();
        InputStream sin = conn.getInputStream();
        OutPort out = new OutPort(sout, FilePath.valueOf("/dev/stdout"));
        TtyInPort in = new TtyInPort(sin, FilePath.valueOf("/dev/stdin"), out);
        Thread thread = new BiggerFuture(new TelnetRepl(language, client), in, out, out, "Telnet Repl Thread", 262144L);
        thread.start();
        return thread;
    }
}
