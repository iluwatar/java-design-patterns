---
layout: pattern
title: class table inheritance pattern
folder: class-table-inheritance
permalink: /patterns/class-table-inheritance/
categories: Creational
language: en
tags:
- Decoupling
---

## Intent

Represents an inheritance hierarchy of classes, with one table per class.
Class table inheritance supports one database table per class in the inheritance structure.

## Explanation

Real world example

> Suppose you have a parent class called Player, where the football Player and cricketer naturally inherit from 
> the Player class and the bowler from the cricketer class. At this point, the relationship between the 
> domain model and the database is very simple.


**Programmatic Example**

Let's first introduce the `palyer` interface . It defines various methods.

```java
public interface player {
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

```

Next here's the `mapper/Player.xml` .It will be used by Mybatis3Utils.java

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="mapper.player">
<insert id="insert_player">insert into player(name) values (#{name})</insert>
<insert id="insert_footballer">insert into footballer(name,club) values (#{name},#{club})</insert>
<insert id="insert_Cricketer">insert into cricketer(battingAvarage,name) values (#{battingAvarage},#{name})</insert>
<insert id="insert_Bowler">insert into bowler(bowlingAvarage,battingAvarage,name) values (#{bowlingAvarage},#{battingAvarage},#{name})</insert>


<update id="update_footballer">update  footballer set club=#{club} where name =#{name}</update>
<update id="update_Cricketer">update  cricketer set battingAvarage=#{battingAvarage} where name =#{name}</update>
<update id="update_Bowler">update bowler set battingAvarage=#{battingAvarage}, bowlingAvarage=#{bowlingAvarage} where name =#{name}</update>


<delete id="delete_player"> DELETE FROM player WHERE name = #{name}</delete>
<delete id="delete_footballer">DELETE FROM footballer WHERE club = #{name}</delete>
<delete id="delete_Cricketer">DELETE FROM cricketer WHERE battingAvarage = #{id}</delete>
<delete id="delete_Bowler">DELETE FROM bowler WHERE bowlingAvarage = #{id}</delete>

<select id="list_player" resultType="entity.Player">select * from player</select>
<select id="list_football_player" resultType="entity.Footballer">select * from footballer</select>
<select id="list_cricketer" resultType="entity.Cricketer">select * from cricketer</select>
<select id="list_bowler" resultType="entity.Bowler">select * from bowler</select>
<select id="get_player" resultType="entity.Player">select * from player where name=#{name}</select>
<select id="get_footballer" resultType="entity.Footballer">select * from footballer where name=#{name}</select>
<select id="get_Cricketer" resultType="entity.Cricketer">select * from cricketer where battingAvarage=#{id}</select>
<select id="get_Bowler" resultType="entity.Bowler">select * from bowler where bowlingAvarage=#{id}</select>
</mapper>
```


Let's first introduce the `Mybatis3Utils.java`  . It will use mybatis-config.xml to get some messages from Player.xml

```java
public abstract class Mybatis3Utils {

    public static final SqlSessionFactory sqlSessionFactory;
    public static final ThreadLocal<SqlSession> sessionThread = new ThreadLocal<>();

    static {
        try {
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static SqlSession getCurrentSqlSession() {
        SqlSession sqlSession = sessionThread.get();
        if (Objects.isNull(sqlSession)) {
            sqlSession = sqlSessionFactory.openSession();
            sessionThread.set(sqlSession);
        }
        return sqlSession;
    }

    public static void closeCurrentSession() {
        SqlSession sqlSession = sessionThread.get();
        if (Objects.nonNull(sqlSession)) {
            sqlSession.close();
        }
        sessionThread.set(null);
    }
}

```

## Class diagram

![alt text](https://github.com/KingOfXi/java-design-patterns/blob/master/class-table-inheritance/src/main/java/test.png "class table inheritance pattern")

## Applicability

Use the Class table inheritance pattern when:

* All columns are relevant for every row so tables are easier to understand and don’t waste space.
* The relationship between the domain model and the database is very straightforward.

## Credits

* [Example for java](https://www.martinfowler.com/eaaCatalog/classTableInheritance.html)
* [Java8 Streams](https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html)
