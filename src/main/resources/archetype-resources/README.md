<!--
 Licensed to the Apache Software Foundation (ASF) under one or more
 contributor license agreements.  See the NOTICE file distributed with
 this work for additional information regarding copyright ownership.
 The ASF licenses this file to You under the Apache License, Version 2.0
 (the "License"); you may not use this file except in compliance with
 the License.  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
-->

Instamo
=======

Introduction
-----------

Instamo makes it easy to write some code and run it against a local, transient
[Accumulo](http://accumulo.apache.org) instance in minutes.  No setup or
installation is required.  This is possible if Java and Maven are already
installed by following the steps below.

```
vim src/test/java/${package}/ExampleAccumuloUnitTest.java
mvn package
```

The maven package command will run the unit test.  After packing the code, you
can also run one of the below applications.

Map Reduce
----------

It's possible to run local map reduce jobs against the MiniAccumuloCluster
instance.   There is an example of this in the src directory  The following
command will run the map reduce example.

```
mvn exec:exec -P mapreduce
```

Accumulo Shell
-----------

The Accumulo shell is a simple application that, among other features, provides
interactive access to tables in Accumulo. The following command will launch the
shell against a local Accumulo instance.

```
mvn exec:exec -P shell
```
