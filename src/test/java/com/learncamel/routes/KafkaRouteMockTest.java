package com.learncamel.routes;

import com.learncamel.domain.Item;
import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.DisableJmx;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

/**
 * Created by z001qgd on 1/13/18.
 */
@ActiveProfiles("mock")
@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisableJmx(true)
public class KafkaRouteMockTest extends CamelTestSupport{


    @Autowired
    private CamelContext context;

    @Autowired
    protected CamelContext createCamelContext() {
        return context;
    }

    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private ConsumerTemplate consumerTemplate;

    @Override
    protected RouteBuilder createRouteBuilder(){
        return new KafkaRoute();
    }

    @Autowired
    Environment environment;

    @Before
    public void setUp(){

    }

    @Test
    public void unMarshalTest(){
        String input = "{\"transactionType\":\"ADD\", \"sku\":\"100\", \"itemDescription\":\"SamsungTV\", \"price\":\"500.00\"}";

        Item item = (Item) producerTemplate.requestBodyAndHeader(environment.getProperty("fromRoute"), input, "env", "mock");

        System.out.println("Items: "+item);
        assertEquals("100", item.getSku());
    }

}
