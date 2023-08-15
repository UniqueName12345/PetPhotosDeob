package gnu.mapping;

/* loaded from: classes.dex */
public class ReadOnlyLocation extends ConstrainedLocation {
    public static ReadOnlyLocation make(Location base) {
        ReadOnlyLocation rloc = new ReadOnlyLocation();
        rloc.base = base;
        return rloc;
    }

    @Override // gnu.mapping.ConstrainedLocation, gnu.mapping.Location
    public boolean isConstant() {
        return true;
    }

    @Override // gnu.mapping.ConstrainedLocation
    protected Object coerce(Object newValue) {
        StringBuffer sbuf = new StringBuffer("attempt to modify read-only location");
        Symbol name = getKeySymbol();
        if (name != null) {
            sbuf.append(": ");
            sbuf.append(name);
        }
        throw new IllegalStateException(sbuf.toString());
    }
}
