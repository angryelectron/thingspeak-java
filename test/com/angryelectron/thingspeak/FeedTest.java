/**
 * ThingSpeak Java Client 
 * Copyright 2014, Andrew Bythell <abythell@ieee.org>
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
 * theThingSpeak Java Client. If not, see <http://www.gnu.org/licenses/>.
 */

package com.angryelectron.thingspeak;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.BeforeClass;
import org.junit.Test;

public class FeedTest {
    
    /**
     * Update the channel with some sample data that will be read-back by all of
     * the Feed Tests.
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpClass() throws Exception {
        Entry entry = new Entry();
        entry.setElevation(0.0);
        entry.setField(1, "nonsense-data1");
        entry.setField(2, "nonsense-data2");
        entry.setField(3, "nonsense-data3");
        entry.setField(4, "nonsense-data4");
        entry.setField(5, "nonsense-data5");
        entry.setField(6, "nonsense-data6");
        entry.setField(7, "nonsense-data7");
        entry.setField(8, "nonsense-data8");
        entry.setLatitude(0.0);
        entry.setLong(0.0);
        entry.setStatus("unimportant status");
        entry.setTweet("irrelevant tweet");
        entry.setTwitter("invalid twitter user");        
        
        Channel publicChannel = new Channel(TestChannelSettings.publicChannelID, TestChannelSettings.publicChannelWriteKey);        
        Channel privateChannel = new Channel(TestChannelSettings.privateChannelID, TestChannelSettings.privateChannelWriteKey, TestChannelSettings.privateChannelReadKey);

        publicChannel.setUrl(TestChannelSettings.server);
        privateChannel.setUrl(TestChannelSettings.server);
        
        publicChannel.update(entry);
        privateChannel.update(entry);
    }
    
    /**
     * When testing is complete, pause before running other tests to prevent
     * hitting the API rate limit.
     * @throws Exception 
     */
    @AfterClass
    public static void tearDownClass() throws Exception {
        Thread.sleep(TestChannelSettings.rateLimit);
    }
    
    @Test
    public void testGetChannelFeedPublic() throws Exception {
        Channel publicChannel = new Channel(TestChannelSettings.publicChannelID, TestChannelSettings.publicChannelWriteKey);
        publicChannel.setUrl(TestChannelSettings.server);
        Feed publicFeed = publicChannel.getChannelFeed();
        assertEquals(TestChannelSettings.publicChannelID, publicFeed.getChannelId());
        assertNotNull(publicFeed.getChannelCreationDate());
        assertNotNull(publicFeed.getChannelDescription());
        assertNotNull(publicFeed.getChannelName());
        assertNotNull(publicFeed.getChannelUpdateDate());
        assertNotNull(publicFeed.getChannelLastEntryId());                
        assertNotNull(publicFeed.getEntryList());
        assertNotNull(publicFeed.getEntryMap());
        assertNotNull(publicFeed.getFieldName(1));                                        
    }
    
    @Test
    public void testGetChannelFeedPrivate() throws Exception {
        Channel publicChannel = new Channel(TestChannelSettings.privateChannelID, TestChannelSettings.privateChannelWriteKey, TestChannelSettings.privateChannelReadKey);
        publicChannel.setUrl(TestChannelSettings.server);
        Feed publicFeed = publicChannel.getChannelFeed();
        assertEquals(TestChannelSettings.privateChannelID, publicFeed.getChannelId());
        assertNotNull(publicFeed.getChannelCreationDate());
        assertNotNull(publicFeed.getChannelDescription());
        assertNotNull(publicFeed.getChannelName());
        assertNotNull(publicFeed.getChannelUpdateDate());
        assertNotNull(publicFeed.getChannelLastEntryId());        
        assertNotNull(publicFeed.getEntry(publicFeed.getChannelLastEntryId()));
        assertNotNull(publicFeed.getEntryList());
        assertNotNull(publicFeed.getEntryMap());
        assertNotNull(publicFeed.getFieldName(1));                                        
        
        Entry entry = publicChannel.getLastChannelEntry();
        
        /**
         * any one of these may fail if not defined in the Channel Settings
         * via the web.
         */
        assertNotNull(entry.getField(1));
        assertNotNull(entry.getField(2));
        assertNotNull(entry.getField(3));
        assertNotNull(entry.getField(4));
        assertNotNull(entry.getField(5));
        assertNotNull(entry.getField(6));
        assertNotNull(entry.getField(7));
        assertNotNull(entry.getField(8));
    }
    
    @Test
    public void testGetChannelFeedWithOptions() throws Exception {
        Channel publicChannel = new Channel(TestChannelSettings.publicChannelID, TestChannelSettings.publicChannelWriteKey);
        publicChannel.setUrl(TestChannelSettings.server);
        FeedParameters options = new FeedParameters();
        options.location(true);
        options.status(true);
        Feed publicFeed = publicChannel.getChannelFeed(options);
        Entry entry = publicFeed.getChannelLastEntry();
        assertNotNull(entry.getStatus());
        assertNotNull(entry.getElevation());
        assertNotNull(entry.getLatitude());
        assertNotNull(entry.getLongitude());                
    }
    
    @Test
    public void testGetLastEntry() throws Exception {
        Channel publicChannel = new Channel(TestChannelSettings.publicChannelID, TestChannelSettings.publicChannelWriteKey);        
        publicChannel.setUrl(TestChannelSettings.server);
        Entry entry = publicChannel.getLastChannelEntry();
        
        /**
         * any one of these may fail if not defined in the Channel Settings
         * via the web.
         */
        assertNotNull(entry.getField(1));
        assertNotNull(entry.getField(2));
        assertNotNull(entry.getField(3));
        assertNotNull(entry.getField(4));
        assertNotNull(entry.getField(5));
        assertNotNull(entry.getField(6));
        assertNotNull(entry.getField(7));
        assertNotNull(entry.getField(8));
    }
    
    @Test
    public void testGetLastEntryWithOptions() throws Exception {
        Channel publicChannel = new Channel(TestChannelSettings.publicChannelID, TestChannelSettings.publicChannelWriteKey);
        publicChannel.setUrl(TestChannelSettings.server);
        FeedParameters options = new FeedParameters();
        options.location(true);
        options.status(true);        
        Entry entry = publicChannel.getLastChannelEntry(options);
        assertNotNull(entry.getStatus());
        assertNotNull(entry.getElevation());
        assertNotNull(entry.getLatitude());
        assertNotNull(entry.getLongitude());                
    }
    
    @Test
    public void testGetLastEntryWithTimezoneOffset() throws Exception {
        Channel channel = new Channel(TestChannelSettings.publicChannelID);
        channel.setUrl(TestChannelSettings.server);
        FeedParameters options = new FeedParameters();
        options.offset(-8);                
        Date pst = channel.getLastChannelEntry(options).getCreated();        
        SimpleDateFormat df = new SimpleDateFormat("Z");
        assert(df.format(pst).equals("-0800"));        
    }
    
     @Test
    public void testGetFieldFeed() throws Exception {
        Channel publicChannel = new Channel(TestChannelSettings.publicChannelID);
        publicChannel.setUrl(TestChannelSettings.server);
        Feed feed = publicChannel.getFieldFeed(1);
        assertEquals(TestChannelSettings.publicChannelID, feed.getChannelId());
        assertNotNull(feed.getChannelCreationDate());
        assertNotNull(feed.getChannelDescription());
        assertNotNull(feed.getChannelName());
        assertNotNull(feed.getChannelUpdateDate());
        assertNotNull(feed.getChannelLastEntryId());        
        assertNotNull(feed.getEntry(feed.getChannelLastEntryId()));
        assertNotNull(feed.getEntryList());
        assertNotNull(feed.getEntryMap());
        assertNotNull(feed.getFieldName(1)); 
        
        Entry entry = feed.getChannelLastEntry();
        assertNotNull(entry.getField(1));
        assertNull(entry.getField(2));
        
    }
    
    @Test
    public void testGetFieldFeedWithOptions() throws Exception {
        Channel publicChannel = new Channel(TestChannelSettings.publicChannelID);
        publicChannel.setUrl(TestChannelSettings.server);
        FeedParameters options = new FeedParameters();
        options.status(true);
        options.location(true);
        Feed feed = publicChannel.getFieldFeed(1, options);
        Entry entry = feed.getChannelLastEntry();
        assertNotNull(entry.getField(1));
        assertNull(entry.getField(2));
        assertNotNull(entry.getStatus());
        assertNotNull(entry.getElevation());
        assertNotNull(entry.getLatitude());
        assertNotNull(entry.getLongitude());
    }
    
    
    @Test
    public void testGetFieldFeedLastEntry() throws Exception {
        Channel publicChannel = new Channel(TestChannelSettings.publicChannelID);        
        publicChannel.setUrl(TestChannelSettings.server);
        Entry entry = publicChannel.getLastFieldEntry(1);
        assertNotNull(entry.getField(1));
        assertNull(entry.getField(2));        
    }
    
    @Test
    public void testGetFieldFeedLastEntryWithOptions() throws Exception {
        Channel publicChannel = new Channel(TestChannelSettings.publicChannelID);        
        publicChannel.setUrl(TestChannelSettings.server);
        FeedParameters options = new FeedParameters();
        options.status(true);
        options.location(true);
        Entry entry = publicChannel.getLastFieldEntry(1, options);
        assertNotNull(entry.getField(1));
        assertNull(entry.getField(2));   
        assertNotNull(entry.getStatus());
        assertNotNull(entry.getElevation());
        assertNotNull(entry.getLatitude());
        assertNotNull(entry.getLongitude());
    }
    
    @Test
    public void testGetStatus() throws Exception {
        Channel channel = new Channel(TestChannelSettings.publicChannelID);
        channel.setUrl(TestChannelSettings.server);
        Feed feed = channel.getStatusFeed();
        ArrayList<Entry> entry = feed.getEntryList();
        Entry last = entry.get(entry.size() -1);
        assertNotNull(last.getStatus());
    }
    
    @Test
    public void testGetStatusWithOptions() throws Exception {
        Channel channel = new Channel(TestChannelSettings.publicChannelID);
        channel.setUrl(TestChannelSettings.server);
        FeedParameters options = new FeedParameters();
        options.offset(-8);
        Feed feed = channel.getStatusFeed(options);
        ArrayList<Entry> entry = feed.getEntryList();
        Entry last = entry.get(entry.size() -1);                
        Date pst = last.getCreated();                     
        SimpleDateFormat df = new SimpleDateFormat("Z");
        assert(df.format(pst).equals("-0800"));        
    }      
    
    @Test
    public void testGetStatus_emptyFields() throws Exception {
        Channel channel = new Channel(TestChannelSettings.publicChannelID);
        channel.setUrl(TestChannelSettings.server);
        Feed statusFeed = channel.getStatusFeed();
        assertNull(statusFeed.getChannelCreationDate());
        assertNull(statusFeed.getChannelDescription());
        assertNull(statusFeed.getChannelId());
        try {
            assertNull(statusFeed.getChannelLastEntry());
        } catch (ThingSpeakException ex) {
            /* this is expected */
        }
        assertNull(statusFeed.getChannelLastEntryId());
        assertNotNull(statusFeed.getChannelName());
        assertNull(statusFeed.getChannelUpdateDate());
        assertNull(statusFeed.getFieldName(1));
        for (Entry entry : statusFeed.getEntryList()) {
            assertNull(entry.getElevation());
            assertNull(entry.getField(1));
            assertNull(entry.getLatitude());
            assertNull(entry.getLongitude());            
        }
        
    }
        
}
