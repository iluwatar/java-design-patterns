/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package com.iluwatar.leaderfollowers;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for TaskSet
 */
public class TaskSetTest {

    @Test
    public void testAddTask() throws InterruptedException {
        var taskSet = new TaskSet();
        taskSet.addTask(new Task(10));
        Assert.assertTrue(taskSet.getSize() == 1);
    }

    @Test
    public void testGetTask() throws InterruptedException {
        var taskSet = new TaskSet();
        taskSet.addTask(new Task(100));
        Task task = taskSet.getTask();
        Assert.assertTrue(task.getTime() == 100);
        Assert.assertTrue(taskSet.getSize() == 0);
    }

}
