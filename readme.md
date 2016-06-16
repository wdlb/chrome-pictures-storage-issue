# Pictures memory issue on Chrome

This repository contains the code necessary to reproduce and quantify the ["Chrome stores memory that never will be
released when taking a picture"](https://bugs.chromium.org/p/chromium/issues/detail?id=607482) issue,
and is composed of 2 modules. The first is a simple webpage with a input html-field of type 'file', the second
is a Java/Appium project to automatically interact with the webpage and reproduce the mentioned issue.

## Prerequisites
You need the following things properly installed on your machine:
* [Git](http://git-scm.com/)
* [Node.js](http://nodejs.org/) (with NPM)
* [Java JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) (tested with Java 8)
* [Maven](https://maven.apache.org/)
* [Appium](http://appium.io/)
* [Android SDK Tools](https://developer.android.com/studio/releases/sdk-tools.html) (with adb)

## Installation
* `git clone https://github.com/wdlb/chrome-pictures-storage-issue`
* change into the new directory
  * change into the 'memory-issue-page' directory
  * `npm install`
* import the 'memory-issue-reproduction'-module as a maven project into your preferred IDE

## Running the project
* change into the 'memory-issue-page' directory
  * `node server.js`
* connect your mobile device to your machine
  * allow usb debugging
  * `adb devices` to see if the device is connected and recognised
  * `adb reverse tcp:8085 tcp:8085`
* start the appium server
* run one of the test methods
* as long as the bug in Chromium/Chrome is not solved, the storage used by Chrome will
go up with each picture, eventually causing the application to crash. This application
allows you to automatically reproduce this behaviour.

## Configuring a new device
Some devices have different UI layout and structure. To be able to run these tests with
other devices, you can always extend the AbstractAndroidDevice class. Inspection of the
UI and identification of the elements can be done using the 'uiautomatorviewer', contained
in the Android SDK tools.
