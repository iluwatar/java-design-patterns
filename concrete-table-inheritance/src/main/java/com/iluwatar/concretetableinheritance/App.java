/*
 * Permission is hereby granted, free of charge, to any person obtaining a copy.
 */

package com.iluwatar.concretetableinheritance;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

/**
 * The App class to run the application.
 */
@Slf4j
public final class App {

  /**
   * constructor.
   */
  private App() { }

  /**
   * This is main entrance.
   *
   * @param args input args.
   */
  public static void main(String[] args) {

    final SqlSession sqlSession = Mybatis3Utils.getCurrentSqlSession();
    final PlayerMapper playerMapper = new PlayerMapper(sqlSession);
    playerMapper.createNewTable();

    final Bowler bowler = new Bowler();
    bowler.setPlayerId(1);
    bowler.setName("bowler");
    bowler.setBowlingAverage(1.0);
    bowler.setBattingAverage(1.0);
    playerMapper.insertPlayer(bowler);

    final Cricketer cricketer = new Cricketer();
    cricketer.setPlayerId(2);
    cricketer.setName("cricketer");
    cricketer.setBattingAverage(1.0);
    playerMapper.insertPlayer(cricketer);

    final Footballer footballer = new Footballer();
    footballer.setPlayerId(3);
    footballer.setName("footballer");
    footballer.setClub("club");
    playerMapper.insertPlayer(footballer);
    sqlSession.commit();

    playerMapper.listPlayers("cricketer").forEach(x -> {
      if (LOGGER.isInfoEnabled()) {
        LOGGER.info(x.getPlayerId() + ' ' + x.getName());
      }
    });
    playerMapper.listPlayers("bowler").forEach(x -> {
      if (LOGGER.isInfoEnabled()) {
        LOGGER.info(x.getPlayerId() + ' ' + x.getName());
      }
    });
    playerMapper.listPlayers("footballer").forEach(x -> {
      if (LOGGER.isInfoEnabled()) {
        LOGGER.info(x.getPlayerId() + ' ' + x.getName());
      }
    });

    playerMapper.dropTable();
    sqlSession.close();
  }
}
