package com.angryelectron.thingspeak;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import java.util.HashMap;

/**
 * Thingspeak Channel.  Methods for updating and requesting feeds and entries from
 * Thingspeak channels.
 */
public class Channel {
    
    private static final String APIURL = "http://api.thingspeak.com";
    private static final String APIHEADER = "X-THINGSPEAKAPIKEY";    
    private Integer channelId;
    private String readAPIKey;
    private String writeAPIKey;
    private Boolean isReadOnly;
    private Boolean isPublic;    
    private HashMap<String, Object> fields = new HashMap<>();            
    
    /**
     * Constructor for a public, read-only, Thingspeak channel.
     * @param channelId Channel Id.
     */
    public Channel(Integer channelId) {
        this.isReadOnly = true;
        this.isPublic = true;
        this.channelId = channelId;
    }
    
    /**
     * Constructor for a public, writeable, Thingspeak channel.
     * @param channelId Channel Id.
     * @param writeKey API Key for the channel.  See https://thingspeak.com/channels/<channelId>#apikeys
     */
    public Channel(Integer channelId, String writeKey) {
        this.isReadOnly = false;
        this.isPublic = true;
        this.channelId = channelId;
    }
    
    /**
     * Constructor for a private, writeable, Thingspeak channel.
     * @param channelId Channel Id.
     * @param writeKey Write API Key.  See https://thingspeak.com/channels/<channelId>#apikeys.
     * @param readKey Read API Key.  See https://thingspeak.com/channels/<channelId>#apikeys.
     */
    public Channel(Integer channelId, String writeKey, String readKey) {
        this.channelId = channelId;
        this.readAPIKey = readKey;
        this.writeAPIKey = writeKey;
        this.isReadOnly = false;
        this.isPublic = false;
    }  
    
    /**
     * Make requests to the Thingspeak API without additional
     * feed parameters.
     * @param url The API url.
     * @return Feed object.
     * @throws UnirestException The request cannot be made.
     * @throws ThingSpeakException The request is invalid.
     */
    private Feed thingRequest(String url) throws UnirestException, ThingSpeakException {
        HttpRequestWithBody request = Unirest.post(url).header("Connection", "close");
        if (!this.isPublic) {
            request.header(APIHEADER, this.readAPIKey);
        }    
        HttpResponse<JsonNode> response = request.asJson();
        if (response.getCode() != 200) {
            throw new ThingSpeakException("Request failed with code " + response.getCode());
        }
        Gson gson = new Gson();        
        return gson.fromJson(response.getBody().toString(), Feed.class);  
    }
    
    /**
     * Make requests to the Thingspeak API with additional feed parameters.
     * @param url The API url.
     * @param options Optional feed parameters.
     * @return Feed object.
     * @throws UnirestException The request cannot be made.
     * @throws ThingSpeakException The request is invalid.
     */
    private Feed thingRequest(String url, FeedParameters options) throws UnirestException, ThingSpeakException {
        HttpRequestWithBody request = Unirest.post(url).header("Connection", "close");
        if (!this.isPublic) {
            request.header(APIHEADER, this.readAPIKey);
        }   
        request.fields(options.fields);
        HttpResponse<JsonNode> response = request.asJson();
        if (response.getCode() != 200) {
            throw new ThingSpeakException("Request failed with code " + response.getCode());
        }
        Gson gson = new Gson();        
        return gson.fromJson(response.getBody().toString(), Feed.class);  
    }
                 
    /**
     * Update channel with new data.
     * @param entry The new data to be posted.
     * @return The id of the new entry.
     * @throws UnirestException The request cannot be made.
     * @throws ThingSpeakException The request is invalid.
     */
    Integer update(Entry entry) throws UnirestException, ThingSpeakException {
        HttpResponse<String> response = Unirest.post(APIURL + "/update")
                .header(APIHEADER, this.writeAPIKey)
                .header("Connection", "close")
                .fields(entry.getFields())
                .asString();        
        if (response.getCode() != 200) {
            throw new ThingSpeakException("Request failed with code " + response.getCode());
        } else if (response.getBody().equals("0")) {
            throw new ThingSpeakException("Update failed.");                            
        }   
        return Integer.parseInt(response.getBody());
    } 
    
    /**
     * Get a feed.  Uses the default feed settings, which returns all entries but
     * does not contain status or location info.  To customize the feed format,
     * use {@link #getFeed(com.angryelectron.thingspeak.FeedParameters)}.
     * @return Feed for this channel.
     * @throws UnirestException The request cannot be made.
     * @throws ThingSpeakException The request is invalid.
     */
    public Feed getChannelFeed() throws UnirestException, ThingSpeakException {
        String url = APIURL + "/channels/" + this.channelId + "/feed.json";       
        return thingRequest(url);       
    }
    
    /**
     * Retrieve channel feed with optional parameters.
     * @param options Optional parameters that control the format of the feed.
     * @return Feed for this channel
     * @throws UnirestException The request cannot be made.
     * @throws ThingSpeakException  The request is invalid.
     */
    public Feed getChannelFeed(FeedParameters options) throws UnirestException, ThingSpeakException {
        String url = APIURL + "/channels/" + this.channelId + "/feed.json";
        return thingRequest(url, options);      
    }
    
    /**
     * Get last entry in this channel with default feed options.
     * @return Entry.
     * @throws UnirestException The request cannot be made.
     * @throws ThingSpeakException The request is invalid.
     */
    Entry getLastChannelEntry() throws UnirestException, ThingSpeakException {
        String url = APIURL + "/channels/" + this.channelId + "/last.json";
        Feed feed = thingRequest(url); 
        return feed.getLastEntry();
    }
    
    /**
     * Get last entry in this channel with additional feed options.
     * @param options
     * @return Entry.
     * @throws UnirestException The request cannot be made.
     * @throws ThingSpeakException The request is invalid.
     */
    Entry getLastChannelEntry(FeedParameters options) throws UnirestException, ThingSpeakException {
        String url = APIURL + "/channels/" + this.channelId + "/last.json";
        Feed feed = thingRequest(url, options); 
        return feed.getLastEntry();
    }
    
    /**
     * Get a field feed with default feed options.
     * @param fieldId The field to include in the field (1-8).
     * @return Feed.
     * @throws UnirestException The request cannot be made.
     * @throws ThingSpeakException The request is invalid.
     */
    Feed getFieldFeed(Integer fieldId) throws UnirestException, ThingSpeakException {
        String url = APIURL + "/channels/" + this.channelId + "/field/" + fieldId + ".json"; 
        return thingRequest(url);   
    }
    
    /**
     * Get a field feed with additional feed options. 
     * @param fieldId The field to include in the field (1-8).
     * @param options Optional parameters that control the format of the feed.
     * @return Feed.
     * @throws UnirestException The request cannot be made.
     * @throws ThingSpeakException The request is invalid.
     */
    Feed getFieldFeed(Integer fieldId, FeedParameters options) throws UnirestException, ThingSpeakException {
        String url = APIURL + "/channels/" + this.channelId + "/field/" + fieldId + ".json";  
        return thingRequest(url, options);   
    }
    
    /**
     * Get the last entry in a field feed.  Uses the default feed options.  
     * TXT format is not supported by the API.
     * @param fieldId The field to return (0-8).
     * @return Last entry for the specified field.
     * @throws UnirestException The request cannot be made.
     * @throws ThingSpeakException The request is invalid.
     */
    Entry getLastFieldEntry(Integer fieldId) throws UnirestException, ThingSpeakException {
        String url = APIURL + "/channels/" + this.channelId + "/field/" + fieldId + "/last.json";   
        Feed feed = thingRequest(url);
        return feed.getLastEntry();
    }
    
    /**
     * Get the last entry in a field feed.  TXT format is not supported by the API.
     * @param fieldId The field to return (0-8).
     * @param options Supported options: offset, status, and location.
     * @return Last entry for the specified field.
     * @throws UnirestException The request cannot be made.
     * @throws ThingSpeakException The request is invalid.
     */
    Entry getLastFieldEntry(Integer fieldId, FeedParameters options) throws UnirestException, ThingSpeakException {
        String url = APIURL + "/channels/" + this.channelId + "/field/" + fieldId + "/last.json";   
        Feed feed = thingRequest(url, options);
        return feed.getLastEntry();
    }
    
    /**
     * Get channel status updates.  Uses the default feed options.
     * @return Status feed.
     * @throws UnirestException The request cannot be made.
     * @throws ThingSpeakException The request is invalid.
     */
    Feed getStatusFeed() throws UnirestException, ThingSpeakException {
        String url = APIURL + "/channels/" + this.channelId + "/status.json";
        return thingRequest(url);
    }
    
    /**
     * Get channel status updates.  
     * @param options Supported options:  offset.
     * @return Status feed.
     * @throws UnirestException The request cannot be made.
     * @throws ThingSpeakException The request is invalid.
     */
    Feed getStatusFeed(FeedParameters options) throws UnirestException, ThingSpeakException {
        String url = APIURL + "/channels/" + this.channelId + "/status.json";
        return thingRequest(url, options);
    }
    
    void getPublicChannels() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
    void getUserInfo() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
    void getUserChannels() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
    
}
