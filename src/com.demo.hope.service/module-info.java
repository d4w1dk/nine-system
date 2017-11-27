module com.demo.hope.service {
    requires com.demo.hope;
    requires retrofit;
    requires org.apache.commons.lang3;
    requires jdk.incubator.httpclient;
    requires reactor.core;
    requires com.demo.hope.flow;
    requires akka.stream2;


    exports com.demo.hope.service.Population;
    exports com.demo.hope.service.Weather;
}