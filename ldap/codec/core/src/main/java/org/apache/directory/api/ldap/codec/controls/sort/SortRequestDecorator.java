/*
 *   Licensed to the Apache Software Foundation (ASF) under one
 *   or more contributor license agreements.  See the NOTICE file
 *   distributed with this work for additional information
 *   regarding copyright ownership.  The ASF licenses this file
 *   to you under the Apache License, Version 2.0 (the
 *   "License"); you may not use this file except in compliance
 *   with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing,
 *   software distributed under the License is distributed on an
 *   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *   KIND, either express or implied.  See the License for the
 *   specific language governing permissions and limitations
 *   under the License.
 *
 */
package org.apache.directory.api.ldap.codec.controls.sort;


import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.apache.directory.api.asn1.Asn1Object;
import org.apache.directory.api.asn1.DecoderException;
import org.apache.directory.api.asn1.EncoderException;
import org.apache.directory.api.asn1.ber.Asn1Decoder;
import org.apache.directory.api.asn1.ber.tlv.BerValue;
import org.apache.directory.api.asn1.ber.tlv.TLV;
import org.apache.directory.api.asn1.ber.tlv.UniversalTag;
import org.apache.directory.api.i18n.I18n;
import org.apache.directory.api.ldap.codec.api.ControlDecorator;
import org.apache.directory.api.ldap.codec.api.LdapApiService;
import org.apache.directory.api.ldap.model.message.controls.SortKey;
import org.apache.directory.api.ldap.model.message.controls.SortRequestControl;
import org.apache.directory.api.ldap.model.message.controls.SortRequestControlImpl;
import org.apache.directory.api.util.Strings;


/**
 * Decorator of SortRequestControl.
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
public class SortRequestDecorator extends ControlDecorator<SortRequestControl> implements SortRequestControl
{
    private Asn1Decoder decoder = new Asn1Decoder();

    private int sortReqLen = 0;

    private List<Integer> sortKeyLenList = new ArrayList<Integer>();


    /**
     * Creates a new instance of SortRequestDecorator.
     *
     * @param codec the LDAP codec
     */
    public SortRequestDecorator( LdapApiService codec )
    {
        super( codec, new SortRequestControlImpl() );
    }


    /**
     * Creates a new instance of SortRequestDecorator.
     *
     * @param codec the LDAP codec
     * @param control the control instance
     */
    public SortRequestDecorator( LdapApiService codec, SortRequestControl control )
    {
        super( codec, control );
    }


    /**
     * 
     */
    @Override
    public int computeLength()
    {
        sortReqLen = 0;
        sortKeyLenList.clear();
        valueLength = 0;

        for ( SortKey sk : getSortKeys() )
        {
            int skLen = 0;

            byte[] atBytes = Strings.getBytesUtf8( sk.getAttributeTypeDesc() );
            skLen += 1 + TLV.getNbBytes( atBytes.length ) + atBytes.length;

            if ( sk.getMatchingRuleId() != null )
            {
                byte[] mrBytes = Strings.getBytesUtf8( sk.getMatchingRuleId() );
                skLen += 1 + TLV.getNbBytes( mrBytes.length ) + mrBytes.length;
            }

            // reverse order flag
            skLen += 1 + 1 + 1;

            sortKeyLenList.add( skLen );

            // the sequence
            sortReqLen += 1 + TLV.getNbBytes( skLen ) + skLen;
        }

        valueLength = 1 + TLV.getNbBytes( sortReqLen ) + sortReqLen;

        return valueLength;
    }


    @Override
    public ByteBuffer encode( ByteBuffer buffer ) throws EncoderException
    {
        if ( buffer == null )
        {
            throw new EncoderException( I18n.err( I18n.ERR_04023 ) );
        }

        buffer.put( UniversalTag.SEQUENCE.getValue() );
        buffer.put( TLV.getBytes( sortReqLen ) );

        List<SortKey> lst = getSortKeys();

        for ( int i = 0; i < lst.size(); i++ )
        {
            SortKey sk = lst.get( i );
            int skLen = sortKeyLenList.get( i );

            buffer.put( UniversalTag.SEQUENCE.getValue() );
            buffer.put( TLV.getBytes( skLen ) );

            BerValue.encode( buffer, sk.getAttributeTypeDesc() );

            if ( sk.getMatchingRuleId() != null )
            {
                BerValue.encode( buffer, sk.getMatchingRuleId() );
            }

            BerValue.encode( buffer, sk.isReverseOrder() );
        }

        return buffer;
    }


    @Override
    public Asn1Object decode( byte[] controlBytes ) throws DecoderException
    {
        ByteBuffer buffer = ByteBuffer.wrap( controlBytes );
        SortRequestContainer container = new SortRequestContainer( getCodecService(), this );
        decoder.decode( buffer, container );
        return this;
    }


    /**
     * {@inheritDoc}
     */
    public byte[] getValue()
    {
        if ( value == null )
        {
            try
            {
                computeLength();
                ByteBuffer buffer = ByteBuffer.allocate( valueLength );

                value = encode( buffer ).array();
            }
            catch ( Exception e )
            {
                return null;
            }
        }

        return value;
    }


    @Override
    public void setSortKeys( List<SortKey> sortKeys )
    {
        getDecorated().setSortKeys( sortKeys );
    }


    @Override
    public List<SortKey> getSortKeys()
    {
        return getDecorated().getSortKeys();
    }


    @Override
    public void addSortKey( SortKey sortKey )
    {
        getDecorated().addSortKey( sortKey );
    }

}
