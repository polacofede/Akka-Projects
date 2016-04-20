package com.aaj.ecommerce.recommendations.app.actor;

import akka.actor.UntypedActor;

public class RecommendationsActor extends UntypedActor {

	public RecommendationsActor() {
		super();
	}

	@Override
	public void onReceive(Object message) throws Exception {
		System.out.println("RecommendationsActor got " + message);
	}

}
