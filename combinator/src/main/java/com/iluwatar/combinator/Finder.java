package com.iluwatar.combinator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Functional interface to find lines in text.
 */
public interface Finder {

  /**
   * The function to find lines in text.
   * @param text full tet
   * @return result of searching
   */
  List<String> find(String text);

  /**
   * Simple implementation of function {@link #find(String)}.
   * @param word for searching
   * @return this
   */
  static Finder contains(String word) {
    return txt -> Stream.of(txt.split("\n"))
        .filter(line -> line.toLowerCase().contains(word.toLowerCase()))
        .collect(Collectors.toList());
  }

  /**
   * combinator not.
   * @param notFinder finder to combine
   * @return new finder including previous finders
   */
  default Finder not(Finder notFinder) {
    return txt -> {
      List<String> res = this.find(txt);
      res.removeAll(notFinder.find(txt));
      return res;
    };
  }

  /**
   * combinator or.
   * @param orFinder finder to combine
   * @return new finder including previous finders
   */
  default Finder or(Finder orFinder) {
    return txt -> {
      List<String> res = this.find(txt);
      res.addAll(orFinder.find(txt));
      return res;
    };
  }

  /**
   * combinator or.
   * @param andFinder finder to combine
   * @return new finder including previous finders
   */
  default Finder and(Finder andFinder) {
    return
        txt -> this
            .find(txt)
            .stream()
            .flatMap(line -> andFinder.find(line).stream())
            .collect(Collectors.toList());
  }

}
