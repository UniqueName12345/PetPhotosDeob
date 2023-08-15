package kawa.standard;

import gnu.mapping.Environment;
import gnu.mapping.Procedure1;
import gnu.mapping.Values;
import gnu.text.Path;
import gnu.text.SyntaxException;
import java.io.FileNotFoundException;
import kawa.Shell;

/* loaded from: classes.dex */
public class load extends Procedure1 {
    public static final load load = new load("load", false);
    public static final load loadRelative = new load("load-relative", true);
    boolean relative;

    public load(String name, boolean relative) {
        super(name);
        this.relative = relative;
    }

    @Override // gnu.mapping.Procedure1, gnu.mapping.Procedure
    public final Object apply1(Object arg1) throws Throwable {
        return apply2(arg1, Environment.getCurrent());
    }

    @Override // gnu.mapping.Procedure1, gnu.mapping.Procedure
    public final Object apply2(Object name, Object arg2) throws Throwable {
        Path curPath;
        try {
            Environment env = (Environment) arg2;
            Path path = Path.valueOf(name);
            if (this.relative && (curPath = (Path) Shell.currentLoadPath.get()) != null) {
                path = curPath.resolve(path);
            }
            Shell.runFile(path.openInputStream(), path, env, true, 0);
            return Values.empty;
        } catch (SyntaxException ex) {
            throw new RuntimeException("load: errors while compiling '" + name + "':\n" + ex.getMessages().toString(20));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("cannot load " + e.getMessage());
        }
    }
}
