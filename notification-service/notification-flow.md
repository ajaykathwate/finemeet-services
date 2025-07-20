## ✅ Kafka Payload (Example)

```json
{
  "recipientId": "user-uuid",
  "channels": ["EMAIL", "PUSH", "SMS"],
  "eventType": "USER_REGISTERED",
  "scheduledAt": null,
  "metadata": {},
  "recipients": [
    {
      "channel": "EMAIL",
      "recipient": "ajay@example.com",
      "content": {
        "templateId": "welcome_email",
        "subject": "Welcome to FineMeet!",
        "templateData": {
          "name": "Ajay",
          "verificationLink": "https://finemeet.in/auth/verify?token=abc123"
        }
      }
    },
    {
      "channel": "PUSH",
      "recipient": "device_token_fcm_xyz",
      "content": {
        "pushTitle": "Event Reminder",
        "pushBody": "You have a meeting at 10 AM"
      }
    },
    {
      "channel": "SMS",
      "recipient": "+919999999999",
      "content": {
        "smsBody": "Welcome Ajay! Your OTP is 123456"
      }
    }
  ]
}
```

---

## 🔁 End-to-End Flow

---

### 🔹 1. Kafka Consumer: `NotificationConsumer::onMessage(NotificationRequest)`

* Triggered when Kafka pushes the message.
* Deserializes JSON into `NotificationRequest` object.
* Passes the object to the service layer:

  ```java
  notificationService.processNotification(NotificationRequest request);
  ```

---

### 🔹 2. Service Layer: `NotificationService::processNotification(NotificationRequest)`

* Delegates creation to builder:

  ```java
  Notification notification = notificationBuilder.build(request);
  ```

---

### 🔹 3. Builder Layer: `NotificationBuilder::build(NotificationRequest)`

**Creates and saves:**

#### 🔸 a. `Notification` entity

```java
Notification notification = new Notification(
    recipientId = request.getRecipientId(),
    eventType = request.getEventType(),
    channels = request.getChannels(),
    metadata = request.getMetadata()
);
notificationRepository.save(notification);
```

#### 🔸 b. `NotificationChannelDelivery` entities (one per channel)

```java
for each recipient in request.getRecipients():
    NotificationChannelDelivery delivery = new NotificationChannelDelivery(
        channel = recipient.getChannel(),
        recipientAddress = recipient.getRecipient(),
        status = PENDING
    );
    delivery.setNotification(notification);
    notificationChannelDeliveryRepository.save(delivery);
```

#### 🔸 c. `NotificationContent` (abstract + concrete class based on channel)

```java
switch (channel):
    case EMAIL:
        EmailNotificationContent content = new EmailNotificationContent(
            subject, email, templateId, templateData
        );
    case PUSH:
        PushNotificationContent content = new PushNotificationContent(
            pushTitle, pushBody, deviceToken
        );
    case SMS:
        SMSNotificationContent content = new SMSNotificationContent(
            smsBody, phoneNumber
        );
content.setNotification(notification);
notificationContentRepository.save(content);
```

---

### 🔹 4. `NotificationService` resumes: Calls `NotificationEngine`

```java
notificationEngine.notify(notification, request.getChannels());
```

---

### 🔹 5. Engine Layer: `NotificationEngine::notify(Notification, List<Channel>)`

For each channel:

#### 🔸 a. Pick Strategy

```java
NotificationStrategy strategy = strategyFactory.getStrategy(channel); // EmailStrategy, PushStrategy, SMSStrategy
```

#### 🔸 b. Fetch Delivery + Content

```java
NotificationChannelDelivery delivery = deliveryRepository.findBy(notification, channel);
NotificationContent content = contentRepository.findBy(notification, channel);
```

#### 🔸 c. Render via Decorator (internally in strategy)

```java
// e.g., EmailStrategy uses ThymeleafTemplateRenderer internally
renderedEmail = renderer.render(templateId, templateData);
```

#### 🔸 d. Send

```java
strategy.send(delivery, content);
```

#### 🔸 e. Update Status

```java
if success:
    delivery.setStatus(SENT);
    delivery.setSentAt(now);
else:
    delivery.setStatus(FAILED);
    delivery.setErrorMessage(ex.getMessage());
```

```java
deliveryRepository.save(delivery);
```

---

## 🧱 What Gets Persisted?

| Table                           | Records                                                                 |
| ------------------------------- | ----------------------------------------------------------------------- |
| `notification`                  | 1 record – overall event info                                           |
| `notification_channel_delivery` | 3 records – one per channel (EMAIL, PUSH, SMS)                          |
| `notification_content`          | 3 records – one per channel, polymorphic subclass used for each content |

---

## 📘 Summary of Involved Classes & Methods

| Class                  | Method                    | Responsibility                                        |
| ---------------------- | ------------------------- | ----------------------------------------------------- |
| `NotificationConsumer` | `onMessage()`             | Kafka consumer entry point                            |
| `NotificationService`  | `processNotification()`   | Main orchestration method                             |
| `NotificationBuilder`  | `build()`                 | Creates and saves Notification + Deliveries + Content |
| `NotificationEngine`   | `notify()`                | Picks strategy and sends                              |
| `EmailStrategy`        | `send(delivery, content)` | Renders & sends email                                 |
| `SMSStrategy`          | `send(delivery, content)` | Sends SMS                                             |
| `PushStrategy`         | `send(delivery, content)` | Sends push notification                               |

---
