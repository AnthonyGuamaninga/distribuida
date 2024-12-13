package com.programacion.distribuida.authors.rest;

import io.quarkus.runtime.StartupEvent;
import io.vertx.ext.consul.ConsulClientOptions;
import io.vertx.ext.consul.ServiceOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.consul.ConsulClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

@ApplicationScoped
public class AuthorsLifeCycle {

    @Inject
    @ConfigProperty(name = "consul.host", defaultValue = "127.0.0.1")
    String consultIp;

    @Inject
    @ConfigProperty(name = "consult.port", defaultValue = "8500")
    Integer consulPort;

    @Inject
    @ConfigProperty(name = "consult.http.port", defaultValue = "8500")
    Integer appPort;

    String serviceId;


    public void init(@Observes StartupEvent event, Vertx vertx) throws UnknownHostException {
        System.out.println("Authors service is starting ...");

        ConsulClient client = ConsulClient.create(vertx,
                new ConsulClientOptions()
                        .setHost(consultIp)
                        .setPort(consulPort)
        );

        serviceId = UUID.randomUUID().toString();
        var ipAddress = InetAddress.getLocalHost();

        client.registerServiceAndAwait(
                new ServiceOptions()
                        .setName("app-authors")
                        .setId(serviceId)
                        .setAddress(ipAddress.getHostAddress())
                        .setPort(appPort)
        );
    }

    public void stop(@Observes StartupEvent event, Vertx vertx) throws UnknownHostException {
        System.out.println("Authors service is stopping ...");

        ConsulClient client = ConsulClient.create(vertx,
                new ConsulClientOptions()
                        .setHost(consultIp)
                        .setPort(consulPort)
        );

        client.deregisterServiceAndAwait(serviceId);
    }

}
