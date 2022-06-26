# HDFS-File-Viewer

Provides an interface to visualize Hadoop file formats such as Parquet, Avro, and Orc in table. It is built upon [Data Pipeline](https://northconcepts.com/) to read
these files. You can also convert these file formats into JSON and CSV files.

It allows you to capture a snapshot of the table and export it as a PNG image.

## Tech Stacks
  - [JavaFX](https://openjfx.io/) for UI.
  - [Gradle](https://gradle.org/) for dependecy management.
  - [Data Pipeline](https://northconcepts.com/) for reading HDFS files.

## Setup
  - Install [JDK](https://www.oracle.com/java/technologies/downloads/) and [Gradle](https://gradle.org/install/) if you don't already have them.
  - Clone this repository in your machine.
  - Get your [Data Pipeline license key](https://northconcepts.com/pricing/) and place NorthConcepts-DataPipeline.license file into the `HDFS-File-Viewer/src/main/resources`
    folder.
  - Open this project in your favorite IDE and run it as any Java program.

Note: This app might not visualize complex types such as arrays and records as expected.
    

