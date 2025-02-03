/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */
package org.apache.directory.api.ldap.codec.factory;

import org.apache.directory.api.asn1.util.Asn1Buffer;
import org.apache.directory.api.ldap.codec.api.LdapApiService;
import org.apache.directory.api.ldap.codec.api.LdapCodecConstants;
import org.apache.directory.api.ldap.model.message.Message;

/**
 * The SearchResultDone factory.
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
public final class SearchResultDoneFactory extends ResponseFactory
{
    /** The static instance */
    public static final SearchResultDoneFactory INSTANCE = new SearchResultDoneFactory();

    /**
     * A default private constructor
     */
    private SearchResultDoneFactory()
    {
        super();
    }


    /**
     * Encode the SearchResultDone message to a PDU.
     * <br>
     * SearchResultDone :
     * <pre>
     * 0x65 L1
     *  |
     *  +--&gt; LdapResult
     * </pre>
     *
     * @param codec The LdapApiService instance
     * @param buffer The buffer where to put the PDU
     * @param message the SearchResultDone to encode
     */
    @Override
    public void encodeReverse( LdapApiService codec, Asn1Buffer buffer, Message message )
    {
        encodeReverse( codec, buffer, LdapCodecConstants.SEARCH_RESULT_DONE_TAG, message );
    }
}
