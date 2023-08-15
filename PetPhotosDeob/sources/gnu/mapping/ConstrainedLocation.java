package gnu.mapping;

/* loaded from: classes.dex */
public class ConstrainedLocation extends Location {
    protected Location base;
    protected Procedure converter;

    public static ConstrainedLocation make(Location base, Procedure converter) {
        ConstrainedLocation cloc = new ConstrainedLocation();
        cloc.base = base;
        cloc.converter = converter;
        return cloc;
    }

    @Override // gnu.mapping.Location
    public Symbol getKeySymbol() {
        return this.base.getKeySymbol();
    }

    @Override // gnu.mapping.Location
    public Object getKeyProperty() {
        return this.base.getKeyProperty();
    }

    @Override // gnu.mapping.Location
    public boolean isConstant() {
        return this.base.isConstant();
    }

    @Override // gnu.mapping.Location
    public final Object get(Object defaultValue) {
        return this.base.get(defaultValue);
    }

    @Override // gnu.mapping.Location
    public boolean isBound() {
        return this.base.isBound();
    }

    protected Object coerce(Object newValue) {
        try {
            return this.converter.apply1(newValue);
        } catch (Throwable ex) {
            throw WrappedException.wrapIfNeeded(ex);
        }
    }

    @Override // gnu.mapping.Location
    public final void set(Object newValue) {
        this.base.set(coerce(newValue));
    }

    @Override // gnu.mapping.Location
    public Object setWithSave(Object newValue) {
        return this.base.setWithSave(coerce(newValue));
    }

    @Override // gnu.mapping.Location
    public void setRestore(Object oldValue) {
        this.base.setRestore(oldValue);
    }
}
