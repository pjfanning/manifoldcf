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
package org.apache.lcf.crawler.connectors.meridio;

import org.apache.lcf.core.interfaces.*;
import org.apache.lcf.crawler.connectors.meridio.meridiowrapper.MeridioTestWrapper;

public class UpdateDoc
{
        public static final String _rcsid = "@(#)$Id$";

        private UpdateDoc()
        {
        }


        public static void main(String[] args)
        {
                if (args.length != 8)
                {
                        System.err.println("Usage: UpdateDoc <docurl> <recurl> <username> <password> <docid> <filepath> <filename> <comment>");
                        System.exit(1);
                }

                try
                {
                        MeridioTestWrapper handle = new MeridioTestWrapper(args[0],args[1],args[2],args[3]);
                        try
                        {
                                int docId = Integer.parseInt(args[4]);
                                handle.lockDocument(docId,args[7]);
                                handle.addDocumentVersion(docId,args[5],args[6],args[7]);
                                handle.unlockDocument(docId);
                        }
                        finally
                        {
                                handle.logout();
                        }
                        System.err.println("Successfully updated");
                }
                catch (Exception e)
                {
                        e.printStackTrace(System.err);
                        System.exit(2);
                }
        }

}
