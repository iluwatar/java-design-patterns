---
title: Leader Election
category: Behavioral
language: en
tag:
 - Cloud distributed
---

## Intent
Leader Election pattern is commonly used in cloud system design. It can help to ensure that task 
instances select the leader instance correctly and do not conflict with each other, cause 
contention for shared resources, or inadvertently interfere with the work that other task 
instances are performing.

## Explanation

Real world example
> In a horizontally scaling cloud-based system, multiple instances of the same task could be 
> running at the same time with each instance serving a different user. If these instances 
> write to a shared resource, it's necessary to coordinate their actions to prevent each instance 
> from overwriting the changes made by the others. In another scenario, if the tasks are performing
> individual elements of a complex calculation in parallel, the results need to be aggregated 
> when they all complete.

In plain words
> this pattern is used in distributed cloud-based system where leader must need to act as 
> coordinator/aggregator among horizontal scaling instances for avoiding conflict on shared resources. 

Wikipedia says
> In distributed computing, leader election is the process of designating a single process as 
> the organizer of some task distributed among several computers (nodes).

**Programmatic Example**

Ring algorithm and Bully algorithm implement leader election pattern. 
Each algorithm require every participated instance to have unique ID.

```java
public interface Instance {

  /**
   * Check if the instance is alive or not.
   *
   * @return {@code true} if the instance is alive.
   */
  boolean isAlive();

  /**
   * Set the health status of the certain instance.
   *
   * @param alive {@code true} for alive.
   */
  void setAlive(boolean alive);

  /**
   * Consume messages from other instances.
   *
   * @param message Message sent by other instances
   */
  void onMessage(Message message);

}
```

Abstract class who implements Instance and Runnable interfaces.
```java
public abstract class AbstractInstance implements Instance, Runnable {

  protected static final int HEARTBEAT_INTERVAL = 5000;
  private static final String INSTANCE = "Instance ";

  protected MessageManager messageManager;
  protected Queue<Message> messageQueue;
  protected final int localId;
  protected int leaderId;
  protected boolean alive;

  /**
   * Constructor of BullyInstance.
   */
  public AbstractInstance(MessageManager messageManager, int localId, int leaderId) {
    this.messageManager = messageManager;
    this.messageQueue = new ConcurrentLinkedQueue<>();
    this.localId = localId;
    this.leaderId = leaderId;
    this.alive = true;
  }

  /**
   * The instance will execute the message in its message queue periodically once it is alive.
   */
  @Override
  @SuppressWarnings("squid:S2189")
  public void run() {
    while (true) {
      if (!this.messageQueue.isEmpty()) {
        this.processMessage(this.messageQueue.remove());
      }
    }
  }

  /**
   * Once messages are sent to the certain instance, it will firstly be added to the queue and wait
   * to be executed.
   *
   * @param message Message sent by other instances
   */
  @Override
  public void onMessage(Message message) {
    messageQueue.offer(message);
  }

  /**
   * Check if the instance is alive or not.
   *
   * @return {@code true} if the instance is alive.
   */
  @Override
  public boolean isAlive() {
    return alive;
  }

  /**
   * Set the health status of the certain instance.
   *
   * @param alive {@code true} for alive.
   */
  @Override
  public void setAlive(boolean alive) {
    this.alive = alive;
  }

  /**
   * Process the message according to its type.
   *
   * @param message Message polled from queue.
   */
  private void processMessage(Message message) {
    switch (message.getType()) {
      case ELECTION -> {
        LOGGER.info(INSTANCE + localId + " - Election Message handling...");
        handleElectionMessage(message);
      }
      case LEADER -> {
        LOGGER.info(INSTANCE + localId + " - Leader Message handling...");
        handleLeaderMessage(message);
      }
      case HEARTBEAT -> {
        LOGGER.info(INSTANCE + localId + " - Heartbeat Message handling...");
        handleHeartbeatMessage(message);
      }
      case ELECTION_INVOKE -> {
        LOGGER.info(INSTANCE + localId + " - Election Invoke Message handling...");
        handleElectionInvokeMessage();
      }
      case LEADER_INVOKE -> {
        LOGGER.info(INSTANCE + localId + " - Leader Invoke Message handling...");
        handleLeaderInvokeMessage();
      }
      case HEARTBEAT_INVOKE -> {
        LOGGER.info(INSTANCE + localId + " - Heartbeat Invoke Message handling...");
        handleHeartbeatInvokeMessage();
      }
      default -> {
      }
    }
  }

  /**
   * Abstract methods to handle different types of message. These methods need to be implemented in
   * concrete instance class to implement corresponding leader-selection pattern.
   */
  protected abstract void handleElectionMessage(Message message);

  protected abstract void handleElectionInvokeMessage();

  protected abstract void handleLeaderMessage(Message message);

  protected abstract void handleLeaderInvokeMessage();

  protected abstract void handleHeartbeatMessage(Message message);

  protected abstract void handleHeartbeatInvokeMessage();

}
```

```java
/**
 * Message used to transport data between instances.
 */
@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Message {

  private MessageType type;
  private String content;

}
```

```java
public interface MessageManager {

  /**
   * Send heartbeat message to leader instance to check whether the leader instance is alive.
   *
   * @param leaderId Instance ID of leader instance.
   * @return {@code true} if leader instance is alive, or {@code false} if not.
   */
  boolean sendHeartbeatMessage(int leaderId);

  /**
   * Send election message to other instances.
   *
   * @param currentId Instance ID of which sends this message.
   * @param content   Election message content.
   * @return {@code true} if the message is accepted by the target instances.
   */
  boolean sendElectionMessage(int currentId, String content);

  /**
   * Send new leader notification message to other instances.
   *
   * @param currentId Instance ID of which sends this message.
   * @param leaderId  Leader message content.
   * @return {@code true} if the message is accepted by the target instances.
   */
  boolean sendLeaderMessage(int currentId, int leaderId);

  /**
   * Send heartbeat invoke message. This will invoke heartbeat task in the target instance.
   *
   * @param currentId Instance ID of which sends this message.
   */
  void sendHeartbeatInvokeMessage(int currentId);

}
```
These type of messages are used to pass among instances.
```java
/**
 * Message Type enum.
 */
public enum MessageType {

  /**
   * Start the election. The content of the message stores ID(s) of the candidate instance(s).
   */
  ELECTION,

  /**
   * Nodify the new leader. The content of the message should be the leader ID.
   */
  LEADER,

  /**
   * Check health of current leader instance.
   */
  HEARTBEAT,

  /**
   * Inform target instance to start election.
   */
  ELECTION_INVOKE,

  /**
   * Inform target instance to notify all the other instance that it is the new leader.
   */
  LEADER_INVOKE,

  /**
   * Inform target instance to start heartbeat.
   */
  HEARTBEAT_INVOKE

}
```
Abstract class who implements MessageManager interface. It helps to manage messages among instance.
```java
/**
 * Abstract class of all the message manager classes.
 */
public abstract class AbstractMessageManager implements MessageManager {

  /**
   * Contain all the instances in the system. Key is its ID, and value is the instance itself.
   */
  protected Map<Integer, Instance> instanceMap;

  /**
   * Constructor of AbstractMessageManager.
   */
  public AbstractMessageManager(Map<Integer, Instance> instanceMap) {
    this.instanceMap = instanceMap;
  }

  /**
   * Find the next instance with smallest ID.
   *
   * @return The next instance.
   */
  protected Instance findNextInstance(int currentId) {
    Instance result = null;
    var candidateList = instanceMap.keySet()
        .stream()
        .filter((i) -> i > currentId && instanceMap.get(i).isAlive())
        .sorted()
        .toList();
    if (candidateList.isEmpty()) {
      var index = instanceMap.keySet()
          .stream()
          .filter((i) -> instanceMap.get(i).isAlive())
          .sorted()
          .toList()
          .get(0);
      result = instanceMap.get(index);
    } else {
      var index = candidateList.get(0);
      result = instanceMap.get(index);
    }
    return result;
  }

}
```

Here the implementation of Ring Algorithm
```java
/**
 * Implementation with token ring algorithm. The instances in the system are organized as a ring.
 * Each instance should have a sequential id and the instance with smallest (or largest) id should
 * be the initial leader. All the other instances send heartbeat message to leader periodically to
 * check its health. If one certain instance finds the server done, it will send an election message
 * to the next alive instance in the ring, which contains its own ID. Then the next instance add its
 * ID into the message and pass it to the next. After all the alive instances' ID are add to the
 * message, the message is send back to the first instance and it will choose the instance with
 * smallest ID to be the new leader, and then send a leader message to other instances to inform the
 * result.
 */
@Slf4j
public class RingInstance extends AbstractInstance {
  private static final String INSTANCE = "Instance ";

  /**
   * Constructor of RingInstance.
   */
  public RingInstance(MessageManager messageManager, int localId, int leaderId) {
    super(messageManager, localId, leaderId);
  }

  /**
   * Process the heartbeat invoke message. After receiving the message, the instance will send a
   * heartbeat to leader to check its health. If alive, it will inform the next instance to do the
   * heartbeat. If not, it will start the election process.
   */
  @Override
  protected void handleHeartbeatInvokeMessage() {
    try {
      var isLeaderAlive = messageManager.sendHeartbeatMessage(this.leaderId);
      if (isLeaderAlive) {
        LOGGER.info(INSTANCE + localId + "- Leader is alive. Start next heartbeat in 5 second.");
        Thread.sleep(HEARTBEAT_INTERVAL);
        messageManager.sendHeartbeatInvokeMessage(this.localId);
      } else {
        LOGGER.info(INSTANCE + localId + "- Leader is not alive. Start election.");
        messageManager.sendElectionMessage(this.localId, String.valueOf(this.localId));
      }
    } catch (InterruptedException e) {
      LOGGER.info(INSTANCE + localId + "- Interrupted.");
    }
  }

  /**
   * Process election message. If the local ID is contained in the ID list, the instance will select
   * the alive instance with smallest ID to be the new leader, and send the leader inform message.
   * If not, it will add its local ID to the list and send the message to the next instance in the
   * ring.
   */
  @Override
  protected void handleElectionMessage(Message message) {
    var content = message.getContent();
    LOGGER.info(INSTANCE + localId + " - Election Message: " + content);
    var candidateList = Arrays.stream(content.trim().split(","))
        .map(Integer::valueOf)
        .sorted()
        .toList();
    if (candidateList.contains(localId)) {
      var newLeaderId = candidateList.get(0);
      LOGGER.info(INSTANCE + localId + " - New leader should be " + newLeaderId + ".");
      messageManager.sendLeaderMessage(localId, newLeaderId);
    } else {
      content += "," + localId;
      messageManager.sendElectionMessage(localId, content);
    }
  }

  /**
   * Process leader Message. The instance will set the leader ID to be the new one and send the
   * message to the next instance until all the alive instance in the ring is informed.
   */
  @Override
  protected void handleLeaderMessage(Message message) {
    var newLeaderId = Integer.valueOf(message.getContent());
    if (this.leaderId != newLeaderId) {
      LOGGER.info(INSTANCE + localId + " - Update leaderID");
      this.leaderId = newLeaderId;
      messageManager.sendLeaderMessage(localId, newLeaderId);
    } else {
      LOGGER.info(INSTANCE + localId + " - Leader update done. Start heartbeat.");
      messageManager.sendHeartbeatInvokeMessage(localId);
    }
  }

  /**
   * Not used in Ring instance.
   */
  @Override
  protected void handleLeaderInvokeMessage() {
    // Not used in Ring instance.
  }

  @Override
  protected void handleHeartbeatMessage(Message message) {
    // Not used in Ring instance.
  }

  @Override
  protected void handleElectionInvokeMessage() {
    // Not used in Ring instance.
  }

}
```

```java
/**
 * Implementation of RingMessageManager.
 */
public class RingMessageManager extends AbstractMessageManager {

  /**
   * Constructor of RingMessageManager.
   */
  public RingMessageManager(Map<Integer, Instance> instanceMap) {
    super(instanceMap);
  }

  /**
   * Send heartbeat message to current leader instance to check the health.
   *
   * @param leaderId leaderID
   * @return {@code true} if the leader is alive.
   */
  @Override
  public boolean sendHeartbeatMessage(int leaderId) {
    var leaderInstance = instanceMap.get(leaderId);
    var alive = leaderInstance.isAlive();
    return alive;
  }

  /**
   * Send election message to the next instance.
   *
   * @param currentId currentID
   * @param content   list contains all the IDs of instances which have received this election
   *                  message.
   * @return {@code true} if the election message is accepted by the target instance.
   */
  @Override
  public boolean sendElectionMessage(int currentId, String content) {
    var nextInstance = this.findNextInstance(currentId);
    var electionMessage = new Message(MessageType.ELECTION, content);
    nextInstance.onMessage(electionMessage);
    return true;
  }

  /**
   * Send leader message to the next instance.
   *
   * @param currentId Instance ID of which sends this message.
   * @param leaderId  Leader message content.
   * @return {@code true} if the leader message is accepted by the target instance.
   */
  @Override
  public boolean sendLeaderMessage(int currentId, int leaderId) {
    var nextInstance = this.findNextInstance(currentId);
    var leaderMessage = new Message(MessageType.LEADER, String.valueOf(leaderId));
    nextInstance.onMessage(leaderMessage);
    return true;
  }

  /**
   * Send heartbeat invoke message to the next instance.
   *
   * @param currentId Instance ID of which sends this message.
   */
  @Override
  public void sendHeartbeatInvokeMessage(int currentId) {
    var nextInstance = this.findNextInstance(currentId);
    var heartbeatInvokeMessage = new Message(MessageType.HEARTBEAT_INVOKE, "");
    nextInstance.onMessage(heartbeatInvokeMessage);
  }

}
```
```java
public static void main(String[] args) {

    Map<Integer, Instance> instanceMap = new HashMap<>();
    var messageManager = new RingMessageManager(instanceMap);

    var instance1 = new RingInstance(messageManager, 1, 1);
    var instance2 = new RingInstance(messageManager, 2, 1);
    var instance3 = new RingInstance(messageManager, 3, 1);
    var instance4 = new RingInstance(messageManager, 4, 1);
    var instance5 = new RingInstance(messageManager, 5, 1);

    instanceMap.put(1, instance1);
    instanceMap.put(2, instance2);
    instanceMap.put(3, instance3);
    instanceMap.put(4, instance4);
    instanceMap.put(5, instance5);

    instance2.onMessage(new Message(MessageType.HEARTBEAT_INVOKE, ""));

    final var thread1 = new Thread(instance1);
    final var thread2 = new Thread(instance2);
    final var thread3 = new Thread(instance3);
    final var thread4 = new Thread(instance4);
    final var thread5 = new Thread(instance5);

    thread1.start();
    thread2.start();
    thread3.start();
    thread4.start();
    thread5.start();

    instance1.setAlive(false);
  }
```

The console output
```
[Thread-1] INFO com.iluwatar.leaderelection.AbstractInstance - Instance 2 - Heartbeat Invoke Message handling...
[Thread-1] INFO com.iluwatar.leaderelection.ring.RingInstance - Instance 2- Leader is not alive. Start election.
[Thread-2] INFO com.iluwatar.leaderelection.AbstractInstance - Instance 3 - Election Message handling...
[Thread-2] INFO com.iluwatar.leaderelection.ring.RingInstance - Instance 3 - Election Message: 2
[Thread-3] INFO com.iluwatar.leaderelection.AbstractInstance - Instance 4 - Election Message handling...
[Thread-3] INFO com.iluwatar.leaderelection.ring.RingInstance - Instance 4 - Election Message: 2,3
[Thread-4] INFO com.iluwatar.leaderelection.AbstractInstance - Instance 5 - Election Message handling...
[Thread-4] INFO com.iluwatar.leaderelection.ring.RingInstance - Instance 5 - Election Message: 2,3,4
[Thread-1] INFO com.iluwatar.leaderelection.AbstractInstance - Instance 2 - Election Message handling...
[Thread-1] INFO com.iluwatar.leaderelection.ring.RingInstance - Instance 2 - Election Message: 2,3,4,5
[Thread-1] INFO com.iluwatar.leaderelection.ring.RingInstance - Instance 2 - New leader should be 2.
[Thread-2] INFO com.iluwatar.leaderelection.AbstractInstance - Instance 3 - Leader Message handling...
[Thread-2] INFO com.iluwatar.leaderelection.ring.RingInstance - Instance 3 - Update leaderID
[Thread-3] INFO com.iluwatar.leaderelection.AbstractInstance - Instance 4 - Leader Message handling...
[Thread-3] INFO com.iluwatar.leaderelection.ring.RingInstance - Instance 4 - Update leaderID
[Thread-4] INFO com.iluwatar.leaderelection.AbstractInstance - Instance 5 - Leader Message handling...
[Thread-4] INFO com.iluwatar.leaderelection.ring.RingInstance - Instance 5 - Update leaderID
[Thread-1] INFO com.iluwatar.leaderelection.AbstractInstance - Instance 2 - Leader Message handling...
[Thread-1] INFO com.iluwatar.leaderelection.ring.RingInstance - Instance 2 - Update leaderID
[Thread-2] INFO com.iluwatar.leaderelection.AbstractInstance - Instance 3 - Leader Message handling...
[Thread-2] INFO com.iluwatar.leaderelection.ring.RingInstance - Instance 3 - Leader update done. Start heartbeat.
[Thread-3] INFO com.iluwatar.leaderelection.AbstractInstance - Instance 4 - Heartbeat Invoke Message handling...
[Thread-3] INFO com.iluwatar.leaderelection.ring.RingInstance - Instance 4- Leader is alive. Start next heartbeat in 5 second.
```
Here the implementation of Bully algorithm
```java
/**
 * Impelemetation with bully algorithm. Each instance should have a sequential id and is able to
 * communicate with other instances in the system. Initially the instance with smallest (or largest)
 * ID is selected to be the leader. All the other instances send heartbeat message to leader
 * periodically to check its health. If one certain instance finds the server done, it will send an
 * election message to all the instances of which the ID is larger. If the target instance is alive,
 * it will return an alive message (in this sample return true) and then send election message with
 * its ID. If not, the original instance will send leader message to all the other instances.
 */
@Slf4j
public class BullyInstance extends AbstractInstance {
  private static final String INSTANCE = "Instance ";

  /**
   * Constructor of BullyInstance.
   */
  public BullyInstance(MessageManager messageManager, int localId, int leaderId) {
    super(messageManager, localId, leaderId);
  }

  /**
   * Process the heartbeat invoke message. After receiving the message, the instance will send a
   * heartbeat to leader to check its health. If alive, it will inform the next instance to do the
   * heartbeat. If not, it will start the election process.
   */
  @Override
  protected void handleHeartbeatInvokeMessage() {
    try {
      boolean isLeaderAlive = messageManager.sendHeartbeatMessage(leaderId);
      if (isLeaderAlive) {
        LOGGER.info(INSTANCE + localId + "- Leader is alive.");
        Thread.sleep(HEARTBEAT_INTERVAL);
        messageManager.sendHeartbeatInvokeMessage(localId);
      } else {
        LOGGER.info(INSTANCE + localId + "- Leader is not alive. Start election.");
        boolean electionResult =
            messageManager.sendElectionMessage(localId, String.valueOf(localId));
        if (electionResult) {
          LOGGER.info(INSTANCE + localId + "- Succeed in election. Start leader notification.");
          messageManager.sendLeaderMessage(localId, localId);
        }
      }
    } catch (InterruptedException e) {
      LOGGER.info(INSTANCE + localId + "- Interrupted.");
    }
  }

  /**
   * Process election invoke message. Send election message to all the instances with smaller ID. If
   * any one of them is alive, do nothing. If no instance alive, send leader message to all the
   * alive instance and restart heartbeat.
   */
  @Override
  protected void handleElectionInvokeMessage() {
    if (!isLeader()) {
      LOGGER.info(INSTANCE + localId + "- Start election.");
      boolean electionResult = messageManager.sendElectionMessage(localId, String.valueOf(localId));
      if (electionResult) {
        LOGGER.info(INSTANCE + localId + "- Succeed in election. Start leader notification.");
        leaderId = localId;
        messageManager.sendLeaderMessage(localId, localId);
        messageManager.sendHeartbeatInvokeMessage(localId);
      }
    }
  }

  /**
   * Process leader message. Update local leader information.
   */
  @Override
  protected void handleLeaderMessage(Message message) {
    leaderId = Integer.valueOf(message.getContent());
    LOGGER.info(INSTANCE + localId + " - Leader update done.");
  }

  private boolean isLeader() {
    return localId == leaderId;
  }

  @Override
  protected void handleLeaderInvokeMessage() {
    // Not used in Bully Instance
  }

  @Override
  protected void handleHeartbeatMessage(Message message) {
    // Not used in Bully Instance
  }

  @Override
  protected void handleElectionMessage(Message message) {
    // Not used in Bully Instance
  }
}
```

```java
/**
 * Implementation of BullyMessageManager.
 */
public class BullyMessageManager extends AbstractMessageManager {

  /**
   * Constructor of BullyMessageManager.
   */
  public BullyMessageManager(Map<Integer, Instance> instanceMap) {
    super(instanceMap);
  }

  /**
   * Send heartbeat message to current leader instance to check the health.
   *
   * @param leaderId leaderID
   * @return {@code true} if the leader is alive.
   */
  @Override
  public boolean sendHeartbeatMessage(int leaderId) {
    var leaderInstance = instanceMap.get(leaderId);
    var alive = leaderInstance.isAlive();
    return alive;
  }

  /**
   * Send election message to all the instances with smaller ID.
   *
   * @param currentId Instance ID of which sends this message.
   * @param content   Election message content.
   * @return {@code true} if no alive instance has smaller ID, so that the election is accepted.
   */
  @Override
  public boolean sendElectionMessage(int currentId, String content) {
    var candidateList = findElectionCandidateInstanceList(currentId);
    if (candidateList.isEmpty()) {
      return true;
    } else {
      var electionMessage = new Message(MessageType.ELECTION_INVOKE, "");
      candidateList.stream().forEach((i) -> instanceMap.get(i).onMessage(electionMessage));
      return false;
    }
  }

  /**
   * Send leader message to all the instances to notify the new leader.
   *
   * @param currentId Instance ID of which sends this message.
   * @param leaderId  Leader message content.
   * @return {@code true} if the message is accepted.
   */
  @Override
  public boolean sendLeaderMessage(int currentId, int leaderId) {
    var leaderMessage = new Message(MessageType.LEADER, String.valueOf(leaderId));
    instanceMap.keySet()
        .stream()
        .filter((i) -> i != currentId)
        .forEach((i) -> instanceMap.get(i).onMessage(leaderMessage));
    return false;
  }

  /**
   * Send heartbeat invoke message to the next instance.
   *
   * @param currentId Instance ID of which sends this message.
   */
  @Override
  public void sendHeartbeatInvokeMessage(int currentId) {
    var nextInstance = this.findNextInstance(currentId);
    var heartbeatInvokeMessage = new Message(MessageType.HEARTBEAT_INVOKE, "");
    nextInstance.onMessage(heartbeatInvokeMessage);
  }

  /**
   * Find all the alive instances with smaller ID than current instance.
   *
   * @param currentId ID of current instance.
   * @return ID list of all the candidate instance.
   */
  private List<Integer> findElectionCandidateInstanceList(int currentId) {
    return instanceMap.keySet()
        .stream()
        .filter((i) -> i < currentId && instanceMap.get(i).isAlive())
        .toList();
  }

}
```

```java
public static void main(String[] args) {

    Map<Integer, Instance> instanceMap = new HashMap<>();
    var messageManager = new BullyMessageManager(instanceMap);

    var instance1 = new BullyInstance(messageManager, 1, 1);
    var instance2 = new BullyInstance(messageManager, 2, 1);
    var instance3 = new BullyInstance(messageManager, 3, 1);
    var instance4 = new BullyInstance(messageManager, 4, 1);
    var instance5 = new BullyInstance(messageManager, 5, 1);

    instanceMap.put(1, instance1);
    instanceMap.put(2, instance2);
    instanceMap.put(3, instance3);
    instanceMap.put(4, instance4);
    instanceMap.put(5, instance5);

    instance4.onMessage(new Message(MessageType.HEARTBEAT_INVOKE, ""));

    final var thread1 = new Thread(instance1);
    final var thread2 = new Thread(instance2);
    final var thread3 = new Thread(instance3);
    final var thread4 = new Thread(instance4);
    final var thread5 = new Thread(instance5);

    thread1.start();
    thread2.start();
    thread3.start();
    thread4.start();
    thread5.start();

    instance1.setAlive(false);
  }
```

The console output
```
[Thread-3] INFO com.iluwatar.leaderelection.AbstractInstance - Instance 4 - Heartbeat Invoke Message handling...
[Thread-3] INFO com.iluwatar.leaderelection.bully.BullyInstance - Instance 4- Leader is alive.
[Thread-4] INFO com.iluwatar.leaderelection.AbstractInstance - Instance 5 - Heartbeat Invoke Message handling...
[Thread-4] INFO com.iluwatar.leaderelection.bully.BullyInstance - Instance 5- Leader is not alive. Start election.
[Thread-3] INFO com.iluwatar.leaderelection.AbstractInstance - Instance 4 - Election Invoke Message handling...
[Thread-2] INFO com.iluwatar.leaderelection.AbstractInstance - Instance 3 - Election Invoke Message handling...
[Thread-3] INFO com.iluwatar.leaderelection.bully.BullyInstance - Instance 4- Start election.
[Thread-2] INFO com.iluwatar.leaderelection.bully.BullyInstance - Instance 3- Start election.
[Thread-2] INFO com.iluwatar.leaderelection.AbstractInstance - Instance 3 - Election Invoke Message handling...
[Thread-2] INFO com.iluwatar.leaderelection.bully.BullyInstance - Instance 3- Start election.
[Thread-1] INFO com.iluwatar.leaderelection.AbstractInstance - Instance 2 - Election Invoke Message handling...
[Thread-1] INFO com.iluwatar.leaderelection.bully.BullyInstance - Instance 2- Start election.
[Thread-1] INFO com.iluwatar.leaderelection.bully.BullyInstance - Instance 2- Succeed in election. Start leader notification.
[Thread-1] INFO com.iluwatar.leaderelection.AbstractInstance - Instance 2 - Election Invoke Message handling...
[Thread-3] INFO com.iluwatar.leaderelection.AbstractInstance - Instance 4 - Leader Message handling...
[Thread-2] INFO com.iluwatar.leaderelection.AbstractInstance - Instance 3 - Leader Message handling...
[Thread-1] INFO com.iluwatar.leaderelection.AbstractInstance - Instance 2 - Election Invoke Message handling...
[Thread-4] INFO com.iluwatar.leaderelection.AbstractInstance - Instance 5 - Leader Message handling...
[Thread-0] INFO com.iluwatar.leaderelection.AbstractInstance - Instance 1 - Leader Message handling...
[Thread-1] INFO com.iluwatar.leaderelection.AbstractInstance - Instance 2 - Election Invoke Message handling...
[Thread-3] INFO com.iluwatar.leaderelection.bully.BullyInstance - Instance 4 - Leader update done.
[Thread-2] INFO com.iluwatar.leaderelection.bully.BullyInstance - Instance 3 - Leader update done.
[Thread-0] INFO com.iluwatar.leaderelection.bully.BullyInstance - Instance 1 - Leader update done.
[Thread-4] INFO com.iluwatar.leaderelection.bully.BullyInstance - Instance 5 - Leader update done.
[Thread-2] INFO com.iluwatar.leaderelection.AbstractInstance - Instance 3 - Heartbeat Invoke Message handling...
[Thread-2] INFO com.iluwatar.leaderelection.bully.BullyInstance - Instance 3- Leader is alive.
```

## Class diagram
![alt text](./etc/leader-election.urm.png "Leader Election pattern class diagram")

## Applicability
Use this pattern when

* the tasks in a distributed application, such as a cloud-hosted solution, require careful coordination and there is no natural leader.

Do not use this pattern when

* there is a natural leader or dedicated process that can always act as the leader. For example, it may be possible to implement a singleton process that coordinates the task instances. If this process fails or becomes unhealthy, the system can shut it down and restart it.
* the coordination between tasks can be easily achieved by using a more lightweight mechanism. For example, if several task instances simply require coordinated access to a shared resource, a preferable solution might be to use optimistic or pessimistic locking to control access to that resource.

## Credits

* [Leader Election pattern](https://docs.microsoft.com/en-us/azure/architecture/patterns/leader-election)
* [Raft Leader Election](https://github.com/ronenhamias/raft-leader-election)
