package gnu.xquery.util;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.text.CollationKey;
import java.text.Collator;

/* loaded from: classes.dex */
public class NamedCollator extends Collator implements Externalizable {
    public static final String UNICODE_CODEPOINT_COLLATION = "http://www.w3.org/2005/xpath-functions/collation/codepoint";
    public static final NamedCollator codepointCollation = new NamedCollator();
    Collator collator;
    String name;

    public static NamedCollator make(String name) {
        NamedCollator coll = new NamedCollator();
        coll.name = name;
        coll.resolve();
        return coll;
    }

    public String getName() {
        return this.name;
    }

    public static NamedCollator find(String name) {
        return make(name);
    }

    static {
        codepointCollation.name = UNICODE_CODEPOINT_COLLATION;
    }

    public void resolve() {
        if (this.name != null && !this.name.equals(UNICODE_CODEPOINT_COLLATION)) {
            throw new RuntimeException("unknown collation: " + this.name);
        }
    }

    public static int codepointCompare(String str1, String str2) {
        int len1 = str1.length();
        int len2 = str2.length();
        int i2 = 0;
        int i1 = 0;
        while (i1 != len1) {
            if (i2 == len2) {
                return 1;
            }
            int i12 = i1 + 1;
            int c1 = str1.charAt(i1);
            if (c1 >= 55296 && c1 < 56320 && i12 < len1) {
                c1 = ((c1 - 55296) * 1024) + (str1.charAt(i12) - 56320) + 65536;
                i12++;
            }
            int i22 = i2 + 1;
            int c2 = str2.charAt(i2);
            if (c2 >= 55296 && c2 < 56320 && i22 < len2) {
                c2 = ((c2 - 55296) * 1024) + (str2.charAt(i22) - 56320) + 65536;
                i22++;
            }
            if (c1 != c2) {
                return c1 < c2 ? -1 : 1;
            }
            i2 = i22;
            i1 = i12;
        }
        return i2 == len2 ? 0 : -1;
    }

    @Override // java.text.Collator
    public int compare(String str1, String str2) {
        return this.collator != null ? this.collator.compare(str1, str2) : codepointCompare(str1, str2);
    }

    @Override // java.text.Collator
    public CollationKey getCollationKey(String source) {
        return this.collator.getCollationKey(source);
    }

    @Override // java.text.Collator
    public int hashCode() {
        if (this.collator != null) {
            return this.collator.hashCode();
        }
        return 0;
    }

    @Override // java.io.Externalizable
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(this.name);
    }

    @Override // java.io.Externalizable
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.name = in.readUTF();
        resolve();
    }
}
