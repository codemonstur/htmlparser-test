
DATE=`date +'%F'`
NAME=`xmllint --xpath "//project/artifactId/text()" pom.xml`
VERSION=`xmllint --xpath "//project/version/text()" pom.xml`

clean:
	@echo "[$(NAME)] Cleaning"
	@mvn -q clean

build:
	@echo "[$(NAME)] Building"
	@mvn -q -e clean package

check-versions:
	@mvn versions:display-dependency-updates
	@mvn versions:display-plugin-updates

install:
	@echo "Compiling jar and copying to /usr/local/bin/gauntlet.jar"
	@mvn clean package
	@cp target/gauntlet.jar /usr/local/bin
	@chmod +x /usr/local/bin/gauntlet.jar

