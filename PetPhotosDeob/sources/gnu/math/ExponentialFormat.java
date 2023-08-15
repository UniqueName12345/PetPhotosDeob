package gnu.math;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

/* loaded from: classes.dex */
public class ExponentialFormat extends Format {
    static final double LOG10 = Math.log(10.0d);
    public int expDigits;
    public boolean exponentShowSign;
    public boolean general;
    public int intDigits;
    public char overflowChar;
    public char padChar;
    public boolean showPlus;
    public int width;
    public int fracDigits = -1;
    public char exponentChar = 'E';

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean addOne(StringBuffer sbuf, int digStart, int digEnd) {
        int j = digEnd;
        while (j != digStart) {
            j--;
            char ch = sbuf.charAt(j);
            if (ch != '9') {
                sbuf.setCharAt(j, (char) (ch + 1));
                return false;
            }
            sbuf.setCharAt(j, '0');
        }
        sbuf.insert(j, '1');
        return true;
    }

    public StringBuffer format(float value, StringBuffer sbuf, FieldPosition fpos) {
        return format(value, this.fracDigits < 0 ? Float.toString(value) : null, sbuf, fpos);
    }

    public StringBuffer format(double value, StringBuffer sbuf, FieldPosition fpos) {
        return format(value, this.fracDigits < 0 ? Double.toString(value) : null, sbuf, fpos);
    }

    StringBuffer format(double value, String dstr, StringBuffer sbuf, FieldPosition fpos) {
        int exponent;
        int digits;
        int scale;
        int exponentLen;
        int ee;
        int k = this.intDigits;
        int d = this.fracDigits;
        boolean negative = value < 0.0d;
        if (negative) {
            value = -value;
        }
        int oldLen = sbuf.length();
        int signLen = 1;
        if (negative) {
            if (d >= 0) {
                sbuf.append('-');
            }
        } else if (this.showPlus) {
            sbuf.append('+');
        } else {
            signLen = 0;
        }
        int digStart = sbuf.length();
        boolean nonFinite = Double.isNaN(value) || Double.isInfinite(value);
        if (d < 0 || nonFinite) {
            if (dstr == null) {
                dstr = Double.toString(value);
            }
            int indexE = dstr.indexOf(69);
            if (indexE >= 0) {
                sbuf.append(dstr);
                int indexE2 = indexE + digStart;
                boolean negexp = dstr.charAt(indexE2 + 1) == '-';
                exponent = 0;
                for (int i = indexE2 + (negexp ? 2 : 1); i < sbuf.length(); i++) {
                    exponent = (exponent * 10) + (sbuf.charAt(i) - '0');
                }
                if (negexp) {
                    exponent = -exponent;
                }
                sbuf.setLength(indexE2);
            } else {
                exponent = RealNum.toStringScientific(dstr, sbuf);
            }
            if (negative) {
                digStart++;
            }
            sbuf.deleteCharAt(digStart + 1);
            digits = sbuf.length() - digStart;
            if (digits > 1 && sbuf.charAt((digStart + digits) - 1) == '0') {
                digits--;
                sbuf.setLength(digStart + digits);
            }
            scale = (digits - exponent) - 1;
        } else {
            digits = d + (k > 0 ? 1 : k);
            int log = (int) ((Math.log(value) / LOG10) + 1000.0d);
            scale = (digits - (log == Integer.MIN_VALUE ? 0 : log - 1000)) - 1;
            RealNum.toScaledInt(value, scale).format(10, sbuf);
            exponent = (digits - 1) - scale;
        }
        int exponent2 = exponent - (k - 1);
        int exponentAbs = exponent2 < 0 ? -exponent2 : exponent2;
        if (exponentAbs >= 1000) {
            exponentLen = 4;
        } else {
            exponentLen = exponentAbs >= 100 ? 3 : exponentAbs >= 10 ? 2 : 1;
        }
        if (this.expDigits > exponentLen) {
            exponentLen = this.expDigits;
        }
        boolean showExponent = true;
        if (this.general) {
            ee = this.expDigits > 0 ? this.expDigits + 2 : 4;
        } else {
            ee = 0;
        }
        boolean fracUnspecified = d < 0;
        if (this.general || fracUnspecified) {
            int n = digits - scale;
            if (fracUnspecified) {
                d = n < 7 ? n : 7;
                if (digits > d) {
                    d = digits;
                }
            }
            int dd = d - n;
            if (this.general && n >= 0 && dd >= 0) {
                digits = d;
                k = n;
                showExponent = false;
            } else if (fracUnspecified) {
                if (this.width <= 0) {
                    digits = d;
                } else {
                    int avail = ((this.width - signLen) - exponentLen) - 3;
                    digits = avail;
                    if (k < 0) {
                        digits -= k;
                    }
                    if (digits > d) {
                        digits = d;
                    }
                }
                if (digits <= 0) {
                    digits = 1;
                }
            }
        }
        int digEnd = digStart + digits;
        while (sbuf.length() < digEnd) {
            sbuf.append('0');
        }
        char nextDigit = digEnd == sbuf.length() ? '0' : sbuf.charAt(digEnd);
        boolean addOne = nextDigit >= '5';
        if (addOne && addOne(sbuf, digStart, digEnd)) {
            scale++;
        }
        int length = scale - (sbuf.length() - digEnd);
        sbuf.setLength(digEnd);
        int dot = digStart;
        if (k < 0) {
            int j = k;
            while (true) {
                j++;
                if (j > 0) {
                    break;
                }
                sbuf.insert(digStart, '0');
            }
        } else {
            while (digStart + k > digEnd) {
                sbuf.append('0');
                digEnd++;
            }
            dot += k;
        }
        if (nonFinite) {
            showExponent = false;
        } else {
            sbuf.insert(dot, '.');
        }
        if (showExponent) {
            sbuf.append(this.exponentChar);
            if (this.exponentShowSign || exponent2 < 0) {
                sbuf.append(exponent2 >= 0 ? '+' : '-');
            }
            int i2 = sbuf.length();
            sbuf.append(exponentAbs);
            int newLen = sbuf.length();
            int j2 = this.expDigits - (newLen - i2);
            if (j2 > 0) {
                int i3 = newLen + j2;
                while (true) {
                    j2--;
                    if (j2 < 0) {
                        break;
                    }
                    sbuf.insert(i2, '0');
                }
            }
        } else {
            exponentLen = 0;
        }
        int used = sbuf.length() - oldLen;
        int i4 = this.width - used;
        if (fracUnspecified && ((dot + 1 == sbuf.length() || sbuf.charAt(dot + 1) == this.exponentChar) && (this.width <= 0 || i4 > 0))) {
            i4--;
            sbuf.insert(dot + 1, '0');
        }
        if ((i4 >= 0 || this.width <= 0) && (!showExponent || exponentLen <= this.expDigits || this.expDigits <= 0 || this.overflowChar == 0)) {
            if (k <= 0 && (i4 > 0 || this.width <= 0)) {
                sbuf.insert(digStart, '0');
                i4--;
            }
            if (!showExponent && this.width > 0) {
                while (true) {
                    ee--;
                    if (ee < 0) {
                        break;
                    }
                    sbuf.append(' ');
                    i4--;
                }
            }
            while (true) {
                i4--;
                if (i4 < 0) {
                    break;
                }
                sbuf.insert(oldLen, this.padChar);
            }
        } else if (this.overflowChar != 0) {
            sbuf.setLength(oldLen);
            int i5 = this.width;
            while (true) {
                i5--;
                if (i5 < 0) {
                    break;
                }
                sbuf.append(this.overflowChar);
            }
        }
        return sbuf;
    }

    public StringBuffer format(long num, StringBuffer sbuf, FieldPosition fpos) {
        return format(num, sbuf, fpos);
    }

    @Override // java.text.Format
    public StringBuffer format(Object num, StringBuffer sbuf, FieldPosition fpos) {
        return format(((RealNum) num).doubleValue(), sbuf, fpos);
    }

    public Number parse(String text, ParsePosition status) {
        throw new Error("ExponentialFormat.parse - not implemented");
    }

    @Override // java.text.Format
    public Object parseObject(String text, ParsePosition status) {
        throw new Error("ExponentialFormat.parseObject - not implemented");
    }
}
