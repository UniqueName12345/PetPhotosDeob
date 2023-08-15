package com.google.appinventor.components.runtime.repackaged.org.json.zip;

import com.google.appinventor.components.runtime.repackaged.org.json.JSONException;

/* loaded from: classes2.dex */
public class Huff implements None, PostMortem {
    private final int domain;
    private final Symbol[] symbols;
    private Symbol table;
    private boolean upToDate = false;
    private int width;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class Symbol implements PostMortem {
        public final int integer;
        public long weight = 0;
        public Symbol next = null;
        public Symbol back = null;
        public Symbol one = null;
        public Symbol zero = null;

        public Symbol(int integer) {
            this.integer = integer;
        }

        @Override // com.google.appinventor.components.runtime.repackaged.org.json.zip.PostMortem
        public boolean postMortem(PostMortem pm) {
            boolean result = true;
            Symbol that = (Symbol) pm;
            if (this.integer == that.integer && this.weight == that.weight) {
                if ((this.back != null) == (that.back != null)) {
                    Symbol zero = this.zero;
                    Symbol one = this.one;
                    if (zero == null) {
                        if (that.zero != null) {
                            return false;
                        }
                    } else {
                        result = zero.postMortem(that.zero);
                    }
                    if (one == null) {
                        if (that.one != null) {
                            return false;
                        }
                    } else {
                        result = one.postMortem(that.one);
                    }
                    return result;
                }
                return false;
            }
            return false;
        }
    }

    public Huff(int domain) {
        this.domain = domain;
        int length = (domain * 2) - 1;
        this.symbols = new Symbol[length];
        for (int i = 0; i < domain; i++) {
            this.symbols[i] = new Symbol(i);
        }
        for (int i2 = domain; i2 < length; i2++) {
            this.symbols[i2] = new Symbol(-1);
        }
    }

    public void generate() {
        Symbol next;
        Symbol next2;
        if (!this.upToDate) {
            Symbol head = this.symbols[0];
            Symbol previous = head;
            this.table = null;
            head.next = null;
            for (int i = 1; i < this.domain; i++) {
                Symbol symbol = this.symbols[i];
                if (symbol.weight < head.weight) {
                    symbol.next = head;
                    head = symbol;
                } else {
                    if (symbol.weight < previous.weight) {
                        previous = head;
                    }
                    while (true) {
                        next2 = previous.next;
                        if (next2 == null || symbol.weight < next2.weight) {
                            break;
                        }
                        previous = next2;
                    }
                    symbol.next = next2;
                    previous.next = symbol;
                    previous = symbol;
                }
            }
            int avail = this.domain;
            Symbol previous2 = head;
            while (true) {
                Symbol first = head;
                Symbol second = first.next;
                head = second.next;
                Symbol symbol2 = this.symbols[avail];
                avail++;
                symbol2.weight = first.weight + second.weight;
                symbol2.zero = first;
                symbol2.one = second;
                symbol2.back = null;
                first.back = symbol2;
                second.back = symbol2;
                if (head != null) {
                    if (symbol2.weight < head.weight) {
                        symbol2.next = head;
                        head = symbol2;
                        previous2 = head;
                    } else {
                        while (true) {
                            next = previous2.next;
                            if (next == null || symbol2.weight < next.weight) {
                                break;
                            }
                            previous2 = next;
                        }
                        symbol2.next = next;
                        previous2.next = symbol2;
                        previous2 = symbol2;
                    }
                } else {
                    this.table = symbol2;
                    this.upToDate = true;
                    return;
                }
            }
        }
    }

    private boolean postMortem(int integer) {
        boolean z = true;
        int[] bits = new int[this.domain];
        Symbol symbol = this.symbols[integer];
        if (symbol.integer != integer) {
            return false;
        }
        int i = 0;
        while (true) {
            Symbol back = symbol.back;
            if (back != null) {
                if (back.zero == symbol) {
                    bits[i] = 0;
                } else if (back.one != symbol) {
                    return false;
                } else {
                    bits[i] = 1;
                }
                i++;
                symbol = back;
            } else if (symbol == this.table) {
                this.width = 0;
                Symbol symbol2 = this.table;
                while (symbol2.integer == -1) {
                    i--;
                    symbol2 = bits[i] != 0 ? symbol2.one : symbol2.zero;
                }
                if (symbol2.integer != integer || i != 0) {
                    z = false;
                }
                return z;
            } else {
                return false;
            }
        }
    }

    @Override // com.google.appinventor.components.runtime.repackaged.org.json.zip.PostMortem
    public boolean postMortem(PostMortem pm) {
        for (int integer = 0; integer < this.domain; integer++) {
            if (!postMortem(integer)) {
                JSONzip.log("\nBad huff ");
                JSONzip.logchar(integer, integer);
                return false;
            }
        }
        return this.table.postMortem(((Huff) pm).table);
    }

    public int read(BitReader bitreader) throws JSONException {
        try {
            this.width = 0;
            Symbol symbol = this.table;
            while (symbol.integer == -1) {
                this.width++;
                symbol = bitreader.bit() ? symbol.one : symbol.zero;
            }
            tick(symbol.integer);
            return symbol.integer;
        } catch (Throwable e) {
            throw new JSONException(e);
        }
    }

    public void tick(int value) {
        this.symbols[value].weight++;
        this.upToDate = false;
    }

    public void tick(int from, int to) {
        for (int value = from; value <= to; value++) {
            tick(value);
        }
    }

    private void write(Symbol symbol, BitWriter bitwriter) throws JSONException {
        try {
            Symbol back = symbol.back;
            if (back != null) {
                this.width++;
                write(back, bitwriter);
                if (back.zero == symbol) {
                    bitwriter.zero();
                } else {
                    bitwriter.one();
                }
            }
        } catch (Throwable e) {
            throw new JSONException(e);
        }
    }

    public void write(int value, BitWriter bitwriter) throws JSONException {
        this.width = 0;
        write(this.symbols[value], bitwriter);
        tick(value);
    }
}
