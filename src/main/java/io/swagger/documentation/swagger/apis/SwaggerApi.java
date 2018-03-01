package io.swagger.documentation.swagger.apis;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by Fares on 3/9/16.
 */


@RequiredArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class SwaggerApi {
    protected String name;
    protected String description;
    protected String image;
    protected String humanURL;
    protected String baseURL;
    protected String version;
    protected List<String> tags;
    protected List<Properties> properties;
    protected List<Contact> contacts;
}