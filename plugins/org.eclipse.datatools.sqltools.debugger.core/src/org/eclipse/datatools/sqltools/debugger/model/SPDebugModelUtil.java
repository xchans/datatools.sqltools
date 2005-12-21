/*******************************************************************************
 * Copyright (c) 2004, 2005 Sybase, Inc. and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Sybase, Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.datatools.sqltools.debugger.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.datatools.sqltools.core.IDBHelper;
import org.eclipse.datatools.sqltools.core.ProcIdentifier;
import org.eclipse.datatools.sqltools.core.ProcIdentifierImpl;
import org.eclipse.datatools.sqltools.core.SQLToolsFacade;
import org.eclipse.datatools.sqltools.debugger.breakpoint.SPLineBreakpoint;
import org.eclipse.datatools.sqltools.debugger.core.internal.DebuggerCorePlugin;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.model.IBreakpoint;

/**
 * Utility class to process routine debug model objects.
 * @author Yang Liu
 */
public class SPDebugModelUtil
{
    /**
     * Returns the identifier of the debug model the <code>SPLineBreakpoint</code> is
     * associated with.
     */
    public static String getModelIdentifier()
    {
        return DebuggerCorePlugin.PLUGIN_ID + ".debugModelPresentation"; //$NON-NLS-1$
    }

    /**
     * Creates a line breakpoint at the specified line for the specified proc.
     * 
     * @param procid <code>Routine</code> identifier
     * @param lineNumber breakpoint line
     */
    public static SPLineBreakpoint createLineBreakpoint(ProcIdentifier procid, int lineNumber, boolean register)
        throws CoreException
    {
        SPLineBreakpoint bp = new SPLineBreakpoint(procid, lineNumber, true);
        if (register)
        {
            DebugPlugin.getDefault().getBreakpointManager().addBreakpoint(bp);
        }
        return bp;
    }

    /**
     * Returns the breakpoint object in a <code>Routine</code> identified by <code>procid</code>, at line <code>lineNumber</code>
     * @param procid <code>Routine</code> identifier
     * @param lineNumber line number
     * @return a <code>SPLineBreakpoint</code> if any, otherwise null.
     */
    public static SPLineBreakpoint findLineBreakpoint(ProcIdentifier procid, int lineNumber)
    {
        String modelId = getModelIdentifier();

        IBreakpointManager manager = DebugPlugin.getDefault().getBreakpointManager();
        IBreakpoint[] breakpoints = manager.getBreakpoints(modelId);
        for (int i = 0; i < breakpoints.length; i++)
        {
            if (!(breakpoints[i] instanceof SPLineBreakpoint))
            {
                continue;
            }
            SPLineBreakpoint breakpoint = (SPLineBreakpoint) breakpoints[i];
            IMarker marker = breakpoint.getMarker();

            if (marker != null && marker.exists())
            {
                try
                {
                    ProcIdentifier pi = breakpoint.getProcIdentifier();
                    if (!(procid.equals(pi)))
                    {
                        continue;
                    }

                    if (breakpoint.getLineNumber() == lineNumber) 
                    {
                        return breakpoint; 
                    }
                }
                catch (CoreException ex)
                {
                    continue;
                }
            }
        }
        return null;
    }

    /**
     * Finds breakpoints of all types in a <code>Routine</code> identified by <code>procid</code>, at line <code>lineNumber</code>
     * @param procid <code>Routine</code> identifier
     * @param lineNumber line number
     * @return list of <code>IBreakpoint</code>, may be empty
     */
    public static List findAllLineBreakpoint(ProcIdentifier procid, int lineNumber)
    {
        List ret = new ArrayList();
        String modelId = getModelIdentifier();

        IBreakpointManager manager = DebugPlugin.getDefault().getBreakpointManager();
        IBreakpoint[] breakpoints = manager.getBreakpoints(modelId);
        for (int i = 0; i < breakpoints.length; i++)
        {
            if (!(breakpoints[i] instanceof SPLineBreakpoint))
            {
                continue;
            }
            SPLineBreakpoint breakpoint = (SPLineBreakpoint) breakpoints[i];
            IMarker marker = breakpoint.getMarker();

            if (marker != null && marker.exists())
            {
                try
                {
                    ProcIdentifier pi = breakpoint.getProcIdentifier();
                    if (!(procid.equals(pi)))
                    {
                        continue;
                    }

                    if (breakpoint.getLineNumber() == lineNumber)
                    {
                        ret.add(breakpoint);
                    }
                }
                catch (CoreException ex)
                {
                    continue;
                }
            }
        }
        return ret;
    }

    /**
     * Tests whether the marker is a SP breakpoint marker for the specified routine.
     * 
     * @param marker
     * @param proc <code>Routine</code> identifier
     */
    public static boolean isSPBreakpointMarker(IMarker marker, ProcIdentifier proc)
    {
        try
        {
            String type = marker.getType();
            if (SPLineBreakpoint.MARKER_TYPE.equals(type))
            {
                // good, this is a SPbreakpoint marker, next check proc
                String str = marker.getAttribute(SPLineBreakpoint.ATTR_PROCIDENTIFIER, ""); //$NON-NLS-1$
                try
                {
                    ProcIdentifier id = ProcIdentifierImpl.decode(str);
                    return proc.equals(id);
                }
                catch (ParseException ex)
                {
                    DebuggerCorePlugin.getDefault().log(ex);
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
        catch (CoreException ex)
        {
            DebuggerCorePlugin.getDefault().log(ex);
            return false;
        }
    }

    /**
     * Tests whether the markerDelta is a SP breakpoint marker delta for the specified routine.
     * @param markerDelta
     * @param identifier
     */
    public static boolean isSPBreakpointMarkerDelta(IMarkerDelta markerDelta, ProcIdentifier identifier)
    {
        String type = markerDelta.getType();
        if (SPLineBreakpoint.MARKER_TYPE.equals(type))
        {
            // good, this is a SPbreakpoint marker, next check proc
            String str = markerDelta.getAttribute(SPLineBreakpoint.ATTR_PROCIDENTIFIER, ""); //$NON-NLS-1$
            try
            {
                ProcIdentifier id = ProcIdentifierImpl.decode(str);
                return identifier.equals(id);
            }
            catch (ParseException ex)
            {
                DebuggerCorePlugin.getDefault().log(ex);
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * Tests whether a breakpoint support condition. 
     * @param bp
     * @return true if no, otherwise false
     */
    public static boolean supportCondition(SPLineBreakpoint bp) throws CoreException
    {
        IDBHelper helper = SQLToolsFacade.getDBFactory(bp.getProcIdentifier().getDatabaseIdentifier(), "").getDBHelper();
        if (helper != null )
        {
            return helper.supportsConditionBreakpoint();
        }
        return false;

    }

    /**
     * Finds all breakpoints belonging to the specified routine
     * @param procid <code>Routine</code> identifier
     * @return list of <code>SPLineBreakpoint</code>, may be empty
     */
    public static List findAllSPLineBreakpointForSP(ProcIdentifier procid)
    {
        List ret = new ArrayList();
        String modelId = getModelIdentifier();

        IBreakpointManager manager = DebugPlugin.getDefault().getBreakpointManager();
        IBreakpoint[] breakpoints = manager.getBreakpoints(modelId);
        for (int i = 0; i < breakpoints.length; i++)
        {
            if (!(breakpoints[i] instanceof SPLineBreakpoint))
            {
                continue;
            }
            SPLineBreakpoint breakpoint = (SPLineBreakpoint) breakpoints[i];
            try
            {
                ProcIdentifier pi = breakpoint.getProcIdentifier();
                if ((procid.equals(pi)))
                {
                    ret.add(breakpoint);
                }
            }
            catch(Exception ex)
            {
                // skip
            }
        }
        return ret;
    }

    /**
     * Removes all breakpoints for a particular routine.
     * @param procid <code>Routine</code> identifier
     */
    public static void removeAllBreakpointForSP(ProcIdentifier procid)
    {
        List list = findAllSPLineBreakpointForSP(procid);
        for (int i=0, size=list.size(); i<size; i++)
        {
            IBreakpoint bp = (IBreakpoint) list.get(i);
            try
            {
                bp.delete();
            }
            catch(Exception ex)
            {
                // skip
            }
        }
    }
}