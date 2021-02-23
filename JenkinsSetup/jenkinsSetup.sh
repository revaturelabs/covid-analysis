#!/bin/sh
# Install Git
sudo yum install git -y
​
# Set Java 8 as default
sudo yum install java-1.8.0-openjdk-devel -y
sudo /usr/sbin/alternatives --set java /usr/lib/jvm/jre-1.8.0-openjdk.x86_64/bin/java
sudo /usr/sbin/alternatives --set javac /usr/lib/jvm/java-1.8.0-openjdk.x86_64/bin/javac
sudo yum remove java-1.7.0-openjdk -y
​
# Set JAVA_HOME
echo "export JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk.x86_64/" >> ~/.bashrc
source ~/.bashrc
​
# Download Maven (Thanks to https://gist.github.com/sebsto/19b99f1fa1f32cae5d00)
sudo wget http://repos.fedorapeople.org/repos/dchen/apache-maven/epel-apache-maven.repo -O /etc/yum.repos.d/epel-apache-maven.repo
sudo sed -i s/\$releasever/6/g /etc/yum.repos.d/epel-apache-maven.repo
sudo yum install -y apache-maven
​
# Set M2_HOME
echo "export M2_HOME=/usr/bin/mvn" >> ~/.bashrc
echo "export M2=$M2_HOME/bin" >> ~/.bashrc
echo "export PATH=$M2:$PATH" >> ~/.bashrc
source ~/.bashrc
​
​
# Download Tomcat 9
cd /tmp
wget https://mirrors.ocf.berkeley.edu/apache/tomcat/tomcat-9/v9.0.41/bin/apache-tomcat-9.0.41.tar.gz
tar -xf apache-tomcat-9.0.41.tar.gz
sudo mkdir /opt/tomcat
sudo mv apache-tomcat-9.0.41/ /opt/tomcat/
​
# Set CATALINE_HOME
echo "export CATALINA_HOME=/opt/tomcat/apache-tomcat-9.0.41" >> ~/.bashrc
source ~/.bashrc
​
# Start Tomcat
sh /opt/tomcat/apache-tomcat-9.0.41/bin/catalina.sh start
​
# Download Jenkins
cd /tmp
wget http://mirrors.jenkins.io/war-stable/latest/jenkins.war
​
# Deploy Jenkins
cp /tmp/jenkins.war /opt/tomcat/apache-tomcat-9.0.41/webapps/
​
# Download Docker
sudo yum install -y docker
​
# Start Docker Daemon
sudo service docker start
​
# Add User Group
sudo usermod -a -G docker ec2-user
sudo chown ec2-user:ec2-user /var/run/docker.sock
​
# Add Swap Space
sudo fallocate -l 2G /swapspace
sudo mkswap /swapspace
sudo chmod 600 /swapspace
sudo swapon /swapspace
echo "Execute 'cat /proc/swaps' to verify that the swapspace file exists"
​
# Verify Output
git version
java -version
javac -version
mvn --version
docker version