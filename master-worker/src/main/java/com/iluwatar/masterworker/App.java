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

package com.iluwatar.masterworker;

import java.util.Random;
import com.iluwatar.masterworker.system.ArrayTransposeMasterWorker;

/**
 * <p>The <b><em>Master-Worker</em></b> pattern is used when the problem at hand can be solved by dividing into 
 * multiple parts which need to go through the same computation and may need to be aggregated to get final result.
 * Parallel processing is performed using a system consisting of a master and some number of workers, where a 
 * master divides the work among the workers, gets the result back from them and assimilates all the results to
 * give final result. The only communication is between the master and the worker - none of the workers communicate
 * among one another and the user only communicates with the master to get required job done.</p>
 * <p>In our example, we have generic abstract classes {@link MasterWorker}, {@link Master} and {@link Worker} which
 * have to be extended by the classes which will perform the specific job at hand (in this case finding transpose of
 * matrix, done by {@link ArrayTransposeMasterWorker}, {@link ArrayTransposeMaster} and {@link ArrayTransposeWorker}).
 * The Master class has private fields numOfWorkers (int, number of workers), workers (ArrayList, containing the 
 * references to workers), expectedNumResults (int, number of divisions of input data, same as number of expected 
 * results) allResultData (Hashtable, containing all the results obtained so far from the workers) and finalResult
 * (type Result, aggregated result from allResultData when results from all workers has been received). Master class 
 * also contains private methods divideWork and collectResult, which divide work into segments for workers and collect 
 * result from worker and store in allResultData respectively, accessible to MasterWorker class through public methods 
 * doWork and to Worker class through receiveData respectively. It also has getters for numOfWorkers, workers,
 * expectedNumResults, allReceivedData and finalResult, and abstract methods aggregateData (defining how all the
 * received data is to be aggregated to get final result) and setter method for workers field. The Worker class extends
 * the Thread class to enable parallel processing, and has private fields master (type Master, reference to master from
 * which it takes work), workerId (int, its unique id) and receivedData(type Input, the data received from Master, to
 * be operated on). It defines private method sendToMaster (sending result to master) and the Thread.run() method which
 * calls the abstract method executeOperation(), which defines what operation is to be performed on data. It also
 * defines getter methods for workerId and receivedData fields and a setter method for receivedData. The MasterWorker
 * has a private master field and defines a public getResult method and an abstract setter method for the master field.
 * These 3 classes define the system which computes the result. We also have 2 abstract classes {@link Input} and {@link
 * Result}, both of which have a generic field data. These classes contain the input data and result data respectively.
 * The Input class also has an abstract method divideData which defines how the data is to be divided into segments.
 * These classes are extended by {@link ArrayInput} and {@ArrayResult}.</p>
 */

public class App {

  static void printMatrix(int[][] matrix) {
    //prints out int[][]
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        System.out.print(matrix[i][j] + " ");
      }
      System.out.println("");
    }
  }
  
  /**
   * Program entry point.
   * @param args command line args
   */

  public static void main(String[] args) {
    ArrayTransposeMasterWorker mw = new ArrayTransposeMasterWorker();
    int rows = 10;
    int columns = 20;
    int[][] inputMatrix = new int[rows][columns];
    Random rand = new Random();
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        //filling cells in matrix
        inputMatrix[i][j] = rand.nextInt(10);
      }
    }    
    ArrayInput input = new ArrayInput(inputMatrix);
    ArrayResult result = (ArrayResult) mw.getResult(input);    
    if (result != null) {
      App.printMatrix(inputMatrix);
      System.out.println("");
      App.printMatrix(result.data);
    } else {
      System.out.println("Please enter non-zero input");
    }
  }
}
