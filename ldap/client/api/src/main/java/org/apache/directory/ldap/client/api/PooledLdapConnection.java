/*
 *   Licensed to the Apache Software Foundation (ASF) under one
 *   or more contributor license agreements.  See the NOTICE file
 *   distributed with this work for additional information
 *   regarding copyright ownership.  The ASF licenses this file
 *   to you under the Apache License, Version 2.0 (the
 *   "License"); you may not use this file except in compliance
 *   with the License.  You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing,
 *   software distributed under the License is distributed on an
 *   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *   KIND, either express or implied.  See the License for the
 *   specific language governing permissions and limitations
 *   under the License.
 *
 */

package org.apache.directory.ldap.client.api;

import java.io.IOException;
import org.apache.directory.api.ldap.model.exception.LdapException;

/**
 * A wrapper around an LdapConnection that returns the connection to the pool when closed.
 */
public class PooledLdapConnection extends LdapConnectionWrapper
{
    /** The connection pool */
    private LdapConnectionPool pool;

    /**
     * A PooledLdapConnection instance that delegates the LdapConnection creation
     * 
     * @param delegate The instance that will create the connection under the hood
     */
    PooledLdapConnection( LdapConnection delegate )
    {
        super( delegate );
    }

    /**
     * A PooledLdapConnection instance that delegates the LdapConnection creation
     * 
     * @param delegate The instance that will create the connection under the hood
     * @param pool The underlying pool to use
     */
    PooledLdapConnection( LdapConnection delegate, LdapConnectionPool pool )
    {
        super( delegate );
        this.pool = pool;
    }

    @Override
    public void close() throws IOException
    {
        try
        {
            pool.releaseConnection( connection );
        }
        catch ( LdapException e )
        {
            throw new IOException( "Failed to release connection to pool", e );
        }
    }

    /**
     * Set the connection pool to use
     * 
     * @param pool The connection pool to use
     */
    public void setConnectionPool( LdapConnectionPool pool )
    {
        this.pool = pool;
    }
}