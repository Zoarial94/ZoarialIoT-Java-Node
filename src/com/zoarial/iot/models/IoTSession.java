package com.zoarial.iot.models;

import javax.print.attribute.standard.MediaSize;

// TODO: refactor all uses of this class
public class IoTSession {
    private final int sessionID;
    private final IoTSessionType type;
    private final long sessionStart;
    private long lastHeardFrom;

    public enum IoTSessionType {
        ACTION,
        INFO,
        OTHER,
    }

    public IoTSession(int sessionID) {
        this(sessionID, IoTSessionType.OTHER);
    }

    public IoTSession(int sessionID, IoTSessionType type) {
        this.sessionID = sessionID;
        this.type = type;
        this.sessionStart = System.currentTimeMillis();
    }

    public int getSessionID() {
        return sessionID;
    }

    public IoTSessionType getType() {
        return type;
    }

    public long getSessionStart() {
        return sessionStart;
    }

    public long getLastHeardFrom() {
        return lastHeardFrom;
    }

    public void setLastHeardFrom(long lastHeardFrom) {
        this.lastHeardFrom = lastHeardFrom;
    }
}
