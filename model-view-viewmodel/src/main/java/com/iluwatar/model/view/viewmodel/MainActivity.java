/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iluwatar.model.view.viewmodel;

import android.R;
import android.app.Activity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, 
        R.layout.activity_main);

    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    activityMainBinding.recView.setLayoutManager(layoutManager);

    List<DesignPatternBook> designPatternBooks = new ArrayList<>();
    designPatternBooks.add(new DesignPatternBook(
        "Head First Design Patterns: A Brain-Friendly Guide",
        "Eric Freeman, Bert Bates, Kathy Sierra, Elisabeth Robson"));
    designPatternBooks.add(new DesignPatternBook(
        "Design Patterns: Elements of Reusable Object-Oriented Software",
        "Erich Gamma, Richard Helm, Ralph Johnson, and John Vlissides"));
    designPatternBooks.add(new DesignPatternBook(
        "Patterns of Enterprise Application Architecture", "Martin Fowler"));
    designPatternBooks.add(new DesignPatternBook(
        "Design Patterns Explained", "Alan Shalloway, James Trott"));
    designPatternBooks.add(new DesignPatternBook(
        "Applying UML and Patterns: An Introduction to "
        + "Object-Oriented Analysis and Design and Iterative Development",
        "Craig Larman"));

    BookAdapter bookAdapter = new BookAdapter(designPatternBooks, this);
    activityMainBinding.recView.setAdapter(bookAdapter);

  }

}
