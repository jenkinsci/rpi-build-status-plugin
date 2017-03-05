#Build and Deploy

To configure the development environment to build & deploy correctly, a number of steps need to be taken.

Full instructions are documented [here](https://wiki.jenkins-ci.org/display/JENKINS/Hosting+Plugins)

##GitHub

Configure github to use [SSH](https://help.github.com/articles/generating-a-new-ssh-key-and-adding-it-to-the-ssh-agent/).
This saves issues with account names and passwords when building

##Maven

###Tell Maven where the repository is

Put this in your pom.

```
<scm>
  <connection>scm:git:ssh://github.com/jenkinsci/MYPLUGINNAME.git</connection>
  <developerConnection>scm:git:ssh://git@github.com/jenkinsci/MYPLUGINNAME.git</developerConnection>
  <url>https://github.com/jenkinsci/MYPLUGINNAME</url>
</scm>

```

###Tell Maven release which credentials to use

Two sets of credentials are required to perform a release. Your github account and your jenkins-ci.org account.  A problem
arises in telling Maven which set to use where.  The GitHub account credentials is solved by use of SSH.  To specify the jenkins-ci.org account 
credentials, put the following into ~/.m2/settings.xml

```
  <servers>
    <server>
      <id>maven.jenkins-ci.org</id>
      <username>...</username>
      <password>...</password>
    </server>
  </servers>
```
###Run

To execute releaser process, use this command:

```
mvn release:prepare release:perform
```

#Useful Links

Build: [Link](https://jenkins.ci.cloudbees.com/job/plugins/job/rpi-build-status-plugin/)
Plugin Docs: [Link](https://wiki.jenkins-ci.org/display/JENKINS/Hosting+Plugins)


#A Quote From The Jenkins Plugin Docs

*However if your plugin is hosted on GitHub and you have different username and/or password for GitHub and jenkins-ci.org use of the command-line arguments for username/password will result in errors. You will have to start an ssh-agent and import your private key so the release plugin can access the GitHub repository and configure the password for jenkins-ci.org in your ~/.m2/settings.xml*







