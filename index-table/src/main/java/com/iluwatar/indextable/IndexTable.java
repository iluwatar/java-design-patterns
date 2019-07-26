/**
 * The MIT License Copyright (c) 2014-2016 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.iluwatar.indextable;

import java.util.*;

import com.iluwatar.indextable.Data;
import com.iluwatar.indextable.FactTable;

public class IndexTable {
    private final Map<String, List<Integer>> indexTable = new HashMap<String, List<Integer>>();

    public IndexTable() {

    }

    public IndexTable(FactTable factTable) {
        HashSet<Integer> keySet = new HashSet<Integer>(factTable.keySet());
        for(int key: keySet) {
            String data = factTable.get(key).getTown();
            add(data, key);
            //indexTable.put(data.getTown(), key);
        }
    }


    public java.util.Set<String> keySet() {
        return indexTable.keySet();
    }

    public List<Integer> get(String key) {
        return indexTable.get(key);
    }


    public void add(String key, Integer value) {
        if(!indexTable.containsKey(key)) {
            indexTable.put(key, new ArrayList<Integer>());
        }
        indexTable.get(key).add(value);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(String string: indexTable.keySet()) {
            stringBuilder.append(string + ":\t" + indexTable.get(string) + "\n");
        }
        return stringBuilder.toString();
    }
}