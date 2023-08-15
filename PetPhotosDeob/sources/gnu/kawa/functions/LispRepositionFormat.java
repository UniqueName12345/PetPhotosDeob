package gnu.kawa.functions;

import gnu.text.ReportFormat;
import java.io.IOException;
import java.io.Writer;
import java.text.FieldPosition;

/* compiled from: LispFormat.java */
/* loaded from: classes.dex */
class LispRepositionFormat extends ReportFormat {
    boolean absolute;
    boolean backwards;
    int count;

    public LispRepositionFormat(int count, boolean backwards, boolean absolute) {
        this.count = count;
        this.backwards = backwards;
        this.absolute = absolute;
    }

    @Override // gnu.text.ReportFormat
    public int format(Object[] args, int start, Writer dst, FieldPosition fpos) throws IOException {
        int count = getParam(this.count, this.absolute ? 0 : 1, args, start);
        if (!this.absolute) {
            if (this.backwards) {
                count = -count;
            }
            count += start;
        }
        if (count < 0) {
            return 0;
        }
        return count > args.length ? args.length : count;
    }
}
