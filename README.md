# MABAR.CU

## Deskripsi Aplikasi

MABAR.CU adalah aplikasi desktop berbasis JavaFX yang dirancang untuk membantu pemain game online menemukan teman bermain (mabar) berdasarkan game, rank, dan role yang dibutuhkan. Aplikasi ini juga menyediakan fitur Party Room, Community Chat, Friendlist, serta Profile Management untuk meningkatkan pengalaman bermain secara bersama-sama.

Aplikasi dibangun menggunakan JavaFX sebagai antarmuka pengguna, Spring Boot sebagai backend, H2 Database sebagai penyimpanan data, serta JPA/Hibernate untuk pengelolaan database.

---

## Fitur Utama

### Authentication

* Register akun
* Login dan Logout
* Password terenkripsi menggunakan BCrypt

### Dashboard

* Menampilkan pemain online dan offline
* Notifikasi pengguna
* Riwayat aktivitas akun

### Matchmaking

* Mencari pemain berdasarkan game
* Mencari pemain berdasarkan rank
* Mencari pemain berdasarkan role

### Party Room

* Membuat room mabar
* Menentukan game, rank, role, dan jumlah pemain
* Join dan leave room
* Chat khusus di dalam room

### Community Chat

* Channel komunitas berdasarkan game
* Chat antar pemain dalam komunitas game yang sama

### Profile Management

* Edit profil pemain
* Mengatur game favorit
* Mengatur rank dan role
* Mengatur informasi pengguna

---

## Teknologi yang Digunakan

* Java 17
* JavaFX
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate ORM
* H2 Database
* Maven

---

## Cara Menjalankan Aplikasi

### Prasyarat

Pastikan telah menginstal:

* JDK 17 atau lebih baru
* Maven 3.9+
* Git (opsional)

### Langkah Instalasi

1. Clone repository:

```bash
git clone https://github.com/username/MABARCU.git
```

2. Masuk ke folder project:

```bash
cd MABARCU
```

3. Jalankan aplikasi:

```bash
mvn clean javafx:run
```

4. Aplikasi akan otomatis:

   * Menjalankan Spring Boot Backend
   * Membuat Database H2
   * Membuka antarmuka JavaFX

---

## Struktur Arsitektur

```text
JavaFX (View)
      ↓
Controller
      ↓
Service
      ↓
Repository
      ↓
H2 Database
```

Arsitektur yang digunakan menerapkan pola MVC (Model-View-Controller) dengan pemisahan Service dan Repository.

---

## Penerapan Pemrograman Berorientasi Objek

Aplikasi ini menerapkan empat pilar PBO:

* Encapsulation
* Inheritance
* Polymorphism
* Abstraction

Selain itu juga menerapkan:

* MVC Architecture
* Validation
* Security
* ORM dengan JPA/Hibernate

---

## Video Presentasi

Link Video YouTube:

https://youtube.com/ISI_LINK_VIDEO_PRESENTASI_DISINI
