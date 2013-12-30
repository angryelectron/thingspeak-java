/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.angryelectron.thingspeak;

import com.mashape.unirest.http.exceptions.UnirestException;
import java.util.UUID;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author abythell
 */
public class ChannelTest {

    final String writeKey = "8OBEPNQB06X9WDMZ ";
    final String readKey = "CLXKLBG0GB0M766F";
    final Integer channelId = 9235;
    private Integer entry_id;
    private Channel channel;
    private Entry entry;

    public ChannelTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws UnirestException, ThingSpeakException {
        channel = new Channel(channelId, writeKey, readKey);
        entry = new Entry();
        entry.setField(1, UUID.randomUUID().toString());
        entry.setField(2, String.valueOf(Math.random()));
        entry.setElevation(Math.random());
        entry.setLat(Math.random());
        entry.setLong(Math.random());
        entry.setStatus(UUID.randomUUID().toString());
        entry_id = channel.update(entry);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of update method, of class Channel.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testUpdate() throws Exception {
        System.out.println("update");
        assertFalse(entry_id == 0);
    }

    /**
     * Test of getChannelFeed method, of class Channel.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetChannelFeed_0args() throws Exception {
        System.out.println("getChannelFeed");
        Feed result = channel.getChannelFeed();
        assertNotNull(result);
        assertEquals(result.getChannelId(), this.channelId);
    }

    /**
     * Test of getChannelFeed method, of class Channel.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetChannelFeed_FeedParameters() throws Exception {
        System.out.println("getChannelFeed");
        FeedParameters options = new FeedParameters();
        options.location(true);

        Feed feed = channel.getChannelFeed(options);
        Entry result = feed.getEntry(entry_id);
        assertNotNull(result.getElevation());
        assertEquals(result.getElevation(), entry.getElevation(), 1);
    }

    /**
     * Test of getLastChannelEntry method, of class Channel.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetLastChannelEntry_0args() throws Exception {
        System.out.println("getLastChannelEntry");
        Feed feed = channel.getChannelFeed();
        Entry result = feed.getLastEntry();
        assertEquals(result.getField(1), entry.getField(1));
        assertEquals(result.getField(2), entry.getField(2));
        //TODO:  getField() returns string - it cannot know the data type
    }

    /**
     * Test of getLastChannelEntry method, of class Channel.
     */
    @Test
    public void testGetLastChannelEntry_FeedParameters() throws Exception {
        System.out.println("getLastChannelEntry");
        FeedParameters options = new FeedParameters();
        options.location(true);
        Feed feed = channel.getChannelFeed(options);
        Entry result = feed.getLastEntry();
        assertEquals(result.getElevation(), entry.getElevation(), 1);
        assertEquals(result.getField(1), entry.getField(1));
        assertEquals(result.getField(2), entry.getField(2));
    }

    /**
     * Test of getFieldFeed method, of class Channel.
     */
    @Test
    public void testGetFieldFeed_Integer() throws Exception {
        System.out.println("getFieldFeed");
        Integer fieldId = null;
        Channel instance = null;
        Feed expResult = null;
        Feed result = instance.getFieldFeed(fieldId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFieldFeed method, of class Channel.
     */
    @Test
    public void testGetFieldFeed_Integer_FeedParameters() throws Exception {
        System.out.println("getFieldFeed");
        Integer fieldId = null;
        FeedParameters options = null;
        Channel instance = null;
        Feed expResult = null;
        Feed result = instance.getFieldFeed(fieldId, options);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLastFieldEntry method, of class Channel.
     */
    @Test
    public void testGetLastFieldEntry_Integer() throws Exception {
        System.out.println("getLastFieldEntry");
        Integer fieldId = null;
        Channel instance = null;
        Entry expResult = null;
        Entry result = instance.getLastFieldEntry(fieldId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLastFieldEntry method, of class Channel.
     */
    @Test
    public void testGetLastFieldEntry_Integer_FeedParameters() throws Exception {
        System.out.println("getLastFieldEntry");
        Integer fieldId = null;
        FeedParameters options = null;
        Channel instance = null;
        Entry expResult = null;
        Entry result = instance.getLastFieldEntry(fieldId, options);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStatusFeed method, of class Channel.
     */
    @Test
    public void testGetStatusFeed_0args() throws Exception {
        System.out.println("getStatusFeed");
        Channel instance = null;
        Feed expResult = null;
        Feed result = instance.getStatusFeed();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStatusFeed method, of class Channel.
     */
    @Test
    public void testGetStatusFeed_FeedParameters() throws Exception {
        System.out.println("getStatusFeed");
        FeedParameters options = null;
        Channel instance = null;
        Feed expResult = null;
        Feed result = instance.getStatusFeed(options);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPublicChannels method, of class Channel.
     */
    @Test
    public void testGetPublicChannels() {
        System.out.println("getPublicChannels");
        Channel instance = null;
        instance.getPublicChannels();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUserInfo method, of class Channel.
     */
    @Test
    public void testGetUserInfo() {
        System.out.println("getUserInfo");
        Channel instance = null;
        instance.getUserInfo();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUserChannels method, of class Channel.
     */
    @Test
    public void testGetUserChannels() {
        System.out.println("getUserChannels");
        Channel instance = null;
        instance.getUserChannels();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
