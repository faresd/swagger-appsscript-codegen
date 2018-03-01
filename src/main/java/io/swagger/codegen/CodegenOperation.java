package io.swagger.codegen;

public class CodegenOperation extends io.swagger.codegen.CodegenOperation {

    public String resource = "";

    public CodegenOperation(io.swagger.codegen.CodegenOperation op) {
        this.hasAuthMethods = op.hasAuthMethods;
        this.hasConsumes = op.hasConsumes;
        this.hasProduces = op.hasProduces;
        this.hasParams = op.hasParams;
        this.hasOptionalParams = op.hasOptionalParams;
        this.returnTypeIsPrimitive = op.returnTypeIsPrimitive;
        this.returnSimpleType = op.returnSimpleType;
        this.subresourceOperation = op.subresourceOperation;
        this.isMapContainer = op.isMapContainer;
        this.isListContainer = op.isListContainer;
        this.isMultipart = op.isMultipart;
        this.hasMore = op.hasMore;
        this.isResponseBinary = op.isResponseBinary;
        this.hasReference = op.hasReference;
        this.path = op.path;
        this.operationId = op.operationId;
        this.returnType = op.returnType;
        this.httpMethod = op.httpMethod;
        this.returnBaseType = op.returnBaseType;
        this.returnContainer = op.returnContainer;
        this.summary = op.summary;
        this.notes = op.notes;
        this.baseName = op.baseName;
        this.defaultResponse = op.defaultResponse;
        this.discriminator = op.discriminator;
        this.consumes = op.consumes;
        this.produces = op.produces;
        this.bodyParam = op.bodyParam;
        this.allParams = op.allParams;
        this.bodyParams = op.bodyParams;
        this.pathParams = op.pathParams;
        this.queryParams = op.queryParams;
        this.headerParams = op.headerParams;
        this.formParams = op.formParams;
        this.authMethods = op.authMethods;
        this.tags = op.tags;
        this.responses = op.responses;
        this.imports = op.imports;
        this.examples = op.examples;
        this.externalDocs = op.externalDocs;
        this.vendorExtensions = op.vendorExtensions;
        this.nickname = op.nickname;
        this.operationIdLowerCase = op.operationIdLowerCase;
    }
}
