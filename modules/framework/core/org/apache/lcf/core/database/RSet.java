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
package org.apache.lcf.core.database;

import org.apache.lcf.core.interfaces.*;
import java.util.*;

public class RSet implements IResultSet
{
        public static final String _rcsid = "@(#)$Id$";

        // Instance of a result
        protected ArrayList _rows = new ArrayList();

        public RSet()
        {
        }

        /** Add a row of data to the resultset.
        */
        public void addRow(IResultRow m)
        {
                _rows.add(m);
        }

        /** Get a specific row in the resultset.
        *@param rowNumber is the number of the row.
        *@return the immutable row description, or null if there is no such row.
        */
        public IResultRow getRow(int rowNumber)
        {
                return (IResultRow)_rows.get(rowNumber);
        }

        /** Get the number of rows in this resultset.
        *@return the number of rows the resultset contains.
        */
        public int getRowCount()
        {
                return _rows.size();
        }

        /** Get an array of all the rows.
        * This method is NOT preferred because it requires a new
        * array object to be constructed.
        *@return the array.
        */
        public IResultRow[] getRows()
        {
                IResultRow[] rval = new IResultRow[_rows.size()];
                int i = 0;
                while (i < _rows.size())
                {
                        rval[i] = (IResultRow)_rows.get(i);
                        i++;
                }
                return rval;
        }

}
