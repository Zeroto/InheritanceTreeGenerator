package zerot.itg;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Created by Zerot on 11/10/2014.
 */
public class Visitor extends ClassVisitor {
    private final ClassDataManager classData;

    public Visitor(ClassDataManager classData) {
        super(Opcodes.ASM5);
        this.classData = classData;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        classData.addClass(name, superName, interfaces);

        super.visit(version, access, name, signature, superName, interfaces);
    }
}
