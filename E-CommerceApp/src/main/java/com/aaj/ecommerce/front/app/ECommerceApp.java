package com.aaj.ecommerce.front.app;

import java.io.File;
import java.net.URL;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.server.HttpApp;
import akka.http.javadsl.server.Route;
import akka.routing.FromConfig;

import com.aaj.ecommerce.front.app.actor.FrontActor;
import com.aaj.ecommerce.front.app.actor.GreeterActor;
import com.aaj.ecommerce.front.app.controller.Controller;
import com.aaj.ecommerce.front.app.controller.ECommerceController;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ECommerceApp extends HttpApp {

	private static ActorSystem frontSystem;
	private static ActorRef greeterActor;
	private Controller ecommerceController;

	public static void main(String[] args) throws Exception {
		URL filePath = ECommerceApp.class.getClassLoader().getResource(
				"front.conf");
		Config config = ConfigFactory.parseFile(new File(filePath.toURI()));
		frontSystem = ActorSystem.create("front", config);

		ActorRef recommendations = frontSystem.actorOf(FromConfig.getInstance()
				.props(), "recommendations");
		ActorRef trend = frontSystem.actorOf(FromConfig.getInstance().props(),
				"trends");

		ActorRef frontActor = frontSystem.actorOf(FromConfig.getInstance()
				.props(FrontActor.getProps(recommendations, trend)),
				"frontActor");
		greeterActor = frontSystem.actorOf(
				FromConfig.getInstance().props(GreeterActor.getProps()),
				"greeterActor");

		int i = 0;
		for (; i < 10; i++) {
			frontActor.tell("message " + i, ActorRef.noSender());
		}
		Thread.sleep(2000);
		for (; i < 20; i++) {
			frontActor.tell("message " + i, ActorRef.noSender());
		}

		new ECommerceApp(new ECommerceController(greeterActor)).bindRoute(
				"localhost", 8080, frontSystem);
		System.out.println("Type RETURN to exit the app");
		System.in.read();
		frontSystem.terminate();

	}

	private ECommerceApp(Controller ecommerceController) {
		this.ecommerceController = ecommerceController;
	}

	@Override
	public Route createRoute() {
		try {
			return this.ecommerceController.defineRoutes().get();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}