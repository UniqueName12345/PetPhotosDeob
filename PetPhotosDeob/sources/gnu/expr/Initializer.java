package gnu.expr;

import gnu.bytecode.Field;

/* loaded from: classes.dex */
public abstract class Initializer {
    public Field field;
    Initializer next;

    public abstract void emit(Compilation compilation);

    public static Initializer reverse(Initializer list) {
        Initializer prev = null;
        while (list != null) {
            Initializer next = list.next;
            list.next = prev;
            prev = list;
            list = next;
        }
        return prev;
    }

    public void reportError(String message, Compilation comp) {
        comp.error('e', message + "field " + this.field);
    }
}
