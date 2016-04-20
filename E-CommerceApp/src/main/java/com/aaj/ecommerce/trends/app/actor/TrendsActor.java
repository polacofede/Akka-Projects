package com.aaj.ecommerce.trends.app.actor;

import akka.actor.UntypedActor;

public class TrendsActor extends UntypedActor{

	public TrendsActor() {
		super();
	}

	@Override
	public void onReceive(Object message) throws Exception {
		System.out.println("TrendsActor got " + message);
	}

}
