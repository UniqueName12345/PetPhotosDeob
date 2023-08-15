package gnu.text;

import java.net.URI;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: URIPath.java */
/* loaded from: classes.dex */
public class URIStringPath extends URIPath {
    String uriString;

    @Override // gnu.text.URIPath, gnu.text.Path
    public String toURIString() {
        return this.uriString;
    }

    public URIStringPath(URI uri, String uriString) {
        super(uri);
        this.uriString = uriString;
    }
}
