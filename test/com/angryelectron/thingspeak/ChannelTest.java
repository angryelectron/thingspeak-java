/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.angryelectron.thingspeak;

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
    
    public ChannelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of update method, of class Channel.
     */
    @Test
    public void testUpdate() throws Exception {
        System.out.println("update");
        Entry entry = null;
        Channel instance = null;
        Integer expResult = null;
        Integer result = instance.update(entry);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getChannelFeed method, of class Channel.
     */
    @Test
    public void testGetChannelFeed_0args() throws Exception {
        System.out.println("getChannelFeed");
        Channel instance = null;
        Feed expResult = null;
        Feed result = instance.getChannelFeed();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getChannelFeed method, of class Channel.
     */
    @Test
    public void testGetChannelFeed_FeedParameters() throws Exception {
        System.out.println("getChannelFeed");
        FeedParameters options = null;
        Channel instance = null;
        Feed expResult = null;
        Feed result = instance.getChannelFeed(options);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLastChannelEntry method, of class Channel.
     */
    @Test
    public void testGetLastChannelEntry_0args() throws Exception {
        System.out.println("getLastChannelEntry");
        Channel instance = null;
        Entry expResult = null;
        Entry result = instance.getLastChannelEntry();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLastChannelEntry method, of class Channel.
     */
    @Test
    public void testGetLastChannelEntry_FeedParameters() throws Exception {
        System.out.println("getLastChannelEntry");
        FeedParameters options = null;
        Channel instance = null;
        Entry expResult = null;
        Entry result = instance.getLastChannelEntry(options);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
