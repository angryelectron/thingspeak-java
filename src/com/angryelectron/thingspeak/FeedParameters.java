package com.angryelectron.thingspeak;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Set optional parameters which control the format of a feed.  All optional
 * parameters are supported except 'key' (because it is set via Channel) and 
 * 'format' (which must be JSON in order to parse the feed with this API).
 * 
 */
public class FeedParameters {
        
    /**
     * Time periods.  Used with timescale, sum, average, and median.
     */
    public enum Period { 
        T10m(10), 
        T15m(15), 
        T20m(20),
        T30m(30),
        T1h(60), 
        T4h(240), 
        T12h(720),
        T24h(1440);
        
        private final Integer minutes;       
        
        private Period(Integer minutes) {
            this.minutes = minutes;
        }
        
        protected Integer minutes() {
            return this.minutes;
        }
        
    }
    
    protected HashMap<String, Object> fields = new HashMap<>();
    
    /**
     * The date format required by ThingSpeak.
     */
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    
    /**
     * Constructor. 
     */
    public FeedParameters() {   
         /**
          * The feed must be returned in JSON format to be compatible with the 
          * Feed class.
          */
        fields.put("format", "json");
    }
            
    /**
     * Set the number of results to be returned.  Feeds that return more than 100
     * entries are cached for 5 minutes, so set results &lt; 100 for real time 
     * applications.
     * @param results 8000 max, or less than 100 to disable 5 minute data cache.
     */
    public void results(Integer results) {
        if (results > 8000) {
            throw new IllegalArgumentException("Feed cannot return more than 8000 results.");
        }
        fields.put("results", results);
    }
    /**
     * Number of days to include in the feed.
     * @param days 
     */       
    public void days(Integer days) {
        fields.put("days", days);
    }
    
    /**
     * Feed start date/time.
     * @param date 
     */
    public void start(Date date) {
        fields.put("start", formatter.format(date));
    }
    
    /**
     * Feed end date/time.
     * @param date 
     */
    public void end(Date date) {
        fields.put("end", formatter.format(date));
    }
    
    /**
     * Timezone offset, in hours.
     * @param hours 
     */
    public void offset(Integer hours) {
        fields.put("offset", hours);
    }
    
    /**
     * Include status information.
     * @param include Feed includes the status field when True.
     */
    public void status(Boolean include) {
        fields.put("status", include);
    }
    
    /**
     * Include location information.
     * @param include Feed includes lat, long, and elevation when True.
     */
    public void location(Boolean include) {
        fields.put("location", include);
    }
    
    /**
     * Minimum value.
     * @param value 
     */
    public void min(Double value) {
        fields.put("min", value);
    }
    
    /**
     * Maximum value.
     * @param value 
     */
    public void max(Double value) {
        fields.put("max", value);
    }
    
    /**
     * Rounding.
     * @param places - Round to this many decimal places.
     */
    public void round(Integer places) {
        fields.put("round", places);
    }
    
    /**
     * Return only the first value in the given period.
     * @param t Time period.
     */
    public void timescale(Period t) {
        fields.put("timescale", t.minutes());
    }
    
    /**
     * Sum all values in the given period.
     * @param t Time period.
     */
    public void sum(Period t) {
        fields.put("sum", t.minutes());
    }
    
    /**
     * Average all values in the given period.
     * @param t Time period.
     */
    public void average(Period t) {
        fields.put("average", t.minutes());        
    }
    
    /**
     * Median value for the given period.
     * @param t Time period.
     */
    public void median(Period t) {
        fields.put("median", t.minutes());
    }
    
}
