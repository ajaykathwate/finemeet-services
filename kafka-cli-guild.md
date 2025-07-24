## 🐳 Docker Compose Services Overview

### 1. **Kafka Broker (Container Name: `kafka`)**

* Runs the Kafka broker in KRaft (no Zookeeper).
* Port Mapping:

  * Internal: `9092`
  * Host: `29092`
* Used by other containers or tools to connect to Kafka.

### 2. **Kafka UI (Container Name: `kafka-ui`)**

* Image: `provectuslabs/kafka-ui`
* Port: `9090`
* URL: [http://localhost:9090](http://localhost:9090)

### 3. **Kafka CLI (Container Name: `kafka-cli`)**

* Image: `confluentinc/cp-kafka`
* Used to run Kafka CLI commands (`kafka-topics`, `kafka-console-producer`, etc.)

---

## ✅ How to Access Kafka Services

### 📌 Kafka UI

* Open in browser: [http://localhost:9090](http://localhost:9090)
* View topics, producers, consumers, offsets, partitions, etc.

### 📌 Kafka CLI

Run a shell inside the CLI container:

```bash
docker exec -it kafka-cli bash
```

---

## 🔑 Kafka CLI Quick Reference Guide

### 🔌 1. Bootstrap Server

* **Inside container:** `kafka:9092`
* **Outside container:** `localhost:29092`

---

## 📂 Topic Management

### ✅ Create Topic

```bash
kafka-topics --bootstrap-server kafka:9092 --create \
  --topic my-topic --partitions 3 --replication-factor 1
```

### ✅ List Topics

```bash
kafka-topics --bootstrap-server kafka:9092 --list
```

### ✅ Describe Topic

```bash
kafka-topics --bootstrap-server kafka:9092 --describe --topic my-topic
```

### ✅ Delete Topic

```bash
kafka-topics --bootstrap-server kafka:9092 --delete --topic my-topic
```

---

## 🧑‍💻 Producer & Consumer

### ✅ Start Producer

```bash
kafka-console-producer --broker-list kafka:9092 --topic my-topic
```

Then type messages, hit Enter to send.

### ✅ Start Consumer

```bash
kafka-console-consumer --bootstrap-server kafka:9092 --topic my-topic --from-beginning
```

---

## 🔁 Consumer Groups

### ✅ List Groups

```bash
kafka-consumer-groups --bootstrap-server kafka:9092 --list
```

### ✅ Describe Group

```bash
kafka-consumer-groups --bootstrap-server kafka:9092 --describe --group my-group
```

### ✅ Reset Offsets

```bash
kafka-consumer-groups --bootstrap-server kafka:9092 \
  --group my-group --topic my-topic \
  --reset-offsets --to-earliest --execute
```

---

## 🔧 Utilities

### ✅ Cluster Info

```bash
kafka-metadata-shell --bootstrap-server kafka:9092 --describe-cluster
```

### ✅ List Partitions

```bash
kafka-topics --bootstrap-server kafka:9092 --describe --topic my-topic
```

### ✅ Check Broker Health

```bash
kafka-broker-api-versions --bootstrap-server kafka:9092
```

### ✅ Create Partitions (for existing topic)

```bash
kafka-topics --bootstrap-server kafka:9092 --alter \
  --topic my-topic --partitions 6
```

---

## 📦 Kafka Configuration Files (Optional)

In case you need to inspect or mount configs:

* `server.properties`
* `log4j.properties`
* `kafka-env.sh`

---

## 🔥 Advanced CLI Commands

### ✅ Send JSON Message

```bash
echo '{"id": 1, "message": "Hello"}' | kafka-console-producer \
  --broker-list kafka:9092 --topic json-topic
```

### ✅ Consume Only New Messages

```bash
kafka-console-consumer --bootstrap-server kafka:9092 \
  --topic my-topic --group new-group
```

### ✅ Consume Messages with Key

```bash
kafka-console-producer --broker-list kafka:9092 \
  --topic my-topic --property "parse.key=true" --property "key.separator=:"
```

Type:

```bash
key1:value1
key2:value2
```

### ✅ Consume Keys and Values

```bash
kafka-console-consumer --bootstrap-server kafka:9092 \
  --topic my-topic --property print.key=true --property key.separator=":" --from-beginning
```

---

## 🧠 Notes

* `kafka:9092` is accessible from other containers.
* `localhost:29092` is for your host machine.
* Kafka CLI commands vary slightly by version.
* Always check the topic name, bootstrap address, and whether you're inside or outside Docker.

---

## 🙋‍♂️ FAQ

### Q. How do I check Kafka logs?

```bash
docker logs kafka -f
```

### Q. Can I connect external producers/consumers?

Yes. Use `localhost:29092` as the broker address.

### Q. Can I add more Kafka brokers?

Yes. You can scale brokers using Docker Compose (best for advanced setups).

---