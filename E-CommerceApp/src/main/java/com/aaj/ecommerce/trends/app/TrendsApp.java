package com.aaj.ecommerce.trends.app;

import java.io.File;
import java.net.URL;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.FromConfig;

import com.aaj.ecommerce.recommendations.app.RecommendationsApp;
import com.aaj.ecommerce.trends.app.actor.TrendsActor;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class TrendsApp {
	private static ActorSystem trends;
	public static void main(String[] args) throws Exception {
		URL filePath = RecommendationsApp.class.getClassLoader().getResource("trends.conf");
		Config config = ConfigFactory.parseFile(new File(filePath.toURI()));
		trends = ActorSystem.create("trends", config);
		ActorRef trend = trends.actorOf(FromConfig.getInstance().props(Props.create(TrendsActor.class)), 
				 "TrendsActor");
		
	}

}
