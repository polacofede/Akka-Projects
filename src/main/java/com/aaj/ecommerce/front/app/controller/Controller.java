package com.aaj.ecommerce.front.app.controller;

import java.util.concurrent.CompletableFuture;

import akka.http.javadsl.server.Route;

public interface Controller {

	CompletableFuture<Route> defineRoutes();
	
}
