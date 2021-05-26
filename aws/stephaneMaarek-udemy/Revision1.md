|EBS||
|---|---|
|SSD Backed eg GP2, IO1| Low Latency + Highest Iops (boot vol, random IO) - DB, SAP HANA etc|
|HDD Backed eg ST1, SC1 (less frequently accessed) | Streaming seq. access for highest thru-put - Hadoop, Kafka, Big Data apps, Data warehouse - splunk, log processing. Staging App level backus before snapshoting to S3|
|Modify Life volumes| Point-in-time snapshots. Stored in S3. Monitor with Cloudwatch
|automatically replicated within that zone to prevent data loss due to a failure of any single hardware component|EBS volumes support live configuration changes while in production which means that you can modify the volume type, size, IOPS capacity without service interruptions|
|GP2| 1 to 16 TB, IOPS = 3 IOPS per GB ie @5334 GB we are at max IOPS of 16000. Max burst: 3000 1 to 33 GB => 100/3000 IOPs. 100 GB => 300/3000. 500 GB => 1500/3000, 4000 => 12000 (no burst as 12000>3000|
|IO1| 4 to 16 TB. 100 to 64000 IOPS (Nitro), 32,000 others. Provisioned IOPS: vol = 50:1|
|ST1| 500 GB to 16 TB. Max IOPS 500. Thruput 500 MB/s - can burst|
|SC1| -do-, Max IOPS 250. 250 MB.s - can burst. Infrequently accessed |
|Snapshot|Amazon Data LifeCycleManager - 100,000 snapshots. EBS vol restored from snapshots must be pre-warmed|
|Instance store| Millions of IOPS. Can't be increased in size|
|RAID0|need IOPS, not fault tolerant. eg DB with replication built in. applicable to instance store too|
|RAID1|fault tolerant. Send more data 2x network|
|EC2 Placement Groups| __Cluster__ - big data jobs - great n/w, low latency. __Spread__ - cross AZ, high avail. 7 inst per AZ. __Partition__: 7 partitions per AZ. Up to 100 of Ec2 instance. HBasw, HDFC, Cassandra, Kafka|
|[EBS Encyption by default](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/EBSEncryption.html#encryption-by-default)| Region specific |
- EBS Volumes are only locked to a specific AZ.
- SQS - can't set priority. Use two SQS in such case
- SQS vs SNS - when it says like S3 notification to be processedonly by one EC2 use SQS. SNS will broadcast to all (we could do filtering but that's extra task. So use SQS)
- SQS - message prioritizing. Use 2 SQS - say SQS-Priority and SQS-Lite. Consumers like EC2 decide which to consume.
- AWS Glue: AWS Glue - AWS Glue provides a managed ETL service that runs on a serverless Apache Spark environment
- A service role is an IAM role that a service assumes to perform actions on your behalf. Service roles provide access only within your account and cannot be used to grant access to services in other accounts. An IAM administrator can create, modify, and delete a service role from within IAM. When you create the service role, you define the trusted entity in the definition.
- S3 Notification destination: SQS, SNS, Lambda
- Can ASG exceed max capacity? Yes - in case of rebalancing (https://docs.aws.amazon.com/autoscaling/ec2/userguide/auto-scaling-benefits.html)
- You can set the DeleteOnTermination attribute to False when you launch a new instance. It is not possible to update this attribute of a running instance from the AWS console. Can do only using CLI.
- S3 Expedited - if you require access to Expedited retrievals under all circumstances, you must purchase provisioned retrieval capacity.
- https://docs.aws.amazon.com/vpc/latest/peering/invalid-peering-configurations.html#edge-to-edge-vgw
- Test 2 - q21 (tutorial dojo)
- Read <b>Geoproximity</b> for bias in Route 53 vs Geolocation for affinity https://medium.com/awesome-cloud/aws-amazon-route-53-routing-policies-overview-285cee2d4d3b
-  Anycast IP => Global Accelator
- https://tutorialsdojo.com/aws-certified-solutions-architect-associate-saa-c02/
- https://stackoverflow.com/questions/24603620/redirecting-ec2-elastic-load-balancer-from-http-to-https
- EFA: Enables you to run applications requiring high levels of inter-node communications at scale on AWS through its custom-built operating system (OS)
 bypass hardware interface is a capability of __Elastic Fabric Adapter__.
```shell script
http://169.254.169.254/latest/user-data . bootstrap.txt => to get the user-data script
http://169.254.169.254/latest/meta-data/
http://169.254.169.254/latest/meta-data/local-ipv4
http://169.254.169.254/latest/meta-data/local-ipv6
```
- ENI is linked to AZ / subnet. Has SG.  Use it as Secondary IP for network failure.
    - Option to enable EFA for HPC (EFA uses MPI - message Passing Interface)
    - can be attached to EC2 or even Fargate task
  
- io1/i02 - EBS multi-attach in same AZ (must be cluster aware, but same AZ. Only Nitro. If different AZ use EFS. No concurrent modification)
- EFS : Performance Mode (general purpose or Max I/O) OR Thruput Mode (Bursting or Provisioned - say small capacitry but need high thru put)
     - SG: allow NFS from EC2 instance
- https://aws.amazon.com/elasticloadbalancing/features/
- Load Balancers run in one region (not cross region)
- CLB: HTTP/s and TCP/SSL (layer 4 and 7)
- ALB: Websocket, gRPC and HTTP/S. Global Accelerator: __Static IP (so that customer can whitelist your IP)__
   -  Global accelerator: use of Edge Location to speed ur request using AWS n/w. Can be used with ALB, NLB EIP, and EC2. 
   Charged on dominant side of traffic.Assign traffic Dial in % up or down to increase or decrease the traffic to the endpoints.
   You can uncheck __Preserve Client IP Address__ to NOT preserve client IP for internet facing apps. 
   If your Endpoint is not configured to accept the traffic from the client like in our demoes where it accepts only from ALB, you may uncheck it.
   - https://docs.aws.amazon.com/global-accelerator/latest/dg/dns-addressing-custom-domains.mapping-your-custom-domain.html
   - __Target Group__ needs to configured and added as __Listeners__ with the IF THEN kind of rule. IF X goto TG1 Else if Y TG2 ELSE some constant response
   - Target group can have ECS tasks also in which case automatically do port mapping (NOTE ECS tasks can use CLB, ALB or NLB). Additionally, ECS task will need an IAM role for IAM.
- NLB: Layer 4: TCP/UDP
    - Static IP per zone (in lab when u select a subnet, it asks you for ipV4 setting either assigned by AWS or your EIP - useful for whitelisting)
    - works with Target Group. SG of EC2 instance: HTTP from anywhere (not NLB, unlike ALB)
 - Stickiness only valid for CLB & ALB. It is at target group level | Read Cross Zone Load balancing
 - __cross zone load balancing__ : ALB - Always on (can't be diabled), no charge for interAZ data. NLB - disabled. Pay if enable. CLB (read PDF)
 - SNI (ALB, NLB and cloudFront) 
 - __Connection Draining__ -CLB or Deregistration Delay (ALB/NLB) - 300 sec by default
 - ASG - instance purchase options: either by launch template or combine purchase options & instance types
       - Network: Choose VPN and subnets (so only in one region)
       - we can attach a Load Balancer + target group and then choose Health check based on ELB instead of EC2 
       - ASG - Scaling Cooldown (default cooldown 300 seconds)
       - __Target Tracking Policy__ | Step Scaling | Scheduled Scaling
       - __Step Scaling__ > Create cloudWatch Alarm > Select metrics by EC2 by autoscaling group and choose the ASG > CPU Utilzation > condition if avg CPU utlilization > 50 % then trigger the alarm and send email to SNS topic. On trigger of this alarm add 1 capacity unit when CPU utilization is say 50 % oe even 40%
       __Schedules Scaling__
       - Stress CPU - sudo amazon-linux-extras install epel -y &&  sudo yum install stress -y
       - Read ASG Default Termination Policy & lifecycle hook (install extra software when EC2 is launching or clean up when terminating)
       - Launch config - old. New Launch Template
 - RDS : Read replicas managed so no cost even when multi AZ. Cross region charge. ASYNC
     - RDS Multi AZ (DR) - one DNS name - automatic failover. SYNC (for aurora it is async) - Not available in Free Tier
            - (https://aws.amazon.com/rds/features/multi-az/)
     - Read Replicas can be set as multi AZ for DR
     - RDS - From single to multi AZ (just 1 click. Behind the scenes, DB snapshot is created, a standby DB is restored in another AZ and a sync replication takes place)
     - Enable storage autoscaling option - check this to allow storage to increase once specified threshold is exceeded (from 20 GB up)
     - TDE - Oracle & SQL Server. IAM - Mysql & Postgres
     - __Aurora - storage automatically grows from 10GB-64 TB. 15 replicas (cf MySQl 5)__
              - HA & Read scaling: 6 copies of your data across 3 AZ.
                  - 4 Copies available for writes, 3 for read
              - Serverless option available
              - writer & reader endpoint (also __custom endpoints__ for read replicas subset), Replica autoscaling, multimaster
              - global aurora
              - https://chartio.com/resources/tutorials/understanding-amazon-auroras-multi-az-deployment/
              - aurora - reboot with failover for standby to become primary
     - Automated backup: maximum backup retention period = 35 days.Occurs during user-configurable 30 min backup window
     - Manual Snapshots: kept in S3 till deleted.can't export an automated snapshot automatically to Amazon S3. Can do manually
     - __u cannot directly download or export an automated snapshot in RDS to Amazon S3__. You have to copy the automated snapshot first for it to become a manual snapshot, 
       which you can move to an Amazon S3 bucket
     - AWS Backup
 * AWS X-RAY
 * https://aws.amazon.com/datapipeline/
 * To increase aggregate IOPS, or to improve sequential disk throughput, multiple instance store volumes can be grouped together using RAID 0 (disk striping) software. Because the bandwidth of the disks is not limited by the network, aggregate sequential throughput for multiple instance volumes can be higher than for the same number of EBS volumes.
 * IAM Certificate Manager & ACM - https://docs.aws.amazon.com/IAM/latest/UserGuide/id_credentials_server-certs.html
 *  default CloudWatch doesn't monitor memory usage but only the CPU utilization, Network utilization, Disk performance, and Disk Reads/Writes.
 * Default VPC has NACL with allows all Traffic in & out from 0.0.0.0/0
 * VPC CIDR /28 (16 IPs) to /16 (655536). Can add 5 IPv4 or 6 CIDR
 * AWS Quicksight - visualize s3 data. Can have Endpoint for Redshift (Data warehouse)
 * [How do I prevent CloudFront from caching certain files](https://aws.amazon.com/premiumsupport/knowledge-center/prevent-cloudfront-from-caching-files/)
 * Inter-region VPC peering 
 * https://aws.amazon.com/ec2/instance-types/
 * Assume Role: https://www.youtube.com/watch?v=20tr9gUY4i0
 * IAM role has Trust relationship: https://www.youtube.com/watch?v=Zvz-qYYhvMk
 * [This pic](../images/IAM/4AssumeRoleService.PNG) - How does EC2 which has an IAM role associated with it assumes that. This is created by default under Trust relationship
 * Resource based Policy: How we allow other account outside into your account.__Access Analyzer__: static analyzer to see which resource can be accessed from outside.
 * https://jayendrapatil.com/tag/eni/
 * https://aws.amazon.com/about-aws/whats-new/2020/12/amazon-s3-bucket-keys-reduce-the-costs-of-server-side-encryption-with-aws-key-management-service-sse-kms/
* HPC
   * Data Management & Transfer: Direct Connect, Snowball/mobile, AWS Datasync
   * Compute/Network: 
      * Ec2 spot instance, spot fleets for cost saving, Ec2 cluster placement (10Gbps)
      * EC2 Enhanced Networking(SR-IOV): Higer bandwidth, PPS, lower latency
           1. ENA - 100 gbps
           2. Intel 82599 VF (legacy) - 10 gbps
      * EFA (improved ENA for HPC, only works for linux) 
        * gr8 4 inter-node communication
        * Leverages Message Passing Interface (MPI) Standard 
        * Bypasses the underlying Liunx OS (low latency, reliable transport)
   * Storage 
     * Instance-attached : EBS: scale to 64000 IOPS io1 | instance store: millions of IOPS
     * Network storage: S3, EFS, FSx for Lustre: HPC optimized distributed File System million of IPOS, backed by S3
   * Automation & Orchestration
     * AWS Batch: supports multi-node //l jobs, which enables you to run single job spanning EC2 instances, easily schedule jos and launch EC2 instances accordingly.
     * AWS Parallel Clustre - Open Source Cluster Management tool to deploy HPC on AWS, configure with text files, automate creati
     
     A leading online gaming company is migrating its flagship application to AWS Cloud for delivering its online games to users across the world. 
     The company would like to use a Network Load Balancer (NLB) to handle millions of requests per second. 
     The engineering team has provisioned multiple instances in a public subnet and specified these instance IDs as the targets for the NLB.
     As a solutions architect, can you help the engineering team understand the correct routing mechanism for these target instances?
     
     Traffic is routed to instances using the primary private IP address specified in the primary network interface for the instance
     After the load balancer receives a connection request, it selects a target from the target group for the default rule. It attempts to open a 
     TCP connection to the selected target on the port specified in the listener configuration.
     
     Request Routing and IP Addresses -
     
     If you specify targets using an instance ID, traffic is routed to instances using the primary private IP address specified in the primary network interface
      for the instance. The load balancer rewrites the destination IP address from the data packet before forwarding it to the target instance.
     
     If you specify targets using IP addresses, you can route traffic to an instance using any private IP address from one or more network interfaces. 
     This enables multiple applications on an instance to use the same port. Note that each network interface can have its security group. 
     The load balancer rewrites the destination IP address before forwarding it to the target.
     
     - https://www.whizlabs.com/blog/s3-server-side-encryption/
     - You can create an Amazon CloudWatch alarm that monitors an Amazon EC2 instance and automatically reboots the instance.
      The reboot alarm action is recommended for Instance Health Check failures (as opposed to the recover alarm action, which is suited for System Health Check failures).
      
      Set up an Aurora multi-master DB cluster
      In a multi-master cluster, all DB instances can perform write operations. There isn't any failover when a writer DB instance becomes unavailable, 
      because another writer DB instance is immediately available to take over the work of the failed instance. 
      AWS refers to this type of availability as continuous availability, to distinguish it from the high availability (with brief downtime during failover) 
      offered by a single-master cluster. For applications where you can't afford even brief downtime for database write operations, 
      a multi-master cluster can help to avoid an outage when a writer instance becomes unavailable. 
      The multi-master cluster doesn't use the failover mechanism, because it doesn't need to promote another DB instance to have read/write capability.
      - https://aws.amazon.com/blogs/architecture/reduce-cost-and-increase-security-with-amazon-vpc-endpoints/
      ===
      - https://jayendrapatil.com/aws-rds-db-maintenance-upgrades/
      - Marek - Test 6 - q15 about DX and resiliency 
      - https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/dedicated-instance.html#change-tenancy-vpc