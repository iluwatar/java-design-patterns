---
title: Combinator
category: Idiom
language: hi
tag:
 - Reactive
---

## दूसरा नाम

संयोजन पैटर्न

## हेतु

कार्यों के संयोजन के विचार पर केंद्रित पुस्तकालयों को व्यवस्थित करने की शैली का प्रतिनिधित्व करने वाला कार्यात्मक पैटर्न।
इसे सीधे शब्दों में कहें तो, कुछ प्रकार टी हैं, प्रकार टी के "आदिम" मूल्यों के निर्माण के लिए कुछ कार्य हैं, और कुछ "कॉम्बिनेटर" हैं जो प्रकार टी के अधिक जटिल मूल्यों को बनाने के लिए विभिन्न तरीकों से प्रकार टी के मूल्यों को जोड़ सकते हैं।

## व्याख्या

वास्तविक दुनिया का उदाहरण

> कंप्यूटर विज्ञान में, कॉम्बिनेटरी लॉजिक का उपयोग गणना के सरलीकृत मॉडल के रूप में किया जाता है, जिसका उपयोग कंप्यूटेबिलिटी सिद्धांत और प्रूफ सिद्धांत में किया जाता है। अपनी सरलता के बावजूद, संयोजनात्मक तर्क गणना की कई आवश्यक विशेषताओं को पकड़ लेता है।
>

साफ़ शब्दों में

> कॉम्बिनेटर आपको पहले से परिभाषित "things" से नई "things" बनाने की अनुमति देता है।
>

विकिपीडिया कहता है

> कॉम्बिनेटर एक उच्च-क्रम वाला फ़ंक्शन है जो अपने तर्कों से परिणाम को परिभाषित करने के लिए केवल फ़ंक्शन एप्लिकेशन और पहले से परिभाषित कॉम्बिनेटर का उपयोग करता है।
>

**प्रोग्रामेटिक उदाहरण**

उपरोक्त कॉम्बिनेटर उदाहरण का अनुवाद करना। सबसे पहले, हमारे पास एक इंटरफ़ेस है जिसमें कई विधियाँ `contains`, `not`, `or`, `and` शामिल हैं।

```java
// Functional interface to find lines in text.
public interface Finder {

	// The function to find lines in text.
	List<String> find(String text);

	// Simple implementation of function {@link #find(String)}.
	static Finder contains(String word) {
    		return txt -> Stream.of(txt.split("\n"))
        		.filter(line -> line.toLowerCase().contains(word.toLowerCase()))
        		.collect(Collectors.toList());
  	}

	// combinator not.
	default Finder not(Finder notFinder) {
    		return txt -> {
      			List<String> res = this.find(txt);
      			res.removeAll(notFinder.find(txt));
      			return res;
    			};
  	}

	// combinator or.
	default Finder or(Finder orFinder) {
    		return txt -> {
      			List<String> res = this.find(txt);
      			res.addAll(orFinder.find(txt));
      			return res;
    			};
	}

	// combinator and.
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

फिर हमारे पास कुछ जटिल खोजकों `advancedFinder`, `filteredFinder`, `specializedFinder` and `expandedFinder` के लिए एक और कॉम्बिनेटर भी है।

```java
// Complex finders consisting of simple finder.
public class Finders {

	private Finders() {
  	}

	// Finder to find a complex query.
	public static Finder advancedFinder(String query, String orQuery, String notQuery) {
    		return
        		Finder.contains(query)
            			.or(Finder.contains(orQuery))
            			.not(Finder.contains(notQuery));
	}

	// Filtered finder looking a query with excluded queries as well.
	public static Finder filteredFinder(String query, String... excludeQueries) {
		var finder = Finder.contains(query);

    		for (String q : excludeQueries) {
      			finder = finder.not(Finder.contains(q));
    		}
    		return finder;
	}

	// Specialized query. Every next query is looked in previous result.
	public static Finder specializedFinder(String... queries) {
    		var finder = identMult();

		for (String query : queries) {
      			finder = finder.and(Finder.contains(query));
    		}
    		return finder;
  	}

	// Expanded query. Looking for alternatives.
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

अब हमने कॉम्बिनेटरों के लिए इंटरफ़ेस और विधियाँ बना ली हैं। अब हमारे पास इन कॉम्बिनेटरों पर काम करने वाला एक एप्लिकेशन है।

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

**प्रोग्राम आउटपुट:**

```java
the result of expanded(or) query[[many, Annabel]] is [It was many and many a year ago,, By the name of ANNABEL LEE;, I and my Annabel Lee;]
the result of specialized(and) query[[Annabel, my]] is [I and my Annabel Lee;]
the result of advanced query is [It was many and many a year ago,]
the result of filtered query is [But we loved with a love that was more than love-]
```

अब हम अपने ऐप को `expandedFinder`, `specializedFinder`, `advancedFinder`, `filteredFinder` खोजने वाले प्रश्नों के साथ डिज़ाइन कर सकते हैं, जो सभी `contains`, `or`, `not`, `and` से प्राप्त होते हैं।


## क्लास डायग्राम
![alt text](../../../combinator/etc/combinator.urm.png "Combinator class diagram")

## प्रयोज्यता
कॉम्बिनेटर पैटर्न का उपयोग तब करें जब:

- आप अधिक सादे मानों से अधिक जटिल मान बनाने में सक्षम हैं, लेकिन उनका प्रकार समान है (उनका संयोजन)

## फ़ायदे

- डेवलपर्स के नजरिए से एपीआई डोमेन के शब्दों से बना है।
- संयोजन और अनुप्रयोग चरण के बीच स्पष्ट अंतर है।
- पहले एक इंस्टेंस बनाता है और फिर उसे निष्पादित करता है।
- यह पैटर्न को समानांतर वातावरण में लागू बनाता है।


## वास्तविक दुनिया के उदाहरण

- java.util.function.Function#compose
- java.util.function.Function#andThen

## श्रेय

- [Example for java](https://gtrefs.github.io/code/combinator-pattern/)
- [Combinator pattern](https://wiki.haskell.org/Combinator_pattern)
- [Combinatory logic](https://wiki.haskell.org/Combinatory_logic)
