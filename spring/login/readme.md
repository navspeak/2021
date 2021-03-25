#### How to run on EC2 (there are better ways, but this works!)

* Create EC2 instance adding followng bash script in user data. Add a security group with following settings:
  * Allow SSH from your IP (just in case you want to log into the EC2 for troubleshooting)
  * Allow port 8080 from everywhere (8080 is the tomcat port being used in this app)
  
> **_NOTE:_** Following is quite a verbose script. It's purpose: 
(1) pull the latest code from github (2) builds the sprinboot app (3) starts it as a service. This way when the EC2 instance is up, no manual step is needed (unless Murphy's law prevail)

```shell script
#!/bin/bash
echo NAVNEET START
sudo su
yum update -y
wget https://downloads.apache.org/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz
tar xvf apache-maven-3.6.3-bin.tar.gz
mv apache-maven-3.6.3 /usr/local/apache-maven
rm apache-maven-3.6.3-bin.tar.gz
yum install -y java-11-amazon-corretto
yum install -y git
echo ================================
echo NAVNEET maven java git done
echo ================================
cd /usr/local
echo NAVNEET downloading login springboot app from github...
mkdir login
cd login
echo NAVNEET we are in $PWD
git init
git remote add origin https://github.com/navspeak/2021.git
git config core.sparseCheckout true
echo "/spring/login" > .git/info/sparse-checkout
git pull origin master
cd /usr/local/login/spring/login
echo ================================
echo NAVNEET we are now in $PWD
echo ================================
/usr/local/apache-maven/bin/mvn clean package spring-boot:repackage
echo ================================
echo NAVNEET Installation done
echo ================================
chown ec2-user:ec2-user /usr/local/login/spring/login/target/login-0.0.1-SNAPSHOT.jar
chmod 500 /usr/local/login/spring/login/target/login-0.0.1-SNAPSHOT.jar
cp login-dev.service /etc/systemd/system/login-dev.service
systemctl daemon-reload
systemctl enable login-dev.service
systemctl start login-dev
systemctl status login-dev -l
#echo ================================
#echo NAVNEET started the app as service
#echo ================================
``` 
* If the user-data is run without any errors, the application will start at port 8080 (which is default server port)
> **_NOTE:_**  If you use server.port = 80, you will run into permission issues when starting the app as service (manually will work)
* Command to manually start the app
```shell script
java -Dspring.profiles.active=dev -jar /usr/local/spring/login/target/login-0.0.1-SNAPSHOT.jar
```
* ssh to the ec2 instance
* From a browser hit: http://aws-public-ip:8080 to get the app
* http://aws-public-ip:8080/h2 will bring up h2 console to browse the database (in this example we use in memory h2)
  - user = sa | password = empty | jdbc URL = jdbc:h2:mem:login

* Systemctl commands
```shell script
systemctl stop login-dev.service #stop the service
journalctl -u login-dev -e #view logs
```

Read this: [running-spring-boot-applications-as-system-services-on-linux](https://medium.com/@manjiki/running-spring-boot-applications-as-system-services-on-linux-5ea5f148c39a)
