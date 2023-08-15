package gnu.kawa.functions;

import gnu.math.IntNum;
import gnu.text.ReportFormat;
import java.io.IOException;
import java.io.Writer;
import java.text.FieldPosition;

/* compiled from: LispFormat.java */
/* loaded from: classes.dex */
class LispPluralFormat extends ReportFormat {
    boolean backup;
    boolean y;

    LispPluralFormat() {
    }

    public static LispPluralFormat getInstance(boolean backup, boolean y) {
        LispPluralFormat fmt = new LispPluralFormat();
        fmt.backup = backup;
        fmt.y = y;
        return fmt;
    }

    @Override // gnu.text.ReportFormat
    public int format(Object[] args, int start, Writer dst, FieldPosition fpos) throws IOException {
        if (this.backup) {
            start--;
        }
        int start2 = start + 1;
        Object arg = args[start];
        boolean plural = arg != IntNum.one();
        if (this.y) {
            print(dst, plural ? "ies" : "y");
        } else if (plural) {
            dst.write(115);
        }
        return start2;
    }
}
