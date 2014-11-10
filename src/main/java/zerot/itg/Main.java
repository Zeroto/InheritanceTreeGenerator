package zerot.itg;

import org.objectweb.asm.ClassReader;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by Zerot on 11/10/2014.
 */
public class Main {

    public static void main(String[] args)
    {
        if (args.length != 2)
        {
            printUsageAndExit();
            return;
        }

        File jarFile = new File(args[0]);
        String className = args[1];

        if (!jarFile.exists())
        {
            System.out.println("Jar file "+args[0]+" does not exist");
            printUsageAndExit();
            return;
        }

        ClassDataManager classData = new ClassDataManager();
        try {
            parseJar(jarFile, classData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        classData.printClassStructure(className);
    }

    private static void parseJar(File jarFile, ClassDataManager classData) throws IOException {
        ZipInputStream inputStream = new ZipInputStream(new BufferedInputStream(new FileInputStream(jarFile)));
        ZipEntry entry = null;
        while ((entry = inputStream.getNextEntry()) != null)
        {
            if (entry.isDirectory())
                continue;

            if (!entry.getName().endsWith(".class"))
                continue;

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] dataBuffer = new byte[1024];
            int length;
            while((length = inputStream.read(dataBuffer))>=0)
            {
                buffer.write(dataBuffer, 0, length);
            }
            byte[] fileData = buffer.toByteArray();

            parseClass(fileData, classData);
        }
    }

    private static void parseClass(byte[] data, ClassDataManager classData) {
        ClassReader reader = new ClassReader(data);
        reader.accept(new Visitor(classData), 0);
    }

    private static void printUsageAndExit() {
        System.out.println("Usage: itg <JarFile> <ClassName>");
        System.exit(-1);
    }
}
