package gnu.lists;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/* loaded from: classes.dex */
public class UnescapedData implements CharSequence, Externalizable {
    String data;

    public UnescapedData() {
    }

    public UnescapedData(String data) {
        this.data = data;
    }

    public final String getData() {
        return this.data;
    }

    @Override // java.lang.CharSequence
    public final String toString() {
        return this.data;
    }

    public final boolean equals(Object other) {
        return (other instanceof UnescapedData) && this.data.equals(other.toString());
    }

    public final int hashCode() {
        if (this.data == null) {
            return 0;
        }
        return this.data.hashCode();
    }

    @Override // java.lang.CharSequence
    public int length() {
        return this.data.length();
    }

    @Override // java.lang.CharSequence
    public char charAt(int index) {
        return this.data.charAt(index);
    }

    @Override // java.lang.CharSequence
    public CharSequence subSequence(int start, int end) {
        return new UnescapedData(this.data.substring(start, end));
    }

    @Override // java.io.Externalizable
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.data);
    }

    @Override // java.io.Externalizable
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.data = (String) in.readObject();
    }
}
