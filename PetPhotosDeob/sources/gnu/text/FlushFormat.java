package gnu.text;

import java.io.IOException;
import java.io.Writer;
import java.text.FieldPosition;

/* loaded from: classes.dex */
public class FlushFormat extends ReportFormat {
    private static FlushFormat flush;

    public static FlushFormat getInstance() {
        if (flush == null) {
            flush = new FlushFormat();
        }
        return flush;
    }

    @Override // gnu.text.ReportFormat
    public int format(Object[] args, int start, Writer dst, FieldPosition fpos) throws IOException {
        dst.flush();
        return start;
    }
}
