---
title: Combinator
category: Idiom
language: zh
tag:
 - Reactive
---

## 或称

构图模式

## 目的

功能模式代表了一种以组合功能为中心的图书馆组织风格。
简单地说，有一些类型 T，一些用于构造类型 T 的“原始”值的函数，以及一些可以以各种方式组合类型 T 的值以构建更复杂的类型 T 值的“组合器”。

## 解释

真实世界例子

> 在计算机科学中，组合逻辑被用作计算的简化模型，用于可计算性理论和证明理论。 尽管组合逻辑很简单，但它捕获了计算的许多基本特征。
> 

通俗的说
> 组合器允许从先前定义的“事物”创建新的“事物”。
> 

维基百科说

> 组合器是一个高阶函数，仅使用函数应用程序和之前定义的组合器来定义其参数的结果。
> 

**程序示例**

翻译上面的组合器示例。 首先，我们有一个由几个方法`contains`, `not`, `or`, `and`组成的接口。

```java
// 用于查找文本中的行的功能界面。
public interface Finder {

	// 在文本中查找行的函数。
	List<String> find(String text);

	// 函数{@link #find(String)}的简单实现。
	static Finder contains(String word) {
    		return txt -> Stream.of(txt.split("\n"))
        		.filter(line -> line.toLowerCase().contains(word.toLowerCase()))
        		.collect(Collectors.toList());
  	}

	// 组合器：not。
	default Finder not(Finder notFinder) {
    		return txt -> {
      			List<String> res = this.find(txt);
      			res.removeAll(notFinder.find(txt));
      			return res;
    			};
  	}

	// 组合器：or。
	default Finder or(Finder orFinder) {
    		return txt -> {
      			List<String> res = this.find(txt);
      			res.addAll(orFinder.find(txt));
      			return res;
    			};
	}

	// 组合器：and。
	default Finder and(Finder andFinder) {
    		return
        	txt -> this
            		.find(txt)
            		.stream()
            		.flatMap(line -> andFinder.find(line).stream())
            		.collect(Collectors.toList());
  	}
	...
}
```

然后我们还有另一个组合器用于一些复杂的查找器`advancedFinder`, `filteredFinder`, `specializedFinder`和`expandedFinder`。

```java
// 由简单取景器组成的复杂取景器。
public class Finders {

	private Finders() {
  	}

	// Finder 用于查找复杂的查询。
	public static Finder advancedFinder(String query, String orQuery, String notQuery) {
    		return
        		Finder.contains(query)
            			.or(Finder.contains(orQuery))
            			.not(Finder.contains(notQuery));
	}

	// 过滤查找器也会查找包含排除查询的查询。
	public static Finder filteredFinder(String query, String... excludeQueries) {
		var finder = Finder.contains(query);

    		for (String q : excludeQueries) {
      			finder = finder.not(Finder.contains(q));
    		}
    		return finder;
	}

	// 专门查询。 每个下一个查询都会在上一个结果中查找。
	public static Finder specializedFinder(String... queries) {
    		var finder = identMult();

		for (String query : queries) {
      			finder = finder.and(Finder.contains(query));
    		}
    		return finder;
  	}

	// 扩展查询。 寻找替代品。
	public static Finder expandedFinder(String... queries) {
    		var finder = identSum();

    		for (String query : queries) {
      			finder = finder.or(Finder.contains(query));
    		}
   		return finder;
  	}
	...
}
```

现在我们已经创建了组合器的接口和方法。 现在我们有一个处理这些组合器的应用程序。

```java
var queriesOr = new String[]{"many", "Annabel"};
var finder = Finders.expandedFinder(queriesOr);
var res = finder.find(text());
LOGGER.info("the result of expanded(or) query[{}] is {}", queriesOr, res);

var queriesAnd = new String[]{"Annabel", "my"};
finder = Finders.specializedFinder(queriesAnd);
res = finder.find(text());
LOGGER.info("the result of specialized(and) query[{}] is {}", queriesAnd, res);

finder = Finders.advancedFinder("it was", "kingdom", "sea");
res = finder.find(text());
LOGGER.info("the result of advanced query is {}", res);

res = Finders.filteredFinder(" was ", "many", "child").find(text());
LOGGER.info("the result of filtered query is {}", res);

private static String text() {
    return
        "It was many and many a year ago,\n"
            + "In a kingdom by the sea,\n"
            + "That a maiden there lived whom you may know\n"
            + "By the name of ANNABEL LEE;\n"
            + "And this maiden she lived with no other thought\n"
            + "Than to love and be loved by me.\n"
            + "I was a child and she was a child,\n"
            + "In this kingdom by the sea;\n"
            + "But we loved with a love that was more than love-\n"
            + "I and my Annabel Lee;\n"
            + "With a love that the winged seraphs of heaven\n"
            + "Coveted her and me.";
  }
```

**程序输出:**

```java
the result of expanded(or) query[[many, Annabel]] is [It was many and many a year ago,, By the name of ANNABEL LEE;, I and my Annabel Lee;]
the result of specialized(and) query[[Annabel, my]] is [I and my Annabel Lee;]
the result of advanced query is [It was many and many a year ago,]
the result of filtered query is [But we loved with a love that was more than love-]
```

现在我们可以设计我们的应用程序，使其具有查询查找功能`expandedFinder`, `specializedFinder`, `advancedFinder`, `filteredFinder`，这些功能均派生自`contains`, `or`, `not`, `and`。


## 类图
![alt text](./etc/combinator.urm.png "Combinator class diagram")

## 适用性
在以下情况下使用组合器模式：

- 您可以从更简单的值创建更复杂的值，但具有相同的类型（它们的组合）

## 好处

- 从开发人员的角度来看，API 由领域中的术语组成。
- 组合阶段和应用阶段之间有明显的区别。
- 首先构造一个实例，然后执行它。
- 这使得该模式适用于并行环境。


## 现实世界的例子

- java.util.function.Function#compose
- java.util.function.Function#andThen

## 鸣谢

- [Example for java](https://gtrefs.github.io/code/combinator-pattern/)
- [Combinator pattern](https://wiki.haskell.org/Combinator_pattern)
- [Combinatory logic](https://wiki.haskell.org/Combinatory_logic)
