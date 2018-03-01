package io.swagger.documentation.swagger.apis;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by Fares on 3/9/16.
 */

/**
 * Swagger apisjson describes the list of Apis.
 * Built based on apijson.org specs V0.15
 * @link http://apisjson.org/format/apisjson_0.15.txt
 *
 */
@RequiredArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class SwaggerApis {
    private String description;
    private String image;
    private String created;
    private String url;
    private final String specificationVersion = "0.15";
    private String basePath;
    private List<SwaggerApi> apisList;
}

