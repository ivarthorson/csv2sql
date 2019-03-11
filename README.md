# csv2sql

A simple ETL tool to load CSVs in a directory tree into a SQL database. See: [http://roboloco.net/blog/csv-etl-in-clojure/](http://roboloco.net/blog/csv-etl-in-clojure/)


## Usage

    lein uberjar  # Build the uberjar

    # Edit the following path to be the root of the CSV directory tree
    CSVDIR=/path/to/some/csvs/ java -jar target/csv2sql-0.1.0-SNAPSHOT-standalone.jar


## License

Copyright © 2019 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.

