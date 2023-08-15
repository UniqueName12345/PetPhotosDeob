package gnu.lists;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Writer;

/* loaded from: classes.dex */
public class FString extends SimpleVector implements Comparable, Appendable, CharSeq, Externalizable, Consumable {
    protected static char[] empty = new char[0];
    public char[] data;

    public FString() {
        this.data = empty;
    }

    public FString(int num) {
        this.size = num;
        this.data = new char[num];
    }

    public FString(int num, char value) {
        char[] array = new char[num];
        this.data = array;
        this.size = num;
        while (true) {
            num--;
            if (num >= 0) {
                array[num] = value;
            } else {
                return;
            }
        }
    }

    public FString(char[] values) {
        this.size = values.length;
        this.data = values;
    }

    public FString(String str) {
        this.data = str.toCharArray();
        this.size = this.data.length;
    }

    public FString(StringBuffer buffer) {
        this(buffer, 0, buffer.length());
    }

    public FString(StringBuffer buffer, int offset, int length) {
        this.size = length;
        this.data = new char[length];
        if (length > 0) {
            buffer.getChars(offset, offset + length, this.data, 0);
        }
    }

    public FString(char[] buffer, int offset, int length) {
        this.size = length;
        this.data = new char[length];
        System.arraycopy(buffer, offset, this.data, 0, length);
    }

    public FString(Sequence seq) {
        this.data = new char[seq.size()];
        addAll(seq);
    }

    public FString(CharSeq seq) {
        this(seq, 0, seq.size());
    }

    public FString(CharSeq seq, int offset, int length) {
        char[] data = new char[length];
        seq.getChars(offset, offset + length, data, 0);
        this.data = data;
        this.size = length;
    }

    public FString(CharSequence seq) {
        this(seq, 0, seq.length());
    }

    public FString(CharSequence seq, int offset, int length) {
        char[] data = new char[length];
        int i = length;
        while (true) {
            i--;
            if (i >= 0) {
                data[i] = seq.charAt(offset + i);
            } else {
                this.data = data;
                this.size = length;
                return;
            }
        }
    }

    @Override // gnu.lists.CharSeq, java.lang.CharSequence
    public int length() {
        return this.size;
    }

    @Override // gnu.lists.SimpleVector
    public int getBufferLength() {
        return this.data.length;
    }

    @Override // gnu.lists.SimpleVector
    public void setBufferLength(int length) {
        int oldLength = this.data.length;
        if (oldLength != length) {
            char[] tmp = new char[length];
            char[] cArr = this.data;
            if (oldLength >= length) {
                oldLength = length;
            }
            System.arraycopy(cArr, 0, tmp, 0, oldLength);
            this.data = tmp;
        }
    }

    public void ensureBufferLength(int sz) {
        if (sz > this.data.length) {
            char[] d = new char[sz < 60 ? 120 : sz * 2];
            System.arraycopy(this.data, 0, d, 0, sz);
            this.data = d;
        }
    }

    @Override // gnu.lists.SimpleVector
    protected Object getBuffer() {
        return this.data;
    }

    @Override // gnu.lists.SimpleVector
    public final Object getBuffer(int index) {
        return Convert.toObject(this.data[index]);
    }

    @Override // gnu.lists.SimpleVector
    public final Object setBuffer(int index, Object value) {
        Object old = Convert.toObject(this.data[index]);
        this.data[index] = Convert.toChar(value);
        return old;
    }

    @Override // gnu.lists.SimpleVector, gnu.lists.AbstractSequence
    public final Object get(int index) {
        if (index >= this.size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return Convert.toObject(this.data[index]);
    }

    @Override // gnu.lists.CharSeq, java.lang.CharSequence
    public final char charAt(int index) {
        if (index >= this.size) {
            throw new StringIndexOutOfBoundsException(index);
        }
        return this.data[index];
    }

    public final char charAtBuffer(int index) {
        return this.data[index];
    }

    @Override // gnu.lists.CharSeq
    public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
        if (srcBegin < 0 || srcBegin > srcEnd) {
            throw new StringIndexOutOfBoundsException(srcBegin);
        }
        if (srcEnd > this.size) {
            throw new StringIndexOutOfBoundsException(srcEnd);
        }
        if ((dstBegin + srcEnd) - srcBegin > dst.length) {
            throw new StringIndexOutOfBoundsException(dstBegin);
        }
        if (srcBegin < srcEnd) {
            System.arraycopy(this.data, srcBegin, dst, dstBegin, srcEnd - srcBegin);
        }
    }

    public void getChars(int srcBegin, int srcEnd, StringBuffer dst) {
        if (srcBegin < 0 || srcBegin > srcEnd) {
            throw new StringIndexOutOfBoundsException(srcBegin);
        }
        if (srcEnd > this.size) {
            throw new StringIndexOutOfBoundsException(srcEnd);
        }
        if (srcBegin < srcEnd) {
            dst.append(this.data, srcBegin, srcEnd - srcBegin);
        }
    }

    public void getChars(StringBuffer dst) {
        dst.append(this.data, 0, this.size);
    }

    public char[] toCharArray() {
        int val_length = this.data.length;
        int seq_length = this.size;
        if (seq_length == val_length) {
            return this.data;
        }
        char[] tmp = new char[seq_length];
        System.arraycopy(this.data, 0, tmp, 0, seq_length);
        return tmp;
    }

    @Override // gnu.lists.SimpleVector
    public void shift(int srcStart, int dstStart, int count) {
        System.arraycopy(this.data, srcStart, this.data, dstStart, count);
    }

    public FString copy(int start, int end) {
        char[] copy = new char[end - start];
        char[] src = this.data;
        for (int i = start; i < end; i++) {
            copy[i - start] = src[i];
        }
        return new FString(copy);
    }

    public boolean addAll(FString s) {
        int newSize = this.size + s.size;
        if (this.data.length < newSize) {
            setBufferLength(newSize);
        }
        System.arraycopy(s.data, 0, this.data, this.size, s.size);
        this.size = newSize;
        return s.size > 0;
    }

    public boolean addAll(CharSequence s) {
        int ssize = s.length();
        int newSize = this.size + ssize;
        if (this.data.length < newSize) {
            setBufferLength(newSize);
        }
        if (s instanceof FString) {
            System.arraycopy(((FString) s).data, 0, this.data, this.size, ssize);
        } else if (s instanceof String) {
            ((String) s).getChars(0, ssize, this.data, this.size);
        } else if (s instanceof CharSeq) {
            ((CharSeq) s).getChars(0, ssize, this.data, this.size);
        } else {
            int i = ssize;
            while (true) {
                i--;
                if (i < 0) {
                    break;
                }
                this.data[this.size + i] = s.charAt(i);
            }
        }
        this.size = newSize;
        return ssize > 0;
    }

    public void addAllStrings(Object[] args, int startIndex) {
        int total = this.size;
        for (int i = startIndex; i < args.length; i++) {
            Object arg = args[i];
            total += ((CharSequence) arg).length();
        }
        if (this.data.length < total) {
            setBufferLength(total);
        }
        for (int i2 = startIndex; i2 < args.length; i2++) {
            Object arg2 = args[i2];
            addAll((CharSequence) arg2);
        }
    }

    @Override // gnu.lists.AbstractSequence
    public String toString() {
        return new String(this.data, 0, this.size);
    }

    public String substring(int start, int end) {
        return new String(this.data, start, end - start);
    }

    @Override // gnu.lists.CharSeq, java.lang.CharSequence
    public CharSequence subSequence(int start, int end) {
        return new FString(this.data, start, end - start);
    }

    @Override // gnu.lists.CharSeq
    public void setCharAt(int index, char ch) {
        if (index < 0 || index >= this.size) {
            throw new StringIndexOutOfBoundsException(index);
        }
        this.data[index] = ch;
    }

    public void setCharAtBuffer(int index, char ch) {
        this.data[index] = ch;
    }

    @Override // gnu.lists.CharSeq
    public final void fill(char ch) {
        char[] d = this.data;
        int i = this.size;
        while (true) {
            i--;
            if (i >= 0) {
                d[i] = ch;
            } else {
                return;
            }
        }
    }

    @Override // gnu.lists.CharSeq
    public void fill(int fromIndex, int toIndex, char value) {
        if (fromIndex < 0 || toIndex > this.size) {
            throw new IndexOutOfBoundsException();
        }
        char[] d = this.data;
        for (int i = fromIndex; i < toIndex; i++) {
            d[i] = value;
        }
    }

    @Override // gnu.lists.SimpleVector
    protected void clearBuffer(int start, int count) {
        char[] d = this.data;
        while (true) {
            int start2 = start;
            count--;
            if (count >= 0) {
                start = start2 + 1;
                d[start2] = 0;
            } else {
                return;
            }
        }
    }

    public void replace(int where, char[] chars, int start, int count) {
        System.arraycopy(chars, start, this.data, where, count);
    }

    public void replace(int where, String string) {
        string.getChars(0, string.length(), this.data, where);
    }

    @Override // gnu.lists.AbstractSequence, java.util.List, java.util.Collection
    public int hashCode() {
        char[] val = this.data;
        int len = this.size;
        int hash = 0;
        for (int i = 0; i < len; i++) {
            hash = (hash * 31) + val[i];
        }
        return hash;
    }

    @Override // gnu.lists.AbstractSequence, java.util.List, java.util.Collection
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof FString)) {
            return false;
        }
        char[] str = ((FString) obj).data;
        int n = this.size;
        if (str == null || str.length != n) {
            return false;
        }
        char[] d = this.data;
        int i = n;
        do {
            i--;
            if (i < 0) {
                return true;
            }
        } while (d[i] == str[i]);
        return false;
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        FString str2 = (FString) obj;
        char[] cs1 = this.data;
        char[] cs2 = str2.data;
        int n1 = this.size;
        int n2 = str2.size;
        int n = n1 > n2 ? n2 : n1;
        for (int i = 0; i < n; i++) {
            char c1 = cs1[i];
            char c2 = cs2[i];
            int d = c1 - c2;
            if (d != 0) {
                return d;
            }
        }
        return n1 - n2;
    }

    @Override // gnu.lists.SimpleVector
    public int getElementKind() {
        return 29;
    }

    @Override // gnu.lists.AbstractSequence, gnu.lists.Consumable
    public void consume(Consumer out) {
        out.write(this.data, 0, this.data.length);
    }

    @Override // gnu.lists.SimpleVector, gnu.lists.AbstractSequence
    public boolean consumeNext(int ipos, Consumer out) {
        int index = ipos >>> 1;
        if (index >= this.size) {
            return false;
        }
        out.write(this.data[index]);
        return true;
    }

    @Override // gnu.lists.SimpleVector, gnu.lists.AbstractSequence
    public void consumePosRange(int iposStart, int iposEnd, Consumer out) {
        if (!out.ignoring()) {
            int i = iposStart >>> 1;
            int end = iposEnd >>> 1;
            if (end > this.size) {
                end = this.size;
            }
            if (end > i) {
                out.write(this.data, i, end - i);
            }
        }
    }

    @Override // java.lang.Appendable
    public FString append(char c) {
        int sz = this.size;
        if (sz >= this.data.length) {
            ensureBufferLength(sz + 1);
        }
        char[] d = this.data;
        d[sz] = c;
        this.size = sz + 1;
        return this;
    }

    @Override // java.lang.Appendable
    public FString append(CharSequence csq) {
        if (csq == null) {
            csq = "null";
        }
        return append(csq, 0, csq.length());
    }

    @Override // java.lang.Appendable
    public FString append(CharSequence csq, int start, int end) {
        if (csq == null) {
            csq = "null";
        }
        int len = end - start;
        int sz = this.size;
        if (sz + len > this.data.length) {
            ensureBufferLength(sz + len);
        }
        char[] d = this.data;
        if (csq instanceof String) {
            ((String) csq).getChars(start, end, d, sz);
        } else if (csq instanceof CharSeq) {
            ((CharSeq) csq).getChars(start, end, d, sz);
        } else {
            int i = start;
            int j = sz;
            while (i < end) {
                d[j] = csq.charAt(i);
                i++;
                j++;
            }
        }
        this.size = sz;
        return this;
    }

    @Override // gnu.lists.CharSeq
    public void writeTo(int start, int count, Appendable dest) throws IOException {
        if (dest instanceof Writer) {
            try {
                ((Writer) dest).write(this.data, start, count);
                return;
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        dest.append(this, start, start + count);
    }

    @Override // gnu.lists.CharSeq
    public void writeTo(Appendable dest) throws IOException {
        writeTo(0, this.size, dest);
    }

    @Override // java.io.Externalizable
    public void writeExternal(ObjectOutput out) throws IOException {
        int size = this.size;
        out.writeInt(size);
        char[] d = this.data;
        for (int i = 0; i < size; i++) {
            out.writeChar(d[i]);
        }
    }

    @Override // java.io.Externalizable
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int size = in.readInt();
        char[] data = new char[size];
        for (int i = 0; i < size; i++) {
            data[i] = in.readChar();
        }
        this.data = data;
        this.size = size;
    }
}
