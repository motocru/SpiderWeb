This project uses the jsoup library to crawl through a given root url by finding anchor tags inside of the web page.
Once all href tags are found, it will crawl to the next given link found and collect the tags on that page.
A limit of collectable tags between 1 and 10000 will be specified by the user as well as a timeout limit of between 1 and 10000 miliseconds.

The program takes 3 arguments inside the command line that will be used in the program.

Arg #1: The root url that will be used to start the crawl
Arg #2: The max number of reachable anchor tags N to be collected as specified by the user
Arg #3: The timeout limit in miliseconds as specified by the user

Reachable urls are stored as a custome object "result" and then printed in ascending alphaetical order.
any queries in the url are tabbed underneath the url they were found in and are also in ascending alphabetical order.

The program will exit if args 2 and 3 are not numbers and will not work properly if a non proper url is given.

This progam was made for a web applications class
   - as per the specifications of the profressor, The program contains no recursion.  
