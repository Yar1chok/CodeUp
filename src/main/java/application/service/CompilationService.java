package application.service;

import org.springframework.stereotype.Service;

import javax.tools.*;
import java.io.IOException;
import java.util.Arrays;
import java.net.URI;

@Service
public class CompilationService {

    public String compileCode(String code) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

        try (StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null)) {
            StringSourceJavaObject sourceObject = new StringSourceJavaObject("Main", code);
            Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(sourceObject);

            JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits);
            boolean success = task.call();

            if (success) {
                return "Code compiled successfully";
            } else {
                StringBuilder errorMsg = new StringBuilder("Error occurred during compilation:");
                for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                    errorMsg.append("\nError on line ")
                            .append(diagnostic.getLineNumber())
                            .append(" in ")
                            .append(diagnostic.getSource().toUri());
                }
                return errorMsg.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "IOException during compilation: " + e.getMessage();
        }
    }

    static class StringSourceJavaObject extends SimpleJavaFileObject {
        private final String code;

        protected StringSourceJavaObject(String name, String code) {
            super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
            this.code = code;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return code;
        }
    }
}
