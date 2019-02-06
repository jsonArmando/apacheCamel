package com.learncamel.route;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SimpleCamelRoute  extends RouteBuilder{

    @Autowired
    Environment environment;

    @Override
    public void configure() throws Exception {
        log.info("Starting the Camel Route");
        from("{{startRoute}}")
                .log("Timer Invoked and the body" + environment.getProperty("message"))
                .choice()
                    .when((header("env").isNotEqualTo("mock")))
                        .pollEnrich("{{fromRoute}}")
                    .otherwise()
                        .log("mock env flow and the body is ${body}")
                .end()
                .to("{{toRoute1}}");
        log.info("Ending the Camel Route");
    }
}
