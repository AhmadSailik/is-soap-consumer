package com.example.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SoapConsumer extends RouteBuilder {
    @Override
    public void configure() throws Exception {


        from("timer:foo?repeatCount=1")
//        from("direct:processOrder")
                .setHeader(CxfConstants.OPERATION_NAME,
                        constant("Add"))
                .setHeader(CxfConstants.OPERATION_NAMESPACE,
                        constant("http://tempuri.org/"))
                .process(ex->{
                    List list=new ArrayList<>();
                    list.add(9);
                    list.add(9);
                    ex.getIn().setBody(list, List.class);
                })
                .to("cxf://http://www.dneonline.com/calculator.asmx"
                        + "?serviceClass=org.tempuri.CalculatorSoap"
                        + "&wsdlURL=classpath:wsdl/BookService.wsdl")
                .log("${body}");

    }
}
