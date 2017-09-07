# SCOIR Technical Interview for Back-End Engineers
This repo contains an exercise intended for Back-End Engineers.

## Instructions
1. Fork this repo.
1. Using technology of your choice, complete [the assignment](./Assignment.md).
1. Update this README with
    * a `How-To` section containing any instructions needed to execute your program.
    * an `Assumptions` section containing documentation on any assumptions made while interpreting the requirements.
1. Before the deadline, submit a pull request with your solution.

## Expectations
1. Please take no more than 8 hours to work on this exercise. Complete as much as possible and then submit your solution.
1. This exercise is meant to showcase how you work. With consideration to the time limit, do your best to treat it like a production system.


## How-To
1. From the top level directory build and assemble the converter by running ```mvn package```. 
    * This will create ```target/csvtojson.jar``` and populate ```target/libs```.
1. Run the converter from the top level directory with ```sh csvtojson.jar ```
   * The converter will run with default source, destination and error directories, ```/tmp/source```, ```/tmp/dest``` and ```/tmp/error``` respectively.
   * Defaults can be overridden by setting the environment variables ```SOURCE_DIR```, ```DEST_DIR``` OR ```ERROR_DIR``` before calling ```sh csvtojson.sh```
     * Example:
     ```
     $ setenv SOURCE_DIR=/tmp/alternatesource
     $ sh csvtojson.sh
     ```
     
### Assumptions
1. Embedded commas in CSV fields are encoded by double quoting the field contents.  For example:
    ```
    FIELD1, FIELD2
    Value,"Value,With,Commas"
    ```   
1. There are no particular performance or concurrency goals.        

### Caveats
The converter wasn't functional after eight hours, so I kept working.   This is about 20 hours work.  I learned an awful lot about CSV and JSON converters in java along the way.

If you want to see where I was after eight hours, look for the tag ```After8Hours```.

There's more I'd like to do, but I have to honor the time limit at some point.
  
