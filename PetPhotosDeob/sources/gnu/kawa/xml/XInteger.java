package gnu.kawa.xml;

import gnu.math.IntNum;

/* loaded from: classes.dex */
public class XInteger extends IntNum {
    private XIntegerType type;

    public XIntegerType getIntegerType() {
        return this.type;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public XInteger(IntNum value, XIntegerType type) {
        this.words = value.words;
        this.ival = value.ival;
        this.type = type;
    }
}
