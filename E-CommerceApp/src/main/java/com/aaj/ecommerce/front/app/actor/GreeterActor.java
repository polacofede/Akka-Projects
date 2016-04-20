package com.aaj.ecommerce.front.app.actor;

import akka.actor.Props;
import akka.actor.UntypedActor;

public class GreeterActor extends UntypedActor {

	
	public static Props getProps(){
		return Props.create(GreeterActor.class);
	}
	
	public GreeterActor() {
		System.out.println("GreeterActor created");
	}

	@Override
	public void onReceive(Object name) throws Exception {
		System.out.println(getSelf().path().toString() + " got message: " + name);
		sender().tell("Hello " + name, self());
	}
	
	


}
