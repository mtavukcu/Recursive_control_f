# cs0320 Term Project 2020

Recursive Find

## How to Build and Run

To load the chrome extension:
navigate to 'chrome://extensions/' in your chrome browser
tick the developer mode switch in the top right corner of this page
click on "Load Unpacked" and select the root directory of the project folder

To compile and run the backend:
navigate to the root directory of the project folder in your terminal
run 'mvn package' to compile the project and run the test suite
run the command './run' in this same directory to start the backend server

Once this backend server is running, return to the chrome browser
open up the chrome popup using either 'ctrl+shift+f' or simply by clicking the magnifying glass icon in the top right corner
At this point the project should be functional for use in most webpages

When finished:
exit the backend by returning to the running process in the terminal and using 'ctrl+c'

**NOTE: The plugin works with recursion depth 2 most quickly. This is recommended. Any depth beyond a level of 2 will be slower.

Some code references:
HTML table:
https://colorlib.com/wp/css3-table-templates/

Search bar:
https://www.gungorbudak.com/blog/2018/12/12/bootstrap-4-search-box-with-search-icon/
https://codepen.io/gungorbudak/pen/ooKNpz



**Team Members:** _Fill this in!_
Sebastian Martino
Yilmaz Kaan Sayin
Garrett Warren
Mert Tavukcuoglu
Edward Li

**Team Strengths and Weaknesses:** _Fill this in!_

Strengths (technical): sqlite, system testing
Strengths (nontechnical): communication

Weaknesses (technical): html, GUI, thorough unit testing
Weaknesses (nontechnical): tbd


**Project Idea(s):** _Fill this in with three unique ideas! (Due by March 2)_

### Idea 1: Recursive Control Find
Problem: standard control-F only searches the text on-screen. This is a hindrance when one
needs to search through the links that may be contained as well.

Solution:
This idea is to create a web browser plug-in that implements a recursive control-find feature.
If control-F is deployed by a user on a webpage, the plugin searches all of the links on that
page and returns the results. An example use would be going to the cs32/notes webpage and then
searching a word and all of the pdf note files are searched and results are returned in a
visually pleasing manner.

requirements: parsing data, storage, fast querying, caching, web scraping/crawling links
Challenges: parsing pdf files, lack of experience with plugin development

TA Approval: Approved - Lots of complexity but be sure to manage the amount of data at hand

### Idea 2: Multiplayer Fruit Ninja
Problem: current fruit ninja is anti-social

Solution:
Traditional fruit ninja with a twist. A browser-based multiplayer version of fruit ninja.

requirements: online game. user's can play against others over internet.
Extra features: Power ups
Challenges: networking, visuals (GUI)

TA Approval: Rejected - Not differentiated enough from CS15

### Idea 3: Student-Powered Lecture Capture

Problem: When a student misses lecture for whatever reason, they need a way to catch up . Lecture capture doesn't exist for all classes, and asking for notes from friends can be a pain and can be a source of disparity for students with different levels of sociability.

Solution:
A website for students to share class notes. Students can post their class notes which can be hand-written, typed, photos of the chalk board, or even video footage of the class (if allowed!). The website is sorted by course and users can like notes that they find useful. Notes with the most likes will have precedence in search results. Additionally, there will be a discussion section for each class of a given course in which students can discuss the content of that day's lecture.

Requirements/challenges: store user notes of different file types. return relevant search results by class and note quality. Discussion section where students can discuss the content of the lecture.

TA Approval: Rejected - Seems like a simple query and update app

Note: You do not need to resubmit your final project ideas.


**Mentor TA:** _Put your mentor TA's name and email here once you're assigned one!_

## Meetings
_On your first meeting with your mentor TA, you should plan dates for at least the following meetings:_

**Specs, Mockup, and Design Meeting:** _(Schedule for on or before March 13)_

**4-Way Checkpoint:** _(Schedule for on or before April 23)_

**Adversary Checkpoint:** _(Schedule for on or before April 29 once you are assigned an adversary TA)_
