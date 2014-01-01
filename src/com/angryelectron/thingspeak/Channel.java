package com.angryelectron.thingspeak;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import java.util.HashMap;

/**
 * Thingspeak Channel.  Methods for updating and requesting feeds and entries from
 * Thingspeak channels.
 */
public class Channel {
    
    //TODO: the API url should be configurable so the client can be used with
    //self-hosted servers.
    private String APIURL = "http://api.thingspeak.com";
    private static final String APIHEADER = "X-THINGSPEAKAPIKEY";    
    private final Integer channelId;
    private String readAPIKey;
    private String writeAPIKey;    
    private final Boolean isPublic;    
    private final HashMap<String, Object> fields = new HashMap<>();            
    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").create();
    
    /**
     * Constructor for a public, read-only, Thingspeak channel.  This type of 
     * channel cannot be updated.
     * @param channelId Channel Id.
     */
    public Channel(Integer channelId) {        
        this.isPublic = true;
        this.channelId = channelId;
    }
    
    /**
     * Constructor for a public, writeable, Thingspeak channel.
     * @param channelId Channel Id.
     * @param writeKey API Key for the channel.  See https://thingspeak.com/channels/<channelId>#apikeys
     */
    public Channel(Integer channelId, String writeKey) {        
        this.isPublic = true;
        this.channelId = channelId;
        this.writeAPIKey = writeKey;
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
        this.isPublic = false;
    }  
    
     /**
     * Make GET requests to the Thingspeak API without additional
     * feed parameters.
     * @param url The API url.
     * @return JSON string.
     * @throws UnirestException The request cannot be made.
     * @throws ThingSpeakException The request is invalid.
     */
    private String thingRequest(String url) throws UnirestException, ThingSpeakException {
        GetRequest request = Unirest.get(url);
        if (!this.isPublic) {
            request.field("key", this.readAPIKey);
        }        
        HttpResponse<JsonNode> response = request.asJson();
        if (response.getCode() != 200) {
            throw new ThingSpeakException("Request failed with code " + response.getCode());
        }
        return response.getBody().toString();
    }

    /**
     * Make GET requests to the Thingspeak API with additional
     * feed parameters.
     * @param url The API url.
     * @param options Optional feed parameters.
     * @return JSON string.
     * @throws UnirestException The request cannot be made.
     * @throws ThingSpeakException The request is invalid.
     */
    private String thingRequest(String url, FeedParameters options) throws UnirestException, ThingSpeakException {
        GetRequest request = Unirest.get(url);
        if (!this.isPublic) {
            request.field("key", this.readAPIKey);
        }        
        request.fields(options.fields);
        HttpResponse<JsonNode> response = request.asJson();
        if (response.getCode() != 200) {
            throw new ThingSpeakException("Request failed with code " + response.getCode());
        }
        return response.getBody().toString();
    }
    
    /**
     * Use a server other than thingspeak.com.  If you are hosting your own
     * Thingspeak server, set the url of the server here.
     * @param url eg. http://localhost, http://thingspeak.local:3000, etc.
     */
    public void setUrl(String url) {
        this.APIURL = url;
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
                .fields(entry.getUpdateMap())
                .asString();        
        if (response.getCode() != 200) {
            throw new ThingSpeakException("Request failed with code " + response.getCode());
        } else if (response.getBody().equals("0")) {
            throw new ThingSpeakException("Update failed.");                            
        }   
        return Integer.parseInt(response.getBody());
    } 
    
    /**
     * Get a feed. Does not include location or status info.  Only fields
     * that have been named in the channel's settings (via the web) will
     * be returned.
     * @return Feed for this channel.
     * @throws UnirestException The request cannot be made.
     * @throws ThingSpeakException The request is invalid.
     */
    public Feed getChannelFeed() throws UnirestException, ThingSpeakException {
        String url = APIURL + "/channels/" + this.channelId + "/feed.json";                       
        return gson.fromJson(thingRequest(url), Feed.class);  
    }
    
    /**
     * Get a feed with optional parameters. Only fields that have been named in
     * the channel's settings (via the web) will be returned.
     * @param options Additional feed parameters.
     * @return Feed for this channel.
     * @throws UnirestException The request cannot be made.
     * @throws ThingSpeakException  The request is invalid.
     */
    public Feed getChannelFeed(FeedParameters options) throws UnirestException, ThingSpeakException {
        String url = APIURL + "/channels/" + this.channelId + "/feed.json";        
        return gson.fromJson(thingRequest(url, options), Feed.class);  
    }
    
    /**
     * Get last entry in this channel with default feed options.  This is a faster
     * alternative to getting a Channel Feed and then calling {@link Feed#getLastEntry()}.
     * @return Entry.
     * @throws UnirestException The request cannot be made.
     * @throws ThingSpeakException The request is invalid.
     */
    Entry getLastChannelEntry() throws UnirestException, ThingSpeakException {
        String url = APIURL + "/channels/" + this.channelId + "/feed/last.json";        
        return gson.fromJson(thingRequest(url), Entry.class);  
    }
    
    /**
     * Get last entry in this channel with additional feed options.  This is a faster
     * alternative to getting a Channel Feed and then calling {@link Feed#getLastEntry()}
     * @param options
     * @return Entry.
     * @throws UnirestException The request cannot be made.
     * @throws ThingSpeakException The request is invalid.
     */
    Entry getLastChannelEntry(FeedParameters options) throws UnirestException, ThingSpeakException {
        String url = APIURL + "/channels/" + this.channelId + "/feed/last.json";        
        return gson.fromJson(thingRequest(url, options), Entry.class);  
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
        return gson.fromJson(thingRequest(url), Feed.class);  
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
        return gson.fromJson(thingRequest(url, options), Feed.class);  
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
        return gson.fromJson(thingRequest(url), Entry.class);  
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
        return gson.fromJson(thingRequest(url, options), Entry.class);  
    }
    
    /**
     * Get channel status updates.  Uses the default feed options.
     * @return Status feed.
     * @throws UnirestException The request cannot be made.
     * @throws ThingSpeakException The request is invalid.
     */
    Feed getStatusFeed() throws UnirestException, ThingSpeakException {
        String url = APIURL + "/channels/" + this.channelId + "/status.json";
        return gson.fromJson(thingRequest(url), Feed.class);
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
        return gson.fromJson(thingRequest(url, options), Feed.class);
    }
    
    void getPublicChannels() {
        throw new UnsupportedOperationException("Not implemented.");
    }
    
    void getUserInfo() {
        throw new UnsupportedOperationException("Not implemented.");
    }
    
    void getUserChannels() {
        throw new UnsupportedOperationException("Not implemented.");
    }        
}
