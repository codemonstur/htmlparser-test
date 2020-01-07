
.PHONY: help clean build check-versions install

DATE=`date +'%F'`
NAME=`xmllint --xpath "//project/artifactId/text()" pom.xml`
VERSION=`xmllint --xpath "//project/version/text()" pom.xml`

help:
	@echo "Available targets for $(NAME):"
	@echo "\thelp\t\t\tThis help"
	@echo "\tclean\t\t\tDelete everything in ./target"
	@echo "\tbuild\t\t\tCleans the project and rebuilds the code"
	@echo "\tcheck-versions\t\tCheck if the versions of dependencies are up to date"
	@echo "\tinstall\t\t\tClean, build and install the CLI to a local dir"

clean:
	@echo "[$(NAME)] Cleaning"
	@mvn -Dorg.slf4j.simpleLogger.defaultLogLevel=warn clean

build:
	@echo "[$(NAME)] Building"
	@mvn -Dorg.slf4j.simpleLogger.defaultLogLevel=warn -DskipTests=true clean package

check-versions:
	@mvn versions:display-dependency-updates
	@mvn versions:display-plugin-updates

install:
	@echo "Compiling jar and copying to /usr/local/bin/$(NAME).jar"
	@mvn clean package
	@sudo cp target/$(NAME).jar /usr/local/bin/
	@sudo chmod +x /usr/local/bin/$(NAME).jar
