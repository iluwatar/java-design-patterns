---
title: Table Module
category: Structural
language: zh
tag:
 - Data access
---

## 又被称为
表模块

## Intent
表模块模式将域逻辑按数据库中的每个表组织为一个类，并且一个类的单个实例包含将对数据执行的各种过程。

## Explanation

现实世界例子

> 当处理一个用户系统时，我们需要在用户表上进行一些操作。在这种情况下，我们可以使用表模块模式。我们可以创建一个名为 UserTableModule 的类，并初始化该类的一个实例，来处理用户表中所有行的业务逻辑。

直白点说

> 一个单独的实例，处理数据库表或视图中所有行的业务逻辑。

**编程实例**

在用户系统的示例中，我们需要处理用户登录和用户注册的域逻辑。我们可以使用表模块模式，并创建UserTableModule类的一个实例来处理用户表中所有行的业务逻辑。

以下是基本的User实体。

```java
@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class User {
  private int id;
  private String username;
  private String password;
}
```

下面的是 `UserTableModule` 类.

```java
public class UserTableModule {
  private final DataSource dataSource;
  private Connection connection = null;
  private ResultSet resultSet = null;
  private PreparedStatement preparedStatement = null;

  public UserTableModule(final DataSource userDataSource) {
    this.dataSource = userDataSource;
  }
  
  /**
   * Login using username and password.
   *
   * @param username the username of a user
   * @param password the password of a user
   * @return the execution result of the method
   * @throws SQLException if any error
   */
  public int login(final String username, final String password) throws SQLException {
  		// Method implementation.

  }

  /**
   * Register a new user.
   *
   * @param user a user instance
   * @return the execution result of the method
   * @throws SQLException if any error
   */
  public int registerUser(final User user) throws SQLException {
  		// Method implementation.
  }
}
```

在App类中，我们使用UserTableModule的一个实例来处理用户登录和注册。

```java
// Create data source and create the user table.
final var dataSource = createDataSource();
createSchema(dataSource);
userTableModule = new UserTableModule(dataSource);

//Initialize two users.
var user1 = new User(1, "123456", "123456");
var user2 = new User(2, "test", "password");

//Login and register using the instance of userTableModule.
userTableModule.registerUser(user1);
userTableModule.login(user1.getUsername(), user1.getPassword());
userTableModule.login(user2.getUsername(), user2.getPassword());
userTableModule.registerUser(user2);
userTableModule.login(user2.getUsername(), user2.getPassword());

deleteSchema(dataSource);
```

程序输出：

```java
12:22:13.095 [main] INFO com.iluwatar.tablemodule.UserTableModule - Register successfully!
12:22:13.117 [main] INFO com.iluwatar.tablemodule.UserTableModule - Login successfully!
12:22:13.128 [main] INFO com.iluwatar.tablemodule.UserTableModule - Fail to login!
12:22:13.136 [main] INFO com.iluwatar.tablemodule.UserTableModule - Register successfully!
12:22:13.144 [main] INFO com.iluwatar.tablemodule.UserTableModule - Login successfully!
```

## 类图

![](etc/table-module.urm.png "table module")

## 应用
使用表模块模式当：

- 域逻辑简单且数据呈表格形式。
- 应用程序仅使用少量共享的常见的面向表格的数据结构。

## 教学

- [Transaction Script](https://java-design-patterns.com/patterns/transaction-script/)

- [Domain Model](https://java-design-patterns.com/patterns/domain-model/)

## 鸣谢

* [Table Module Pattern](http://wiki3.cosc.canterbury.ac.nz/index.php/Table_module_pattern)
* [Patterns of Enterprise Application Architecture](https://www.amazon.com/gp/product/0321127420/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0321127420&linkId=18acc13ba60d66690009505577c45c04)
* [Architecture patterns: domain model and friends](https://inviqa.com/blog/architecture-patterns-domain-model-and-friends)