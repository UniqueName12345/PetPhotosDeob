package com.google.appinventor.components.runtime.util;

import com.google.appinventor.components.runtime.collect.Lists;
import com.google.appinventor.components.runtime.repackaged.org.json.HTTP;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public final class CsvUtil {
    private CsvUtil() {
    }

    public static YailList fromCsvTable(String csvString) throws Exception {
        CsvParser csvParser = new CsvParser(new StringReader(csvString));
        ArrayList<YailList> csvList = new ArrayList<>();
        while (csvParser.hasNext()) {
            csvList.add(YailList.makeList((List) csvParser.next()));
        }
        csvParser.throwAnyProblem();
        return YailList.makeList((List) csvList);
    }

    public static YailList fromCsvRow(String csvString) throws Exception {
        CsvParser csvParser = new CsvParser(new StringReader(csvString));
        if (csvParser.hasNext()) {
            YailList row = YailList.makeList((List) csvParser.next());
            if (csvParser.hasNext()) {
                throw new IllegalArgumentException("CSV text has multiple rows. Expected just one row.");
            }
            csvParser.throwAnyProblem();
            return row;
        }
        throw new IllegalArgumentException("CSV text cannot be parsed as a row.");
    }

    public static String toCsvRow(YailList csvRow) {
        StringBuilder csvStringBuilder = new StringBuilder();
        makeCsvRow(csvRow, csvStringBuilder);
        return csvStringBuilder.toString();
    }

    public static String toCsvTable(YailList csvList) {
        Object[] array;
        StringBuilder csvStringBuilder = new StringBuilder();
        for (Object rowObj : csvList.toArray()) {
            makeCsvRow((YailList) rowObj, csvStringBuilder);
            csvStringBuilder.append(HTTP.CRLF);
        }
        return csvStringBuilder.toString();
    }

    private static void makeCsvRow(YailList row, StringBuilder csvStringBuilder) {
        Object[] array;
        String fieldDelim = "";
        for (Object fieldObj : row.toArray()) {
            String field = fieldObj.toString();
            csvStringBuilder.append(fieldDelim).append("\"").append(field.replaceAll("\"", "\"\"")).append("\"");
            fieldDelim = ",";
        }
    }

    /* loaded from: classes.dex */
    private static class CsvParser implements Iterator<List<String>> {
        private final Reader in;
        private Exception lastException;
        private int limit;
        private int pos;
        private long previouslyRead;
        private final Pattern ESCAPED_QUOTE_PATTERN = Pattern.compile("\"\"");
        private final char[] buf = new char[10240];
        private boolean opened = true;
        private int cellLength = -1;
        private int delimitedCellLength = -1;

        public CsvParser(Reader in) {
            this.in = in;
        }

        public void skip(long charPosition) throws IOException {
            int n;
            while (charPosition > 0 && (n = this.in.read(this.buf, 0, Math.min((int) charPosition, this.buf.length))) >= 0) {
                this.previouslyRead += n;
                charPosition -= n;
            }
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (this.limit == 0) {
                fill();
            }
            return (this.pos < this.limit || indexAfterCompactionAndFilling(this.pos) < this.limit) && lookingAtCell();
        }

        @Override // java.util.Iterator
        public List<String> next() {
            ArrayList<String> result = Lists.newArrayList();
            do {
                if (this.buf[this.pos] != '\"') {
                    result.add(new String(this.buf, this.pos, this.cellLength).trim());
                } else {
                    String cell = new String(this.buf, this.pos + 1, this.cellLength - 2);
                    result.add(this.ESCAPED_QUOTE_PATTERN.matcher(cell).replaceAll("\"").trim());
                }
                boolean trailingComma = this.delimitedCellLength > 0 && this.buf[(this.pos + this.delimitedCellLength) + (-1)] == ',';
                this.pos += this.delimitedCellLength;
                this.cellLength = -1;
                this.delimitedCellLength = -1;
                int oldLimit = this.limit;
                boolean haveMoreData = this.pos < this.limit || indexAfterCompactionAndFilling(this.pos) < this.limit;
                if (!haveMoreData && this.buf[oldLimit - 1] == ',') {
                    result.add("");
                }
                if (!trailingComma || !haveMoreData) {
                    break;
                }
            } while (lookingAtCell());
            return result;
        }

        public long getCharPosition() {
            return this.previouslyRead + this.pos;
        }

        private int indexAfterCompactionAndFilling(int i) {
            if (this.pos > 0) {
                i = compact(i);
            }
            fill();
            return i;
        }

        private int compact(int i) {
            int oldPos = this.pos;
            this.pos = 0;
            int toMove = this.limit - oldPos;
            if (toMove > 0) {
                System.arraycopy(this.buf, oldPos, this.buf, 0, toMove);
            }
            this.limit -= oldPos;
            this.previouslyRead += oldPos;
            return i - oldPos;
        }

        private void fill() {
            int toFill = this.buf.length - this.limit;
            while (this.opened && toFill > 0) {
                try {
                    int n = this.in.read(this.buf, this.limit, toFill);
                    if (n == -1) {
                        this.opened = false;
                    } else {
                        this.limit += n;
                        toFill -= n;
                    }
                } catch (IOException e) {
                    this.lastException = e;
                    this.opened = false;
                }
            }
        }

        private boolean lookingAtCell() {
            return this.buf[this.pos] == '\"' ? findUnescapedEndQuote(this.pos + 1) : findUnquotedCellEnd(this.pos);
        }

        private boolean findUnescapedEndQuote(int i) {
            while (true) {
                if (i < this.limit || (i = indexAfterCompactionAndFilling(i)) < this.limit) {
                    if (this.buf[i] != '\"' || ((i = checkedIndex(i + 1)) != this.limit && this.buf[i] == '\"')) {
                        i++;
                    }
                } else {
                    this.lastException = new IllegalArgumentException("Syntax Error. unclosed quoted cell");
                    return false;
                }
            }
            this.cellLength = i - this.pos;
            return findDelimOrEnd(i);
        }

        private boolean findDelimOrEnd(int i) {
            while (true) {
                if (i < this.limit || (i = indexAfterCompactionAndFilling(i)) < this.limit) {
                    switch (this.buf[i]) {
                        case '\t':
                        case ' ':
                            i++;
                        case '\n':
                        case ',':
                            this.delimitedCellLength = checkedIndex(i + 1) - this.pos;
                            return true;
                        case '\r':
                            int j = checkedIndex(i + 1);
                            if (this.buf[j] == '\n') {
                                j = checkedIndex(j + 1);
                            }
                            this.delimitedCellLength = j - this.pos;
                            return true;
                        default:
                            this.lastException = new IOException("Syntax Error: non-whitespace between closing quote and delimiter or end");
                            return false;
                    }
                } else {
                    this.delimitedCellLength = this.limit - this.pos;
                    return true;
                }
            }
        }

        private int checkedIndex(int i) {
            return i < this.limit ? i : indexAfterCompactionAndFilling(i);
        }

        private boolean findUnquotedCellEnd(int i) {
            while (true) {
                if (i < this.limit || (i = indexAfterCompactionAndFilling(i)) < this.limit) {
                    switch (this.buf[i]) {
                        case '\n':
                        case ',':
                            this.cellLength = i - this.pos;
                            this.delimitedCellLength = this.cellLength + 1;
                            return true;
                        case '\r':
                            this.cellLength = i - this.pos;
                            int j = checkedIndex(i + 1);
                            if (this.buf[j] == '\n') {
                                j = checkedIndex(j + 1);
                            }
                            this.delimitedCellLength = j - this.pos;
                            return true;
                        case '\"':
                            this.lastException = new IllegalArgumentException("Syntax Error: quote in unquoted cell");
                            return false;
                        default:
                            i++;
                    }
                } else {
                    int i2 = this.limit - this.pos;
                    this.cellLength = i2;
                    this.delimitedCellLength = i2;
                    return true;
                }
            }
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }

        public void throwAnyProblem() throws Exception {
            if (this.lastException != null) {
                throw this.lastException;
            }
        }
    }
}
