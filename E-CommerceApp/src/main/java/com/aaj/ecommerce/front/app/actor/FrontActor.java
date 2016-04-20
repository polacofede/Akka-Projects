package com.aaj.ecommerce.front.app.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class FrontActor extends UntypedActor {

	private final ActorRef recommendations;
	private final ActorRef trend;
	
	public static Props getProps(ActorRef recommendations, ActorRef trend){
		return Props.create(FrontActor.class, recommendations, trend);
	}
	
	public FrontActor(ActorRef recommendations, ActorRef trend) {
		System.out.println("FrontActor created");
		this.recommendations = recommendations;
		this.trend = trend;
	}

	@Override
	public void onReceive(Object message) throws Exception {
		System.out.println(getSelf().path().toString() + " got message: " + message);
		recommendations.tell(message, self());
		trend.tell(message, self());
	}
	
	


}
