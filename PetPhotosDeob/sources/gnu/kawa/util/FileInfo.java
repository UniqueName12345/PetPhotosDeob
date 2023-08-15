package gnu.kawa.util;

import gnu.mapping.InPort;
import gnu.mapping.OutPort;
import gnu.text.Path;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.Hashtable;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: FixupHtmlToc.java */
/* loaded from: classes.dex */
class FileInfo {
    StringBuffer beforeNavbarText;
    ByteArrayOutputStream bout;
    String[] childLinkText;
    OutPort cout;
    File file;
    FileInputStream fin;
    InPort in;
    int nchildren;
    StringBuffer newNavbarText;
    StringBuffer oldNavbarText;
    FileInfo parent;
    String parentName;
    String path;
    boolean scanned;
    boolean writeNeeded;
    static Hashtable fileMap = new Hashtable();
    static final Pattern childPat = Pattern.compile("<a .*</a>");
    static final Pattern parentPat = Pattern.compile("<ul[^>]* parent=['\"]([^'\"]*)['\"]");
    static final Pattern linkPat = Pattern.compile(" href=['\"]([^'\"]*)['\"]");

    FileInfo() {
    }

    public static FileInfo find(File file) throws Throwable {
        String abs = file.getCanonicalPath();
        FileInfo info = (FileInfo) fileMap.get(abs);
        if (info == null) {
            FileInfo info2 = new FileInfo();
            info2.file = file;
            fileMap.put(abs, info2);
            return info2;
        }
        return info;
    }

    public void scan() throws Throwable {
        if (!this.scanned) {
            this.scanned = true;
            this.fin = new FileInputStream(this.file);
            this.in = new InPort(new BufferedInputStream(this.fin));
            this.oldNavbarText = new StringBuffer();
            this.newNavbarText = new StringBuffer();
            if (this.writeNeeded) {
                this.bout = new ByteArrayOutputStream();
                this.cout = new OutPort(this.bout);
            }
            boolean inNavbar = false;
            boolean inChildList = false;
            Vector chvec = new Vector();
            while (true) {
                String line = this.in.readLine();
                if (line == null) {
                    break;
                }
                if (inNavbar) {
                    if (line.indexOf("<!--end-generated-navbar-->") >= 0) {
                        break;
                    }
                    this.oldNavbarText.append(line);
                    this.oldNavbarText.append('\n');
                    if (inChildList) {
                        if (line.indexOf("<!--end-children-toc-->") >= 0) {
                            inChildList = false;
                        } else {
                            Matcher childMatcher = childPat.matcher(line);
                            if (childMatcher.find()) {
                                chvec.add(childMatcher.group());
                            }
                            Matcher parentMatcher = parentPat.matcher(line);
                            if (parentMatcher.find() && this.parentName == null) {
                                this.parentName = parentMatcher.group(1);
                            }
                        }
                    }
                    if (line.indexOf("<!--start-children-toc-->") >= 0) {
                        inChildList = true;
                    }
                } else if (line.indexOf("<!--start-generated-navbar-->") >= 0) {
                    inNavbar = true;
                }
                if (this.writeNeeded && !inNavbar) {
                    this.cout.println(line);
                }
            }
            String[] charr = new String[chvec.size()];
            this.nchildren = charr.length;
            chvec.copyInto(charr);
            this.childLinkText = charr;
            if (!this.writeNeeded) {
                this.in.close();
            }
            if (this.parentName != null) {
                File parentFile = new File(this.file.toURI().resolve(this.parentName));
                FileInfo parentInfo = find(parentFile);
                parentInfo.scan();
                this.parent = parentInfo;
            }
        }
    }

    public void writeLinks(int uplevel, StringBuffer out) throws Throwable {
        int i;
        FileInfo current = this;
        FileInfo thischild = null;
        int i2 = uplevel;
        while (true) {
            i2--;
            if (i2 < 0) {
                break;
            }
            thischild = current;
            current = current.parent;
        }
        if (uplevel == 0) {
            out.append("<!--start-children-toc-->\n");
        }
        if (uplevel == 0 && this.parentName != null) {
            out.append("<ul parent=\"");
            out.append(this.parentName);
            out.append("\">\n");
        } else {
            out.append("<ul>\n");
        }
        URI thisURI = this.file.toURI();
        URI currentURI = current.file.toURI();
        while (i < current.nchildren) {
            String linkText = current.childLinkText[i];
            boolean ancestor = false;
            if (uplevel > 0) {
                Matcher linkMatcher = linkPat.matcher(linkText);
                linkMatcher.find();
                String hrefText = linkMatcher.group(1);
                URI linkURI = currentURI.resolve(hrefText);
                linkText = linkMatcher.replaceFirst(" href=\"" + Path.relativize(linkURI.toString(), thisURI.toString()) + "\"");
                int hash = hrefText.indexOf(35);
                if (hash >= 0) {
                    hrefText = hrefText.substring(0, hash);
                }
                FileInfo curchild = find(new File(currentURI.resolve(hrefText)));
                ancestor = curchild == thischild;
                i = (!ancestor && uplevel > 1) ? i + 1 : 0;
            }
            out.append("<li>");
            out.append(linkText);
            if (ancestor) {
                out.append('\n');
                writeLinks(uplevel - 1, out);
            }
            out.append("</li>\n");
        }
        out.append("</ul>\n");
        if (uplevel == 0) {
            out.append("<!--end-children-toc-->\n");
        }
    }

    public void write() throws Throwable {
        int level = 0;
        FileInfo current = this;
        while (current.parent != null) {
            current = current.parent;
            level++;
        }
        this.cout.println("<!--start-generated-navbar-->");
        writeLinks(level, this.newNavbarText);
        this.cout.print(this.newNavbarText);
        this.cout.println("<!--end-generated-navbar-->");
        while (true) {
            String line = this.in.readLine();
            if (line == null) {
                break;
            }
            this.cout.println(line);
        }
        new StringBuffer();
        this.in.close();
        if (this.oldNavbarText.toString().equals(this.newNavbarText.toString())) {
            System.err.println("fixup " + this.file + " - no change");
            return;
        }
        FileOutputStream outs = new FileOutputStream(this.file);
        byte[] bbuf = this.bout.toByteArray();
        outs.write(bbuf);
        outs.close();
        System.err.println("fixup " + this.file + " - updated");
    }
}
