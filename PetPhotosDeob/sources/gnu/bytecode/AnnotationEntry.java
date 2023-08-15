package gnu.bytecode;

import java.lang.annotation.Annotation;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class AnnotationEntry implements Annotation {
    ClassType annotationType;
    LinkedHashMap<String, Object> elementsValue = new LinkedHashMap<>(10);

    public ClassType getAnnotationType() {
        return this.annotationType;
    }

    public void addMember(String name, Object value) {
        this.elementsValue.put(name, value);
    }

    @Override // java.lang.annotation.Annotation
    public Class<? extends Annotation> annotationType() {
        return this.annotationType.getReflectClass();
    }

    @Override // java.lang.annotation.Annotation
    public boolean equals(Object obj) {
        if (obj instanceof AnnotationEntry) {
            AnnotationEntry other = (AnnotationEntry) obj;
            if (getAnnotationType().getName().equals(other.getAnnotationType().getName())) {
                for (Map.Entry<String, Object> it : this.elementsValue.entrySet()) {
                    String key = it.getKey();
                    Object value1 = it.getValue();
                    Object value2 = other.elementsValue.get(key);
                    if (value1 != value2 && (value1 == null || value2 == null || !value1.equals(value2))) {
                        return false;
                    }
                }
                for (Map.Entry<String, Object> it2 : other.elementsValue.entrySet()) {
                    String key2 = it2.getKey();
                    Object value22 = it2.getValue();
                    Object value12 = this.elementsValue.get(key2);
                    if (value12 != value22 && (value12 == null || value22 == null || !value12.equals(value22))) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
        return false;
    }

    @Override // java.lang.annotation.Annotation
    public int hashCode() {
        int hash = 0;
        for (Map.Entry<String, Object> it : this.elementsValue.entrySet()) {
            int khash = it.getKey().hashCode();
            int vhash = it.getValue().hashCode();
            hash += (khash * 127) ^ vhash;
        }
        return hash;
    }

    @Override // java.lang.annotation.Annotation
    public String toString() {
        StringBuilder sbuf = new StringBuilder();
        sbuf.append('@');
        sbuf.append(getAnnotationType().getName());
        sbuf.append('(');
        int count = 0;
        for (Map.Entry<String, Object> it : this.elementsValue.entrySet()) {
            if (count > 0) {
                sbuf.append(", ");
            }
            sbuf.append(it.getKey());
            sbuf.append('=');
            sbuf.append(it.getValue());
            count++;
        }
        sbuf.append(')');
        return sbuf.toString();
    }
}
