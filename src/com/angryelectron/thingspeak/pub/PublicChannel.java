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

package com.angryelectron.thingspeak.pub;

import java.util.ArrayList;
import java.util.Date;

public class PublicChannel {
       
    private class Tag {
        private Integer id;
        private String name;
    }

    private Date created_at;
    private String description;
    private Double elevation;
    private Integer id;
    private Integer last_entry_id;
    private Double latitude;
    private Double longitude;
    private String name;
    private Integer ranking;
    private String username;
    private final ArrayList<Tag> tags = new ArrayList<>();
    
    public String getName() {
        return name;
    }

    public Date getCreatedAt() {
        return created_at;
    }

    public String getDescription() {
        return description;
    }

    public Double getElevation() {
        return elevation;
    }

    public Integer getId() {
        return id;
    }

    public Integer getLastEntryId() {
        return last_entry_id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Integer getRanking() {
        return ranking;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }
                    
}
