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

Instamo Archetype
=======

About
-----------
This is a Maven Archetype which automates the customization of Instamo, a small
amount of code meant to quickly spin up Accumulo processes in memory in an
attempt to get out of the way of the developer who wants to run code against
Accumulo.

Usage
-----------
Checkout this project and run a `mvn install`. This will install the archetype
to your local repository.

Then, change to a new directory and run 

`mvn archetype:generate -DarchetypeGroupId=org.apache.accumulo -DarchetypeArtifactId=instamo-archetype -DinteractiveMode=false`

and Maven will prompt you to enter the rest of the necessary configuration
parameters (e.g. groupId, artifactId, version and Java package)
