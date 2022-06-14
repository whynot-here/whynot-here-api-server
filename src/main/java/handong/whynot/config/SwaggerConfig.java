package handong.whynot.config;

import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    private static final String TITLE = "whynot-here-api";
    private static final String DESCRIPTION = "whynot-here-api";
    private static final String VERSION = "1.0.0";

    public static OpenApiCustomiser globalOpenApiCustomer() {
        return openApi ->
            openApi.info(new Info()
                             .title(TITLE)
                             .description(DESCRIPTION)
                             .version(VERSION));

    }

    // 22.03.13. 추후 API가 많아질 때 group 적용
    public GroupedOpenApi accountGroup() {
        return GroupedOpenApi.builder()
                             .group("Account")
                             .addOpenApiCustomiser(globalOpenApiCustomer())
                             .addOpenApiMethodFilter(method ->
                                                         StringUtils.contains(
                                                             String.valueOf(method.getDeclaringClass()),
                                                             "AccountController"))
                             .build();
    }

    public GroupedOpenApi commentGroup() {
        return GroupedOpenApi.builder()
                             .group("Comment")
                             .pathsToMatch("/*/comments/**")
                             .addOpenApiCustomiser(globalOpenApiCustomer())
                             .build();
    }

    public GroupedOpenApi jobGroup() {
        return GroupedOpenApi.builder()
                             .group("Job")
                             .pathsToMatch("/*/jobs/**")
                             .addOpenApiCustomiser(globalOpenApiCustomer())
                             .build();
    }

    public GroupedOpenApi postGroup() {
        return GroupedOpenApi.builder()
                             .group("Post")
                             .pathsToMatch("/*/posts/**")
                             .addOpenApiCustomiser(globalOpenApiCustomer())
                             .build();
    }

    public GroupedOpenApi s3Group() {
        return GroupedOpenApi.builder()
                             .group("S3")
                             .pathsToMatch("/image/**")
                             .addOpenApiCustomiser(globalOpenApiCustomer())
                             .build();
    }
}
