package gnu.kawa.xml;

import gnu.lists.Consumer;
import gnu.lists.XConsumer;
import gnu.mapping.Location;
import gnu.mapping.ThreadLocation;
import gnu.text.Path;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import gnu.xml.NodeTree;
import gnu.xml.XMLParser;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Hashtable;

/* loaded from: classes.dex */
public class Document {
    public static final Document document = new Document();
    private static ThreadLocation docMapLocation = new ThreadLocation("document-map");
    private static HashMap cache = new HashMap();

    public static void parse(Object name, Consumer out) throws Throwable {
        SourceMessages messages = new SourceMessages();
        if (out instanceof XConsumer) {
            ((XConsumer) out).beginEntity(name);
        }
        XMLParser.parse(name, messages, out);
        if (messages.seenErrors()) {
            throw new SyntaxException("document function read invalid XML", messages);
        }
        if (out instanceof XConsumer) {
            ((XConsumer) out).endEntity();
        }
    }

    public static KDocument parse(Object uri) throws Throwable {
        NodeTree tree = new NodeTree();
        parse(uri, tree);
        return new KDocument(tree, 10);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class DocReference extends SoftReference {
        static ReferenceQueue queue = new ReferenceQueue();
        Path key;

        public DocReference(Path key, KDocument doc) {
            super(doc, queue);
            this.key = key;
        }
    }

    public static void clearLocalCache() {
        Location loc = docMapLocation.getLocation();
        loc.set(null);
    }

    public static void clearSoftCache() {
        cache = new HashMap();
    }

    public static KDocument parseCached(Object uri) throws Throwable {
        return parseCached(Path.valueOf(uri));
    }

    public static synchronized KDocument parseCached(Path uri) throws Throwable {
        KDocument doc;
        synchronized (Document.class) {
            while (true) {
                DocReference oldref = (DocReference) DocReference.queue.poll();
                if (oldref == null) {
                    break;
                }
                cache.remove(oldref.key);
            }
            Location loc = docMapLocation.getLocation();
            Hashtable map = (Hashtable) loc.get(null);
            if (map == null) {
                map = new Hashtable();
                loc.set(map);
            }
            KDocument doc2 = (KDocument) map.get(uri);
            if (doc2 != null) {
                doc = doc2;
            } else {
                DocReference ref = (DocReference) cache.get(uri);
                if (ref != null) {
                    KDocument doc3 = (KDocument) ref.get();
                    if (doc3 == null) {
                        cache.remove(uri);
                    } else {
                        map.put(uri, doc3);
                        doc = doc3;
                    }
                }
                KDocument doc4 = parse(uri);
                map.put(uri, doc4);
                cache.put(uri, new DocReference(uri, doc4));
                doc = doc4;
            }
        }
        return doc;
    }
}
