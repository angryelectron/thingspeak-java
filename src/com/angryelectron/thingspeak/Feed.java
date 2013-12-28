package com.angryelectron.thingspeak;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Channel feed data.
 */
public class Feed {

    /**
     * Although there are more sensible ways to build this class, the structure 
     * should match the JSON returned by the ThingSpeak API to allow GSON to 
     * de-serialize it properly.
     */
    
    private class ChannelInfo {

        private Date created_at;
        private String description;
        private String field1;
        private String field2;
        private String field3;
        private String field4;
        private String field5;
        private String field6;
        private String field7;
        private String field8;
        private Integer id;
        private Integer last_entry_id;
        private String name;
        private Date updated_at;
    }
    private final ChannelInfo channel = new ChannelInfo();
    private final ArrayList<Entry> feeds = new ArrayList<>();

    /**
     * Constructor.
     */
    public Feed() {
        /* no arg constructor required for serialization? */
    }
    
    /**
     * Channel creation date.
     * @return Date on which this channel was created.
     */
    public Date getChannelCreationDate() {
        return channel.created_at;
    }
    
    /**
     * Channel description.
     * @return Description of this channel.  Set via web.
     */
    public String getChannelDescription() {
        return channel.description;
    }
    
    /**
     * Get names of fields.
     * @param field 1-8.
     * @return The assigned name of the field.  Set via web.
     */
    public String getFieldName(Integer field) {
        switch(field) {
            case 1:
                return channel.field1;
            case 2:
                return channel.field2;
            case 3:
                return channel.field3;
            case 4:
                return channel.field4;
            case 5:
                return channel.field5;
            case 6:
                return channel.field6;
            case 7:
                return channel.field7;
            case 8:
                return channel.field8;
        }
        throw new IllegalArgumentException("Invalid field.");                
    }
    
    /**
     * Channel ID.  Not that useful, as you need to know this in advance.
     * @return ID of this channel.
     */
    public Integer getChannelId() {
        return channel.id;
    }
    
    /**
     * Last entry ID.
     * @return The ID of the last entry made in this channel.  Can be useful
     * for looking up entries within feeds.
     */
    public Integer getLastEntryId() {
        return channel.last_entry_id;
    }
    
    /**
     * Channel Name.
     * @return The name of this channel.  Set via web.
     */
    public String getChannelName() {
        return channel.name;
    }
    
    /**
     * Channel update date.
     * @return The date of the last update of this channel.
     */
    public Date getChannelUpdateDate() {
        return channel.updated_at;
    }
    
    /**
     * Get a List of all Entries in this feed.
     * @return All entries in this feed.
     */
    public List<Entry> getEntryList() {
        return this.feeds;
    }
    
    /**
     * Get a Map of all Entries in this feed.  A Map may perform better than a List
     * when searching large feeds.
     * @return All Entries in this feed keyed/indexed by entry_id.
     */
    public Map<Integer, Entry> getEntryMap() {
        HashMap<Integer, Entry> map = new HashMap<>();
        for (Entry entry: this.feeds) {
            map.put(entry.getEntryId(), entry);
        }
        return map;
    }
    
    /**
     * Lookup an Entry in the feed.  If the feed is large, or you need to lookup
     * many different entries, this method could be quite slow.  It may be better
     * to call {@link #getEntryMap()} to obtain a map of entries indexed by id.
     * @param id
     * @return Entry with the given id.
     * @throws ThingSpeakException if the feed does not contain an Entry with the
     * given id.
     */
    public Entry getEntry(Integer id) throws ThingSpeakException {                
        for (Entry entry : this.feeds) {
            if (entry.getEntryId() == id) {
                return entry;
            }
        }
        throw new ThingSpeakException("Entry with ID " + id + "not found in feed.");
                
    }
    
    /**
     * Get the last / latest entry in this feed.
     * @return An Entry with id equal to the feed's last_entry_id.
     * @throws ThingSpeakException The channel does not have a last entry.
     */
    public Entry getLastEntry() throws ThingSpeakException {
            return getEntry(channel.last_entry_id);
    }

}