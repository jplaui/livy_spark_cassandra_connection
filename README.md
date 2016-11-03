# Spark Cassandra Query Script

## Description
A service that outputs MIME type statistics and queries data from Cassandra into Spark structures via the spark connector.

## Output
Scala Map structure containing (value, count) pairs of all MIME types.
Example: Map(TeX -> 1, Psion -> 3, JPEG -> 278, FORTRAN -> 2, XHTML -> 1, PNG -> 206, ...)

## Usage
- clone repository
- change into project directory
- start sbt and type the **run** command
