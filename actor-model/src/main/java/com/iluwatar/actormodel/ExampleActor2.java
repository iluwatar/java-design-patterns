package com.iluwatar.actormodel;

import java.util.logging.Logger;

public class ExampleActor2 extends Actor{
  private final ActorSystem actorSystem;

  public ExampleActor2(ActorSystem actorSystem) {
    this.actorSystem = actorSystem;
  }
  Logger logger = Logger.getLogger(getClass().getName());

  @Override
  protected void onReceive(Message message) {
    logger.info("[" + getActorId()+"]" + "Received : " +message.getContent());
  }
}
