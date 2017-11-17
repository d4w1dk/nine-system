module com.demo.hope.service {
    requires com.demo.hope;
    requires retrofit;
    requires org.apache.commons.lang3;
    requires jdk.incubator.httpclient;


    exports com.demo.hope.service.Population;
    exports com.demo.hope.service.Weather;
}