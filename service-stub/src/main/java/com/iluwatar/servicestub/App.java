/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
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

package com.iluwatar.servicestub;

/**
 * Service stub pattern is a design pattern used for replacing third-party services, 
 * such as credit scoring, tax rate lookups and pricing engines, 
 * which are often not available locally for testing.
 * 
 * In this example we use Service stub pattern for protecting the data, such as service name,
 * of the productive system from being changed by the test.
 * 
 * @author jdoetricksy
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ServiceInterface service1 = new ProductiveServiceStub("service1");
        int count = service1.getProductiveServicesCount();
        service1.changeServiceName(count, "service1new");
        
        ServiceInterface service2 = new ProductiveServiceStub("service2");
        int count2 = service2.getProductiveServicesCount();
        service2.changeServiceName(count2, "service2new");
    }
}
