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

package org.eclipse.datatools.sqltools.core.services;

import java.sql.Connection;
import java.util.HashMap;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.datatools.sqltools.core.DatabaseIdentifier;
import org.eclipse.datatools.sqltools.core.ProcIdentifier;
import org.eclipse.datatools.sqltools.editor.core.connection.IConnectionTracker;
import org.eclipse.debug.core.ILaunchConfiguration;


/**
 * A SQL execution service specific to a database definition.
 * @author Hui Cao
 *
 */
public class ExecutionService
{
	/**
     * Returns a <code>Runnable</code> object capable of running CallableStatement. Might be null.
     * @param con the connection
     * @param configuration the lauch configuration
     * @param closeCon whether should close connection
     * @param tracker if closeCon is true and tracker is not null, will notify it when close the connection
     * @param databaseIdentifier 
     */
    public Runnable createCallableSQLResultRunnable(Connection con, ILaunchConfiguration configuration, boolean closeCon,
        IConnectionTracker tracker, DatabaseIdentifier databaseIdentifier)
	{
		return null;
	}
    
    /**
     * Returns a <code>Runnable</code> object capable of running sql statements. Might be null.
     * @see org.eclipse.datatools.sqltools.sqleditor.result.ResultSupportRunnable
     * @param con the connection
     * @param sql the statement to be executed
     * @param closeCon whether should close connection
     * @param tracker if closeCon is true and tracker is not null, will notify it when close the connection
     * @param parentMonitor
     * @param databaseIdentifier 
     * @param configuration the lauch configuration
     * @param addInfo TODO
     * @para
     */
    public Runnable createSimpleSQLResultRunnable(Connection con, String sql, boolean closeCon, IConnectionTracker tracker,
            IProgressMonitor parentMonitor, DatabaseIdentifier databaseIdentifier, ILaunchConfiguration configuration, HashMap addInfo)
    {
    	return null;
    }
    

	/**
	 * The returned thread will be started together with the SQL execution
	 * logic. A typical usage of this method is to retrieve query plan while
	 * executing the sql statement.
	 * 
	 * @return
	 */
	public Runnable createExecuteParallelRunnable(String sql,
			DatabaseIdentifier databaseIdentifier) {
		return null;
	}
	

    /**
     * Returns a String prefix for invoking Routine/Event with the specified type in a CalllableStatement.
     * @return SQL construct that can invoke Routine/Event with the specified type
     */
    public String getCallableStatementPrefix(int type)
    {
        String prefix = "";
        switch (type)
        {
            case ProcIdentifier.TYPE_SP:
                prefix = "call ";
                break;
            case ProcIdentifier.TYPE_UDF:
                prefix = "select ";
                break;
            case ProcIdentifier.TYPE_EVENT:
                prefix = "TRIGGER EVENT ";
                break;
        }
        return prefix;
    }	

}
