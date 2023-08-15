package gnu.kawa.functions;

import gnu.text.Char;
import gnu.text.ReportFormat;
import java.io.IOException;
import java.io.Writer;
import java.text.FieldPosition;

/* compiled from: LispFormat.java */
/* loaded from: classes.dex */
class LispCharacterFormat extends ReportFormat {
    int charVal;
    int count;
    boolean seenAt;
    boolean seenColon;

    LispCharacterFormat() {
    }

    public static LispCharacterFormat getInstance(int charVal, int count, boolean seenAt, boolean seenColon) {
        LispCharacterFormat fmt = new LispCharacterFormat();
        fmt.count = count;
        fmt.charVal = charVal;
        fmt.seenAt = seenAt;
        fmt.seenColon = seenColon;
        return fmt;
    }

    @Override // gnu.text.ReportFormat
    public int format(Object[] args, int start, Writer dst, FieldPosition fpos) throws IOException {
        int count = getParam(this.count, 1, args, start);
        if (this.count == -1610612736) {
            start++;
        }
        int charVal = getParam(this.charVal, '?', args, start);
        if (this.charVal == -1610612736) {
            start++;
        }
        while (true) {
            count--;
            if (count >= 0) {
                printChar(charVal, this.seenAt, this.seenColon, dst);
            } else {
                return start;
            }
        }
    }

    public static void printChar(int ch, boolean seenAt, boolean seenColon, Writer dst) throws IOException {
        if (seenAt) {
            print(dst, Char.toScmReadableString(ch));
        } else if (seenColon) {
            if (ch < 32) {
                dst.write(94);
                dst.write(ch + 64);
            } else if (ch >= 127) {
                print(dst, "#\\x");
                print(dst, Integer.toString(ch, 16));
            } else {
                dst.write(ch);
            }
        } else {
            dst.write(ch);
        }
    }
}
