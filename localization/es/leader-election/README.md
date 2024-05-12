---
title: Leader Election
category: Behavioral
language: es
tag:
 - Cloud distributed
---

## Propósito
El patrón de elección (Leader pattern) del líder se utiliza habitualmente en el diseño de sistemas en la nube. Puede ayudar a garantizar que las instancias de tarea seleccionen la instancia líder correctamente y no entren en conflicto entre sí, causen contención por recursos compartidos o interfieran inadvertidamente con el trabajo que otras instancias de tarea están realizando.

## Explicación

Ejemplo del mundo real
> En un sistema basado en la nube de escalado horizontal, múltiples instancias de la misma tarea podrían estar ejecutándose al mismo tiempo con cada instancia sirviendo a un usuario diferente. Si estas instancias escriben en un recurso compartido, es necesario coordinar sus acciones para evitar que cada instancia sobrescriba los cambios realizados por las demás. En otro escenario, si las tareas están realizando elementos individuales de un cálculo complejo en paralelo, los resultados necesitan ser agregados cuando todos ellos se completan.

En palabras sencillas
> este patrón se utiliza en sistemas distribuidos basados en la nube en los que el líder debe actuar como coordinador/agregador entre instancias de escalado horizontal para evitar conflictos en los recursos compartidos.

Wikipedia dice
> En computación distribuida, la elección del líder es el proceso de designar un único proceso como organizador de alguna tarea distribuida entre varios ordenadores (nodos).

**Ejemplo programático**

El algoritmo Ring y el algoritmo Bully implementan el patrón de elección de líder.
Cada algoritmo requiere que cada instancia participante tenga un ID único.

```java
public interface Instance {

  /**
   * Comprueba si la instancia está viva o no.
   *
   * @return {@code true} si la instancia está viva.
   */
  boolean isAlive();

  /**
   * Establece el estado de salud de la instancia en cuestión.
   *
   * @param alive {@code true} por vivo.
   */
  void setAlive(boolean alive);

  /**
   * Consumir mensajes de otras instancias.
   *
   * @param message Mensaje enviado por otras instancias
   */
  void onMessage(Message message);

}
```

Clase abstracta que implementa las interfaces Instance y Runnable.

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
   * Constructor de BullyInstance.
   */
  public AbstractInstance(MessageManager messageManager, int localId, int leaderId) {
    this.messageManager = messageManager;
    this.messageQueue = new ConcurrentLinkedQueue<>();
    this.localId = localId;
    this.leaderId = leaderId;
    this.alive = true;
  }

  /**
   * La instancia ejecutará el mensaje en su cola de mensajes periódicamente una vez que esté viva.
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
   * Una vez que los mensajes son enviados a una instancia determinada, se añaden a la cola y esperan a ser ejecutados.
   *
   * @param message Mensaje enviado por otras instancias
   */
  @Override
  public void onMessage(Message message) {
    messageQueue.offer(message);
  }

  /**
   * Comprueba si la instancia está viva o no.
   *
   * @return {@code true} si la instancia está viva.
   */
  @Override
  public boolean isAlive() {
    return alive;
  }

  /**
   * Establece el estado de salud de la instancia en cuestión.
   *
   * @param alive {@code true} por vivo.
   */
  @Override
  public void setAlive(boolean alive) {
    this.alive = alive;
  }

  /**
   * Procesa el mensaje según su tipo.
   *
   * @param message Mensaje sondeado desde la cola.
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
   * Métodos abstractos para manejar diferentes tipos de mensajes. 
   * Estos métodos deben implementarse en la clase de instancia concreta para implementar el patrón de selección de líder correspondiente.
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
 * Mensaje utilizado para transportar datos entre instancias.
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
   * Envía un mensaje heartbeat a la instancia líder para comprobar si la instancia líder está viva.
   *
   * @param leaderId ID de instancia de la instancia líder.
   * @return {@code true} si la instancia líder está viva, o {@code false} si no.
   */
  boolean sendHeartbeatMessage(int leaderId);

  /**
   * Enviar mensaje electoral a otras instancias.
   *
   * @param currentId ID de la instancia que envía este mensaje.
   * @param content Contenido del mensaje electoral.
   * @return {@code true} si el mensaje es aceptado por las instancias de destino.
   */
  boolean sendElectionMessage(int currentId, String content);

  /**
   * Enviar mensaje de notificación de nuevo líder a otras instancias.
   *
   * @param currentId ID de la instancia que envía este mensaje.
   * @param leaderId  Contenido del mensaje del líder.
   * @return {@code true} si el mensaje es aceptado por las instancias de destino.
   */
  boolean sendLeaderMessage(int currentId, int leaderId);

  /**
   * Enviar mensaje de invocación de heartbeat. Esto invocará la tarea heartbeat en la instancia de destino.
   *
   * @param currentId ID de la instancia que envía este mensaje.
   */
  void sendHeartbeatInvokeMessage(int currentId);

}
```
These type of messages are used to pass among instances.
```java
/**
 * Enum de tipo de mensaje.
 */
public enum MessageType {

  /**
   * Comienza la elección. El contenido del mensaje almacena ID(s) de la(s) instancia(s) candidata(s).
   */
  ELECTION,

  /**
   * Nodifica al nuevo líder. El contenido del mensaje debe ser el ID del líder.
   */
  LEADER,

  /**
   * Comprueba la salud de la instancia de líder actual.
   */
  HEARTBEAT,

  /**
   * Informar a la instancia de destino para iniciar la elección.
   */
  ELECTION_INVOKE,

  /**
   * Informar a la instancia de destino para que notifique a todas las demás instancias que es el nuevo líder.
   */
  LEADER_INVOKE,

  /**
   * Informar a la instancia de destino para que inicie el heartbeat.
   */
  HEARTBEAT_INVOKE

}
```

Clase abstracta que implementa la interfaz MessageManager. Ayuda a gestionar mensajes entre instancias.

```java
/**
 * Clase abstracta de todas las clases de gestores de mensajes.
 */
public abstract class AbstractMessageManager implements MessageManager {

  /**
   * Contiene todas las instancias del sistema. La clave es su ID y el valor es la propia instancia.   
   */
  
  protected Map<Integer, Instance> instanceMap;

  /**
   * Constructor de AbstractMessageManager.
   */
  public AbstractMessageManager(Map<Integer, Instance> instanceMap) {
    this.instanceMap = instanceMap;
  }

  /**
   * Encuentra la siguiente instancia con el ID más pequeño.
   *
   * @return La siguiente instancia.
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
 * Implementación con algoritmo token ring. Las instancias en el sistema se organizan como un anillo.
 * Cada instancia debe tener un id secuencial y la instancia con el id más pequeño (o más grande) debe
 * ser el líder inicial. Todas las demás instancias envían un mensaje heartbeat al líder periódicamente para
 * comprobar su salud. Si una instancia determinada encuentra que el servidor ha terminado, enviará un mensaje de elección
 * a la siguiente instancia viva en el anillo, que contiene su propio ID. Entonces la siguiente instancia añade su
 * ID en el mensaje y se lo pasa a la siguiente. Después de que todos los ID de las instancias vivas se añaden al * mensaje, el mensaje se devuelve.
 * mensaje, el mensaje es enviado de vuelta a la primera instancia y ésta elegirá la instancia con
 * menor ID para ser el nuevo líder, y entonces enviará un mensaje de líder a otras instancias para informar del
 * resultado.
 */
@Slf4j
public class RingInstance extends AbstractInstance {
  private static final String INSTANCE = "Instance ";

  /**
   * Constructor de RingInstance.
   */
  public RingInstance(MessageManager messageManager, int localId, int leaderId) {
    super(messageManager, localId, leaderId);
  }

  /**
   * Procesar el mensaje de invocación heartbeat. Después de recibir el mensaje, la instancia enviará un
   * heartbeat (latido) al líder para comprobar su salud. Si está vivo, informará a la siguiente instancia para hacer el
   * heartbeat. Si no, iniciará el proceso de elección.
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
   * Procesar mensaje de elección. Si el ID local está contenido en la lista de ID, la instancia seleccionará
   * la instancia viva con menor ID para ser el nuevo líder, y enviará el mensaje de información al líder.
   * Si no, añadirá su ID local a la lista y enviará el mensaje a la siguiente instancia en el anillo.
   * anillo.
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
   * Procesar mensaje de líder. La instancia establecerá el ID de líder para que sea el nuevo y enviará el
   * mensaje a la siguiente instancia hasta que todas las instancias vivas del anillo estén informadas.
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
   * No se utiliza en la instancia Ring.
   */
  @Override
  protected void handleLeaderInvokeMessage() {
      // No se utiliza en la instancia Ring.
  }

  @Override
  protected void handleHeartbeatMessage(Message message) {
      // No se utiliza en la instancia Ring.
  }

  @Override
  protected void handleElectionInvokeMessage() {
      // No se utiliza en la instancia Ring.
  }

}
```

```java
/**
 * Implementación de RingMessageManager.
 */
public class RingMessageManager extends AbstractMessageManager {

  /**
   * Constructor de RingMessageManager.
   */
  public RingMessageManager(Map<Integer, Instance> instanceMap) {
    super(instanceMap);
  }

  /**
   * Envía un mensaje de heartbeat a la instancia líder actual para comprobar su estado.
   *
   * @param leaderId leaderID
   * @return {@code true} si el líder está vivo.
   */
  @Override
  public boolean sendHeartbeatMessage(int leaderId) {
    var leaderInstance = instanceMap.get(leaderId);
    var alive = leaderInstance.isAlive();
    return alive;
  }

  /**
   * Enviar mensaje de elección a la siguiente instancia.
   *
   * @param currentId currentID
   * @param content   contiene todos los ID de las instancias que han recibido este mensaje de elección
   *                  mensaje.
   * @return {@code true} si el mensaje de elección es aceptado por la instancia de destino.
   */
  @Override
  public boolean sendElectionMessage(int currentId, String content) {
    var nextInstance = this.findNextInstance(currentId);
    var electionMessage = new Message(MessageType.ELECTION, content);
    nextInstance.onMessage(electionMessage);
    return true;
  }

  /**
   * Enviar mensaje de líder a la siguiente instancia.
   *
   * @param currentId ID de la instancia que envía este mensaje.
   * @param leaderId Contenido del mensaje del líder.
   * @return {@code true} si el mensaje de líder es aceptado por la instancia de destino.
   */
  @Override
  public boolean sendLeaderMessage(int currentId, int leaderId) {
    var nextInstance = this.findNextInstance(currentId);
    var leaderMessage = new Message(MessageType.LEADER, String.valueOf(leaderId));
    nextInstance.onMessage(leaderMessage);
    return true;
  }

  /**
   * Enviar mensaje de invocación de heartbeat a la siguiente instancia.
   *
   * @param currentId ID de la instancia que envía este mensaje.
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
 * Impelemetación con algoritmo bully. Cada instancia debe tener un id secuencial y es capaz de
 * comunicarse con otras instancias del sistema. Inicialmente la instancia con menor (o mayor)
 * ID más pequeño (o más grande) es seleccionado para ser el líder. Todas las demás instancias envían un mensaje heartbeat al líder
 * periódicamente para comprobar su salud. Si una instancia determinada considera que el servidor ha terminado, enviará un mensaje de elección a todas las instancias del sistema.
 * mensaje de elección a todas las instancias cuyo ID sea mayor. Si la instancia objetivo está viva,
 * devolverá un mensaje alive (en este ejemplo devolverá true) y entonces enviará un mensaje de elección con
 * su ID. Si no, la instancia original enviará un mensaje de líder a todas las demás instancias.
 */
@Slf4j
public class BullyInstance extends AbstractInstance {
  private static final String INSTANCE = "Instance ";

  /**
   * Constructor de BullyInstance.
   */
  public BullyInstance(MessageManager messageManager, int localId, int leaderId) {
    super(messageManager, localId, leaderId);
  }

  /**
   * Procesar el mensaje de invocación heartbeat. Después de recibir el mensaje, la instancia enviará un
   * heartbeat al líder para comprobar su salud. Si está vivo, informará a la siguiente instancia para hacer el
   * heartbeat. Si no, iniciará el proceso de elección.
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
   * Procesar mensaje de invocación de elección. Enviar mensaje de elección a todas las instancias con menor ID. Si
   * alguna de ellas está viva, no hacer nada. Si ninguna instancia está viva, enviar mensaje de líder a todas las instancias vivas y reiniciar el heartbeat.
   * instancia viva y reiniciar heartbeat.
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
   * Procesar mensaje de líder. Actualizar la información del líder local.
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
 * Implementación de BullyMessageManager.
 */
public class BullyMessageManager extends AbstractMessageManager {

  /**
   *  Constructor de BullyMessageManager.
   */
  public BullyMessageManager(Map<Integer, Instance> instanceMap) {
    super(instanceMap);
  }

  /**
   * Envía un mensaje de heartbeat a la instancia líder actual para comprobar su estado.
   *
   * @param leaderId leaderID
   * @return {@code true} si el líder está vivo.
   */
  @Override
  public boolean sendHeartbeatMessage(int leaderId) {
    var leaderInstance = instanceMap.get(leaderId);
    var alive = leaderInstance.isAlive();
    return alive;
  }

  /**
   * Enviar mensaje electoral a todas las instancias con menor ID.
   *
   * @param currentId ID de la instancia que envía este mensaje.
   * @param content Contenido del mensaje electoral.
   * @return {@code true} si ninguna instancia viva tiene menor ID, para que se acepte la elección.
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
   * Enviar mensaje de líder a todas las instancias para notificar el nuevo líder.
   *
   * @param currentId ID de la instancia que envía este mensaje.
   * @param leaderId Contenido del mensaje del líder.
   * @return {@code true} si se acepta el mensaje.
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
   * Enviar mensaje de invocación de heartbeat a la siguiente instancia.
   *
   * @param currentId ID de la instancia que envía este mensaje.
   */
  @Override
  public void sendHeartbeatInvokeMessage(int currentId) {
    var nextInstance = this.findNextInstance(currentId);
    var heartbeatInvokeMessage = new Message(MessageType.HEARTBEAT_INVOKE, "");
    nextInstance.onMessage(heartbeatInvokeMessage);
  }

  /**
   * Encuentra todas las instancias vivas con un ID menor que la instancia actual.
   *
   * @param currentId ID de la instancia actual.
   * @return Lista de ID de todas las instancias candidatas.
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

La salida de la consola
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

## Diagrama de clases
![alt text](./etc/leader-election.urm.png "Leader Election pattern class diagram")

## Aplicabilidad
Utilice este patrón cuando

* las tareas en una aplicación distribuida, como una solución alojada en la nube, requieren una coordinación cuidadosa y no hay un líder natural.

No utilice este patrón cuando

* exista un líder natural o un proceso dedicado que pueda actuar siempre como líder. Por ejemplo, puede ser posible implementar un proceso singleton que coordine las instancias de tareas. Si este proceso falla o se vuelve insalubre, el sistema puede apagarlo y reiniciarlo.
* la coordinación entre tareas puede lograrse fácilmente utilizando un mecanismo más ligero. Por ejemplo, si varias instancias de tareas simplemente requieren un acceso coordinado a un recurso compartido, una solución preferible podría ser utilizar un bloqueo optimista o pesimista para controlar el acceso a ese recurso.

## Créditos

* [Leader Election pattern](https://docs.microsoft.com/en-us/azure/architecture/patterns/leader-election)
* [Raft Leader Election](https://github.com/ronenhamias/raft-leader-election)
