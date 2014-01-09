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

import com.angryelectron.thingspeak.pub.PublicChannelCollection;
import com.angryelectron.thingspeak.pub.PublicChannel;
import java.util.Iterator;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class TestPublicChannels {
    
    @Test
    public void testPublic_IterateAll() throws Exception {
        Integer count = 0;
        PublicChannelCollection pl = new PublicChannelCollection();
        Iterator<PublicChannel> publicIterator = pl.iterator();
        while (publicIterator.hasNext()) {
            PublicChannel p = publicIterator.next();             
            count++;
        }         
        assert(pl.size() == count);
    }
    
    @Test
    public void testPublic_InterateWithTag() throws Exception {
        Integer count = 0;
        PublicChannelCollection pl = new PublicChannelCollection("temperature");
        Iterator<PublicChannel> publicIterator = pl.iterator();
        while (publicIterator.hasNext()) {
            PublicChannel p = publicIterator.next();             
            count++;
        }         
        assert(pl.size() == count);
    }
    
    @Test
    public void testPublic_ChannelInfoPopulated() throws Exception {
        PublicChannelCollection publicChannels = new PublicChannelCollection("cheerlights");
        for (PublicChannel channel : publicChannels) {
            assertNotNull(channel.getCreatedAt());
            assertNotNull(channel.getDescription());
            assertNotNull(channel.getElevation());
            assertNotNull(channel.getId());
            assertNotNull(channel.getLastEntryId());
            assertNotNull(channel.getLatitude());
            assertNotNull(channel.getLongitude());
            assertNotNull(channel.getName());
            assertNotNull(channel.getRanking());
            assertNotNull(channel.getTags());
            assertNotNull(channel.getUsername());
        }
    }
        
}
