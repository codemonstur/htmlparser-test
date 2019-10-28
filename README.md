# HtmlParser test

I'm busy writing an HTML parser.
You can find it in another repository.

This parser needs to be tested against real world HTML.
The more the better of course.

This project implements the 'Gauntlet' test tool.
Written for the sole purpose of testing the HtmlParser.

## Features

The tool manages a repository of downloaded HTML.

There are features for creating the repository. 
Downloading HTML from hostnames.
Redownloading hostnames that failed the first time.
Parsing the newly downloaded HTML.
Parsing all the HTML in the repository. 
Calculating statistics.
And some more housekeeping things.

## Using it

Check out the code from github.
Run `make build` to just create the jar.
Run `make install` to build the jar and copy it to `/usr/local/bin`.

Feel free to create aliases if necessary.

The tool has a command line interface.
Try -h for help.


