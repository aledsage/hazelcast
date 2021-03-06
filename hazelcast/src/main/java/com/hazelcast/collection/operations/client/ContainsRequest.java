/*
 * Copyright (c) 2008-2013, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.collection.operations.client;

import com.hazelcast.client.RetryableRequest;
import com.hazelcast.collection.CollectionPortableHook;
import com.hazelcast.collection.CollectionProxyId;
import com.hazelcast.collection.operations.ContainsOperation;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.Data;
import com.hazelcast.nio.serialization.PortableReader;
import com.hazelcast.nio.serialization.PortableWriter;
import com.hazelcast.spi.Operation;

import java.io.IOException;

/**
 * @author ali 5/10/13
 */
public class ContainsRequest extends CollectionKeyBasedRequest implements RetryableRequest {

    Data value;

    public ContainsRequest() {
    }

    public ContainsRequest(CollectionProxyId proxyId, Data key, Data value) {
        super(proxyId, key);
        this.value = value;
    }

    protected Operation prepareOperation() {
        return new ContainsOperation(proxyId, key, value);
    }

    @Override
    public int getClassId() {
        return CollectionPortableHook.CONTAINS;
    }

    public void writePortable(PortableWriter writer) throws IOException {
        final ObjectDataOutput out = writer.getRawDataOutput();
        value.writeData(out);
        super.writePortable(writer);
    }

    public void readPortable(PortableReader reader) throws IOException {
        final ObjectDataInput in = reader.getRawDataInput();
        value = new Data();
        value.readData(in);
        super.readPortable(reader);
    }
}
