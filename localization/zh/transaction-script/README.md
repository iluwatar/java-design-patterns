---
layout: pattern
title: Transaction Script
folder: transaction-script
permalink: /patterns/transaction-script/zh
categories: Behavioral
language: zh
tags:
 - Data access
---

## 目的
事务脚本按过程组织业务逻辑，其中每个过程处理一个
演示文稿中的请求。

## 解释

真实世界的例子

> 您需要创建一个酒店房间预订系统。由于要求非常简单，我们打算
> 在此处使用事务脚本模式。

简单来说

> 事务脚本将业务逻辑组织成系统需要执行的事务。

程序化示例

“酒店”类负责预订和取消房间预订。

```java
@Slf4j
public class Hotel {

  private final HotelDaoImpl hotelDao;

  public Hotel(HotelDaoImpl hotelDao) {
    this.hotelDao = hotelDao;
  }

  public void bookRoom(int roomNumber) throws Exception {

    Optional<Room> room = hotelDao.getById(roomNumber);

    if (room.isEmpty()) {
      throw new Exception("Room number: " + roomNumber + " does not exist");
    } else {
      if (room.get().isBooked()) {
        throw new Exception("Room already booked!");
      } else {
        Room updateRoomBooking = room.get();
        updateRoomBooking.setBooked(true);
        hotelDao.update(updateRoomBooking);
      }
    }
  }

  public void cancelRoomBooking(int roomNumber) throws Exception {

    Optional<Room> room = hotelDao.getById(roomNumber);

    if (room.isEmpty()) {
      throw new Exception("Room number: " + roomNumber + " does not exist");
    } else {
      if (room.get().isBooked()) {
        Room updateRoomBooking = room.get();
        updateRoomBooking.setBooked(false);
        int refundAmount = updateRoomBooking.getPrice();
        hotelDao.update(updateRoomBooking);

        LOGGER.info("Booking cancelled for room number: " + roomNumber);
        LOGGER.info(refundAmount + " is refunded");
      } else {
        throw new Exception("No booking for the room exists");
      }
    }
  }
}
```
`Hotel` 类有两种方法，一种分别用于预订和取消房间。每一个
它们处理系统中的单个事务，使`Hotel` 实现事务脚本
图案。

`bookRoom` 方法整合了所有需要的步骤，比如检查房间是否已经被预订
与否，如果未预订，则预订房间并使用 DAO 更新数据库。

`cancelRoom` 方法整合了检查房间是否被预订等步骤，
如果已预订，则计算退款金额并使用 DAO 更新数据库。

## 类图

![alt text](./etc/transaction-script.png "Transaction script model")

## 适用性
当应用程序只有少量逻辑并且
将来不会扩展逻辑。

## 结果

* 随着业务逻辑变得越来越复杂，
  保留交易脚本变得越来越困难
  处于精心设计的状态。
* 交易脚本之间可能会出现代码重复。
* 通常不容易将事务脚本重构为其他域逻辑
  模式。

## 相关模式

* 领域模型
* 表格模块
* 服务层

## 鸣谢

* [Transaction Script Pattern](https://dzone.com/articles/transaction-script-pattern#:~:text=Transaction%20Script%20(TS)%20is%20the,need%20big%20architecture%20behind%20them.)
* [Transaction Script](https://www.informit.com/articles/article.aspx?p=1398617)
* [Patterns of Enterprise Application Architecture](https://www.amazon.com/gp/product/0321127420/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0321127420&linkId=18acc13ba60d66690009505577c45c04)
