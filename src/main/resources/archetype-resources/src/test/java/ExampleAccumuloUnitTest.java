#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ${package};

import java.util.Map.Entry;

import org.apache.accumulo.core.Constants;
import org.apache.accumulo.core.client.BatchWriter;
import org.apache.accumulo.core.client.BatchWriterConfig;
import org.apache.accumulo.core.client.Connector;
import org.apache.accumulo.core.client.Instance;
import org.apache.accumulo.core.client.Scanner;
import org.apache.accumulo.core.client.ZooKeeperInstance;
import org.apache.accumulo.core.client.security.tokens.PasswordToken;
import org.apache.accumulo.core.data.Key;
import org.apache.accumulo.core.data.Mutation;
import org.apache.accumulo.core.data.Value;
import org.apache.accumulo.minicluster.MiniAccumuloCluster;
import org.apache.accumulo.minicluster.MiniAccumuloConfig;
import org.apache.log4j.Logger;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * An example unit test that shows how to use MiniAccumuloCluster in a unit test
 */

public class ExampleAccumuloUnitTest {
  private static final Logger log = Logger.getLogger(ExampleAccumuloUnitTest.class);
  
  public static TemporaryFolder folder = new TemporaryFolder();
  
  private static MiniAccumuloCluster accumulo;

  @BeforeClass
  public static void setupMiniCluster() throws Exception {

    folder.create();
    
    log.debug("Using " + folder.getRoot());
    
    MiniAccumuloConfig config = new MiniAccumuloConfig(folder.getRoot(), "superSecret");
    config.setNumTservers(1);
    
    
    accumulo = new MiniAccumuloCluster(config);
    
    accumulo.start();
    
  }

  @Test(timeout = 30000)
  public void test() throws Exception {
    // edit this method to play with Accumulo

    Instance instance = new ZooKeeperInstance(accumulo.getInstanceName(), accumulo.getZooKeepers());
    
    Connector conn = instance.getConnector("root", new PasswordToken("superSecret"));
    
    conn.tableOperations().create("foo");
    
    BatchWriter bw = conn.createBatchWriter("foo", new BatchWriterConfig());
    Mutation m = new Mutation("1234");
    m.put("name", "first", "Alice");
    m.put("friend", "5678", "");
    m.put("enemy", "5555", "");
    m.put("enemy", "9999", "");
    bw.addMutation(m);

    m = new Mutation("5678");
    m.put("name", "first", "Bob");
    m.put("friend", "1234", "");
    m.put("enemy", "5555", "");
    m.put("enemy", "9999", "");
    bw.addMutation(m);

    m = new Mutation("9999");
    m.put("name", "first", "Eve");
    m.put("friend", "5555", "");
    m.put("enemy", "1234", "");
    m.put("enemy", "5678", "");
    bw.addMutation(m);

    m = new Mutation("5555");
    m.put("name", "first", "Mallory");
    m.put("friend", "9999", "");
    m.put("enemy", "1234", "");
    m.put("enemy", "5678", "");
    bw.addMutation(m);

    bw.close();
    
    Scanner scanner = conn.createScanner("foo", Constants.NO_AUTHS);
    for (Entry<Key,Value> entry : scanner) {
      log.debug(entry.getKey() + " " + entry.getValue());
    }
   
    //TODO use scanner to find common enemy ids between Alice and Bob, then
    //use BatchScanner to look up their names
 
    conn.tableOperations().delete("foo");
  }
  
  @AfterClass
  public static void tearDownMiniCluster() throws Exception {
    accumulo.stop();
    folder.delete();
  }
}
