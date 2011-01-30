/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License. 
 *  
 */
package org.apache.directory.shared.ldap.codec;


import java.nio.ByteBuffer;

import org.apache.directory.shared.asn1.Asn1Object;
import org.apache.directory.shared.asn1.DecoderException;
import org.apache.directory.shared.asn1.EncoderException;


public class TestCodecControl implements ITestCodecControl
{
    TestControl decorated = new TestControl();
    
    public String getOid()
    {
        return decorated.getOid();
    }

    public boolean isCritical()
    {
        return decorated.isCritical();
    }

    public void setCritical( boolean isCritical )
    {
        decorated.setCritical( isCritical );
    }

    public ITestControl getDecorated()
    {
        return decorated;
    }

    public int computeLength()
    {
        return 0;
    }

    public ByteBuffer encode( ByteBuffer buffer ) throws EncoderException
    {
        return null;
    }

    public int getFoo()
    {
        return decorated.getFoo();
    }

    public void setFoo( int foo )
    {
        decorated.setFoo( foo );
    }

    public Asn1Object decode( byte[] controlBytes ) throws DecoderException
    {
        return null;
    }

    public boolean hasValue()
    {
        return false;
    }

    public byte[] getValue()
    {
        return null;
    }

    public void setValue( byte[] value )
    {
    }

    public ILdapCodecService getCodecService()
    {
        return null;
    }
}