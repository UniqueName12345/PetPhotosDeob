package gnu.bytecode;

/* loaded from: classes.dex */
public class UninitializedType extends ObjectType {
    ClassType ctype;
    Label label;

    UninitializedType(ClassType ctype) {
        super(ctype.getName());
        setSignature(ctype.getSignature());
        this.ctype = ctype;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public UninitializedType(ClassType ctype, Label label) {
        this(ctype);
        this.label = label;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static UninitializedType uninitializedThis(ClassType ctype) {
        return new UninitializedType(ctype);
    }

    @Override // gnu.bytecode.ObjectType, gnu.bytecode.Type
    public Type getImplementationType() {
        return this.ctype;
    }

    @Override // gnu.bytecode.Type
    public String toString() {
        return "Uninitialized<" + this.ctype.getName() + '>';
    }
}
