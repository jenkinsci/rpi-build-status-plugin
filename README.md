#Jenkins LedBorg Plugin

Show build state via an LED on your Pi

This project is for a Jenkins plugin to control the [LedBorg](https://www.piborg.org/ledborg) module for the Raspberry Pi. 
It uses the [pi4j](http://pi4j.com/) / [wiringpi](http://wiringpi.com/) libraries to drive the GPIO pins.


##Build

1. Install Java (must be 1.7+)
2. Install Maven
3. Clone this repo
4. run 'mvn clean install'
5. Install hpi file to Jenkins


##Configuration


###Step 1.

If not already present, install Jenkins.  I recommend using this method:
```
wget -q -O - https://jenkins-ci.org/debian/jenkins-ci.org.key | sudo apt-key add -
sudo sh -c 'echo deb http://pkg.jenkins-ci.org/debian binary/ > /etc/apt/sources.list.d/jenkins.list'
sudo apt-get update
sudo apt-get install jenkins
```

###Step 2

Once installed the Jenkins user needs various groups configuring:
```
sudo adduser jenkins gpio
```


###Step 3

Install the plugn.


###Step 4

Configure the plugin by adding it as a Post Build Action.  This allows selection of LED colour per build state.



##Links

PiBorg / LedBorg: https://www.piborg.org/ledborg

Pi4J Project: http://pi4j.com/

WiringPi Native Library: http://wiringpi.com/

Chromium: https://www.raspberrypi.org/forums/viewtopic.php?t=121195




