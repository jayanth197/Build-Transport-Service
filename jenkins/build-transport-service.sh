set +x
LocalWorkspace=${WORKSPACE}
WARFilePath="$LocalWorkspace/CintapCloudTransport/cintap-transport-service/target/transport-service.war"
echo "WAR File Path is ( Source path from the server) : $WARFilePath"

foldername="Transport-$(date +"%d-%m-%Y-%H-%M-%S")"
mkdir -p "$LocalWorkspace/CintapCloudTransport/cintap-transport-service/target/$foldername"
echo "Folder is created"

echo "Starting - copying file from $WARFilePath to $LocalWorkspace/CintapCloudTransport/cintap-transport-service/target/$foldername/"
cp $WARFilePath "$LocalWorkspace/CintapCloudTransport/cintap-transport-service/target/$foldername/"
echo "Completed - copying file from $WARFilePath to $LocalWorkspace/CintapCloudTransport/cintap-transport-service/target/$foldername/"

cd "$LocalWorkspace/CintapCloudTransport/cintap-transport-service/target/"
echo "Starting - Upload folder to S3 bucket"
/usr/local/bin/aws s3 cp $foldername "s3://cintap-cloud-services-artifacts/Builds/Transport/$foldername/" --recursive
echo "Completed - Upload folder to S3 bucket"