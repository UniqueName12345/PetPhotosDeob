package gnu.bytecode;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Writer;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;

/* loaded from: classes.dex */
public class dump extends ClassFileInput {
    ClassTypeWriter writer;

    dump(InputStream str, ClassTypeWriter writer) throws IOException, ClassFormatError {
        super(str);
        this.ctype = new ClassType();
        readFormatVersion();
        readConstants();
        readClassInfo();
        readFields();
        readMethods();
        readAttributes(this.ctype);
        writer.print(this.ctype);
        writer.flush();
    }

    @Override // gnu.bytecode.ClassFileInput
    public ConstantPool readConstants() throws IOException {
        this.ctype.constants = super.readConstants();
        return this.ctype.constants;
    }

    @Override // gnu.bytecode.ClassFileInput
    public Attribute readAttribute(String name, int length, AttrContainer container) throws IOException {
        return super.readAttribute(name, length, container);
    }

    static int readMagic(InputStream in) throws IOException {
        int b;
        int magic = 0;
        for (int j = 0; j < 4 && (b = in.read()) >= 0; j++) {
            magic = (magic << 8) | (b & 255);
        }
        return magic;
    }

    public static void process(InputStream in, String filename, OutputStream out, int flags) throws IOException {
        process(in, filename, new ClassTypeWriter((ClassType) null, out, flags));
    }

    public static void process(InputStream in, String filename, Writer out, int flags) throws IOException {
        process(in, filename, new ClassTypeWriter((ClassType) null, out, flags));
    }

    public static void process(InputStream in, String filename, ClassTypeWriter out) throws IOException {
        InputStream inp = new BufferedInputStream(in);
        inp.mark(5);
        int magic = readMagic(inp);
        if (magic == -889275714) {
            out.print("Reading .class from ");
            out.print(filename);
            out.println('.');
            new dump(inp, out);
        } else if (magic == 1347093252) {
            inp.reset();
            out.print("Reading classes from archive ");
            out.print(filename);
            out.println('.');
            ZipInputStream zin = new ZipInputStream(inp);
            while (true) {
                ZipEntry zent = zin.getNextEntry();
                if (zent != null) {
                    String name = zent.getName();
                    if (zent.isDirectory()) {
                        out.print("Archive directory: ");
                        out.print(name);
                        out.println('.');
                    } else {
                        out.println();
                        if (readMagic(zin) == -889275714) {
                            out.print("Reading class member: ");
                            out.print(name);
                            out.println('.');
                            new dump(zin, out);
                        } else {
                            out.print("Skipping non-class member: ");
                            out.print(name);
                            out.println('.');
                        }
                    }
                } else {
                    System.exit(-1);
                    return;
                }
            }
        } else {
            System.err.println("File " + filename + " is not a valid .class file");
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        ClassLoader loader;
        InputStream in;
        int excl;
        int alen = args.length;
        ClassTypeWriter out = new ClassTypeWriter((ClassType) null, System.out, 0);
        if (alen == 0) {
            usage(System.err);
        }
        for (int i = 0; i < alen; i++) {
            String filename = args[i];
            if (filename.equals("-verbose") || filename.equals("--verbose")) {
                out.setFlags(15);
            } else {
                boolean isURL = uriSchemeSpecified(filename);
                if (isURL) {
                    try {
                        boolean isJarURL = filename.startsWith("jar:");
                        if (isJarURL) {
                            String part = filename.substring(4);
                            if (!uriSchemeSpecified(part) && (excl = part.indexOf(33)) >= 0) {
                                filename = "jar:" + new File(part.substring(0, excl)).toURI().toURL().toString() + part.substring(excl);
                            }
                            if (part.indexOf("!/") < 0) {
                                int excl2 = filename.lastIndexOf(33);
                                if (excl2 <= 0) {
                                    isJarURL = false;
                                } else if (filename.indexOf(47, excl2) < 0) {
                                    filename = filename.substring(0, excl2 + 1) + '/' + filename.substring(excl2 + 1).replace('.', '/') + ".class";
                                }
                            }
                        }
                        try {
                            try {
                                URL url = new URL(filename);
                                try {
                                    in = url.openConnection().getInputStream();
                                } catch (ZipException e1) {
                                    if (isJarURL) {
                                        String filepart = url.getFile();
                                        int sl = filepart.lastIndexOf(33);
                                        if (sl > 0) {
                                            filepart = filepart.substring(0, sl);
                                        }
                                        try {
                                            new URL(filepart).openConnection().getInputStream();
                                        } catch (FileNotFoundException e) {
                                            System.err.print("Jar File for URL ");
                                            System.err.print(filepart);
                                            System.err.println(" not found.");
                                            System.exit(-1);
                                        }
                                    }
                                    throw e1;
                                    break;
                                }
                            } catch (FileNotFoundException e2) {
                                System.err.print("File for URL ");
                                System.err.print(filename);
                                System.err.println(" not found.");
                                System.exit(-1);
                                in = null;
                            }
                        } catch (ZipException e12) {
                            System.err.print("Error opening zip archive ");
                            System.err.print(filename);
                            System.err.println(" not found.");
                            e12.printStackTrace();
                            if (e12.getCause() != null) {
                                e12.getCause().printStackTrace();
                            }
                            System.exit(-1);
                            in = null;
                        }
                        process(in, filename, out);
                    } catch (IOException e3) {
                        e3.printStackTrace();
                        System.err.println("caught ");
                        System.err.print(e3);
                        System.exit(-1);
                    }
                } else {
                    try {
                        in = new FileInputStream(filename);
                    } catch (FileNotFoundException e4) {
                        try {
                            Class clas = ObjectType.getContextClass(filename);
                            loader = clas.getClassLoader();
                        } catch (NoClassDefFoundError e5) {
                            loader = ObjectType.getContextClassLoader();
                        } catch (Throwable th) {
                            System.err.print("File ");
                            System.err.print(filename);
                            System.err.println(" not found.");
                            System.exit(-1);
                            loader = null;
                        }
                        String clfilename = filename.replace('.', '/') + ".class";
                        URL resource = loader.getResource(clfilename);
                        in = resource.openConnection().getInputStream();
                        filename = resource.toString();
                    }
                    process(in, filename, out);
                }
            }
        }
    }

    static int uriSchemeLength(String uri) {
        int len = uri.length();
        for (int i = 0; i < len; i++) {
            char ch = uri.charAt(i);
            if (ch != ':') {
                if (i != 0) {
                    if (!Character.isLetterOrDigit(ch) && ch != '+' && ch != '-' && ch != '.') {
                        return -1;
                    }
                } else if (!Character.isLetter(ch)) {
                    return -1;
                }
            } else {
                return i;
            }
        }
        return -1;
    }

    static boolean uriSchemeSpecified(String name) {
        int ulen = uriSchemeLength(name);
        if (ulen != 1 || File.separatorChar != '\\') {
            return ulen > 0;
        }
        char drive = name.charAt(0);
        if (drive < 'a' || drive > 'z') {
            return drive < 'A' || drive > 'Z';
        }
        return false;
    }

    public static void usage(PrintStream err) {
        err.println("Prints and dis-assembles the contents of JVM .class files.");
        err.println("Usage: [--verbose] class-or-jar ...");
        err.println("where a class-or-jar can be one of:");
        err.println("- a fully-qualified class name; or");
        err.println("- the name of a .class file, or a URL reference to one; or");
        err.println("- the name of a .jar or .zip archive file, or a URL reference to one.");
        err.println("If a .jar/.zip archive is named, all its.class file members are printed.");
        err.println();
        err.println("You can name a single .class member of an archive with a jar: URL,");
        err.println("which looks like: jar:jar-spec!/p1/p2/cl.class");
        err.println("The jar-spec can be a URL or the name of the .jar file.");
        err.println("You can also use the shorthand syntax: jar:jar-spec!p1.p2.cl");
        System.exit(-1);
    }
}
