package gnu.kawa.xml;

import androidx.fragment.app.FragmentTransaction;
import gnu.lists.FString;
import gnu.mapping.CharArrayOutPort;
import gnu.mapping.Procedure1;
import gnu.xml.XMLPrinter;

/* loaded from: classes.dex */
public class OutputAsXML extends Procedure1 {
    @Override // gnu.mapping.Procedure1, gnu.mapping.Procedure
    public int numArgs() {
        return FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
    }

    @Override // gnu.mapping.Procedure1, gnu.mapping.Procedure
    public Object apply1(Object arg) {
        CharArrayOutPort port = new CharArrayOutPort();
        XMLPrinter out = new XMLPrinter(port);
        out.writeObject(arg);
        out.flush();
        return new FString(port.toCharArray());
    }
}
