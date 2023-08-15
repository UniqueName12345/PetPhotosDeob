package gnu.kawa.functions;

import gnu.mapping.OutPort;
import gnu.text.ReportFormat;
import java.io.IOException;
import java.io.Writer;
import java.text.FieldPosition;

/* compiled from: LispFormat.java */
/* loaded from: classes.dex */
class LispTabulateFormat extends ReportFormat {
    int colinc;
    int colnum;
    int padChar;
    boolean relative;

    public LispTabulateFormat(int colnum, int colinc, int padChar, boolean relative) {
        this.colnum = colnum;
        this.colinc = colinc;
        this.relative = relative;
        this.padChar = padChar;
    }

    @Override // gnu.text.ReportFormat
    public int format(Object[] args, int start, Writer dst, FieldPosition fpos) throws IOException {
        int spaces;
        int colnum = getParam(this.colnum, 1, args, start);
        if (this.colnum == -1610612736) {
            start++;
        }
        int colinc = getParam(this.colinc, 1, args, start);
        if (this.colinc == -1610612736) {
            start++;
        }
        char padChar = getParam(this.padChar, ' ', args, start);
        if (this.padChar == -1610612736) {
            start++;
        }
        int column = -1;
        if (dst instanceof OutPort) {
            column = ((OutPort) dst).getColumnNumber();
        }
        if (column >= 0) {
            if (!this.relative) {
                if (column < colnum) {
                    spaces = colnum - column;
                } else if (colinc <= 0) {
                    spaces = 0;
                } else {
                    spaces = colinc - ((column - colnum) % colinc);
                }
            } else {
                spaces = (colnum + colinc) - ((column + colnum) % colinc);
            }
        } else {
            spaces = this.relative ? colnum : 2;
        }
        while (true) {
            spaces--;
            if (spaces >= 0) {
                dst.write(padChar);
            } else {
                return start;
            }
        }
    }
}
