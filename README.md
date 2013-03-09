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

Then, change to a new directory and run `mvn archetype:generate
-DarchetypeGroupId=org.apache.accumulo -DarchetypeArtifactId=instamo-archetype`
and Maven will prompt you to enter the rest of the necessary configuration
parameters (e.g. groupId, artifactId, version and Java package)
