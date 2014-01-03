package com.angryelectron.thingspeak;

/**
 * Statics for accessing ThingSpeak channels used for Unit Testing.  Before testing
 * you must create a private and a public channel on the server via the web
 * interface.  Each channel must have a name, description, and all 8 fields defined.
 * The non-public channel must have an API Read Key generated.  Enter the
 * server and channel information below.
 * 
 * Also note that after 100 tests, data starts to be cached and tests may start
 * to fail.  Clear all the feed data from the channel to avoid this problem.
 */
public class TestChannelSettings {
        
    public static String server = "http://api.thingspeak.com";    
    public static Integer publicChannelID = 9235;
    public static String publicChannelWriteKey = "8OBEPNQB06X9WDMZ";    
    public static Integer privateChannelID = 9438;
    public static String privateChannelWriteKey = "AATBGE761SG6QE7J ";
    public static String privateChannelReadKey = "O7YHHVZMQSXRNJI2";    
    public static Integer rateLimit = 15000;    
       
    /*
    public static Integer publicChannelID = 1;
    public static String publicChannelWriteKey = "N6ES1RM97J3846NU ";    
    public static Integer privateChannelID = 2;
    public static String privateChannelWriteKey = "UGN3RZYIVFKZLIHZ  ";
    public static String privateChannelReadKey = "PIHR34GGPEKCOINR";    
    public static Integer rateLimit = 0; 
    public static String server = "http://192.168.2.190:3000";
    */
    
}
