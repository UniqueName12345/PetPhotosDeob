package gnu.lists;

/* loaded from: classes.dex */
public interface Consumer extends Appendable {
    @Override // java.lang.Appendable
    Consumer append(char c);

    @Override // java.lang.Appendable
    Consumer append(CharSequence charSequence);

    @Override // java.lang.Appendable
    Consumer append(CharSequence charSequence, int i, int i2);

    void endAttribute();

    void endDocument();

    void endElement();

    boolean ignoring();

    void startAttribute(Object obj);

    void startDocument();

    void startElement(Object obj);

    void write(int i);

    void write(CharSequence charSequence, int i, int i2);

    void write(String str);

    void write(char[] cArr, int i, int i2);

    void writeBoolean(boolean z);

    void writeDouble(double d);

    void writeFloat(float f);

    void writeInt(int i);

    void writeLong(long j);

    void writeObject(Object obj);
}
