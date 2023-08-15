package gnu.mapping;

/* loaded from: classes.dex */
public class PlainLocation extends NamedLocation {
    public PlainLocation(Symbol symbol, Object property) {
        super(symbol, property);
    }

    public PlainLocation(Symbol symbol, Object property, Object value) {
        super(symbol, property);
        this.value = value;
    }

    @Override // gnu.mapping.Location
    public final Object get(Object defaultValue) {
        return this.base != null ? this.base.get(defaultValue) : this.value != Location.UNBOUND ? this.value : defaultValue;
    }

    @Override // gnu.mapping.Location
    public boolean isBound() {
        return this.base != null ? this.base.isBound() : this.value != Location.UNBOUND;
    }

    @Override // gnu.mapping.Location
    public final void set(Object newValue) {
        if (this.base == null) {
            this.value = newValue;
        } else if (this.value == DIRECT_ON_SET) {
            this.base = null;
            this.value = newValue;
        } else if (this.base.isConstant()) {
            getEnvironment().put(getKeySymbol(), getKeyProperty(), newValue);
        } else {
            this.base.set(newValue);
        }
    }
}
