ThingSpeak Java Client 
===
A Java client for the [ThingSpeak](http://thingspeak.com) Internet of 
Things.  Implements all aspects of the ThingSpeak API and can be used to 
update channel data, retrieve and examine feeds, and query public channels. 
It supports the hosted ThingSpeak server at api.thingspeak.com as well 
as self-hosted open-source servers ([GitHub Source](https://github.com/iobridge/thingspeak)). 

Also included:  An appender for log4j - post data to ThingSpeak channels using Logger
framework.

How To Install
---
Get the source by [downloading a zip file](https://github.com/angryelectron/thingspeak-java/archive/master.zip)
or by cloning the git repository https://github.com/angryelectron/thingspeak-java.git .
Building the source requires the Java 7 SDK and Apache Ant, or use the [Netbeans IDE](http://netbeans.org). 

Here is an example of how to install the client from the command line in Ubuntu/Debian/Raspbian with a minimal build environment:

```
sudo apt-get update
sudo apt-get -y install openjdk-7-jdk git ant
git clone https://github.com/angryelectron/thingspeak-java.git
cd thingspeak-java
ant jar
ant test #optional - run tests 
```

After building, the jars, docs, and dependencies can be found in thingspeak/dist.

How To Use
---
Add thingspeak-x.y.jar to your project and the following dependencies:

* [Unirest](http://unirest.io) 
* [GSON](http://code.google.com/p/google-gson/) 

Dependencies can be found in thingspeak/dist/lib after building the source.  Refer to the included javadocs for more details.  The [ThingSpeak API Documentation](http://community.thingspeak.com/documentation/api/#thingspeak_api)
is also a good source of additional information about using the API.

If you encounter any issues with the ThingSpeak Java Client, please use the [GitHub issue tracker](https://github.com/angryelectron/thingspeak-java/issues).

Examples
---
Here is how to write "hello" to field1 of ThingSpeak public channel 1234. 

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

Please refer to thingspeak/dist/javadoc for more information about customzing channel feeds, searching public channels, using open-source servers, and all the other operations supported by the ThingSpeak API.

log4j Appender
---
Use log4j to update ThingSpeak channels.  Log date, level, and message are 'fields',
written as an 'entry'.  Here's how to configure the appender and send a test 
message (just add your own channelNumber and apiWriteKey):

```
ThingSpeakAppender appender = new ThingSpeakAppender();
appender.configureChannel(channelNumber, apiWriteKey);
appender.setThreshold(Level.INFO);
appender.activateOptions();
Logger.getRootLogger().addAppender(appender);
Logger.getLogger(this.getClass()).log(Level.INFO, "Hello World");
```

You can also configure the appender via log4j.properties:

```
log4j.rootLogger=INFO, ThingSpeak
log4j.appender.ThingSpeak=com.angryelectron.thingspeak.log4j.ThingSpeakAppender
com.angryelectron.thingspeak.log4j.channelNumber = YOUR_CHANNEL_NUMBER
com.angryelectron.thingspeak.log4j.apiWriteKey = YOUR_API_WRITE_KEY
```

About
---
* ThingSpeak Java Client 
* Copyright 2014, Andrew Bythell <abythell@ieee.org>
* http://angryelectron.com/projects/thingspeak-java-client/
 
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

