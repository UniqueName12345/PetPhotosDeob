package gnu.math;

/* loaded from: classes.dex */
public class BitOps {
    static final byte[] bit4_count = {0, 1, 1, 2, 1, 2, 2, 3, 1, 2, 2, 3, 2, 3, 3, 4};

    private BitOps() {
    }

    public static boolean bitValue(IntNum x, int bitno) {
        int i = x.ival;
        if (x.words == null) {
            return bitno >= 32 ? i < 0 : ((i >> bitno) & 1) != 0;
        }
        int wordno = bitno >> 5;
        return wordno >= i ? x.words[i + (-1)] < 0 : ((x.words[wordno] >> bitno) & 1) != 0;
    }

    static int[] dataBufferFor(IntNum x, int bitno) {
        int[] data;
        int i = x.ival;
        int nwords = (bitno + 1) >> 5;
        if (x.words == null) {
            if (nwords == 0) {
                nwords = 1;
            }
            data = new int[nwords];
            data[0] = i;
            if (i < 0) {
                for (int j = 1; j < nwords; j++) {
                    data[j] = -1;
                }
            }
        } else {
            int nwords2 = (bitno + 1) >> 5;
            data = new int[nwords2 > i ? nwords2 : i];
            int j2 = i;
            while (true) {
                j2--;
                if (j2 < 0) {
                    break;
                }
                data[j2] = x.words[j2];
            }
            if (data[i - 1] < 0) {
                for (int j3 = i; j3 < nwords2; j3++) {
                    data[j3] = -1;
                }
            }
        }
        return data;
    }

    public static IntNum setBitValue(IntNum x, int bitno, int newValue) {
        int oldValue;
        int newValue2 = newValue & 1;
        int i = x.ival;
        if (x.words == null) {
            int oldValue2 = (i >> (bitno < 31 ? bitno : 31)) & 1;
            if (oldValue2 != newValue2) {
                if (bitno < 63) {
                    return IntNum.make((1 << bitno) ^ i);
                }
            } else {
                return x;
            }
        } else {
            int wordno = bitno >> 5;
            if (wordno >= i) {
                oldValue = x.words[i + (-1)] < 0 ? 1 : 0;
            } else {
                oldValue = (x.words[wordno] >> bitno) & 1;
            }
            if (oldValue == newValue2) {
                return x;
            }
        }
        int[] data = dataBufferFor(x, bitno);
        int i2 = bitno >> 5;
        data[i2] = (1 << (bitno & 31)) ^ data[i2];
        return IntNum.make(data, data.length);
    }

    public static boolean test(IntNum x, int y) {
        boolean z = false;
        if (x.words == null) {
            return (x.ival & y) != 0;
        }
        if (y < 0 || (x.words[0] & y) != 0) {
            z = true;
        }
        return z;
    }

    public static boolean test(IntNum x, IntNum y) {
        if (y.words == null) {
            return test(x, y.ival);
        }
        if (x.words == null) {
            return test(y, x.ival);
        }
        if (x.ival < y.ival) {
            x = y;
            y = x;
        }
        for (int i = 0; i < y.ival; i++) {
            if ((x.words[i] & y.words[i]) != 0) {
                return true;
            }
        }
        return y.isNegative();
    }

    public static IntNum and(IntNum x, int y) {
        if (x.words == null) {
            return IntNum.make(x.ival & y);
        }
        if (y >= 0) {
            return IntNum.make(x.words[0] & y);
        }
        int len = x.ival;
        int[] words = new int[len];
        words[0] = x.words[0] & y;
        while (true) {
            len--;
            if (len > 0) {
                words[len] = x.words[len];
            } else {
                return IntNum.make(words, x.ival);
            }
        }
    }

    public static IntNum and(IntNum x, IntNum y) {
        if (y.words == null) {
            return and(x, y.ival);
        }
        if (x.words == null) {
            return and(y, x.ival);
        }
        if (x.ival < y.ival) {
            x = y;
            y = x;
        }
        int len = y.isNegative() ? x.ival : y.ival;
        int[] words = new int[len];
        int i = 0;
        while (i < y.ival) {
            words[i] = x.words[i] & y.words[i];
            i++;
        }
        while (i < len) {
            words[i] = x.words[i];
            i++;
        }
        return IntNum.make(words, len);
    }

    public static IntNum ior(IntNum x, IntNum y) {
        return bitOp(7, x, y);
    }

    public static IntNum xor(IntNum x, IntNum y) {
        return bitOp(6, x, y);
    }

    public static IntNum not(IntNum x) {
        return bitOp(12, x, IntNum.zero());
    }

    public static int swappedOp(int op) {
        return "\u0000\u0001\u0004\u0005\u0002\u0003\u0006\u0007\b\t\f\r\n\u000b\u000e\u000f".charAt(op);
    }

    public static IntNum bitOp(int op, IntNum x, IntNum y) {
        switch (op) {
            case 0:
                return IntNum.zero();
            case 1:
                return and(x, y);
            case 3:
                return x;
            case 5:
                return y;
            case 15:
                return IntNum.minusOne();
            default:
                IntNum result = new IntNum();
                setBitOp(result, op, x, y);
                return result.canonicalize();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static void setBitOp(IntNum result, int op, IntNum x, IntNum y) {
        int yi;
        int ylen;
        int xi;
        int xlen;
        int i;
        int ni;
        int i2;
        if (y.words != null && (x.words == null || x.ival < y.ival)) {
            x = y;
            y = x;
            op = swappedOp(op);
        }
        if (y.words == null) {
            yi = y.ival;
            ylen = 1;
        } else {
            yi = y.words[0];
            ylen = y.ival;
        }
        if (x.words == null) {
            xi = x.ival;
            xlen = 1;
        } else {
            xi = x.words[0];
            xlen = x.ival;
        }
        if (xlen > 1) {
            result.realloc(xlen);
        }
        int[] w = result.words;
        int i3 = 0;
        int finish = 0;
        switch (op) {
            case 0:
                ni = 0;
                i = 0;
                break;
            case 1:
                while (true) {
                    i = i3;
                    ni = xi & yi;
                    if (i + 1 < ylen) {
                        i3 = i + 1;
                        w[i] = ni;
                        xi = x.words[i3];
                        yi = y.words[i3];
                    } else if (yi < 0) {
                        finish = 1;
                        break;
                    }
                }
                break;
            case 2:
                while (true) {
                    i = i3;
                    ni = xi & (yi ^ (-1));
                    if (i + 1 < ylen) {
                        i3 = i + 1;
                        w[i] = ni;
                        xi = x.words[i3];
                        yi = y.words[i3];
                    } else if (yi >= 0) {
                        finish = 1;
                        break;
                    }
                }
                break;
            case 3:
                ni = xi;
                finish = 1;
                i = 0;
                break;
            case 4:
                while (true) {
                    i = i3;
                    ni = (xi ^ (-1)) & yi;
                    if (i + 1 < ylen) {
                        i3 = i + 1;
                        w[i] = ni;
                        xi = x.words[i3];
                        yi = y.words[i3];
                    } else if (yi < 0) {
                        finish = 2;
                        break;
                    }
                }
                break;
            case 5:
                while (true) {
                    i = i3;
                    ni = yi;
                    if (i + 1 >= ylen) {
                        break;
                    } else {
                        i3 = i + 1;
                        w[i] = ni;
                        int xi2 = x.words[i3];
                        yi = y.words[i3];
                    }
                }
            case 6:
                while (true) {
                    i = i3;
                    ni = xi ^ yi;
                    if (i + 1 < ylen) {
                        i3 = i + 1;
                        w[i] = ni;
                        xi = x.words[i3];
                        yi = y.words[i3];
                    } else if (yi >= 0) {
                        finish = 1;
                        break;
                    } else {
                        finish = 2;
                        break;
                    }
                }
            case 7:
                while (true) {
                    i = i3;
                    ni = xi | yi;
                    if (i + 1 < ylen) {
                        i3 = i + 1;
                        w[i] = ni;
                        xi = x.words[i3];
                        yi = y.words[i3];
                    } else if (yi >= 0) {
                        finish = 1;
                        break;
                    }
                }
                break;
            case 8:
                while (true) {
                    i = i3;
                    ni = (xi | yi) ^ (-1);
                    if (i + 1 < ylen) {
                        i3 = i + 1;
                        w[i] = ni;
                        xi = x.words[i3];
                        yi = y.words[i3];
                    } else if (yi >= 0) {
                        finish = 2;
                        break;
                    }
                }
                break;
            case 9:
                while (true) {
                    i = i3;
                    ni = (xi ^ yi) ^ (-1);
                    if (i + 1 < ylen) {
                        i3 = i + 1;
                        w[i] = ni;
                        xi = x.words[i3];
                        yi = y.words[i3];
                    } else if (yi < 0) {
                        finish = 1;
                        break;
                    } else {
                        finish = 2;
                        break;
                    }
                }
            case 10:
                while (true) {
                    i = i3;
                    ni = yi ^ (-1);
                    if (i + 1 >= ylen) {
                        break;
                    } else {
                        i3 = i + 1;
                        w[i] = ni;
                        int xi3 = x.words[i3];
                        yi = y.words[i3];
                    }
                }
            case 11:
                while (true) {
                    i = i3;
                    ni = xi | (yi ^ (-1));
                    if (i + 1 < ylen) {
                        i3 = i + 1;
                        w[i] = ni;
                        xi = x.words[i3];
                        yi = y.words[i3];
                    } else if (yi < 0) {
                        finish = 1;
                        break;
                    }
                }
                break;
            case 12:
                ni = xi ^ (-1);
                finish = 2;
                i = 0;
                break;
            case 13:
                while (true) {
                    i = i3;
                    ni = (xi ^ (-1)) | yi;
                    if (i + 1 < ylen) {
                        i3 = i + 1;
                        w[i] = ni;
                        xi = x.words[i3];
                        yi = y.words[i3];
                    } else if (yi >= 0) {
                        finish = 2;
                        break;
                    }
                }
                break;
            case 14:
                while (true) {
                    i = i3;
                    ni = (xi & yi) ^ (-1);
                    if (i + 1 < ylen) {
                        i3 = i + 1;
                        w[i] = ni;
                        xi = x.words[i3];
                        yi = y.words[i3];
                    } else if (yi < 0) {
                        finish = 2;
                        break;
                    }
                }
                break;
            default:
                ni = -1;
                i = 0;
                break;
        }
        if (i + 1 == xlen) {
            finish = 0;
        }
        switch (finish) {
            case 0:
                if (i == 0 && w == null) {
                    result.ival = ni;
                    return;
                }
                i2 = i + 1;
                w[i] = ni;
                break;
            case 1:
                w[i] = ni;
                i2 = i;
                while (true) {
                    i2++;
                    if (i2 >= xlen) {
                        break;
                    } else {
                        w[i2] = x.words[i2];
                    }
                }
            case 2:
                w[i] = ni;
                i2 = i;
                while (true) {
                    i2++;
                    if (i2 >= xlen) {
                        break;
                    } else {
                        w[i2] = x.words[i2] ^ (-1);
                    }
                }
            default:
                i2 = i;
                break;
        }
        result.ival = i2;
    }

    public static IntNum extract(IntNum x, int startBit, int endBit) {
        int x_len;
        long l;
        if (endBit < 32) {
            int word0 = x.words == null ? x.ival : x.words[0];
            return IntNum.make(((((-1) << endBit) ^ (-1)) & word0) >> startBit);
        }
        if (x.words == null) {
            if (x.ival >= 0) {
                return IntNum.make(startBit >= 31 ? 0 : x.ival >> startBit);
            }
            x_len = 1;
        } else {
            x_len = x.ival;
        }
        boolean neg = x.isNegative();
        if (endBit > x_len * 32) {
            endBit = x_len * 32;
            if (!neg && startBit == 0) {
                return x;
            }
        } else {
            x_len = (endBit + 31) >> 5;
        }
        int length = endBit - startBit;
        if (length < 64) {
            if (x.words == null) {
                l = x.ival >> (startBit >= 32 ? 31 : startBit);
            } else {
                l = MPN.rshift_long(x.words, x_len, startBit);
            }
            return IntNum.make((((-1) << length) ^ (-1)) & l);
        }
        int startWord = startBit >> 5;
        int buf_len = ((endBit >> 5) + 1) - startWord;
        int[] buf = new int[buf_len];
        if (x.words == null) {
            buf[0] = startBit >= 32 ? -1 : x.ival >> startBit;
        } else {
            MPN.rshift0(buf, x.words, startWord, x_len - startWord, startBit & 31);
        }
        int x_len2 = length >> 5;
        buf[x_len2] = buf[x_len2] & (((-1) << length) ^ (-1));
        return IntNum.make(buf, x_len2 + 1);
    }

    public static int lowestBitSet(int i) {
        if (i == 0) {
            return -1;
        }
        int index = 0;
        while ((i & 255) == 0) {
            i >>>= 8;
            index += 8;
        }
        while ((i & 3) == 0) {
            i >>>= 2;
            index += 2;
        }
        if ((i & 1) == 0) {
            return index + 1;
        }
        return index;
    }

    public static int lowestBitSet(IntNum x) {
        int[] x_words = x.words;
        if (x_words == null) {
            return lowestBitSet(x.ival);
        }
        int x_len = x.ival;
        while (0 < x_len) {
            int b = lowestBitSet(x_words[0]);
            if (b >= 0) {
                return b + 0;
            }
        }
        return -1;
    }

    public static int bitCount(int i) {
        int count = 0;
        while (i != 0) {
            count += bit4_count[i & 15];
            i >>>= 4;
        }
        return count;
    }

    public static int bitCount(int[] x, int len) {
        int count = 0;
        while (true) {
            len--;
            if (len >= 0) {
                count += bitCount(x[len]);
            } else {
                return count;
            }
        }
    }

    public static int bitCount(IntNum x) {
        int x_len;
        int i;
        int[] x_words = x.words;
        if (x_words == null) {
            x_len = 1;
            i = bitCount(x.ival);
        } else {
            x_len = x.ival;
            i = bitCount(x_words, x_len);
        }
        return x.isNegative() ? (x_len * 32) - i : i;
    }

    public static IntNum reverseBits(IntNum x, int start, int end) {
        int wi;
        int ival = x.ival;
        int[] xwords = x.words;
        if (xwords == null && end < 63) {
            long w = ival;
            int i = start;
            for (int j = end - 1; i < j; j--) {
                long bitj = (w >> j) & 1;
                w = (((w >> i) & 1) << j) | (w & (((1 << i) | (1 << j)) ^ (-1))) | (bitj << i);
                i++;
            }
            return IntNum.make(w);
        }
        int[] data = dataBufferFor(x, end - 1);
        int i2 = start;
        for (int j2 = end - 1; i2 < j2; j2--) {
            int ii = i2 >> 5;
            int jj = j2 >> 5;
            int wi2 = data[ii];
            int biti = (wi2 >> i2) & 1;
            if (ii == jj) {
                int bitj2 = (wi2 >> j2) & 1;
                wi = (biti << j2) | ((int) (wi2 & (((1 << i2) | (1 << j2)) ^ (-1)))) | (bitj2 << i2);
            } else {
                int wj = data[jj];
                int bitj3 = (wj >> (j2 & 31)) & 1;
                wi = (wi2 & ((1 << (i2 & 31)) ^ (-1))) | (bitj3 << (i2 & 31));
                data[jj] = (wj & ((1 << (j2 & 31)) ^ (-1))) | (biti << (j2 & 31));
            }
            data[ii] = wi;
            i2++;
        }
        return IntNum.make(data, data.length);
    }
}
