/**
 * Created on 2005-11-30 Copyright (c) Sybase, Inc. 2004-2006 All rights reserved.
 */
package org.eclipse.datatools.sqltools.core.dbitem;

/**
 * Represents a SQL Event object.
 * @author Hui Cao
 *
 */
public interface IEvent extends IDBItem
{
    /**
     * Event type constant indicating the event is manually invoked.
     */
    public final static int      MANUAL_EVENT                     = 1;
    /**
     * Event type constant indicating the event is invoked when predefined condition is met.
     */
    public final static int      CONDITION_EVENT                  = 2;

    /**
     * Event type constant indicating the event is scheduled to be invoked at certain time.
     */
    public final static int      SCHEDULE_EVENT                   = 3;

    /**
     * 
     * @return event type defined in this interface
     */
    public int getEventType();

    /**
     * Retrieves the supported parameter list for a given event type
     * @param eventType event type defined in this interface
     * @return supported parameter array
     */
    public String[] getSupportedParameter(int eventType);

    /**
     * Retrieves the valid parameter values for a given parameter name.
     * @param parameter parameter name
     * @return parameter value array
     */
    public String[] getValidValues(String parameter);
}