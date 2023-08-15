package gnu.bytecode;

import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes.dex */
public class SourceDebugExtAttr extends Attribute {
    int curFileIndex;
    String curFileName;
    int curLineIndex;
    byte[] data;
    private String defaultStratumId;
    int dlength;
    int fileCount;
    int[] fileIDs;
    String[] fileNames;
    int lineCount;
    int[] lines;
    int maxFileID;
    private String outputFileName;

    private int fixLine(int sourceLine, int index) {
        int sourceMin = this.lines[index];
        int repeat = this.lines[index + 2];
        if (sourceLine < sourceMin) {
            if (index > 0) {
                return -1;
            }
            int sourceMax = (sourceMin + repeat) - 1;
            this.lines[index] = sourceLine;
            this.lines[index + 2] = (sourceMax - sourceLine) + 1;
            this.lines[index + 3] = sourceLine;
            sourceMin = sourceLine;
        }
        int delta = this.lines[index + 3] - sourceMin;
        if (sourceLine < sourceMin + repeat) {
            return sourceLine + delta;
        }
        if (index == (this.lineCount - 1) * 5 || (index == 0 && sourceLine < this.lines[8])) {
            this.lines[index + 2] = (sourceLine - sourceMin) + 1;
            return sourceLine + delta;
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int fixLine(int sourceLine) {
        int outputStartLine;
        int outLine;
        int outLine2;
        if (this.curLineIndex < 0 || (outLine2 = fixLine(sourceLine, this.curLineIndex)) < 0) {
            int i5 = 0;
            int findex = this.curFileIndex;
            for (int i = 0; i < this.lineCount; i++) {
                if (i5 != this.curLineIndex && findex == this.lines[i5 + 1] && (outLine = fixLine(sourceLine, i5)) >= 0) {
                    this.curLineIndex = i5;
                    return outLine;
                }
                i5 += 5;
            }
            if (this.lines == null) {
                this.lines = new int[20];
            } else if (i5 >= this.lines.length) {
                int[] newLines = new int[i5 * 2];
                System.arraycopy(this.lines, 0, newLines, 0, i5);
                this.lines = newLines;
            }
            if (i5 == 0) {
                outputStartLine = sourceLine;
            } else {
                outputStartLine = this.lines[(i5 - 5) + 3] + this.lines[(i5 - 5) + 2];
                if (i5 == 5 && outputStartLine < 10000) {
                    outputStartLine = 10000;
                }
                sourceLine = outputStartLine;
            }
            this.lines[i5] = sourceLine;
            this.lines[i5 + 1] = findex;
            this.lines[i5 + 2] = 1;
            this.lines[i5 + 3] = outputStartLine;
            this.lines[i5 + 4] = 1;
            this.curLineIndex = i5;
            this.lineCount++;
            return sourceLine;
        }
        return outLine2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void addFile(String fname) {
        String fentry;
        if (this.curFileName != fname) {
            if (fname == null || !fname.equals(this.curFileName)) {
                this.curFileName = fname;
                String fname2 = SourceFileAttr.fixSourceFile(fname);
                int slash = fname2.lastIndexOf(47);
                if (slash >= 0) {
                    fname2 = fname2.substring(slash + 1);
                    fentry = fname2 + '\n' + fname2;
                } else {
                    fentry = fname2;
                }
                if (this.curFileIndex < 0 || !fentry.equals(this.fileNames[this.curFileIndex])) {
                    int n = this.fileCount;
                    for (int i = 0; i < n; i++) {
                        if (i != this.curFileIndex && fentry.equals(this.fileNames[i])) {
                            this.curFileIndex = i;
                            this.curLineIndex = -1;
                            return;
                        }
                    }
                    if (this.fileIDs == null) {
                        this.fileIDs = new int[5];
                        this.fileNames = new String[5];
                    } else if (n >= this.fileIDs.length) {
                        int[] newIDs = new int[n * 2];
                        String[] newNames = new String[n * 2];
                        System.arraycopy(this.fileIDs, 0, newIDs, 0, n);
                        System.arraycopy(this.fileNames, 0, newNames, 0, n);
                        this.fileIDs = newIDs;
                        this.fileNames = newNames;
                    }
                    this.fileCount++;
                    int id = this.maxFileID + 1;
                    this.maxFileID = id;
                    int id2 = id << 1;
                    if (slash >= 0) {
                        id2++;
                    }
                    this.fileNames[n] = fentry;
                    if (this.outputFileName == null) {
                        this.outputFileName = fname2;
                    }
                    this.fileIDs[n] = id2;
                    this.curFileIndex = n;
                    this.curLineIndex = -1;
                }
            }
        }
    }

    public void addStratum(String name) {
        this.defaultStratumId = name;
    }

    public SourceDebugExtAttr(ClassType cl) {
        super("SourceDebugExtension");
        this.curLineIndex = -1;
        this.curFileIndex = -1;
        addToFrontOf(cl);
    }

    void nonAsteriskString(String str, StringBuffer sbuf) {
        if (str == null || str.length() == 0 || str.charAt(0) == '*') {
            sbuf.append(' ');
        }
        sbuf.append(str);
    }

    @Override // gnu.bytecode.Attribute
    public void assignConstants(ClassType cl) {
        super.assignConstants(cl);
        StringBuffer sbuf = new StringBuffer();
        sbuf.append("SMAP\n");
        nonAsteriskString(this.outputFileName, sbuf);
        sbuf.append('\n');
        String stratum = this.defaultStratumId == null ? "Java" : this.defaultStratumId;
        nonAsteriskString(stratum, sbuf);
        sbuf.append('\n');
        sbuf.append("*S ");
        sbuf.append(stratum);
        sbuf.append('\n');
        sbuf.append("*F\n");
        for (int i = 0; i < this.fileCount; i++) {
            int id = this.fileIDs[i];
            boolean with_path = (id & 1) != 0;
            int id2 = id >> 1;
            if (with_path) {
                sbuf.append("+ ");
            }
            sbuf.append(id2);
            sbuf.append(' ');
            sbuf.append(this.fileNames[i]);
            sbuf.append('\n');
        }
        if (this.lineCount > 0) {
            int prevFileID = 0;
            sbuf.append("*L\n");
            int i2 = 0;
            int i5 = 0;
            do {
                int inputStartLine = this.lines[i5];
                int lineFileID = this.fileIDs[this.lines[i5 + 1]] >> 1;
                int repeatCount = this.lines[i5 + 2];
                int outputStartLine = this.lines[i5 + 3];
                int outputLineIncrement = this.lines[i5 + 4];
                sbuf.append(inputStartLine);
                if (lineFileID != prevFileID) {
                    sbuf.append('#');
                    sbuf.append(lineFileID);
                    prevFileID = lineFileID;
                }
                if (repeatCount != 1) {
                    sbuf.append(',');
                    sbuf.append(repeatCount);
                }
                sbuf.append(':');
                sbuf.append(outputStartLine);
                if (outputLineIncrement != 1) {
                    sbuf.append(',');
                    sbuf.append(outputLineIncrement);
                }
                sbuf.append('\n');
                i5 += 5;
                i2++;
            } while (i2 < this.lineCount);
            sbuf.append("*E\n");
            try {
                this.data = sbuf.toString().getBytes("UTF-8");
                this.dlength = this.data.length;
            } catch (Exception ex) {
                throw new RuntimeException(ex.toString());
            }
        }
        sbuf.append("*E\n");
        this.data = sbuf.toString().getBytes("UTF-8");
        this.dlength = this.data.length;
    }

    @Override // gnu.bytecode.Attribute
    public int getLength() {
        return this.dlength;
    }

    @Override // gnu.bytecode.Attribute
    public void write(DataOutputStream dstr) throws IOException {
        dstr.write(this.data, 0, this.dlength);
    }

    @Override // gnu.bytecode.Attribute
    public void print(ClassTypeWriter dst) {
        dst.print("Attribute \"");
        dst.print(getName());
        dst.print("\", length:");
        dst.println(this.dlength);
        try {
            dst.print(new String(this.data, 0, this.dlength, "UTF-8"));
        } catch (Exception ex) {
            dst.print("(Caught ");
            dst.print(ex);
            dst.println(')');
        }
        if (this.dlength > 0 && this.data[this.dlength - 1] != 13 && this.data[this.dlength - 1] != 10) {
            dst.println();
        }
    }
}
