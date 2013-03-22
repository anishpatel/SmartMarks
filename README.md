SmartMarks
====

https://sites.google.com/a/uncc.edu/bookmarkclassifier/

Project Members
----
Anish Patel and Swapnil Thorat

Abstract
----
Our project will mine the bookmarks in Google Chrome and assign pre-determined labels to each bookmark for easier bookmark browsing and searching.

Data
----
The data set can be anyone's list of bookmarks in Google Chrome. If you have Google Chrome installed on Windows with default settings, you can find the list of bookmarks at C:\Users\<user_name>\AppData\Local\Google\Chrome\"User Data"\Default\Bookmarks, which is in JSON format. From this file, we will crawl the content of the webpage at each listed URL. Each HTML document received from the crawling will be processed into a XML document.

Initially for our project, we will use both of our own personal bookmark lists, as well as creating new instances of sample bookmark listings.

Methods
----
We will initially try to make our own decision rules for tokens to classify each document. A document may be classified under one or more labels. With this method's success, we will then try to further automate the creation of decision rules. We will also sort the documents in each label by how strong its relevance is to its label classification.

Metrics
----
We will manually assign the label(s) for each document (bookmark) and then compare and contrast this to the labels assigned by our program.

Software Dependencies
----
google-gson-2.2.2
guava-14.0.jar (not actually sure whether or not this is being use...)
