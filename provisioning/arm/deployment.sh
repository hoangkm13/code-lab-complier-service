resourceGroupName="remote-code-compiler";
AcrName="remotecodecompileracr"; # Connected registry name must use only lowercase, and follow this pattern '^[a-zA-Z0-9]*$'.
AksClusterName="remote-code-compiler-aks";
sshKey="ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAACAQDW9QZuDjsB7EXVMiE1zfzj00Hdh7GwILDEZj7uMkSCBIHkAbT32g4wm0Jpyt+YfWRs0qCYpOC84xmzEUBC4di7pDyPzAiTWtB0/OFEo4E1jT98pXoDlJ6nIhBN0nu0LsQx6noYUBfI+fjNtVyBz5DA65q569WcGizfnAzNssTu5edW8KkM2622yVuwkxLTe0JbwhOIkcY2b86T6VFodD4+6IEZlGIWWK65MAY3EnqnJEdYwB8HcQ3yXfCeMElFOwOaJI39VoJf34X8CQZUUhVrIjbbLjfFebvUG2uFdlN5zWSuHTNqRWy/BIvEq5T958ZUbZyiRnvnNZzKvLlCE7nnxkp0R5wzNHqjF3nlaaZ2WGgup+X4TOPofO08Z4hdaTWLaZUr6OI6gacQk3FbN7wrbgnZQzqTEzQM+YdgcahYN2uKMP8wPfDRo+0DlWnUwuGHd5+rO4kqKaYkjW9/WoudkiCxDtq9sjmLKHQAjckCta7Ela5UmO+M4w+n/vjgGAEL1yc0aqsRJ+TdjJK17NL3vxqIJIMbEqY3uyu7ZpNUTwSnwQQtPth9rx6Spjtd3n3x/rtjeak37iXlG6g2/xv0dcCCCNyjP/r4bKVteBm64Ab9DCmopz/gKdwZqWbKFl2SlY/gaW6DoRNgS0/faPKd7Ywtx0mNpC1IMOxSvTx+0Q== hoang.workingemail@gmail.com"
principalClientId="3a87737f-9033-4918-8d10-cb9b8cfc49b0"
principalClientSecret="fYe8Q~rCYo5BJXMRmhilGVa8HffNqQUl_V7rwa7Q"
nbVmNodes=1
vmSize="Standard_A2_v2"

# Create resource group
az group create -l southeastasia --resource-group $resourceGroupName;

# Provision Aks Cluster using ARM template
az deployment group create --resource-group $resourceGroupName --template-file ./aks.json --parameters \
clusterName=$AksClusterName \
publicSshKey="$sshKey" \
principalClientId=$principalClientId \
principalClientSecret=$principalClientSecret \
nbVmNodes=$nbVmNodes \
vmSize=$vmSize

# Run the following line to create an Azure Container Registry if you do not already have one
az acr create -n $AcrName -g $resourceGroupName --sku basic

# Configure ACR integration for the AKS cluster
az aks update -n $AksClusterName -g $resourceGroupName --attach-acr $AcrName

