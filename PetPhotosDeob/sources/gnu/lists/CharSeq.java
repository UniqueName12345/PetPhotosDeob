package gnu.lists;

import java.io.IOException;

/* loaded from: classes.dex */
public interface CharSeq extends CharSequence, Sequence {
    @Override // java.lang.CharSequence
    char charAt(int i);

    void consume(int i, int i2, Consumer consumer);

    void fill(char c);

    void fill(int i, int i2, char c);

    void getChars(int i, int i2, char[] cArr, int i3);

    @Override // java.lang.CharSequence
    int length();

    void setCharAt(int i, char c);

    @Override // java.lang.CharSequence
    CharSequence subSequence(int i, int i2);

    @Override // java.lang.CharSequence
    String toString();

    void writeTo(int i, int i2, Appendable appendable) throws IOException;

    void writeTo(Appendable appendable) throws IOException;
}
