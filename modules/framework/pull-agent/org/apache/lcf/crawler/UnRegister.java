/* $Id$ */

/**
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements. See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License. You may obtain a copy of the License at
* 
* http://www.apache.org/licenses/LICENSE-2.0
* 
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.apache.lcf.crawler;

import java.io.*;
import org.apache.lcf.core.interfaces.*;
import org.apache.lcf.crawler.interfaces.*;
import org.apache.lcf.crawler.system.*;

public class UnRegister
{
        public static final String _rcsid = "@(#)$Id$";

        private UnRegister()
        {
        }


        public static void main(String[] args)
        {
                if (args.length != 1)
                {
                        System.err.println("Usage: UnRegister <classname>");
                        System.exit(1);
                }

                String className = args[0];

                try
                {
                        LCF.initializeEnvironment();
                        IThreadContext tc = ThreadContextFactory.make();
                        IDBInterface database = DBInterfaceFactory.make(tc,
                                LCF.getMasterDatabaseName(),
                                LCF.getMasterDatabaseUsername(),
                                LCF.getMasterDatabasePassword());
                        IConnectorManager mgr = ConnectorManagerFactory.make(tc);
                        IJobManager jobManager = JobManagerFactory.make(tc);
                        IRepositoryConnectionManager connManager = RepositoryConnectionManagerFactory.make(tc);
                        // Deregistration should be done in a transaction
                        database.beginTransaction();
                        try
                        {
                                // Find the connection names that come with this class
                                String[] connectionNames = connManager.findConnectionsForConnector(className);
                                // For each connection name, modify the jobs to note that the connector is no longer installed
                                jobManager.noteConnectorDeregistration(connectionNames);
                                // Now that all jobs have been placed into an appropriate state, actually do the deregistration itself.
                                mgr.unregisterConnector(className);
                        }
                        catch (LCFException e)
                        {
                                database.signalRollback();
                                throw e;
                        }
                        catch (Error e)
                        {
                                database.signalRollback();
                                throw e;
                        }
                        finally
                        {
                                database.endTransaction();
                        }
                        System.err.println("Successfully unregistered connector '"+className+"'");
                }
                catch (LCFException e)
                {
                        e.printStackTrace();
                        System.exit(1);
                }
        }



                
}
