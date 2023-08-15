package gnu.ecmascript;

import gnu.mapping.InPort;
import gnu.mapping.Procedure1;

/* loaded from: classes.dex */
class Prompter extends Procedure1 {
    String prompt(InPort port) {
        return "(EcmaScript:" + (port.getLineNumber() + 1) + ") ";
    }

    @Override // gnu.mapping.Procedure1, gnu.mapping.Procedure
    public Object apply1(Object arg) {
        return prompt((InPort) arg);
    }
}
