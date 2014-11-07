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

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class UpdateTest {    
        
    @BeforeClass
    public static void setUpClass() throws Exception {
        BasicConfigurator.resetConfiguration();
        BasicConfigurator.configure();
        Logger.getLogger("org.apache.http").setLevel(Level.OFF);
    }
            
    /**
     * Pause to prevent multiple update requests from exceeding the API rate
     * limit.  Call all the end of each test to prevent subsequent tests from
     * failing.  By appending to the end of each test instead of calling it using
     * \@After, time can be saved when running tests that throw exceptions and don't
     * actually do a successful update.
     * @throws InterruptedException 
     */
    private void pauseForAPIRateLimit() throws InterruptedException {
        System.out.println("Waiting for rate limit to expire.");
        Thread.sleep(TestChannelSettings.rateLimit);
    }
    
    @Test
    public void testUpdateChannel() throws Exception{
        System.out.println("testUpdatePublicChannel");
        Channel channel = new Channel(TestChannelSettings.publicChannelID, TestChannelSettings.publicChannelWriteKey);
        channel.setUrl(TestChannelSettings.server);
        Integer result = channel.update(new Entry());
        assert (result != 0);
        pauseForAPIRateLimit();
    }

    
    @Test(expected = ThingSpeakException.class)
    public void testUpdateChannelWithInvalidAPIKey() throws Exception {
        System.out.println("testUpdatePublicChannelWithInvalidAPIKey");
        Channel channel = new Channel(TestChannelSettings.publicChannelID, "invalidChannelKey");        
        channel.setUrl(TestChannelSettings.server);
        channel.update(new Entry());
        pauseForAPIRateLimit();
    }
            
    @Test
    public void testUpdateSlowerThanAPIRateLimit() throws Exception {
        System.out.println("testUpdateSlowerThanAPIRateLimit");
        /**
         * Do an update an make sure it succeeds.
         */
        Channel channel = new Channel(TestChannelSettings.publicChannelID, TestChannelSettings.publicChannelWriteKey);
        channel.setUrl(TestChannelSettings.server);
        Integer result0 = channel.update(new Entry());
        assert(result0 != 0);
        
        /**
         * Pause until the rate limit has passed.
         */
        Thread.sleep(TestChannelSettings.rateLimit);
        
        /**
         * Do another update and make sure it succeeds.
         */
        Integer result1 = channel.update(new Entry());
        assert(result1 != 0);
        
        pauseForAPIRateLimit();
    }
    
    @Test(expected = ThingSpeakException.class)
    public void testUpdateFasterThanAPIRateLimit() throws Exception {
        System.out.println("testUpdateFasterThanAPIRateLimit");
        /**
         * On self-hosted servers, there is no rate limiting
         */
        if (TestChannelSettings.rateLimit == 0) {
            throw new ThingSpeakException("Rate limiting not supported");
        }
        
        /**
         * Do an update and make sure it succeeds.
         */
        Channel channel = new Channel(TestChannelSettings.publicChannelID, TestChannelSettings.publicChannelWriteKey);
        channel.setUrl(TestChannelSettings.server);
        Integer result = channel.update(new Entry());
        assert(result != 0);
        
        /**
         * Don't wait for the rate limit to pass - send another update
         * right away.  This should cause a ThingSpeakException.
         */
        channel.update(new Entry());        
                
        pauseForAPIRateLimit();
    }
 
}
