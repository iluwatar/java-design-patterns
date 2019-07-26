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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//import com.iluwatar.indextable.Data;

public class FactTable {
    private Map<Integer, Data> factTable = new HashMap<Integer, Data>();

    public Set<Integer> keySet() {
        return factTable.keySet();
    }

    public Data get(Integer key) {
        return factTable.get(key);
    }

    public boolean add(int key, Data data) {
        if(factTable.containsKey(key)||factTable.containsValue(data)){
            return false;
        }
        factTable.put(key, data);
        return true;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i: factTable.keySet()) {
            stringBuilder.append(i + ":\t" + factTable.get(i) + "\n");
        }
        return stringBuilder.toString();
    }
    
}