package com.aaj.ecommerce.recommendations.app;

import java.io.File;
import java.net.URL;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.FromConfig;

import com.aaj.ecommerce.recommendations.app.actor.RecommendationsActor;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;


public class RecommendationsApp {
private static ActorSystem recommendations;
	
	public static void main(String[] args) throws Exception {
		URL filePath = RecommendationsApp.class.getClassLoader().getResource("recommendations.conf");
		Config config = ConfigFactory.parseFile(new File(filePath.toURI()));
		recommendations = ActorSystem.create("recommendations", config);
		ActorRef recommendation = recommendations.actorOf(FromConfig.getInstance().props(Props.create(RecommendationsActor.class)), 
				 "RecommendationsActor");
		recommendation.tell("Pedo", ActorRef.noSender());
	}
}