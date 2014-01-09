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
import java.util.Iterator;

class PublicJSONResult {
    
    /**
     * This class must match the JSON returned by Thingspeak.
     */
    private class Pagination {
        private Integer current_page;
        private Integer per_page;
        private Integer total_entries;
    }

    /**
     * These members must match the JSON returned by Thingspeak.
     */
    private final Pagination pagination = new Pagination();
    private final ArrayList<PublicChannel> channels = new ArrayList<>();
   
    Integer getCurrentPage() {
        return pagination.current_page;
    }
        
    Boolean isLastPage() {
        if (pagination.total_entries <= pagination.per_page) {
            return true;
        } else {
            Double pages = (double) pagination.total_entries / pagination.per_page;
            return (pages.intValue() == pagination.current_page);        
        }
    }
    
    Iterator<PublicChannel> iterator() {
        return channels.iterator();
    }
    
    Integer getTotalEntries() {
        return pagination.total_entries;
    }

}
