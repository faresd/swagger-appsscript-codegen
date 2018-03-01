package io.swagger.codegen;

import io.swagger.documentation.swagger.apis.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import io.swagger.codegen.ClientOptInput;
import io.swagger.codegen.ClientOpts;
import io.swagger.codegen.CodegenConfig;
import io.swagger.codegen.DefaultGenerator;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ServiceLoader;

/**
 * Created by Fares DROUBI on 5/17/16.
 */
public class AppsScriptClients {

    public static void main(String[] args) throws MojoExecutionException, IOException {
        String templateDirParam = "templateDir";
        String language = args[0];
        Path apisPath = Paths.get(args[1]);
        File templateDirectory = new File(args[2]);
        File output = new File(args[3]);
        File buildDirectory = new File(args[4]);
        URL apisUrl = new URL(args[6]);
        Boolean addCompileSourceRoot = true;


//      SwaggerApis apis = new ObjectMapper().readValue(Files.newBufferedReader(apisPath, StandardCharsets.UTF_8), SwaggerApis.class);
        SwaggerApis apis = new ObjectMapper().readValue(apisUrl, SwaggerApis.class);

        if (!output.exists()) {
            output.mkdir();
        }

        TemplateLoader packageLoader = new ClassPathTemplateLoader();
        String packageTemplateDir = "/apis";
        packageLoader.setPrefix(packageTemplateDir);
        Handlebars packageHandlebars = new Handlebars(packageLoader);

        packageHandlebars.registerHelper("dashedName", new Helper<String>() {
            public CharSequence apply(String name, Options options) {
                return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, name);
            }
        });
        Template packageTemplate = packageHandlebars.compile("package.json");

        File packageFile = new File(buildDirectory, "package.json");
        FileWriter packageFileWriter = new FileWriter(packageFile);
        packageFileWriter.write(packageTemplate.apply(apis));
        packageFileWriter.flush();
        packageFileWriter.close();

        for(SwaggerApi api : apis.getApisList()) {

            TemplateLoader appLoader = new ClassPathTemplateLoader();
            String templateDir = "/apis";
            appLoader.setPrefix(packageTemplateDir);

            Handlebars handlebars = new Handlebars(appLoader);
            handlebars.registerHelper("dashedName", new Helper<String>() {
                public CharSequence apply(String name, Options options) {
                    return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, name);
                }
            });
            handlebars.registerHelper("wordToUC", new Helper<String>() {
                public CharSequence apply(String name, Options options) {
                    return StringUtils.capitalize(name);
                }
            });
            Template template = handlebars.compile("app");

            File appFile = new File(output, "app.js");

            FileWriter fileWriter = new FileWriter(appFile);
            fileWriter.write(template.apply(apis));
            fileWriter.flush();
            fileWriter.close();
            File clientOutputDir = new File(output.getAbsolutePath() + File.separator + Joiner.on('-').join(api.getName(), "api"));
            generateClient(api.getBaseURL(),
                    language,
                    clientOutputDir,
                    templateDirectory,
                    addCompileSourceRoot,
                    templateDirParam);
        }
    }

    public static void generateClient(String inputSpec, String language, File output, File templateDirectory, Boolean addCompileSourceRoot, String templateDir) throws MojoExecutionException {

        Swagger swagger = new SwaggerParser().read(inputSpec);

        CodegenConfig config = forName(language);
        config.setOutputDir(output.getAbsolutePath());

        if (null != templateDirectory) {
            config.additionalProperties().put(templateDir, templateDirectory.getAbsolutePath());
        }

        ClientOptInput input = new ClientOptInput().opts(new ClientOpts()).swagger(swagger);
        input.setConfig(config);
        new DefaultGenerator().opts(input).generate();
    }

    private static CodegenConfig forName(String name) {
        ServiceLoader<CodegenConfig> loader = ServiceLoader.load(CodegenConfig.class);
        for (CodegenConfig config : loader) {
            if (config.getName().equals(name)) {
                return config;
            }
        }

        // else try to load directly
        try {
            return (CodegenConfig) Class.forName(name).newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Can't load config class with name ".concat(name), e);
        }
    }
}
