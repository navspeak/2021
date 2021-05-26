# Serverless (page 361 - 367)
* Lambda responds to events
* Free tier 1,000,000 AWS Lambda requests and 400,000 GB-sec of compute time
    * pay per duration (in increment of 100 ms)
    * 1 GB RAM == 400,000 seconds | 128 MB = 1 GB / 8 == 3,200,000 seconds 
    * RAM for lambda 128 MB to 10GB (old was 3 GB)
* Serverless CRON Job: Cloudwatch Event bridge - triggers periodically -> AWS Lambda
* Role for lambda to upload logs to cloudwatch attached as execution Role
``` 
{
   "Statement" : [
      {
        "Effect": "Allow",
 	    "Action": "logs:CreateLogGroup",
        "Resource": "arn:aws:logs-eu-west-2:3679980:*"
      },
      {
       "Effect": "Allow",
 	   "Action": [ "logs:CreateLogStream", "logs:PutLogEvents" ]
       "Resource": [ "arn:aws:logs-eu-west-2:3679980:*log-group:/aws/lambda/hello-world:*" ]
      }    
}
```
* Limit per region: 
    * Memory: 128 MB to 10 GB(64 MB increments)
    * 15 mins execution time | Env var: 4 KB | /tmp - function container - 512 MB | 
    Concurrent exec: 1000 (can be increased)
    * use /tmp directory if deployment size (compressed) > 50 MB & 250 MB (code + deployment)
* Lambda@Edge - alongside CloudFront
    * Can change viewer request/response, origin request/response
    * use case: Search engine optimization, intelligently route acroos origin & data centers, A/B Testing
* Dynamo DB
    * Highly available w/replication across 3 AZ
    * Records == items, item size <= 400 kb
    * Dynamo DB tables, primary keys - https://aws.amazon.com/blogs/database/choosing-the-right-dynamodb-partition-key/
    * if your access pattern  exceeds 3000 RCU or 1000 WCU for a single partition key value, 
    your requests might be throttled with a ProvisionedThroughputExceededException error.
     Thruput can be exceeded with burst credit. When burst credit is empty - proivisionedThruputException
    * Recommendations for partition keys: Use high-cardinality attributes (distinct values), use composite attributes
    * On Demand (no throttling) or Provisioned Capacity /Thruput (can autoscale): https://tutorialsdojo.com/calculating-the-required-read-and-write-capacity-unit-for-your-dynamodb-table/
        * 1 RCU = 1 strongly consistent read of 4 KB/s, 2 Eventually consistent read of 4 KB/s
        * 1 WCU = 1 write of 1 KB, 2WCU = 1 transactional write
* DAX 
* DynamoDB Streams - needed for cross region replication  of dynmodb. has 24 hrs of data retention
* supports transactions. u can get dynamodb endpoints(to access dynamodb without internet)
* Global Tables - DR purpose. Active / Active replication. __Needs Dynamodb streams enabled__
*API Gateway:
    * AWS Lambda + API gateway => Serverless REST service. no infra to manage. can cache API response
    * API gateway can expose any AWS service. e.g. start an AWS step function workflow, post a message to SQS
    * API Gateway - Endpoint Types - Edge Optimized, regional or private (access only from VPC using interface VPC endpoint ENI)
* Lab API Gateway- create IAM role for lambda. This role has permission to upload to cloudwatch
  * HTTP, Websocket, REST, REST Private
  * REST: Endpoint type: Regional, Edge Optimized or Private 
  * Integration Type: Lambda Function, HTTP, Mock, AWS Service, VPC Link
  * Default Timeout 29 seconds
  * Use __Lambda Proxy Integration__ (check box) - There is no integration response in this case as we can't modify request and response from Lambda. In this case Lambda will have to send request body, hearers etc by itself
  * In lambda function go to permissions see that there is a "Resource Based Policy"
  [Policy](../images/resourcebasedpolicy-api-gatewat.PNG)
  * In lambda function if you print the event, you can go to monitoring and see the events in cloudwatch logs
  * Deploy API gateway: New Stage dev, prod etc to get an endpoint. 
       You can Enable Throttling (default 10000 request per sends with burst of 5000 requests)
     * Web Application Firewall WAF setting can also be applied.
 * API Gateway Security Mechanism :
      * IAM Permissions - leverages sig v4 (for users in ur IAM users. ) https://www.youtube.com/watch?v=KXyATZctkmQ
      * Lambda Authorizer (uses Lambda to validate token passed in header. __Can cache result of Authn__. Lambda must return an IAM policy for the user)
        - Helps with 3rd party OAuth / SAML type use case, lambda returns IAM policy for user
      * Cognito User Pools - No custom impl needed. However only authn not authz. Use headers like Authorization. Backed by facebook etc
        * Use Cognito Identity Pool: https://www.youtube.com/watch?v=0dVL70Ayq5I
 * Cognito (page 454)
    * Cognito User Pool: serverless db of username+password. Returns JWT. Integrates w/ API Gateway
    * Cognito Federated Identity Pool: use case: say u want to grant AWS resources access using FB. Can remain anonymous. Returns temp AWS credentials with IAM policy
    * CognitoSync (deprecated - use AppSync. Don't confuse with DataSync) - Device sync
 * SAM - YAML. Quickly deploy (lambda, dynamo db, API Gateway ) using codeDeploy even locally 
 * Amazon API Gateway tracks the number of requests per second. Any requests over the limit will receive a 429 HTTP response.
  The client SDKs generated by Amazon API Gateway retry calls automatically when met with this response.
 * https://tutorialsdojo.com/amazon-api-gateway/?src=udemy
 
 # IAM (pg465)
 * STS - Assume Role - Global Service - Programatic only. returns AccessID | SecretAccessKey | SessionToken | Expiration (15 min to 1 hour)
 * Federation in AWS:
   1. SAML 2.0 Federation:
      * Integrate w/ AD or ADFS with AWS. Provide access to AWS Console or CLI (thru temp cred)
      * AssumeRoleWithSAML. Set up trust b/w AWS IAM & SAML. 
      * Old way - Amazon SSO new and simpler way
   2. Custom Identity Broker - if the iDP is not compatible ith SAML 2.0.
      * AssumeRole or GetFederationToken
   3. Web Identity Federation w/ or w/o Amazon Cognito
      * AssumeRoleWithWebIdentity. Not recommended. Use _AWS Cognito_ instead (using Cognito Federated Identity to get temp credentials from STS)
   5. SSO
   6. Non-SAML w/ AWS Microsoft AD
 * AWS Directory Services
    1. AWS Managed Microsoft AD -> users shared b/w on prem AD and AWS Managed AD. Trust relationship with on-prem. Supports MFA.
    2. AD Connector - Directory Gateway (proxy) to redirect to on prem AD -> users are on on-prem AD. AD Connector proxies request to On-prem AD
    3. Simple AD (Linux Simba AD compatible) - AD-compatible managed directory on AWS. Can't be joined with on-prem AD. Use case no on-prem AD. Standalone. Directory closer to say EC2 on simple AD.
    4. Amazon Cognito User Pools - May not belong here. But in AWS if you go to Directory service it lists there
 * AWS Organization: Global Service. Consolidated Billing.
    * Read Multi Account Strategy
    * Service Control Policy (SCP - pg 487) - applied at OU or account level. Whitelist or blacklist IAM actions. Use cases: Restrict access to certain services (eg, can't use EMR), enforce PCI compliance by explicitly disabling services
    * Lab: Create OU in tree form. Add users to diff OUs. Enable SCPs on OU or Account - like DenyAthena
 * IAM Conditions - like deny certain IPs. Restrict the region. Restrict based on tags. Force MFA
 * IAM for S3 => arn:aws:s3:::test - ListBucket (bucket level). arn:aws:s3:::test/* - GetObject, PutObject (Object level)
 * IAM Roles (like assume role. Here u give up ur orignial permissions) vs Resource Based Policies (eg: User in ac-A needs to scan DynomoDB in account A + dump it in S# bucket in Account B)
   * Resource Based Policy supported by S3, SNS and SQS
 * IAM permission Boundries - supported by users and roles (not groups) : Can be used in conjuction with AWS Organization SCP
    * Effective Permissions: Venn diagram of SCP + Permission Boundry + Identity Based Policy
    * Read IAM Policy Evaluation Logic (page 497 b- Ans No for all. If no explicit allow then not allowed)
 * __Resource Access Manager (RAM)__
    * Share AWS resource with any account or withing an org. Resource duplication is avoided.
    * Can share: e.g. VPC Subnets (must be in same AWS org, can't share SG or default VPC) Network is shared, but other accounts won't see resources by other account. Can access using Private IP
  * AWS SSO
    * Integrated with AWS Organizations. Support SAML 2.0, integration with on-prem AD. 
    * Centralized audit with CloudTrail and centralized permission management (page)
    * Compare with AssumeRoleWithSAML
 * Cognito: (https://www.youtube.com/watch?v=g2s_hJRI70U)
  * Cognito User Pool ~ user directory, allows users to authenticate using OAuth to IDp like FB, Google. CUP is also an IdP
  * Cognito Identity Pool - Provides temporary AWS credentials
  * Cognito Sync  (deprecated)  Use AppSync instead - uses FIP
  
  # AWS Security & Encryption
  * KMS
    * Never get access to the unencypted key (must call KMS API to use)
    * Customer Master Key - CMK : 1. Symmetric (AES-256 Keys) 2. Asymmetric (RSA & ECC Key Pair) - encryption outside of AWS by users who can't call KMS API
    * KMS audit trail - CloudTrail
    * 3 Types of CMK 
        - AWS Managed Service Default (free) | 
        - User Keys created in KMS $1/ months | 
        - User Keys imported $1 /month => + pay for API calls to KMS
    * CMK used to encrypt data can never be retrieved. CMK is rotated for extra security
    * __KMS can only encrypt 4 KB of data per call ( > 4 KB => use envelope encryption) __
    * Key Policies - default (give access to the key to root user)  vs custom (with IAM policies. can allow other account too)  - read
    * bound to a region
    * How do u copy snapshots accross accounts with custom Key 
    [click](../images/CMK-cross-ac.PNG)
    * AWS Managed keys - aws/XYZ | Customer Managed Key | Custom Key stores (out of scope)
    * Customer Managed Key : Lab: Specify alias | Type Symmetric or Asymmetric. Key Origin: KMS, External or Custom Key Store (cloudHSM) => specfy Key Policy 
```json5
# default key policy
{ "Id" : "Key-consolepolicy-3", "version": "2012-10-17", "Statement": [ { "sid" : "Enable IAM User Permissions", "Effect" : "Allow",
  "Prinicipal": { "AWS": "arn:aws:iam:12348899:root" }, "Action" : "kms:*", "Resource": "*"}]}
- aws kms encrypt --key alias/myAliasKey --plaintext fileb://secret.txt --output text --query CiphertextBlob --region eu-west-2 > enct.base64
- decode base64 to binary format
- aws kms decrypt base64
```
* Key rotation for Customer Managed CMK (not AWS managed CMK) - happens every 1 year. Prev key kept active for old data
* Manual Key rotation - 90, 180 days etc. Keep same alias. CMK ID is different. Use key alias. Read KMS Alias Updating
* __SSM Parameter Store__: 
    * AWS Systems Manager was formerly known as Amazon Simple Systems Manager (SSM) and Amazon EC2 Systems Manager (SSM). 
    * Store secrets - encrypt using KMS. Read PDF - pg 515. Std & Advanced (option for Parameter Policy)
    * Lab: Systems Mananger > Craete Parameter > Name /my-app/dev/db-url - string, list or secure list (use KMS Key ID - aws provided or your own from current or another acc). Versioned
      * CLI to retrieve: aws ssm get-parameters --names /my-app/dev/db-url /my-app/dev/db-password --with-decryption  (get-parameters-by-path)
      * Lambda to retrieve: import boto3, ssm = boto.client ('ssm'. region-name="eu-west-3")... python. Also need to add IAM permisssion GetParameters and GetParameterByPath to Lambda role. Also add Kms:decrypt to decrypt if using KMS to decrypt
* __AWS Secrets Manager__: Newer, capability to force rotation of secrets every X days using a Lambda. 
    * Lab - create secret for either RDS/Redshift/API Key integration
* __Cloud HSM__ 
    * Gives hardware, you manage ur encryption keys entirely. Multi AZ. SSE-C is good option. AWS can't recover the keys
    * CloudHSM Client software to use
    * Redshift supports CloudHSM for database ency & key mgmt
    * You should consider using AWS CloudHSM over AWS KMS if you require your keys stored in dedicated, 
      third-party validated hardware security modules under your exclusive control.
* __Shield - DDoS protection__ : Std (layer 3/layer 4 SYN/UDP flood attacks) | Advanced - waives price dure to DDoS usage spikes
    * Protect Route 53. Also CloudFront, ALB, Ec2, Global Accelerator 
* __Web Application Firewall (WAF)__ :
    * Layer 7 exploits. Can be deployed __only__ on 
      1. ALB (regional resources)  not NLB
      2. API Gateway (regional resources) 
      3. CloudFront Distributions.
    * Define Web ACL (rules for IP addresses, headers, URI strings etc). __XSS__, sql injection. 
       __Geo match (block countries)__ , size constraints
      * Managed Rules or your own
      * Rate based rules - for DDoS protection
* __AWS Firewall Manager__: manages shield policy, WAF & security group in all accounts of an AWS org
* __Guard Duty__ : ML to detect threat. __Crypto Currency attack__. Analyze VPC flow Logs, Cloud Trail Logs, DNS logs
                  : S3 monitor also. Disable (relinquish old logs) & stop (temp): https://aws.amazon.com/guardduty/faqs/
* __Inspector__: Automated security assressments from EC2 instances. Known OS vulnerbaility, unintended n/w accessibility. 
Inspector Agent installled on OS of EC2
* __Macie__ :ML to identify and detect sensitive and PII data
* Shared Responsibilty Model

# Networking
 * Max CIDR per VPC = 5
 * VPC only gets Private IP range i.e. 10.0.0.0/8, 172.16.0.0/12 and 192.168.0.0/16 - /16 biggest cidr block
 * Lab: VPC - Description, CIDR Blocks (add more - upto 5), Flow Logs tabs. Comes with __Main Route table & main Network ACL (NACL)__
    * Create Public (usually smaler, put LB) and Private Subnet. 1 AZ <=> 1 or more subnet. In Public subnets check "Enable auto-assign public ipv4"
    * __Internet Gateway__ : Created separately from VPC, highly available. Needed  to edit __Route Table__ for internet (needed for ssh or RDP too). 
    Attached to 1 VPC only. Also a NAT for instances that have apiblic IPV4
    * An Internet Gateway serves two purposes: to provide a target in your VPC route tables for internet-routable traffic and to perform network address translation (NAT) for instances that have been assigned public IPv4 addresses. 
    Therefore, for instance E1, the Network Address Translation is done by Internet Gateway I1.
    * Create __Route Table__. Best Practice not to use Main Route table but at least two separate Route table - one for public and another for private. __Make subnet associations in the Route Table__.
      * In Public Route Table go to "Routes" tab and "Edit Routes":
      
      |Destination | Target| Comments |
      |---:|---:|---|
      |10.0.0.16 |Local| good for private |
      |0.0.0.0/0|igw-12333 | internet access. Go to subnet association tab in route table. see public subnets associated here |
 
 * NAT instances - old. new is NAT Gateway. Must disable EC2 flag source/dest check. Launched in public subnet. Must have EIP. Is a EC2 with NAT type.
    * SG: allow SSH from everywhere. HTTP & HTTPS from within VPC 10.0.0.0/16 (u will need to add all ICMP for ping to the world from private instance)
    * In Private EC2 instance SG: SSH from within VPC 10.0.0.0/16. Create a diff key pair to connect. Only NAT instance must have access to it.
    * Go to Private Route Table: For Private Instance to get internet access.
    
       |Destination | Target| Comments |
       |---:|---:|---|
       |10.0.0.16 |Local| good for private |
       |0.0.0.0/0| NAT-instance | internet access. But ping will not work |
    * Go to NAT instance SG. Add all ICMP from within VPC
 * NAT Gateways: Can't be used by insatce in the subnet where it's created. 5GBps bandwidth that scales automatically to 45 Gbps.
  No security group to configure
  * NAT Gateway is resilient in single-AZ. Create mutiple NAT Gateways in multiple AZ for fault tolerance.
  * Shut down NAT instance
  
   |Destination | Target| Comments |
         |---:|---:|---|
         |10.0.0.16 |Local| good for private |
         |0.0.0.0/0| ~~NAT-instance~~ nat gatwway | nat instance is blackhole after termination |
 * DNS Resolution in VPC:
   * __enableDnsSupport__ (true by default. decide if DNS resolution supported by VPC. If true queries AWS DNS server at 169.254.169.253)
   * __enableDnsHostname__ (false in newly created VPC. True for default VPC. Needed previous setting true. If true will assign public hostname to EC2 instance if it has a public IP)
   * If you have custom DNS domain names in a private zone in Route 53, you must srt both these attr
* __NACL and SG__: Like firewall. Deafult NACL allows everything outbound & inbound.  NACL has  subnet association Subnets are assigned default NACL.
  * Number - Lower number higher precedence. * is last rule. Denies a request in case no match
  * Good for blocking IP at subnet level
* __VPC Peering__ : two VPC uses AWS n/w
  * Note you must update route table in each VPC's subnet to ensure instances can communicate . Works cross account also.
  * Lab: Peering Connection. Set Requester and set another VPC to peer. Also Route Table of default VPC and other VPCs
  * 125 VPC (Target Gateway 5000)
  * Peering Connection: A peering connection enables you to route traffic via private IP addresses between two peered VPCs
   
   |Destination | Target| Comments |
         |---:|---:|---|
         |10.0.0.16 |Local| good for private |
           |0.0.0.0/0|igw-12333 | internet access. Go to subnet association tab in route table. see public subnets associated here |
             |CIDR of another VPC|pcx-12333 | - |
* __Transit Gateway__: hub and spoke star model. Many VPCs thru this GW transitively. IP multicast. ECMP - throughput
    - TGW has its own Route table. VPCS can be in different acc. __TGW and VPC in same region__
    - 5000 attachments (VPC peering only 125)
* __VPC Endpoints__ : How can private instances w/o internet access services like S3, etc
    * Refer page 561  
    * Is tied to a region. 
    * connect to AWS Services using Private Network instead of www. Scale horizontally and redundant
    * Interface: provision an ENI as an entry point (must attach security group) - most AWS service
    * Gateway: provisions a target and must be used in a routre table : S3 and DynamoDB    
    * Lab: Create Endpoint for my private EC2 to access S3. Choose S3 gateway. Choose VPC. Configure Route Table. Creates rule like pl-6da54(com.aws.eu-west.s3) & a target with this endpoint ID that will be added to the route table
    we may also change policy to restrict Endpoint access. [RouteTable](../images/VPC-Endpoint-RouteTable.PNG)
    * NOTE: "VPC endpoint service" is quite different from a "VPC endpoint". With VPC endpoint service, you are the service provider where you can create your own application in your VPC and configure it as an AWS PrivateLink-powered service (referred to as an endpoint service). Other AWS principals can create a connection from their VPC to your endpoint service using an interface VPC endpoint.
 * __Flow Logs__ and athena: 
    * VPC Flow logs, Subnet Flow logs, Elastic Network Interface Flow Logs
    * page 563 => go to S3 or CloudWatch Logs. 
    * Lab: Go to VPC > Flow log Tab > Destination: in cloudwatch create a log group > IAM role: flowlog Role.
 Destination can also be S3 also. __In cloudwatch see that there are so many IPs from everywhere in the world__
   * Analyze using Athena or Cloud watch insights
 * __Site-to-Site VPN__: (pg 568)
    * Corporate DC side:  __Customer Gateway__  : software or Hardware. IP Address: Static or public IP of NAT if behind it.
        * Between these two sides: __Site-to-Site VPN connection__
    * VPC side: __VPN Gateway__ aka VPN concentrator. Can customize ASN
    * 2 IPSecChannel. 
    * creates private connection using IP Sec & TLS.  Still uses public internet (DX does not use)
 * __Direct Connect (DX)__ : One month or so. Not fast to establish. Data is not encrypted though private. 
     Use AWS Direct Connect + VPN provides an IPSec encrypted private connection
    * ViF
    * 1 Gbps or 10 GBps (dedicated) - vcis ethernet port or 50 MBps, 500 mbps to 10gbps (hosted)
    * Hybrid Environment
    * no encryption
    * Customer N/w
        * Private Virtual Inferface between these two (VLAN1)
        * Public Virtual Inferface between AWS DX endpoint & say S3
    * AWS Direct Connect Location: AWS Cage (AWS DX endpoint) & Customer of partner Cage (their router)
    * Read resiliency
  * __Direct Connect Gateway__: one of more VPC in different regions of same account
  * __Egress Only Internet Gateway__: Egress = out going. Only for Ipv6. Like NAT for IPv4. All IPv6 are public. Update Route Table to add this __egw__ as target
  * __AWS PrivateLink or VPC Endpoint Services__
    * How do u expose services in 1 VPC to another: 1. make public 2. VPC peering
    * Better: AWS PrivateLink or VPC Endpoint Services
      * Service VPC side : NLB (multi AZ)
        * AWS Private Link: Between these two - Fault tolerant
      * Customer VPC side: Elastic Network interface (multi AZ)
    * Use case in exam: How do u expose your service in a VPC to 1000s of VPCs
   * EC2 Classic - AWS classicLink - deprecated
   * __VPN CloudHub__ : Provides hub-spoke model for primary and secondary network connectivity between locations.


# Disaster Recovery 
* RPO: Recovery Point Objective
* RTO: Recovery Time Objective
https://www.youtube.com/watch?v=-drcrCeQsHw
```
                    Loss          Downtime
------RPO (Backup)--------Disaster-------- RTO(Recovery)-------
---------Faster RTO----->
Back & Restore (High RPO,snappshots schedule interval) | Pilot Light | Warm Standby             | hot/Multisite
cheap                                      | Critical core always up |Full system up w/ min size | Full Prod scale up and running in AWS
                                small copy is replicated continually |                                Very low RTO (mins or secs)
<---AWS Multi Region --->
```
* Back up Restore - High RPO & RTO use snowball = RPO about 1 week, regular shanpshot => RPO 24 hrs or 1 hr depending on how frequently you take backup. RTO will also be high as restore takes time
* __Data Migration Service (DMS)__: Supports CDC (change data capture for cont. replication).
 Schema Conversion Tool (SCT) if engines not same. Continous.
* On-prem strategies: page 688 - Download Amazon AMI, VM export/import, AWS Application Disovery, AWS DMS,
 __AWS Server Migration (AWS SMS)__ (Incremental repl of on-premise live servers to AWS)
* __AWS DataSync__: scheduled not ongoing. NAS or file system via NFS or SMB. EFS to EFS
* Transferring large amount of Data:
  * Internet / Site-to-Site VPN (needs internet) - may take too long like a year
  * DX (more one time setup)
  * Snowball combined with DMS
  * On-going replicaton: Site-to-Site VPN, DX with DMS or DataSync
* __AWS Backup__ : FSx, EFS, DBs, EC2. EBS. Storage Gateway (Volume Gateway). __NO S3__ Cross Region & cross account
* HPC : Read EFA vs ENA (option to enable EFA in ENI)

# Well architected Principles
- AWS trusted advisor - can enable email, AWS Well architected tool
# More services
- __CloudFormation & CloudFormation - StackSets__ (for multiple accounts & region)
- __Step Functions__ - serverless visualflow to orchestrate Lambda Functions for Workflow (like Order fullfilment). Represented as JSON state function (can integrate human approval feature, have 1 year to complete all steps)
- __SWF__ (Simple workflow service) - Similar to step function. Older. Not serverless. Use case: need external signal to intervene a process or if a child process returns a value to parent
- __EMR__ - Hadoop Cluster made of 100s of EC2 instance w/Auto scaling and integrated with spot instances.
- __OpsWorks__ - Managed chef & Puppet (alternative to AWS SSM)
- __ElasticTranscoder___ - convert medi files stored in S3 into formats for tablets, PC, Smartphone etc. Serverless.
- __AWS Workspace__ - like VDI
- __AppSync__ - store & sync data accross mobile & web apps in realtime. Uses GraphQL (offline data syncc - replaces Cognito sync)
- __Cost Explorer__ 

# Docker Containers Management (page 635)
- AWS ECS - not serverless. Create ECS cluster in multi AZ with EC2 instances having ECS agent installed (comes in an AMI). Integration with Load Balancer. Can run many tasks
- AWS Fargate - serverless. Access tasks using ENI (so have IPs avaible for the ENIs)
  * ECS Instance Profile Role: IAM Roless used by ECS agent to:
    * make API calls to ECS service, send container logs to CloudWatch, pull docker image from ECR, 
    * Reference sensitive data in Systems Manager or SSM Paramter Store.
  * ECS Task Role: defined in task defination
    * one for each task to say access S3 bucket, dynamoDB
 - ECS Data Volumes - EFS File System: Tasks launched in any AZ will be able to share the same data in the EFS vol
   * Fargate + EFS = serveless with data volume in multiAZ. persisent container
  * ALB can integrate to services in ECS cluster:
    * __Dynamic Port mapping__ to find the right port on your EC2 instances. Must allow SG of __EC2 instance to allow any port from ALB__
    * Launch container at random port. When register with ALB it can use dynamic port mapping to discover the task port
  * Loadbalancing for Fargate: There is ENI for each task (Fixed IP + fixed task port). SG of ENI should allow SG of ALB on the task port.
  * ECS Tasks invoked by Event Bridge - for automation instead of invoking from ECS console:
     * when user uploads to S3 buckets we want to run a fargate task to process that file & insert into dynamodb
     * Amazon EventBridge event has a rule to run an ECS task which has an ECS task role to access S3 and Dynamodb
  * If while creating ECS task you choose no Load Balancer: SG allows all traffic from 0.0.0.0/0 to task port(80).
     * If ALB: SG allows SG of ALB on task port
  * ECS Scaling: ECS Service's CPU Usage:
    * 2 level of scaling. Extra and Optional Scale ECS Capacity Provider to add more EC2 instance 
    * CloudWatch Metric (ECS Service's CPU usage) > triggers cloudWatch Alarm > scales ECS tasks or Scale use __ECS Capacity Provider__ to add more EC2 instance
    * Use SQS - CloudWatch Alarm on queue length > trigger alarm -> -do-
  * ECS Rolling updates for new versions. Use rolling logic to upgrade tasks
- ECR - registry. IAM Role to pull. CodeBuild to automate build, push
- EKS - managed k8s clusters. Cloud agnostic, Opensource alternative to ECS. Launch mode: EC2 or Fargate
- https://dev.to/napicella/aws-networking-cheat-sheet-eip-eni-vpc-etc-139m
- https://medium.com/@hk_it_er/summary-on-the-aws-rds-faq-90dd443f983#:~:text=Q%3A%20When%20running%20my%20DB,rather%20than%20read%20scaling%20benefits.
