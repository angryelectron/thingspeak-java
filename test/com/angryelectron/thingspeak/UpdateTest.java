package com.angryelectron.thingspeak;

import org.junit.Test;

public class UpdateTest {    
        
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
