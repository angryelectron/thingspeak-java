package com.angryelectron.thingspeak;

/**
 * Thrown when the ThingSpeak API rejects requests due to invalid API keys, arguments,
 * or data formats.
 */
public class ThingSpeakException extends Exception {
    public ThingSpeakException(String message) {
        super(message);
    }
}
