package com.google.appinventor.components.annotations;

/* loaded from: classes.dex */
public enum PropertyCategory {
    BEHAVIOR("Behavior"),
    APPEARANCE("Appearance"),
    DEPRECATED("Deprecated"),
    UNSET("Unspecified");
    
    private String name;

    PropertyCategory(String categoryName) {
        this.name = categoryName;
    }

    public String getName() {
        return this.name;
    }
}
