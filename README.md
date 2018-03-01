# Swagger Codegen for the appsscript library


## What's Swagger?
The goal of Swagger™ is to define a standard, language-agnostic interface to REST APIs which allows both humans and computers to discover and understand the capabilities of the service without access to source code, documentation, or through network traffic inspection. When properly defined via Swagger, a consumer can understand and interact with the remote service with a minimal amount of implementation logic. Similar to what interfaces have done for lower-level programming, Swagger removes the guesswork in calling the service.


Check out [OpenAPI-Spec](https://github.com/OAI/OpenAPI-Specification) for additional information about the Swagger project, including additional libraries with support for other languages and more. 

## Getting started

### Preparing swagger file(s)
Add your api or list of apis to apis.json under /swagger-apis that respects [apisjson_0.15](http://apisjson.org/format.html) specs and contains a list of one or more swagger files per api.

Example: [Example](/swagger-apis/apis.json)

### Running the generator

```
mvn package -P appsscript-generator
```

This will lunch a set of executions that will generate, install npm packages, browserify and bundle the client library that is ready to deploy.
All the generated code is located under `/target` directory including generated Node.js libraries under `/target/swagger-appsscript-client` and  the browserified and bundled files `/target/bundles`

### Deployment
1. Follow steps 1 and 2 in [node-google-apps-script](https://github.com/danthareja/node-google-apps-script), to create an Apps Script project and authenticate it locally.
2. Get your project ID from the address bar, as mentioned in step 3, and add it to `appsscript.project.id` maven property located in pom.xml.
3. ```mvn package -P appsscript-deployment```

To run and deploy:

```
mvn package -P appsscript-generator,appsscript-deployment 
```


## Further development
### Running the generator from command line.
In your generator project.  A single jar file will be produced in `target`.  You can now use that with codegen:

```
java -cp /path/to/swagger-codegen-distribution:/path/to/your/jar io.swagger.codegen.Codegen -l appsscript -o ./test
```

Now your templates are available to the client generator and you can write output values

### How do I modify this?
The `AppsscriptGenerator.java` has comments in it--lots of comments.  There is no good substitute
for reading the code more, though.  See how the `AppsScriptClientCodegen` extends `JavascriptClientCodegen`.
That class has the signature of all values that can be overridden.


The mustache templates, are located under `/resources/mustache` directory

You can execute the `java` command from above while passing different debug flags to show
the object you have available during client generation:

```
# The following additional debug options are available for all codegen targets:
# -DdebugSwagger prints the OpenAPI Specification as interpreted by the codegen
# -DdebugModels prints models passed to the template engine
# -DdebugOperations prints operations passed to the template engine
# -DdebugSupportingFiles prints additional data passed to the template engine

java -DdebugOperations -cp /path/to/swagger-codegen-distribution:/path/to/your/jar io.swagger.codegen.Codegen -l appsscript -o ./test
```