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

package com.iluwatar.databus.members;

import com.iluwatar.databus.DataType;
import com.iluwatar.databus.Member;
import com.iluwatar.databus.data.MessageData;
import com.iluwatar.databus.data.StartingData;
import com.iluwatar.databus.data.StoppingData;
import java.time.LocalDateTime;
import java.util.logging.Logger;

/**
 * Receiver of Data-Bus events.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class StatusMember implements Member {

  private static final Logger LOGGER = Logger.getLogger(StatusMember.class.getName());

  private final int id;

  private LocalDateTime started;

  private LocalDateTime stopped;

  public StatusMember(int id) {
    this.id = id;
  }

  @Override
  public void accept(final DataType data) {
    if (data instanceof StartingData) {
      handleEvent((StartingData) data);
    } else if (data instanceof StoppingData) {
      handleEvent((StoppingData) data);
    }
  }

  private void handleEvent(StartingData data) {
    started = data.getWhen();
    LOGGER.info(String.format("Receiver #%d sees application started at %s", id, started));
  }

  private void handleEvent(StoppingData data) {
    stopped = data.getWhen();
    LOGGER.info(String.format("Receiver #%d sees application stopping at %s", id, stopped));
    LOGGER.info(String.format("Receiver #%d sending goodbye message", id));
    data.getDataBus().publish(MessageData.of(String.format("Goodbye cruel world from #%d!", id)));
  }

  public LocalDateTime getStarted() {
    return started;
  }

  public LocalDateTime getStopped() {
    return stopped;
  }
}
