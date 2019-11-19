package com.iluwatar.combinator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Finder {

  List<String> find(String text);


  static Finder contains(String word) {
    return txt -> Stream.of(txt.split("\n")).filter(line -> line.contains(word)).collect(Collectors.toList());
  }

  static Finder exact(String line) {
    return txt -> Stream.of(txt.split("\n")).filter(in -> in.equals(line)).collect(Collectors.toList());
  }

  default Finder not(Finder notFinder) {
    return txt -> {
      List<String> res = this.find(txt);
      res.removeAll(notFinder.find(txt));
      return res;
    };
  }

  default Finder or(Finder orFinder) {
    return txt -> {
      List<String> res = this.find(txt);
      res.addAll(orFinder.find(txt));
      return res;
    };
  }

  default Finder and(Finder andFinder) {
    return
        txt ->
            this
                .find(txt)
                .stream()
                .flatMap(line -> andFinder.find(line).stream())
                .collect(Collectors.toList());
  }

}
