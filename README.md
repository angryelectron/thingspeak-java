ThingSpeak Java Client 
===
A Java client for the [ThingSpeak](http://thingspeak.com) Internet of 
Things.  Implements all aspects of the ThingSpeak API and can be used to 
update channel data, retrieve and examine feeds, and query public channels. 
It supports the hosted ThingSpeak server at api.thingspeak.com as well 
as self-hosted open-source servers ([GitHub Source](https://github.com/iobridge/thingspeak)). 

How To Install
---
Get the source by [downloading a zip file](https://github.com/angryelectron/ThingSpeak/archive/master.zip)
or by cloning the git repository https://github.com/angryelectron/thingspeak.git .
Building the source requires either the [Netbeans IDE](http://netbeans.org) or a 
Java 7 SDK and Apache Ant. Build the project by opening it in the Netbeans IDE, or from the command line.  

Here is an example of how to install the client in Ubuntu/Debian/Raspbian:

```
sudo apt-get update
sudo apt-get -y install openjdk-7-jdk git ant
git clone https://github.com/angryelectron/thingspeak.git
cd thingspeak
ant jar
ant test #optional - run tests 
```

The jars, docs, and dependencies can be found in thingspeak/dist.

How To Use
---
Add thingspeak-x.y.jar to your project.  There are two dependencies which
also need to be included: [Unirest](http://unirest.io) and 
[GSON](http://code.google.com/p/google-gson/).  
The dependencies can be found in thingspeak/dist/lib.

Refer to the included javadocs for more details.  The 
[ThingSpeak API Documentation](http://community.thingspeak.com/documentation/api/#thingspeak_api)
is also a good source of additional information.

Here is how to write "hello" to field1 of ThingSpeak public channel 1234:

```
String apiWriteKey = "your-channel-write-key";
Channel channel = new Channel(1234, apiWriteKey);

Entry entry = new Entry();
entry.setField(1, "hello");
channel.update(entry);
```

And here is how to read it back:

```
Channel channel = new Channel(1234);
Entry entry = channel.getLastChannelEntry();
System.out.println(entry.getField(1);
```

If you encounter any issues with the ThingSpeak Java Client, please use the [GitHub issue tracker](https://github.com/angryelectron/ThingSpeak/issues).


About
---
* ThingSpeak Java Client 
* Copyright 2014, Andrew Bythell <abythell@ieee.org>
* http://angryelectron.com
 
The ThingSpeak Java Client is free software: you can redistribute it and/or
modify it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or (at your
option) any later version.

The ThingSpeak Java Client is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
Public License for more details.

You should have received a copy of the GNU General Public License along with
the ThingSpeak Java Client. If not, see <http://www.gnu.org/licenses/>.

