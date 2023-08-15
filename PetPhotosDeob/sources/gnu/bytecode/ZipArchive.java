package gnu.bytecode;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/* loaded from: classes.dex */
public class ZipArchive {
    private static void usage() {
        System.err.println("zipfile [ptxq] archive [file ...]");
        System.exit(-1);
    }

    public static long copy(InputStream in, OutputStream out, byte[] buffer) throws IOException {
        long total = 0;
        while (true) {
            int count = in.read(buffer);
            if (count <= 0) {
                return total;
            }
            out.write(buffer, 0, count);
            total += count;
        }
    }

    public static void copy(InputStream in, String name, byte[] buffer) throws IOException {
        File f = new File(name);
        String dir_name = f.getParent();
        if (dir_name != null) {
            File dir = new File(dir_name);
            if (!dir.exists()) {
                System.err.println("mkdirs:" + dir.mkdirs());
            }
        }
        if (name.charAt(name.length() - 1) != '/') {
            OutputStream out = new BufferedOutputStream(new FileOutputStream(f));
            copy(in, out, buffer);
            out.close();
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            usage();
        }
        String command = args[0];
        String archive_name = args[1];
        try {
            if (command.equals("t") || command.equals("p") || command.equals("x")) {
                PrintStream out = System.out;
                byte[] buf = new byte[1024];
                if (args.length == 2) {
                    ZipInputStream zin = new ZipInputStream(new BufferedInputStream(new FileInputStream(archive_name)));
                    while (true) {
                        ZipEntry zent = zin.getNextEntry();
                        if (zent != null) {
                            String name = zent.getName();
                            if (command.equals("t")) {
                                out.print(name);
                                out.print(" size: ");
                                out.println(zent.getSize());
                            } else if (command.equals("p")) {
                                copy(zin, out, buf);
                            } else {
                                copy(zin, name, buf);
                            }
                        } else {
                            return;
                        }
                    }
                } else {
                    ZipFile zar = new ZipFile(archive_name);
                    for (int i = 2; i < args.length; i++) {
                        String name2 = args[i];
                        ZipEntry zent2 = zar.getEntry(name2);
                        if (zent2 == null) {
                            System.err.println("zipfile " + archive_name + ":" + args[i] + " - not found");
                            System.exit(-1);
                        } else if (command.equals("t")) {
                            out.print(name2);
                            out.print(" size: ");
                            out.println(zent2.getSize());
                        } else if (command.equals("p")) {
                            copy(zar.getInputStream(zent2), out, buf);
                        } else {
                            copy(zar.getInputStream(zent2), name2, buf);
                        }
                    }
                }
            } else if (command.equals("q")) {
                ZipOutputStream zar2 = new ZipOutputStream(new FileOutputStream(archive_name));
                for (int i2 = 2; i2 < args.length; i2++) {
                    File in = new File(args[i2]);
                    if (!in.exists()) {
                        throw new IOException(args[i2] + " - not found");
                    }
                    if (!in.canRead()) {
                        throw new IOException(args[i2] + " - not readable");
                    }
                    int size = (int) in.length();
                    FileInputStream fin = new FileInputStream(in);
                    byte[] contents = new byte[size];
                    if (fin.read(contents) != size) {
                        throw new IOException(args[i2] + " - read error");
                    }
                    fin.close();
                    ZipEntry ze = new ZipEntry(args[i2]);
                    ze.setSize(size);
                    ze.setTime(in.lastModified());
                    zar2.putNextEntry(ze);
                    zar2.write(contents, 0, size);
                }
                zar2.close();
            } else {
                usage();
            }
        } catch (IOException ex) {
            System.err.println("I/O Exception:  " + ex);
        }
    }
}
