package gnu.kawa.xml;

import gnu.lists.UnescapedData;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure1;

/* loaded from: classes.dex */
public class MakeUnescapedData extends Procedure1 {
    public static final MakeUnescapedData unescapedData = new MakeUnescapedData();

    public MakeUnescapedData() {
        setProperty(Procedure.validateApplyKey, "gnu.kawa.xml.CompileXmlFunctions:validateApplyMakeUnescapedData");
    }

    @Override // gnu.mapping.Procedure1, gnu.mapping.Procedure
    public Object apply1(Object arg) {
        return new UnescapedData(arg == null ? "" : arg.toString());
    }
}
