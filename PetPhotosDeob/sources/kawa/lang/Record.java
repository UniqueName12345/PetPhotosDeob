package kawa.lang;

import gnu.bytecode.ArrayClassLoader;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.expr.Compilation;
import gnu.kawa.servlet.HttpRequestContext;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.WrappedException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Vector;

/* loaded from: classes.dex */
public class Record {
    public String getTypeName() {
        return getClass().getName();
    }

    public static boolean isRecord(Object obj) {
        return obj instanceof Record;
    }

    public int hashCode() {
        Field[] fields = getClass().getFields();
        int hash = 12345;
        for (Field field : fields) {
            try {
                Object value = field.get(this);
                if (value != null) {
                    hash ^= value.hashCode();
                }
            } catch (IllegalAccessException e) {
            }
        }
        return hash;
    }

    static Field getField(Class clas, String fname) throws NoSuchFieldException {
        ClassType ctype = (ClassType) Type.make(clas);
        for (gnu.bytecode.Field fld = ctype.getFields(); fld != null; fld = fld.getNext()) {
            if ((fld.getModifiers() & 9) == 1 && fld.getSourceName().equals(fname)) {
                return fld.getReflectField();
            }
        }
        throw new NoSuchFieldException();
    }

    public Object get(String fname, Object defaultValue) {
        Class clas = getClass();
        try {
            return getField(clas, fname).get(this);
        } catch (IllegalAccessException e) {
            throw new GenericError("illegal access for field " + fname);
        } catch (NoSuchFieldException e2) {
            throw new GenericError("no such field " + fname + " in " + clas.getName());
        }
    }

    public Object put(String fname, Object value) {
        return set1(this, fname, value);
    }

    public static Object set1(Object record, String fname, Object value) {
        Class clas = record.getClass();
        try {
            Field fld = getField(clas, fname);
            Object old = fld.get(record);
            fld.set(record, value);
            return old;
        } catch (IllegalAccessException e) {
            throw new GenericError("illegal access for field " + fname);
        } catch (NoSuchFieldException e2) {
            throw new GenericError("no such field " + fname + " in " + clas.getName());
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        Class thisClass = getClass();
        if (obj == null || obj.getClass() != thisClass) {
            return false;
        }
        ClassType ctype = (ClassType) Type.make(thisClass);
        for (gnu.bytecode.Field fld = ctype.getFields(); fld != null; fld = fld.getNext()) {
            if ((fld.getModifiers() & 9) == 1) {
                try {
                    Field field = fld.getReflectField();
                    Object value1 = field.get(this);
                    Object value2 = field.get(obj);
                    if (!value1.equals(value2)) {
                        return false;
                    }
                } catch (Exception ex) {
                    throw new WrappedException(ex);
                }
            }
        }
        return true;
    }

    public String toString() {
        Object obj;
        StringBuffer buf = new StringBuffer((int) HttpRequestContext.HTTP_OK);
        buf.append("#<");
        buf.append(getTypeName());
        ClassType ctype = (ClassType) Type.make(getClass());
        for (gnu.bytecode.Field fld = ctype.getFields(); fld != null; fld = fld.getNext()) {
            if ((fld.getModifiers() & 9) == 1) {
                try {
                    Field field = fld.getReflectField();
                    obj = field.get(this);
                } catch (Exception e) {
                    obj = "#<illegal-access>";
                }
                buf.append(' ');
                buf.append(fld.getSourceName());
                buf.append(": ");
                buf.append(obj);
            }
        }
        buf.append(">");
        return buf.toString();
    }

    public void print(PrintWriter ps) {
        ps.print(toString());
    }

    public static ClassType makeRecordType(String name, LList fnames) {
        ClassType superClass = ClassType.make("kawa.lang.Record");
        String mangledName = Compilation.mangleNameIfNeeded(name);
        ClassType clas = new ClassType(mangledName);
        clas.setSuper(superClass);
        clas.setModifiers(33);
        Method constructor = clas.addMethod("<init>", Type.typeArray0, Type.voidType, 1);
        Method superConstructor = superClass.addMethod("<init>", Type.typeArray0, Type.voidType, 1);
        CodeAttr code = constructor.startCode();
        code.emitPushThis();
        code.emitInvokeSpecial(superConstructor);
        code.emitReturn();
        if (!name.equals(mangledName)) {
            Method meth = clas.addMethod("getTypeName", Type.typeArray0, Compilation.typeString, 1);
            CodeAttr code2 = meth.startCode();
            code2.emitPushString(name);
            code2.emitReturn();
        }
        while (fnames != LList.Empty) {
            Pair pair = (Pair) fnames;
            String fname = pair.getCar().toString();
            gnu.bytecode.Field fld = clas.addField(Compilation.mangleNameIfNeeded(fname), Type.pointer_type, 1);
            fld.setSourceName(fname.intern());
            fnames = (LList) pair.getCdr();
        }
        String[] names = {mangledName};
        byte[][] arrays = {clas.writeToArray()};
        ArrayClassLoader loader = new ArrayClassLoader(names, arrays);
        try {
            Class reflectClass = loader.loadClass(mangledName);
            Type.registerTypeForClass(reflectClass, clas);
            return clas;
        } catch (ClassNotFoundException ex) {
            throw new InternalError(ex.toString());
        }
    }

    public static LList typeFieldNames(Class clas) {
        LList list = LList.Empty;
        ClassType ctype = (ClassType) Type.make(clas);
        Vector vec = new Vector(100);
        for (gnu.bytecode.Field field = ctype.getFields(); field != null; field = field.getNext()) {
            if ((field.getModifiers() & 9) == 1) {
                vec.addElement(SimpleSymbol.valueOf(field.getSourceName()));
            }
        }
        int i = vec.size();
        while (true) {
            LList list2 = list;
            i--;
            if (i >= 0) {
                list = new Pair(vec.elementAt(i), list2);
            } else {
                return list2;
            }
        }
    }

    public static LList typeFieldNames(ClassType ctype) {
        return typeFieldNames(ctype.getReflectClass());
    }
}
