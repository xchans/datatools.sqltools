/*******************************************************************************
 * Copyright (c) 2005 Sybase, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Sybase, Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.datatools.sqltools.result.internal.core;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.datatools.connectivity.ProfileManager;
import org.eclipse.datatools.sqltools.result.IReExecutionRunnable;
import org.eclipse.datatools.sqltools.result.OperationCommand;
import org.eclipse.datatools.sqltools.result.internal.Constants;

/**
 * Utility class to read the extensions
 * @author Dafan Yang
 */
public class ReExecutionRegistryReader
{
    /**
     * Given a operation, this method is to find a approporiate IReExecutionRunnable handler to re-execute this
     * operaion.
     * 
     * @param cmd the operation command
     * @return a runnable class which implements <code>IReExecutionRunnable</code>
     */
    public static IReExecutionRunnable readProperReExecutionHandler(OperationCommand cmd)
    {
        IExtensionRegistry registry = Platform.getExtensionRegistry();
        IExtensionPoint point = registry.getExtensionPoint(Constants.PLUGIN_ID, Constants.RE_EXECUTION_POINT_ID);
        if(point == null)
        {
            // Should not happen
            return null;
        }
        IExtension[] extensions = point.getExtensions();
        for (int i = 0; i < extensions.length; i++)
        {
            IConfigurationElement[] elements = extensions[i].getConfigurationElements();
            for (int j = 0; j < elements.length; j++)
            {
                String database_id = elements[j].getAttribute(Constants.EXTENSION_POINT_DATABASE_ID);
                String consumer_name = elements[j].getAttribute(Constants.EXTENSION_POINT_CONSUMER_NAME);
                if (cmd.getConsumerName().equals(consumer_name)
                        && (ProfileManager.getInstance().getProfileByName(cmd.getProfileName())).getProviderId()
                                .equals(database_id))
                {
                    try
                    {
                        IReExecutionRunnable reExecutionRunnable = (IReExecutionRunnable) elements[j]
                                .createExecutableExtension(Constants.EXTENSION_POINT_CLASS_NAME);
                        return reExecutionRunnable;
                    }
                    catch (Exception e)
                    {
                        return null;
                    }
                }
            }
        }
        return null;
    }
}