---
title: Sharding 
category: Behavioral
language: en
tag:  
 - Performance
 - Cloud distributed
---  
  
## Intent  
Sharding pattern means divide the data store into horizontal partitions or shards. Each shard has the same schema, but holds its own distinct subset of the data. 
A shard is a data store in its own right (it can contain the data for many entities of different types), running on a server acting as a storage node.

## Explanation

Real-world example

> Facebook distributes user profiles, posts, photos, and videos across multiple shards based on user IDs or geographic regions.

In plain words

> Splitting big data into smaller chunks for quicker access.

Wikipedia says

> A database shard, or simply a shard, is a horizontal partition of data in a database or search engine. Each shard is held on a separate database server instance, to spread load. Some data within a database remains present in all shards, but some appear only in a single shard. Each shard acts as the single source for this subset of data.

## Class diagram
![alt text](./etc/sharding.urm.png "Sharding pattern class diagram")

## Applicability  
This pattern offers the following benefits:

- You can scale the system out by adding further shards running on additional storage nodes.
- A system can use off the shelf commodity hardware rather than specialized (and expensive) computers for each storage node.
- You can reduce contention and improved performance by balancing the workload across shards.
- In the cloud, shards can be located physically close to the users that will access the data.

## Credits  
  
* [Sharding pattern](https://docs.microsoft.com/en-us/azure/architecture/patterns/sharding)
