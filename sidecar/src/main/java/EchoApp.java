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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class EchoApp extends SideCarApp {
    private static final Logger LOGGER = LoggerFactory.getLogger(EchoApp.class);
    private long delay;
    private long period;
    private ZoneId zoneId;
    @Override
    public Object call() throws Exception {

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                LOGGER.info("Local Time is:" + Instant.now().atZone(zoneId));
            }
        }, delay, period);

        return null;
    }

    @Override
    public void setProps(Map<String, String> props) {
        this.delay = Long.getLong(props.get("delay"));
        this.period = Long.getLong(props.get("period"));
        this.zoneId = ZoneId.of(props.get("zoneId"));
    }
}
