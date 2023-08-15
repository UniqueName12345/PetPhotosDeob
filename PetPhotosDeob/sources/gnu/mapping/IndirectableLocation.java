package gnu.mapping;

/* loaded from: classes.dex */
public abstract class IndirectableLocation extends Location {
    protected static final Object DIRECT_ON_SET = new String("(direct-on-set)");
    protected static final Object INDIRECT_FLUIDS = new String("(indirect-fluids)");
    protected Location base;
    protected Object value;

    @Override // gnu.mapping.Location
    public Symbol getKeySymbol() {
        if (this.base != null) {
            return this.base.getKeySymbol();
        }
        return null;
    }

    @Override // gnu.mapping.Location
    public Object getKeyProperty() {
        if (this.base != null) {
            return this.base.getKeyProperty();
        }
        return null;
    }

    @Override // gnu.mapping.Location
    public boolean isConstant() {
        return this.base != null && this.base.isConstant();
    }

    @Override // gnu.mapping.Location
    public Location getBase() {
        return this.base == null ? this : this.base.getBase();
    }

    public Location getBaseForce() {
        return this.base == null ? new PlainLocation(getKeySymbol(), getKeyProperty(), this.value) : this.base;
    }

    public void setBase(Location base) {
        this.base = base;
        this.value = null;
    }

    public void setAlias(Location base) {
        this.base = base;
        this.value = INDIRECT_FLUIDS;
    }

    @Override // gnu.mapping.Location
    public void undefine() {
        this.base = null;
        this.value = UNBOUND;
    }

    public Environment getEnvironment() {
        if (this.base instanceof NamedLocation) {
            return ((NamedLocation) this.base).getEnvironment();
        }
        return null;
    }
}
