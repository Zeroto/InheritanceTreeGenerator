package zerot.itg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Zerot on 11/10/2014.
 */
public class ClassDataManager {

    private final HashMap<String, String> classToSuper = new HashMap<>();
    private final HashMap<String, List<String>> superToClass = new HashMap<>();
    private final HashMap<String, String[]> classInterfaces = new HashMap<>();

    public void addClass(String name, String superName, String[] interfaces) {
        classToSuper.put(name, superName);
        classInterfaces.put(name, interfaces);

        List<String> classes = superToClass.get(superName);
        if (classes == null) {
            classes = new ArrayList<>();
            superToClass.put(superName, classes);
        }

        classes.add(name);
    }

    public void printClassStructure(String name) {
        if (classToSuper.get(name) == null) {
            System.out.println("Class " + name + " not found");
            return;
        }

        // get parent list
        List<String> parents = new ArrayList<>();
        String parent = classToSuper.get(name);
        while(parent != null)
        {
            parents.add(0, parent);
            parent = classToSuper.get(parent);
        }
        parents.add(name);
        printParents(parents);

        StringBuilder builder = new StringBuilder();
        printChildren(builder, name, parents.size());
        System.out.print(builder.toString());
    }

    private void printChildren(StringBuilder builder, String name, int size) {
        List<String> classes = superToClass.get(name);
        if (classes != null)
        {
            for (String c : classes)
            {
                for (int ind = 0; ind<size; ind++)
                    builder.append("\t");
                builder.append(c+"\n");

                printChildren(builder, c, size + 1);
            }
        }
    }

    private void printParents(List<String> parents) {
        StringBuilder builder = new StringBuilder();
        for (int i=0; i<parents.size(); i++)
        {
            // indentation
            for (int ind = 0; ind<i; ind++)
                builder.append("\t");

            builder.append(parents.get(i)+"\n");
        }

        System.out.print(builder.toString());
    }
}
