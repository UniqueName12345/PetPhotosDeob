package kawa;

import gnu.expr.Language;
import gnu.mapping.Environment;
import gnu.mapping.Future;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure0;
import gnu.mapping.TtyInPort;
import gnu.mapping.Values;
import gnu.text.FilePath;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/* loaded from: classes.dex */
public class TelnetRepl extends Procedure0 {
    Language language;
    Socket socket;

    public TelnetRepl(Language language, Socket socket) {
        this.language = language;
        this.socket = socket;
    }

    @Override // gnu.mapping.Procedure0, gnu.mapping.Procedure
    public Object apply0() {
        try {
            Shell.run(this.language, Environment.getCurrent());
            return Values.empty;
        } finally {
            try {
                this.socket.close();
            } catch (IOException e) {
            }
        }
    }

    public static void serve(Language language, Socket client) throws IOException {
        Telnet conn = new Telnet(client, true);
        OutputStream sout = conn.getOutputStream();
        InputStream sin = conn.getInputStream();
        OutPort out = new OutPort(sout, FilePath.valueOf("/dev/stdout"));
        TtyInPort in = new TtyInPort(sin, FilePath.valueOf("/dev/stdin"), out);
        Thread thread = new Future(new TelnetRepl(language, client), in, out, out);
        thread.start();
    }
}
