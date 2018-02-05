#PACKAGE_VERSION=$(xmllint --xpath '/*[local-name()="project"]/*[local-name()="version"]/text()' pom.xml)
PACKAGE_VERSION=`cat ../pom.xml | grep version | tail -1 | sed 's/[^0-9,.]*//g'`
echo $PACKAGE_VERSION
