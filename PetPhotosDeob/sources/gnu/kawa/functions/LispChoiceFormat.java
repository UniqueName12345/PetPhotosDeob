package gnu.kawa.functions;

import gnu.text.ReportFormat;
import java.io.IOException;
import java.io.Writer;
import java.text.FieldPosition;

/* compiled from: LispFormat.java */
/* loaded from: classes.dex */
class LispChoiceFormat extends ReportFormat {
    java.text.Format[] choices;
    boolean lastIsDefault;
    int param;
    boolean skipIfFalse;
    boolean testBoolean;

    @Override // gnu.text.ReportFormat
    public int format(Object[] args, int start, Writer dst, FieldPosition fpos) throws IOException {
        java.text.Format fmt;
        if (this.testBoolean) {
            fmt = this.choices[args[start] != Boolean.FALSE ? (char) 1 : (char) 0];
            start++;
        } else if (!this.skipIfFalse) {
            int index = getParam(this.param, -1610612736, args, start);
            if (this.param == -1610612736) {
                start++;
            }
            if (index < 0 || index >= this.choices.length) {
                if (!this.lastIsDefault) {
                    return start;
                }
                index = this.choices.length - 1;
            }
            fmt = this.choices[index];
        } else if (args[start] == Boolean.FALSE) {
            return start + 1;
        } else {
            fmt = this.choices[0];
        }
        return ReportFormat.format(fmt, args, start, dst, fpos);
    }
}
