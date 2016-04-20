package com.aaj.ecommerce.front.app.controller;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import akka.http.javadsl.model.ContentTypes;
import akka.http.javadsl.server.RequestContext;
import akka.http.javadsl.server.RequestVal;
import akka.http.javadsl.server.Route;
import akka.http.javadsl.server.RouteResult;
import akka.http.javadsl.server.values.Parameters;
import akka.pattern.PatternsCS;
import akka.actor.ActorRef;
import akka.http.javadsl.server.directives.PathDirectives;

public class ECommerceController extends PathDirectives implements Controller{

	final ActorRef greeterActor;
	final CompletableFuture<Route> routes;
	
	public ECommerceController(ActorRef greeterActor) {
		this.greeterActor = Objects.requireNonNull(greeterActor);
		routes = initRoutes();
	}

	@Override
	public CompletableFuture<Route> defineRoutes() {
		return this.routes;
	}
	
	
	// A RequestVal is a type-safe representation of some aspect of the request.
    // In this case it represents the `name` URI parameter of type String.
    private RequestVal<String> name = Parameters.stringValue("name").withDefault("Mister X");
	
	private CompletionStage<RouteResult> handleHelloL(final RequestContext ctx){
		return PatternsCS.ask(greeterActor, name.get(ctx), 1000).thenApplyAsync(
				(t) -> {
						return ctx.complete(t.toString());
				}, ctx.executionContext());
	}    
    
    private CompletableFuture<Route> initRoutes() {
    	
    	Route echoRoute =
    			            handleWith1(name,
    			                (ctx, name) -> ctx.complete("Echo " + name + "!")
    			             );

    	return CompletableFuture.supplyAsync(()->{return route(
                // handle GET requests
                get(
                    // matches the empty path
                    pathSingleSlash().route(
                        // return a constant string with a certain content type
                        complete(ContentTypes.TEXT_HTML_UTF8,
                                "<html><body>Hello world!</body></html>")
                    ),
                    path("ping").route(
                        // return a simple `text/plain` response
                        complete("PONG!")
                    ),
                    path("hello").route(handleWith(
                            (ctx) -> 
                            ctx.completeWith(handleHelloL(ctx)), name
                     )),
                     path("echo").route(echoRoute)
                ),
                //handle POST
                post(path("echo").route(echoRoute))
            );});
        
    }

}
