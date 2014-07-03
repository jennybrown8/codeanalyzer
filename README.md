codeanalyzer
============

Code Analyzer looks at a large code base (especially Java) and produces statistics about what's in it.  Its purpose is to enable
fast, easy querying of code structure relationships, size of packages and dependencies, and other relationships.

* Install and Run from Eclipse
* Install and Run from Command Line



Install and Run from Eclipse:
-----------------------------
1. Use git clone, grab the code, import into Eclipse as existing maven project.
   To create generated sources (QueryDSL), run:  mvn process-sources
   If necessary, also run:  mvn eclipse:eclipse 
2. Create a mysql database schema and user. Defaults are in db.properties.
3. Run 000-create.sql against that schema so the tables are created.
4. Choose a war file to work with. Create an empty directory somewhere, put it in there, and note the path to the directory.
5. Run "Main" giving it a command line argument of the path to the directory.
6. You should see log4j and weld initializing in the console, and then Hibernate creating 
   a PersistenceUnit and connecting to your database.
7. Then you should see the application take over.

     ...
     02 Jul 2014 19:13:14 [main] WARN  net.simsa.codeanalyzer.ApplicationMain - Beginning
     02 Jul 2014 19:13:15 [main] INFO  net.simsa.codeanalyzer.model.DebugStats - Processed 100 files.
     02 Jul 2014 19:13:15 [main] INFO  net.simsa.codeanalyzer.model.DebugStats - Processed 200 files.
     02 Jul 2014 19:13:15 [main] INFO  net.simsa.codeanalyzer.model.DebugStats - Processed 300 files.
     ...

After it completes, it will print summary statistics of the types of files it saw.

     02 Jul 2014 19:13:22 [main] INFO  net.simsa.codeanalyzer.model.DebugStats - js  28
     02 Jul 2014 19:13:22 [main] INFO  net.simsa.codeanalyzer.model.DebugStats - class   2245
     02 Jul 2014 19:13:22 [main] INFO  net.simsa.codeanalyzer.model.DebugStats - xml 18
     ...

At this point you should see persisted rows in the database and can use the queries 
in sql/sample_queries.sql (or your own custom queries) to run basic statistics.

If you re-run the application, it will delete the data in the database and start fresh.


Install and Run from Command Line:
----------------------------------
1. If not already built, build using maven:  mvn package appassembler:assemble
2. Create a mysql database schema and user. Defaults are in db.properties which ends up in the target/appassembler/conf directory.
3. Run 000-create.sql against that schema so the tables are created.
4. Choose a war file to work with. Create an empty directory somewhere, put it in there, and note the path to the directory.
5. Run the script: target/appassembler/bin/analyze  (or analyze.bat on Windows)

6. You should see log4j and weld initializing in the console, and then Hibernate creating 
   a PersistenceUnit and connecting to your database.
7. Then you should see the application take over.

     ...
     02 Jul 2014 19:13:14 [main] WARN  net.simsa.codeanalyzer.ApplicationMain - Beginning
     02 Jul 2014 19:13:15 [main] INFO  net.simsa.codeanalyzer.model.DebugStats - Processed 100 files.
     02 Jul 2014 19:13:15 [main] INFO  net.simsa.codeanalyzer.model.DebugStats - Processed 200 files.
     02 Jul 2014 19:13:15 [main] INFO  net.simsa.codeanalyzer.model.DebugStats - Processed 300 files.
     ...

After it completes, it will print summary statistics of the types of files it saw.

     02 Jul 2014 19:13:22 [main] INFO  net.simsa.codeanalyzer.model.DebugStats - js  28
     02 Jul 2014 19:13:22 [main] INFO  net.simsa.codeanalyzer.model.DebugStats - class   2245
     02 Jul 2014 19:13:22 [main] INFO  net.simsa.codeanalyzer.model.DebugStats - xml 18
     ...

At this point you should see persisted rows in the database and can use the queries 
in sql/sample_queries.sql (or your own custom queries) to run basic statistics.

If you re-run the application, it will delete the data in the database and start fresh.
