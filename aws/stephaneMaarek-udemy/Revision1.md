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
|RAID0|need IOPS, not fault tolerant. eg DB with replication built in|
|RAID1|fault tolerant. Send more data 2x network|
|EC2 Placement Groups| __Cluster__ - big data jobs - great n/w, low latency. __Spread__ - cross AZ, high avail. 7 inst per AZ. __Partition__: 7 partitions per AZ. Up to 100 of Ec2 instance. HBasw, HDFC, Cassandra, Kafka|

- SQS - can't set priority. Use two SQS in such case
- S3 Notification destination: SQS, SNS, Lambda
- https://docs.aws.amazon.com/vpc/latest/peering/invalid-peering-configurations.html#edge-to-edge-vgw
- Test 2 - q21
- Read <b>Geoproximity</b> for bias in Route 53 vs Geolocation for affinity https://medium.com/awesome-cloud/aws-amazon-route-53-routing-policies-overview-285cee2d4d3b
-  Anycast IP => Global Accelator
- https://tutorialsdojo.com/aws-certified-solutions-architect-associate-saa-c02/
- https://stackoverflow.com/questions/24603620/redirecting-ec2-elastic-load-balancer-from-http-to-https
- Enables you to run applications requiring high levels of inter-node communications at scale on AWS through its custom-built operating system (OS) bypass hardware interface is a capability of __Elastic Fabric Adapter__.
```shell script
http://169.254.169.254/latest/user-data . bootstrap.txt => to get the user-data script
http://169.254.169.254/latest/meta-data/
http://169.254.169.254/latest/meta-data/local-ipv4
http://169.254.169.254/latest/meta-data/local-ipv6
```
- ENI is linked to AZ / subnet. Has SG.  Use it as Secondary IP for network failure.
    - Option to enable EFA for HPC (EFA uses MPI - message Passing Interface)
    - can be attached to EC2 or even Fargate task
  
- io1/i02 - EBS multi-attach in same AZ (must be cluster aware, but same AZ. If different AZ use EFS)
- EFS : Performance Mode (general purpose or Max I/O) OR Thruput Mode (Bursting or Provisioned - say small capacitry but need high thru put)
     - SG: allow NFS from EC2 instance
- https://aws.amazon.com/elasticloadbalancing/features/
- Load Balancers run in one region (not cross region)
- CLB: HTTP/s and TCP/SSL (layer 4 and 7)
- ALB: Websocket, gRPC and HTTP/S. Global Accelerator: __Static IP (so that customer can whitelist your IP)__
   -  Global accelerator: use of Edge Location to speed ur request using AWS n/w. Can be used with ALB, NLB EIP, and EC2. Charged on dominant side of traffic.Assign traffic Dial in % up or down to increase or decrease the traffic to the endpoints.
   You can uncheck __Preserve Client IP Address__ to NOT preserve client IP for internet facing apps. If your Endpoint is not configured to accept the traffic from the client like in our demoes where it accepts only from ALB, you may uncheck it.
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
 - Aurora - storage automatically grows from 10GB-64 TB. 15 replicas (cf MySQl 5)
          - HA & Read scaling: 6 copies of your data across 3 AZ.
              - 4 Copies available for writes, 3 for read
          - Serverless option available
          - writer & reader endpoint (also __custom endpoints__ for read replicas subset), Replica autoscaling, multimaster
          - global aurora
          - https://chartio.com/resources/tutorials/understanding-amazon-auroras-multi-az-deployment/
          - aurora - reboot with failover for standby to become primary