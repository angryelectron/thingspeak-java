ThingSpeak Java Client 
===
A Java client for the [ThingSpeak](http://thingspeak.com) Internet of Things.
Works with the hosted ThingSpeak server at api.thingspeak.com as well as the
open-source server ([GitHub Source](https://github.com/iobridge/thingspeak)). 

How To Build
---
To build from source, git clone https://github.com/angryelectron/thingspeak.git.
Then build the project using the Netbeans IDE, or from the command line using:

cd thingspeak
ant jar #build jars 
ant javadoc #optional - build documentation 
ant test #optional - run tests 

The jars, docs, and dependencies can be found in thingspeak/dist.

How To Use
---
Include thingspeak-x.y.jar in your project.  There are two dependencies which
also need to be included: unirest-java and GSON.  These jars can be found in
thingspeak/dist/lib, or downloaded from their repsective project homepages.

Refer to the documentation in thingspeak/dist/javadoc for usage details.
