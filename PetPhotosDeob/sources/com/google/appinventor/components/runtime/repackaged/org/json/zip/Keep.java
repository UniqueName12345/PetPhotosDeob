package com.google.appinventor.components.runtime.repackaged.org.json.zip;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public abstract class Keep implements None, PostMortem {
    protected int capacity;
    protected int length = 0;
    protected int power = 0;
    protected long[] uses;

    public abstract Object value(int i);

    public Keep(int bits) {
        this.capacity = JSONzip.twos[bits];
        this.uses = new long[this.capacity];
    }

    public static long age(long use) {
        if (use >= 32) {
            return 16L;
        }
        return use / 2;
    }

    public int bitsize() {
        while (JSONzip.twos[this.power] < this.length) {
            this.power++;
        }
        return this.power;
    }

    public void tick(int integer) {
        long[] jArr = this.uses;
        jArr[integer] = jArr[integer] + 1;
    }
}
