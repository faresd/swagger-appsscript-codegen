package io.swagger.codegen;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.pnikosis.html2markdown.HTML2Md;
import io.swagger.codegen.*;
import io.swagger.codegen.languages.JavascriptClientCodegen;
import io.swagger.models.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AppsScriptClientCodegen extends JavascriptClientCodegen {
  private static final Logger LOGGER = LoggerFactory.getLogger(AppsScriptClientCodegen.class);
  protected String projectName;

  Map<String, String> reservedMethodNames = new HashMap<String, String>();

  public AppsScriptClientCodegen() {
    super();

    cliOptions.add(new CliOption(USE_INHERITANCE,
            "use Appsscript prototype chains & delegation for inheritance")
            .defaultValue(Boolean.TRUE.toString()));

    reservedMethodNames.put("delete", "remove");
  }

  @Override
  public String getName() {
    return "appsscript";
  }

  @Override
  public String getHelp() {
    return "Generates a Appsscript client library.";
  }

  @Override
  public void preprocessSwagger(Swagger swagger) {
    super.preprocessSwagger(swagger);

    // default values
    String projectName = Character.toLowerCase(moduleName.charAt(0)) + moduleName.substring(1);

    if (StringUtils.isBlank(moduleName)) {
      moduleName = camelize(underscore(projectName));
    }
    if (projectDescription == null) {
      projectDescription = "Client library of " + projectName;
    }

    additionalProperties.put(PROJECT_NAME, projectName);
    additionalProperties.put(MODULE_NAME, moduleName);
    additionalProperties.put(PROJECT_DESCRIPTION, escapeText(projectDescription));

    supportingFiles.add(new SupportingFile("app.mustache", "", "app.js"));
  }

  public CodegenOperation fromOperation(String path, String httpMethod, Operation operation, Map<String, Model> definitions, Swagger swagger) {
    io.swagger.codegen.CodegenOperation op =  super.fromOperation(path, httpMethod, operation, definitions, swagger);
    CodegenOperation newOp = new CodegenOperation(op);

    String operationId = operation.getOperationId();
    if(!Strings.isNullOrEmpty(operationId)) {
      String[] split = operationId.split("\\.");
      if (split.length > 2) {
        String methodName = split[2];
        if(reservedMethodNames.containsKey(methodName)) methodName = reservedMethodNames.get(methodName);

        newOp.operationId = operationId;
        newOp.nickname = Joiner.on('.').join(split[1], methodName);
        newOp.resource = split[1];
        try {
          newOp.summary = HTML2Md.convertHtml(newOp.summary, "UTF-8");
          newOp.notes = HTML2Md.convertHtml(newOp.notes, "UTF-8");
        } catch (IOException e) {
          e.printStackTrace();
        }


      }
    }

    return newOp;
  }

}