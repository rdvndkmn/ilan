# İlan Sistemi — Ad Service

Spring Boot tabanlı ilan yönetim servisidir. Kullanıcı kaydı, Basic Authentication, ilan oluşturma/güncelleme, admin onay-red işlemleri, son onaylanan ilanların listelenmesi ve RabbitMQ entegrasyonunu içerir.

## Projenin Amacı

Bu servis ilan sisteminin ana backend uygulamasıdır.

Sistemde kullanıcılar:

- Kayıt olabilir.
- Basic Authentication ile doğrulanabilir.
- İlan oluşturabilir.
- Kendi ilanlarını güncelleyebilir.
- Onaylanan ilanları görüntüleyebilir.
- İlan detaylarını görüntüleyebilir.

Admin kullanıcılar:

- Pasif ilanları listeleyebilir.
- İlanları onaylayabilir.
- İlanları reddedebilir.

Yeni oluşturulan veya güncellenen ilanlar otomatik olarak `PASSIVE` durumuna alınır.

Admin tarafından onaylanan ilanların ID bilgisi RabbitMQ kuyruğuna gönderilir.

---

## Kullanılan Teknolojiler

- Java 21
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- Spring Security
- Basic Authentication
- BCrypt
- PostgreSQL
- RabbitMQ
- Spring AMQP
- Maven
- Docker
- Lombok

---

## Mimari

```text
React Frontend
      |
      | HTTP / Basic Auth
      v
+-----------------------+
|      Ad Service       |
|     Spring Boot       |
+-----------------------+
      |
      +-------------> PostgreSQL
      |
      +-------------> RabbitMQ
                           |
                           v
                  Reporting Service
```

Ad Service kullanıcı, güvenlik ve ilan işlemlerinin ana merkezidir.

---

## Temel İş Kuralları

### Kullanıcı

Kullanıcı kayıt sırasında aşağıdaki bilgileri sağlar:

- Ad
- Soyad
- Telefon
- Email
- Şifre

Şifreler BCrypt kullanılarak hashlenerek saklanır.

---

## İlan Durumları

İlanların üç farklı durumu vardır:

```text
PASSIVE
APPROVED
REJECTED
```

Yeni ilan:

```text
POST /api/ads
        |
        v
     PASSIVE
```

İlan güncellendiğinde:

```text
PUT /api/ads/{id}
        |
        v
     PASSIVE
```

Admin onayladığında:

```text
PASSIVE
   |
   v
APPROVED
```

Admin reddettiğinde:

```text
PASSIVE
   |
   v
REJECTED
```

---

## Authentication ve Authorization

Sistem Basic Authentication kullanmaktadır.

Örnek HTTP header:

```text
Authorization: Basic <credentials>
```

Roller:

```text
USER
ADMIN
```

Admin endpointleri yalnızca `ADMIN` rolüne açıktır.

---

## API Endpointleri

### Kullanıcı Kaydı

```http
POST /api/users/register
```

Authentication gerektirmez.

---

### Giriş Yapan Kullanıcı Bilgisi

```http
GET /api/users/me
```

Authenticated kullanıcının email ve rol bilgisini döndürür.

---

### İlan Oluşturma

```http
POST /api/ads
```

Yeni ilan oluşturur.

Yeni ilan otomatik olarak:

```text
PASSIVE
```

durumunda oluşturulur.

---

### İlan Güncelleme

```http
PUT /api/ads/{id}
```

Kullanıcı yalnızca kendi ilanını güncelleyebilir.

Güncellenen ilan tekrar:

```text
PASSIVE
```

durumuna alınır.

---

### İlan Detayı

```http
GET /api/ads/{id}
```

İlan detayını getirir.

İlan görüntülendiğinde `viewCount` değeri artırılır.

---

### Son 10 Onaylı İlan

```http
GET /api/ads/latest
```

Son onaylanan 10 ilanı getirir.

---

### İlan Arama

İlanlar şehir ve fiyat bilgilerine göre filtrelenebilir.

```http
GET /api/ads
```

Desteklenen filtrelerden bazıları:

```text
city
minPrice
maxPrice
```

---

## Admin Endpointleri

### Pasif İlanları Listeleme

```http
GET /api/admin/ads/passive
```

Sadece `ADMIN` rolü erişebilir.

---

### İlan Onaylama

```http
PUT /api/admin/ads/{id}/approve
```

İlan:

```text
PASSIVE → APPROVED
```

durumuna geçer.

Onaylanan ilanın ID bilgisi RabbitMQ kuyruğuna gönderilir.

---

### İlan Reddetme

```http
PUT /api/admin/ads/{id}/reject
```

İlan:

```text
PASSIVE → REJECTED
```

durumuna geçer.

---

## RabbitMQ

Onaylanan ilanlar aşağıdaki kuyruğa gönderilir:

```text
ad-approval-queue
```

Akış:

```text
Admin Approve
     |
     v
Ad Service
     |
     v
RabbitMQ
     |
     v
ad-approval-queue
     |
     v
Reporting Service
```

---

## PostgreSQL

Ana uygulama PostgreSQL kullanır.

Örnek bağlantı:

```text
jdbc:postgresql://localhost:5432/ilan
```

Database kullanıcı bilgileri environment variable veya local configuration üzerinden verilmelidir.

Şifre gibi hassas bilgiler Git repository içerisine eklenmemelidir.

---

## CORS

React frontend Vite üzerinden:

```text
http://localhost:5173
```

adresinde çalıştığı için backend üzerinde CORS yapılandırması bulunmaktadır.

İzin verilen temel işlemler:

```text
GET
POST
PUT
DELETE
OPTIONS
```

İzin verilen önemli headerlar:

```text
Authorization
Content-Type
```

---

## RabbitMQ Docker

RabbitMQ Docker container ile çalıştırılabilir.

Kullanılan portlar:

```text
5672  → AMQP
15672 → Management UI
```

RabbitMQ Management:

```text
http://localhost:15672
```

---

## Uygulamayı Çalıştırma

Öncelikle PostgreSQL ve RabbitMQ çalışıyor olmalıdır.

Daha sonra gerekli environment variable değerlerini tanımlayın.

Örnek:

```text
DB_PASSWORD
RABBITMQ_USERNAME
RABBITMQ_PASSWORD
```

Ardından proje IntelliJ IDEA üzerinden çalıştırılabilir.

Ana uygulama:

```text
IlanApplication
```

Backend varsayılan olarak:

```text
http://localhost:8080
```

adresinde çalışır.

---

## Sistem Akışı

```text
Kullanıcı Kaydı
      |
      v
Login
      |
      v
İlan Oluştur
      |
      v
PASSIVE
      |
      v
İlan Güncelle
      |
      v
PASSIVE
      |
      v
Admin Onayı
      |
      v
APPROVED
      |
      +--------> RabbitMQ
      |
      v
Son 10 İlan
```

---

## Güvenlik

Frontend üzerinde admin panelinin gizlenmesi tek başına güvenlik sağlamaz.

Gerçek yetkilendirme Spring Security tarafından backend seviyesinde uygulanmaktadır.

Normal bir `USER`, `/api/admin/**` endpointlerine erişmeye çalıştığında yetki verilmez.

---

## Proje Durumu

- [x] Kullanıcı kaydı
- [x] BCrypt password hashing
- [x] Basic Authentication
- [x] USER / ADMIN rolleri
- [x] Admin kullanıcı
- [x] İlan oluşturma
- [x] İlan güncelleme
- [x] İlan sahibi kontrolü
- [x] PASSIVE ilan akışı
- [x] Admin onaylama
- [x] Admin reddetme
- [x] Pasif ilan listesi
- [x] Son 10 onaylı ilan
- [x] View count
- [x] RabbitMQ producer
- [x] Reporting Service entegrasyonu
- [x] React frontend entegrasyonu
- [x] CORS yapılandırması
- [x] Uçtan uca sistem testi
