set +x
set -e

Environment=$Environment
ServiceToDeploy=$ServiceToDeploy
BuildLabelToDeploy=$BuildLabelToDeploy
LocalWorkspace=${WORKSPACE}
CurrentDateTime="$(date +"%d-%m-%Y-%H-%M-%S")"

#### VARIABLES - DECLARATION SECTION ######
echo "########## The Deployment details are ........################ "
echo "The Environment to Deploy is : $Environment"
echo "The ServiceToDeploy is : $ServiceToDeploy "
echo "The BuildLabelToDeploy is: $BuildLabelToDeploy"
echo "The Local Workspace is: $LocalWorkspace"
echo "The DateTimeStamp is: $CurrentDateTime"

########## Declaring the server names by the environment ##########
DevEnvServers=("172.31.37.248")
SitEnvServers=("172.31.40.153")
UATEnvServers=("UAT1" "UAT2" "UAT3")
ProdEnvServers=("Prod1" "Prod2" "Prod3")



if [ -z "$BuildLabelToDeploy" ]
then
# IDENTIFYING THE LATEST FOLDER TO DOWNLOAD "
echo "############ Finding the latest Folder name from where we have to download the WAR Files. For Service :  $ServiceToDeploy ################ "
LatestFileName=$(/usr/local/bin/aws s3api list-objects-v2 --bucket "cintap-cloud-services-artifacts" --prefix "Builds/$ServiceToDeploy/" --query 'reverse(sort_by(Contents,&LastModified))[0].Key')
LatestFileNameArray=(${LatestFileName//// })
LatestFolderToDownload=${LatestFileNameArray[2]}
else
LatestFolderToDownload=$BuildLabelToDeploy
fi
echo "################ The Latest Folder Name from where we have to download the WAR Files is : $LatestFolderToDownload ################"


# CREATING THE DEPLOYMENT FOLDER ON JENKINS AGENT "
# Deploy the WAR File(s) from S3
LocalDeploymentDirectory="$LocalWorkspace/Deployment"
mkdir -p $LocalDeploymentDirectory
echo "Folder is created : $LocalDeploymentDirectory"


# DOWNLOADING THE FILES FROM S3 TO LOCAL DEPLOYMENT DIRECTORY
FilesToDownload="s3://cintap-cloud-services-artifacts/Builds/$ServiceToDeploy/$LatestFolderToDownload/"
echo "Starting - Downloading file(s) from $FilesToDownload to $LocalDeploymentDirectory/"
echo "Copy Command : /usr/local/bin/aws s3 cp $FilesToDownload $LocalDeploymentDirectory/"
/usr/local/bin/aws s3 cp $FilesToDownload $LocalDeploymentDirectory --recursive
echo "Completed - Downloading file(s) $FilesToDownload to $LocalDeploymentDirectory/"

echo "##################################################################"
TargetServersList=""
if [[ $Environment == "Dev" ]]
then
  echo "The Target environment is : Dev"
  TargetServersList=("${DevEnvServers[@]}")
elif [[ $Environment == "Sit" ]]
then
  echo "The Target environment is : Sit"
  TargetServersList=("${SitEnvServers[@]}")
elif [[ $Environment == "UAT" ]]
then
  echo "The Target environment is : UAT"
  TargetServersList=("${UATEnvServers[@]}")
elif [[ $Environment == "Production" ]]
then
  echo "The Target environment is : Production"
  TargetServersList=("${ProdEnvServers[@]}")
fi

TargetServersListCount=${#TargetServersList[@]}


echo "Total Count of servers to be deployed is :$TargetServersListCount "

# use for loop to read all values and indexes
for value in "${TargetServersList[@]}" ; do    #print the new array 
echo "$value" 
done   
echo "##################################################################"



########## Starting for the loop if we have to deploy to multiple servers #########
for TargetServer in "${TargetServersList[@]}" ; do

### Taking the backup
echo "### Starting - Taking the backup files ( War files ) on the server before deployment ###"
#Take_Backup=$(ssh root@$TargetServer "cp /usr/local/tomcat9/webapps/*.war /usr/local/tomcat9/DeploymentBackups/")
#scp -pv -r root@$TargetServer:/usr/local/tomcat9/webapps/*.war root@$TargetServer:/usr/local/tomcat9/DeploymentBackups/
#scp -pv -r root@$TargetServer:/usr/local/tomcat9/webapps/*.war root@$TargetServer:/usr/local/tomcat9/DeploymentBackups/
Take_Backup=$(ssh root@$TargetServer "cd /usr/local/tomcat9/webapps/;ls *.war;cp *.war /usr/local/tomcat9/DeploymentBackups/ ;")
echo $Take_Backup
echo "### Completed - Taking the backup files ( War files ) on the server before deployment ###"

### Stopping Tomcat service
echo "### Starting - Stopping the tomcat service on the remote server ###"
Shutdown_Tomcat=$(ssh root@$TargetServer "/usr/local/tomcat9/bin/shutdown.sh")
echo $Shutdown_Tomcat
sleep 10
echo "### Completed - Stopped the tomcat service on the remote server ###"

### Copying latest files to Tomcat
echo "### Starting - Copying WAR file(s) to the server : $TargetServer ###" 
scp -p -r  /var/lib/jenkins/jobs/DeployWARByService/workspace/Deployment/* root@$TargetServer:/usr/local/tomcat9/webapps/
echo "### Completed - Copying WAR file(s) to the server : $TargetServer ###" 

### Starting tomcat service
echo "### Starting - Starting the tomcat service on the remote server ###"
StartUp_Tomcat=$(ssh root@$TargetServer "/usr/local/tomcat9/bin/startup.sh")
echo $StartUp_Tomcat
sleep 60
echo "### Completed - Started the tomcat service on the remote server ###"
done


############## Staring - Health check section ##############
if [[ $ServiceToDeploy == "Bpi" ]]
then 
  HealthCheckComponentToCheck="bpi-service"
elif [[ $ServiceToDeploy == "OTC" ]]
then 
  HealthCheckComponentToCheck="otc-service"
elif [[ $ServiceToDeploy == "Partners" ]]
then 
  HealthCheckComponentToCheck="partner-service"
fi

HealthCheckURL="https://$Environment.services.cintapcloud.com/$HealthCheckComponentToCheck/actuator/health"
echo "### Health Check URL is : $HealthCheckURL"


for ((i=1;i<=5;i++)); do  
echo "Hitting the URL each time: $i"
curl --header "Connection: keep-alive" $HealthCheckURL;
sleep 10;
done

echo "Health Check, Hitting URL 1st time"
ResponseCode=$(curl $HealthCheckURL)
echo "###################### HEALTH CHECK STATUS ###########################"
echo "Response Code is : $ResponseCode"
echo "###################### HEALTH CHECK STATUS ###########################"


if [[ $ResponseCode == *"UP"* ]]
then
echo "Service is up and running"
else
echo "Service is NOT up, please check"
fi
############## Completed - Health check section ##############
