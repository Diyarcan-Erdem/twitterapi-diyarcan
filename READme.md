Twitter API (Spring Boot + PostgreSQL) – Proje Notlarım
Proje Amacı

Bu projenin amacı, Spring Boot ile öğrendiğim konuları tek bir gerçekçi senaryoda pratik etmekti. Senaryo olarak da “Twitter’ı biz yazsaydık nasıl tasarlardık?” sorusunu seçtim.

Bu projede sadece endpoint yazmak değil; veritabanı tasarımı, katmanlı mimari, validasyon, exception handling, security, unit test, ve frontend–backend entegrasyonu (CORS) gibi bir backend projesinin temel taşlarını uçtan uca deneyimlemeyi hedefledim.

Kullanılan Teknolojiler

Java + Spring Boot

Spring Web (REST API)

Spring Data JPA (Hibernate)

PostgreSQL

Spring Security

Jakarta Validation

Lombok

(Fullstack kısmı) React (3200 portundan çalıştırıldı)

Mimari Yaklaşım

Projeyi klasik katmanlı mimari ile geliştirdim:

Entity: Veritabanı tabloları ve ilişkiler

Repository: DB işlemleri (JPA)

Service: İş kuralları ve kontrol mantığı

Controller: HTTP endpoint’ler

Global Exception Handler: Hataları tek noktadan yönetmek için

Not: Bu yaklaşım sayesinde kod okunabilirliği, test edilebilirlik ve bakım kolaylığı ciddi şekilde arttı.

Veritabanı Tasarımı (Özet)

Projede temel “Twitter” varlıklarını modelledim:

User: kullanıcı bilgileri (username, password)

Tweet: tweet içeriği ve sahibinin bilgisi (user ilişkisi)

Comment: tweet’e yapılan yorum (tweet + user ilişkisi)

Like / Dislike: kullanıcı–tweet ilişkisi üzerinden beğeni yönetimi

Retweet: bir tweet’in başka kullanıcı tarafından tekrar paylaşılması

Not: Şifreler veritabanında plain text tutulmuyor, BCrypt hash olarak saklanıyor.
JSON çıktılarında şifre alanının görünmemesi için entity üzerinde gizleme yapıldı.

Endpointler

Aşağıdaki endpointler proje hedefindeki gereksinimlere göre hazırlanmıştır.

Auth (Ek Zorunluluk)

POST /auth/register → kullanıcı kaydı

POST /auth/login → kullanıcı girişi

Not: Security katmanını Spring Security ile yönettim. Login tarafında şifre doğrulaması passwordEncoder.matches(...) ile yapılıyor.

Tweet (EASY)

POST /tweet → Tweet oluşturur (anonim tweet yok, user zorunlu)

GET /tweet/findByUserId?userId=1 → Kullanıcının tweetlerini getirir

GET /tweet/findById?id=1 → Tweet detayını getirir

PUT /tweet/{id} → Tweet günceller

DELETE /tweet/{id} → Tweet siler (sadece tweet sahibi silebilir)

Not: Delete işleminde “tweet sahibi kontrolü” yapıldı. Sahibi değilse “Unauthorized” hatası döner.

Comment (MEDIUM)

POST /comment → Tweet’e yorum ekler

PUT /comment/{id} → Yorumu günceller

DELETE /comment/{id} → Yorumu siler
(sadece tweet sahibi veya yorum sahibi silebilir)

Not: Comment entity’sinde sonsuz JSON döngüsünü engellemek için @JsonIgnoreProperties kullanıldı.

Like / Dislike (MEDIUM)

POST /like → Tweet’e like atar

POST /dislike → Daha önce atılan like’ı kaldırır

Not: Aynı kullanıcı aynı tweete tekrar like atarsa kontrol mekanizması ile engellendi.

Retweet (HARD)

POST /retweet → Tweet’i retweet eder

DELETE /retweet/{id} → Retweet’i siler

Not: Retweet işlemi tekrar denendiğinde “Already retweeted” tarzı bir kontrol ile duplicate durumlar engellendi.

Global Exception Handling

Projede hata yönetimini tek bir merkezde topladım. Amaç:

Her yerde try-catch yazmamak

Standart hata formatı döndürmek

Frontend/Postman tarafında okunabilir hata mesajları sağlamak

Örnek hata formatı:

{
"error": "Conflict",
"message": "Username already exists",
"timestamp": "...",
"status": 409
}

Validasyonlar

Entity alanlarında validation kullandım:

@NotBlank

@Size(...)

Not: Böylece boş username, kısa password, 280 karakteri aşan tweet/comment gibi durumlar DB’ye gitmeden engellendi.

Unit Test

Projede yazılmış fonksiyonların en az %30’u için unit test yazma hedefi vardı.

Not: En kritik iş kurallarını (register/login, bulunamayan kayıtlar, owner kontrolü gibi) test ederek başladım.
Testleri arttırmak için iyi bir yol: Service katmanını test edip repository’leri mocklamak.

Fullstack (React) + CORS Deneyi

Bu projede sadece backend değil, küçük bir React arayüzüyle CORS problemini bilerek yaşayıp çözmeyi de hedefledim.

React uygulaması 3200 portundan çalıştırıldı.

Backend endpointlerinden birine istek atıldı:

GET http://localhost:3000/tweet/findByUserId?userId=1

CORS Problemi Nasıl Görüldü?

React farklı portta çalıştığı için tarayıcı CORS güvenlik kuralı gereği isteği engelleyebilir ve console’da şu tarz hata çıkabilir:

CORS policy error

CORS Nasıl Çözüldü?

Backend tarafında CORS ayarı eklenerek React’ın geldiği origin’e izin verildi.

Not: CORS’u “herkese açık (*)” yapmak yerine, sadece geliştirme origin’i (ör. http://localhost:3200) izinli olacak şekilde yönetmek daha doğru.

Projeyi Çalıştırma
Backend

PostgreSQL çalışır durumda olmalı

application.properties içinde DB ayarları doğru olmalı

Spring Boot uygulamasını çalıştır:

IntelliJ üzerinden TwitterapiApplication Run

Frontend (React)

React proje klasörüne gir

Bağımlılıkları yükle:

npm install

Çalıştır:

npm start

Not: Windows’ta npm.ps1 script hatası alırsan PowerShell execution policy ayarı gerekebilir. Alternatif olarak CMD ile çalıştırmak da çözüm olabilir.