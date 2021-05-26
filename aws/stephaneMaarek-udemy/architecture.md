---
* Creating Highly available Ec2 instance (1 EIP + 1 EC2)
 * Using Cloudwatch: Have another EC2 as standby
    * Cloudwatch Event monitor some metrics like statusCheckFailed_System or CPU and trigger a Lambda
    * Lambda = starts standby and attach EIP (make it primary, and other standby)
 * Using ASG in >=2 AZ (1 min, max and desired)
    * User Data to attach EIP based on tag
    * When an instance terminates, ASG will start another. User data will attach EIP
     ```json5
    INSTANCE_ID=`/usr/bin/curl -s http://169.254.169.254/latest/meta-data/instance-id`
    aws ec2 associate-address --instance-id $INSTANCE_ID --allocation-id eipalloc-my-eip-id --allow-reassociation
     ```
 * In above we may have EC2 + EBS (which is locked in an AZ). When Ec2 terminates, another will be launched in different AZ,
 but for EBS: 
    * EBS snapshot on ASG Terminate Lifecycle hook (and add tag)
    * EBS Volume created from Snapshot on ASG Launch lifecycle hook
 ---
 * HA for Bastion Host
    * Bastion Host can listen to port 22 i.e. TCP so ALB can't be used
    * Say there are 2 AZ, we will have 2 bastion Host. An NLB in both AZ. Client talks to
      Bastion Host using NLB. Note NLB don't have Security Group
    * In fact if we use NLB, Bastion Host can be pushed to private subnet
 ---
 * Event Processing: see slides
   