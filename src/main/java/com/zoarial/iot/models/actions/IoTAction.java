package com.zoarial.iot.models.actions;

import com.zoarial.iot.models.IoTBasicType;
import com.zoarial.iot.models.IoTNode;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class IoTAction {
    private final String actionName;
    private final String description = "";
    private final UUID actionUuid;
    private final IoTBasicType returnType = IoTBasicType.STRING;

    // Default, anyone with the right level can use.
    private boolean allowByDefault = true;
    List<IoTNode> whiteList;
    List<IoTNode> blackList;

    /*
     * securityLevel is how privileged the request has to be in order to start
     * This action can be run from a higher level, but not a lower one.
     * Level 0 means no security
     * Level 1 means basic security. Needs HTOP or TOTP
     * Level 2 needs stronger identification (public/private keys)
     * Level 10 means only the local machine can run it.
     */
    private final byte actionSecurityLevel;
    private final byte arguments;
    private final boolean encrypted;
    private final boolean local;

    protected IoTAction(String name, UUID uuid, byte level, boolean encrypted, boolean local, byte arguments) {
        actionName = name;
        actionUuid = uuid;
        actionSecurityLevel = level;
        this.arguments = arguments;
        this.encrypted = encrypted;
        this.local = local;
    }

    // This will be called and executed in its own thread
    protected abstract String privExecute(IoTActionArgumentList args);

    public final String execute() {
        return execute(new IoTActionArgumentList());
    }
    //Ensure the argument size is correct
    public final String execute(IoTActionArgumentList args) {
        if(arguments == args.size()) {
            return privExecute(args);
        }
        throw new IllegalArgumentException("Incorrect amount of arguments. Needed: " + arguments + " Got: " + args.size());
    }

    // Just call the other execute function
    public final String execute(JSONArray args) {
        int len = args.length();
        IoTActionArgumentList list = new IoTActionArgumentList();
        for(int i = 0; i < len; i++) {
            list.add(args.getString(i));
        }
        return execute(list);
    }

    public String getName() {
        return actionName;
    }

    public UUID getUUID() {
        return actionUuid;
    }

    public byte getSecurityLevel() {
        return actionSecurityLevel;
    }

    public byte getNumberOfArguments() {
        return arguments;
    }

    public boolean isEncrypted() {
        return encrypted;
    }

    public boolean isLocal() {
        return local;
    }

    @Override
    public String toString() {
        return getUUID().toString() + " " + getSecurityLevel() + " " + getName() + " " + getNumberOfArguments();
    }

}