package com.iluwatar.classtableinheritance;

import java.util.List;

/**
 * solve some function.
 */

public interface MapperPlayer {
  List<Player> list_player();

  List<Footballer> list_football_player();

  List<Cricketer> list_cricketer();

  List<Bowler> list_bowler();

  Player get_player(String name);

  int insert_player(Player player);

  int delete_player(String name);

  Footballer get_footballer(String name);

  int insert_footballer(Footballer player);

  int update_footballer(Footballer footballer);

  int delete_footballer(String name);

  Cricketer get_Cricketer(double id);

  int insert_Cricketer(Cricketer player);

  int update_Cricketer(Cricketer cricketer);

  int delete_Cricketer(double id);

  Bowler get_Bowler(double id);

  int insert_Bowler(Bowler player);

  int update_Bowler(Bowler bowler);

  int delete_Bowler(double id);
}
