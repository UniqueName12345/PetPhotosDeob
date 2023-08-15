package gnu.kawa.xml;

import androidx.fragment.app.FragmentTransaction;
import gnu.lists.Consumer;
import gnu.lists.XConsumer;
import gnu.mapping.CallContext;
import gnu.mapping.Location;
import gnu.mapping.MethodProc;
import gnu.mapping.Values;
import gnu.xml.TextUtils;

/* loaded from: classes.dex */
public class CommentConstructor extends MethodProc {
    public static final CommentConstructor commentConstructor = new CommentConstructor();

    @Override // gnu.mapping.Procedure
    public int numArgs() {
        return FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
    }

    @Override // gnu.mapping.Procedure
    public void apply(CallContext ctx) {
        Consumer saved = ctx.consumer;
        XConsumer out = NodeConstructor.pushNodeContext(ctx);
        try {
            StringBuffer sbuf = new StringBuffer();
            String endMarker = Location.UNBOUND;
            boolean first = true;
            int i = 0;
            while (true) {
                Object arg = ctx.getNextArg(endMarker);
                if (arg != endMarker) {
                    if (arg instanceof Values) {
                        Values vals = (Values) arg;
                        int it = 0;
                        while (true) {
                            it = vals.nextPos(it);
                            if (it != 0) {
                                if (!first) {
                                    sbuf.append(' ');
                                }
                                first = false;
                                TextUtils.stringValue(vals.getPosPrevious(it), sbuf);
                            }
                        }
                    } else {
                        if (!first) {
                            sbuf.append(' ');
                        }
                        first = false;
                        TextUtils.stringValue(arg, sbuf);
                    }
                    i++;
                } else {
                    int len = sbuf.length();
                    char[] buf = new char[len];
                    sbuf.getChars(0, len, buf, 0);
                    out.writeComment(buf, 0, len);
                    return;
                }
            }
        } finally {
            NodeConstructor.popNodeContext(saved, ctx);
        }
    }
}
