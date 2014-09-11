/**
 * ThingSpeak Appender for log4j Copyright 2014, Andrew Bythell
 * <abythell@ieee.org>
 * http://angryelectron.com
 *
 * The ThingSpeak Java Client is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * The ThingSpeak Java Client is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * the ThingSpeak Appender. If not, see <http://www.gnu.org/licenses/>.
 */
package com.angryelectron.thingspeak.log4j;

import com.angryelectron.thingspeak.Channel;
import com.angryelectron.thingspeak.Entry;
import com.angryelectron.thingspeak.ThingSpeakException;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

/**
 * <p>Appender for log4j that logs messages to ThingSpeak. To prepare a channel 
 * for logging, create a new ThingSpeak channel with three fields (names not 
 * important):</p>
 * <ol>
 *   <li>Date</li>
 *   <li>Level</li>
 *   <li>Message</li>
 * </ol>
 * 
 * <p>Then create and configure a new appender.  Use 
 * {@link #configureChannel(java.lang.Integer, java.lang.String) configureChannel}
 * to configure the appender, or set via log4j.properties:</p>
 * <ul>
 *   <li>log4j.appender.ThingSpeak=com.angryelectron.thingspeak.log4j.ThingSpeakAppender</li>
 *   <li>com.angryelectron.thingspeak.log4j.channelNumber = [channel number]</li>
 *   <li>com.angryelectron.thingspeak.log4j.apiWriteKey = [channel api write key]</li>
 * </ul>
 * 
 * <p>Then set your root logger to use the new appender:</p>
 * <ul>
 *   <li>log4j.rootLogger=INFO, ThingSpeak</li>
 * </ul>
 */
public class ThingSpeakAppender extends AppenderSkeleton {

    private final Properties properties = new Properties();
    private final String channelPropertyKey = "com.angryelectron.thingspeak.log4j.channelNumber";
    private final String apiPropertyKey = "com.angryelectron.thingspeak.log4j.apiWriteKey";
    private Integer channelNumber;
    private String apiWriteKey;

    /**
     * Constructor.
     */
    public ThingSpeakAppender() {
        try {
            properties.load(getClass().getResourceAsStream("/log4j.properties"));
            apiWriteKey = properties.getProperty(apiPropertyKey);
            channelNumber = Integer.parseInt(properties.getProperty(channelPropertyKey));
        } catch (IOException | NumberFormatException | NullPointerException ex) {
            /* ignore - will be caught and logged in append() */
        }
    }
    
    /**
     * Configure the channel.  Use to configure the appender in code 
     * (vs. log4j.properties).
     * @param channelNumber ThingSpeak channel number.
     * @param apiWriteKey ThinSpeak API write key for the channel.
     */
    public void configureChannel(Integer channelNumber, String apiWriteKey) {
        this.apiWriteKey = apiWriteKey;
        this.channelNumber = channelNumber;
    }

    /**
     * Internal.  Append log messages as an entry in a ThingSpeak channel.
     * @param event log4j event.
     */
    @Override
    protected void append(LoggingEvent event) {
        Date timeStamp = new Date(event.timeStamp);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Entry entry = new Entry();
        entry.setField(1, dateFormat.format(timeStamp));
        entry.setField(2, event.getLevel().toString());
        entry.setField(3, event.getMessage().toString());
        try {
            if ((channelNumber == null) || (apiWriteKey == null)) {
                throw new ThingSpeakException("Missing channel or API key.  Check log4j.properties.");
            }
            Channel channel = new Channel(channelNumber, apiWriteKey);
            channel.update(entry);
        } catch (UnirestException | ThingSpeakException ex) {
            Logger.getLogger(ThingSpeakAppender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Internal.
     */
    @Override
    public void close() {
        /* nothing to do */
    }

    /**
     * Internal.  Thingspeak maps log data directly to channel "fields", so no layout is
     * required.
     * @return false
     */
    @Override
    public boolean requiresLayout() {
        return false;
    }

}
