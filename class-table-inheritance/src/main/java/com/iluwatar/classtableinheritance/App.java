/*
 * Permission is hereby granted, free of charge, to any person obtaining a copy.
 */

package com.iluwatar.classtableinheritance;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

/**
 * App class use these functions to test.
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

  public static void main( String[] args) {

    SqlSession sqlSession;
    MapperPlayer playerMapper;
    sqlSession = Mybatis3Utils.getCurrentSqlSession();
    playerMapper = sqlSession.getMapper(MapperPlayer.class);

    Player a = new Player();
    a.setName("player1");
    playerMapper.insertPlayer(a);
    sqlSession.commit();

    Footballer footballer = new Footballer();
    footballer.setClub("footballerclub");
    footballer.setName("footballer1");
    playerMapper.insertFootballer(footballer);
    sqlSession.commit();

    Bowler bowler = new Bowler();
    bowler.setName("bowler1");
    playerMapper.insertBowler(bowler);
    sqlSession.commit();

    Cricketer cricketer = new Cricketer();
    cricketer.setName("cricketer1");
    playerMapper.insertCricketer(cricketer);
    sqlSession.commit();

    playerMapper.listplayer().forEach(x -> {
      System.out.println(x.getName());
    });
    playerMapper.listFootballPlayer().forEach(x -> {
      System.out.println(x.getName() + ' ' + x.getClub());
    });
    playerMapper.listCricketer().forEach(x -> {
      System.out.println(x.getName() + " " + x.getBattingAvarage());
    });
    for (Bowler x : playerMapper.listBowler()) {
      System.out.println(x.getName()
          + " " + x.getBattingAvarage() + x.getBowlingAvarage());
    }
  }
}
