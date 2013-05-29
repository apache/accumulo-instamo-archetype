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

import java.io.File;
import java.util.UUID;

import org.apache.accumulo.minicluster.MiniAccumuloCluster;
import org.apache.accumulo.server.test.continuous.ContinuousIngest;
import org.apache.accumulo.server.test.continuous.ContinuousVerify;
import org.apache.commons.io.FileUtils;

/**
 * An example of running local map reduce against MiniAccumuloCluster
 */
public class MapReduceExample {
  
  public static void run(String instanceName, String zookeepers, String rootPassword, String args[]) throws Exception {
    final String MAX_SHORT = Short.toString(Short.MAX_VALUE);
    final String MAX_LONG = Long.toString(Long.MAX_VALUE);
    final String MAX_LATENCY = Integer.toString(2 * 60 * 1000);
    final String MAX_THREADS = "3";
    
    // run continuous ingest to create data. This is not a map reduce job
    ContinuousIngest.main(new String[] {instanceName, zookeepers, "root", rootPassword, "ci", "5000000", "0", MAX_LONG,
      MAX_SHORT, MAX_SHORT, "1000000", MAX_LATENCY, MAX_THREADS, "false"});
    
    String outputDir = FileUtils.getTempDirectoryPath() + File.separator + "ci_verify" + UUID.randomUUID().toString();
    
    try {
      // run verify map reduce job locally. This jobs looks for holes in the linked list create by continuous ingest
      ContinuousVerify.main(new String[] {"-D", "mapred.job.tracker=local", "-D", "fs.default.name=file:///", instanceName, zookeepers, "root", rootPassword, "ci", outputDir, "4", "1", "false"});

    } finally {
      // delete output dir of mapreduce job
      FileUtils.deleteQuietly(new File(outputDir));
    }
  }

  public static void main(String[] args) throws Exception {
    File tmpDir = new File(FileUtils.getTempDirectory(), "macc-" + UUID.randomUUID().toString());
    
    try {
      MiniAccumuloCluster la = new MiniAccumuloCluster(tmpDir, "pass1234");
      la.start();
      
      System.out.println("${symbol_escape}n   ---- Running Mapred Against Accumulo${symbol_escape}n");
      
      run(la.getInstanceName(), la.getZooKeepers(), "pass1234", args);
      
      System.out.println("${symbol_escape}n   ---- Ran Mapred Against Accumulo${symbol_escape}n");
      
      la.stop();
    } finally {
      FileUtils.deleteQuietly(tmpDir);
    }
  }
}
