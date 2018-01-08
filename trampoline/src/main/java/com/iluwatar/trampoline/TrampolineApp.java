/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
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

package com.iluwatar.trampoline;


import lombok.extern.slf4j.Slf4j;

/**
 * <div>
 * By representing a computation in one of 2 states
 (completed with result, or a reference to the reminder of the computation,
 something like the way a java.util.Supplier does)
 it is possible to implement algorithms recursively in Java without blowing the stack
 and to interleave the execution of functions without hard coding them together or even using threads.
 </div>
 <div>
 Trampoline has 2 state : [done], [ more]
 </div>
 When get is called on the returned Trampoline, internally it will iterate calling ‘jump’
 on the returned Trampoline as long as the concrete instance returned is More,
 stopping once the returned instance is Done. Essential we convert looping via recursion into iteration,
 the key enabling mechanism is the fact that Trampoline.more is a lazy operation.
 Trampoline in cyclops-react extends java.util.Supplier. Calling Trampoline.more we are basically creating
 a Supplier that defers the actual recursive call, and having defered the call we can move it outside of the recursive loop.
 This means we can define algorithms recursively in Java but execute them iteratively.
 */

@Slf4j
public class TrampolineApp {
    public static void main(String[] args) {
        log.info("start pattern");
        Integer result = loop(10, 1).result();
        log.info("result {}" ,result);

    }
    /**
     * Manager for pattern.
     * */
    public static Trampoline<Integer> loop(int times,int prod){
        if(times==0)
            return Trampoline.done(prod);
        else
            return Trampoline.more(()->loop(times-1,prod*times));
    }

}
