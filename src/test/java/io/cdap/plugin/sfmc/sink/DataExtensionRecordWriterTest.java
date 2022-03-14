/*
 * Copyright © 2022 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package io.cdap.plugin.sfmc.sink;

import com.exacttarget.fuelsdk.ETClient;
import com.exacttarget.fuelsdk.ETDataExtensionColumn;
import com.exacttarget.fuelsdk.ETDataExtensionRow;
import com.exacttarget.fuelsdk.ETResponse;
import com.exacttarget.fuelsdk.ETResult;
import com.exacttarget.fuelsdk.ETSdkException;
import com.exacttarget.fuelsdk.ETSoapConnection;
import io.cdap.cdap.api.data.format.StructuredRecord;
import io.cdap.cdap.etl.api.validation.ValidationException;
import io.cdap.plugin.sfmc.source.MarketingCloudClient;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DataExtensionRecordWriterTest {

  protected static final String CLIENT_ID = "clientId";
  protected static final String CLIENT_SECRET = "clientSecret";
  protected static final String AUTH_ENDPOINT = "authEndPoint";
  protected static final String SOAP_ENDPOINT = "soapEndPoint";


  @Test
  public void testWriteWInsertWNullResponse() throws ETSdkException, IOException {
    NullWritable key = null;
    StructuredRecord record = Mockito.mock(StructuredRecord.class);
    TaskAttemptContext context = Mockito.mock(TaskAttemptContext.class);
    List<ETDataExtensionRow> batch = new ArrayList<>();
    ETDataExtensionRow row1 = new ETDataExtensionRow();
    row1.setId("121");
    row1.setDataExtensionKey("DE");
    ETDataExtensionRow row2 = new ETDataExtensionRow();
    row1.setId("122");
    batch.add(row1);
    batch.add(row2);
    batch.size();
    List<ETDataExtensionColumn> columnList = new ArrayList<>();
    ETDataExtensionColumn column = new ETDataExtensionColumn();
    column.setLength(123);
    column.setName("Name");
    columnList.add(column);
    DataExtensionClient dataExtensionClient = Mockito.mock(DataExtensionClient.class);
    DataExtensionInfo info = new DataExtensionInfo("externalKey", columnList);
    RecordDataExtensionRowConverter recordDataExtensionRowConverter = new RecordDataExtensionRowConverter(info, true);
    DataExtensionRecordWriter dataExtensionRecordWriter = new DataExtensionRecordWriter(dataExtensionClient,
      recordDataExtensionRowConverter, Operation.INSERT, 1, false);
    try {
      dataExtensionRecordWriter.write(key, record);
    } catch (ValidationException e) {
      Assert.assertEquals("Failed to get a response from {} call to Salesforce API.",
        e.getFailures().get(0).getMessage());
    }
  }



  @Test
  public void testWriteWUpsert() throws ETSdkException, IOException {
    TaskAttemptContext context = Mockito.mock(TaskAttemptContext.class);
    List<ETDataExtensionColumn> columnList = new ArrayList<>();
    ETDataExtensionColumn column = new ETDataExtensionColumn();
    column.setLength(123);
    column.setName("Name");
    columnList.add(column);
    DataExtensionClient dataExtensionClient = Mockito.mock(DataExtensionClient.class);
    DataExtensionInfo info = new DataExtensionInfo("externalKey", columnList);
    RecordDataExtensionRowConverter recordDataExtensionRowConverter = new RecordDataExtensionRowConverter(info, true);
    DataExtensionRecordWriter dataExtensionRecordWriter = new DataExtensionRecordWriter(dataExtensionClient,
      recordDataExtensionRowConverter, Operation.UPSERT, 1, false);
    dataExtensionRecordWriter.close(context);
  }

  @Test
  public void testWriteWInsert() throws ETSdkException, IOException {
    TaskAttemptContext context = Mockito.mock(TaskAttemptContext.class);
    List<ETDataExtensionColumn> columnList = new ArrayList<>();
    ETDataExtensionColumn column = new ETDataExtensionColumn();
    column.setLength(123);
    column.setName("Name");
    DataExtensionClient client = Mockito.mock(DataExtensionClient.class);
    DataExtensionInfo info = new DataExtensionInfo("externalKey", columnList);
    List<ETDataExtensionRow> batch = new ArrayList<>();
    ETResponse<ETDataExtensionRow> response = new ETResponse<>();
    response.setRequestId("id");
    response.setStatus(ETResult.Status.ERROR);
   Mockito.when(client.insert(batch)).thenReturn(response);
    RecordDataExtensionRowConverter recordDataExtensionRowConverter = new RecordDataExtensionRowConverter(info, false);
    DataExtensionRecordWriter dataExtensionRecordWriter = new DataExtensionRecordWriter(client,
      recordDataExtensionRowConverter, Operation.INSERT, 500, false);
    dataExtensionRecordWriter.close(context);
  }

  @Test
  public void testWriteWUpdate() throws ETSdkException, IOException {
    StructuredRecord record = Mockito.mock(StructuredRecord.class);
    TaskAttemptContext context = Mockito.mock(TaskAttemptContext.class);
    List<ETDataExtensionColumn> columnList = new ArrayList<>();
    ETDataExtensionColumn column = new ETDataExtensionColumn();
    column.setLength(123);
    column.setName("Name");
    columnList.add(column);
    DataExtensionClient client = Mockito.mock(DataExtensionClient.class);
    DataExtensionInfo info = new DataExtensionInfo("externalKey", columnList);
    ETResponse<ETDataExtensionRow> response = new ETResponse<>();
    response.setRequestId("id");
    List<ETDataExtensionRow> batch = new ArrayList<>();
    Mockito.when(client.update(batch)).thenReturn(response);
    RecordDataExtensionRowConverter recordDataExtensionRowConverter = new RecordDataExtensionRowConverter(info, false);
    DataExtensionRecordWriter dataExtensionRecordWriter = new DataExtensionRecordWriter(client,
      recordDataExtensionRowConverter, Operation.UPDATE, 500, false);
    dataExtensionRecordWriter.close(context);
  }

}