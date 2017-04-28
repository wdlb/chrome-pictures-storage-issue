# Pictures storage issue on Chrome

This project contains the code for a simple webpage that allows you to reproduce the "Picture storage issue" on
Chrome. The webpage contains a single input html-field of type 'file'.

## Prerequisites
You need the following things properly installed on your machine:
* [Node.js](http://nodejs.org/) (with NPM)

## Installation
* change into the 'storage-issue-page' directory
* `npm install`

## Running the project
* change into the 'storage-issue-page' directory
  * `node server.js`
* visit the page with Chrome Mobile, probably on http://localhost:8085
* check the Chrome Data Storage
* click on the input field and take a picture
* check the Chrome Data Storage again, this will be higher than before
* click on the input field and take a picture
* check the Chrome Data Storage again, this will be higher than before
* ...
* as long as the bug in Chromium/Chrome is not solved, the storage used by Chrome will
go up with each picture, which makes our webapp crash.
