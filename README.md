#Jenkins LedBorg Plugin


This project is for a Jenkins plugin to control the LedBorg module for the Raspberry Pi. It uses the pi4j / wiringpi libraries to drive the GPIO pins.


##Build

1. Install Java (must be 1.7+)
1. Install Maven
1. Clone this repo
2. run 'mvn clean install'
3. Install hpi file to Jenkins


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
sudo groups jenkins gpio
sudo groups jenkins sudo
```


###Step 3

Install the plugn.


###Step 4

Configure the plugin by adding it as a Post Build Action.  This allows selection of LED colour per build state.


###Step 5 (Optional)

I have found that the jenkins UI appears to perform better using the chromium browser.  Install if required using the following steps:

```
wget -qO - http://bintray.com/user/downloadSubjectPublicKey?username=bintray | sudo apt-key add -
echo "deb http://dl.bintray.com/kusti8/chromium-rpi jessie main" | sudo tee -a /etc/apt/sources.list
sudo apt-get update
sudo apt-get install chromium-browser rpi-youtube
```


##Links:

PiBorg / LedBorg: https://www.piborg.org/ledborg

Pi4J Project: http://pi4j.com/

WiringPi Native Library: http://wiringpi.com/

Chromium: https://www.raspberrypi.org/forums/viewtopic.php?t=121195




