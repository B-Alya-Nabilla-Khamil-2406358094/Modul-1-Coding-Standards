# Reflection Notes - Advanced Programming

Repositori ini memuat catatan refleksi pembelajaran dari pengerjaan modul-modul pada mata kuliah Pemrograman Lanjut.

---

## Deployment Link
Aplikasi E-Shop telah berhasil di-*deploy* dan dapat diakses melalui tautan berikut:  
**[https://squealing-matilda-alya-nabilla-advprog-2b91929b.koyeb.app/](https://squealing-matilda-alya-nabilla-advprog-2b91929b.koyeb.app/)**

---

## Module 1: Coding Standards

### Reflection 1

#### Clean Code and Secure Coding Principles
Dalam pengerjaan fitur-fitur pada modul ini, saya telah menerapkan beberapa prinsip *Clean Code* dan *Secure Coding* untuk menjaga kualitas perangkat lunak.

* **Single Responsibility Principle (SRP):** Diterapkan melalui arsitektur MVC, di mana saya memisahkan tanggung jawab antara `ProductController` (untuk *routing* dan *view*), `ProductService` (untuk logika bisnis), dan `ProductRepository` (untuk manipulasi data). Pemisahan ini membuat kode menjadi lebih modular dan mudah dipahami.
* **Meaningful Names:** Berfokus pada penamaan variabel dan metode yang deskriptif, seperti penggunaan `deleteProductById` yang lebih jelas tujuannya dibandingkan sekadar `delete`.
* **Secure Coding:** Mengganti penggunaan ID sekuensial (integer) menjadi **UUID (Universally Unique Identifier)**. Langkah ini krusial untuk mencegah serangan enumerasi (*Enumeration Attack*), di mana penyerang bisa menebak ID produk lain dengan mudah jika hanya menggunakan angka urut.

#### Source Code Analysis and Improvement
Selama proses pengembangan, saya menemukan beberapa kekurangan pada kode sumber asli dan menerapkan prinsip **Boy Scout Rule** (meninggalkan kode lebih bersih dari saat ditemukan).

* **Validasi Input:** Pada kode asli, *form* jumlah produk (`productQuantity`) masih menggunakan tipe teks biasa tanpa validasi. Saya memperbaikinya dengan mengubah tipe input HTML menjadi `number` dan menetapkan atribut `min="0"` untuk mencegah input yang tidak logis di sisi klien.
* **Robustness & Error Handling:** Mengidentifikasi celah stabilitas di mana aplikasi bisa mengalami *crash* (Whitelabel Error) jika pengguna mengirimkan input kosong. Hal ini menyadarkan saya akan pentingnya validasi sisi server (`@NotNull` atau `@Min`) dan penanganan eksepsi global (*Global Exception Handling*).
* **Dependency Injection:** Menyadari bahwa penggunaan **Field Injection** (`@Autowired` langsung pada variabel) kurang ideal untuk *Unit Testing*. Ke depannya, saya berencana menggantinya dengan **Constructor Injection** agar dependensi antar-komponen menjadi lebih eksplisit dan mudah di-*mock*.

### Reflection 2

#### 1. Unit Testing and Code Coverage
Setelah menulis *unit test*, saya merasakan peningkatan yang signifikan terhadap *code confidence*. Meskipun pembuatannya memakan waktu, keberadaannya sangat membantu saat *refactoring* untuk mencegah *regression bug*.

Mengenai jumlah tes, tidak ada angka baku yang dijadikan patokan. Fokus utamanya adalah pada cakupan skenario yang diuji: **Positive Case** (alur normal), **Negative Case** (input tidak valid), dan **Edge Case** (batasan nilai ekstrem).

Saya juga menyadari bahwa **100% Code Coverage tidak menjamin kode bebas bug**. *Coverage* hanyalah metrik eksekusi baris kode, bukan verifikasi kebenaran logika bisnis. Kualitas tes (*assertion quality*) jauh lebih penting daripada sekadar mengejar angka persentase.

#### 2. Clean Code in Functional Testing
Terkait pembuatan *functional test* baru, saya menyadari bahwa menyalin kode konfigurasi (*setup WebDriver*, `baseUrl`, *port*) adalah praktik buruk yang melanggar prinsip **DRY (Don't Repeat Yourself)**. Hal ini menurunkan *maintainability* dan rentan terhadap *human error* jika ada perubahan konfigurasi di masa depan.

Sebagai solusi, saya menyarankan penggunaan teknik **Inheritance** dengan membuat **Base Test Class**. Kelas induk ini khusus menangani seluruh konfigurasi awal dan inisialisasi driver, sehingga kelas tes fungsional baru cukup melakukan *extends* tanpa perlu menduplikasi kode *setup*.

---

## Module 2: CI/CD & DevOps

### 1. Code Quality Issues and Fixing Strategy
Selama mengerjakan *exercise* ini, saya menemukan dan memperbaiki beberapa isu *code quality* dan keamanan (*security hotspots*) berdasarkan analisis SonarCloud:

* **Security Hotspots pada Link CDN (HTML):** SonarCloud memberikan peringatan *"Make sure not using resource integrity feature is safe here"* pada file `CreateProduct.html`, `EditProduct.html`, dan `ProductList.html` karena penggunaan library Bootstrap pihak ketiga tanpa pengaman.
  * **Strategi:** Menambahkan atribut `integrity` (berisi *hash* kriptografi) dan `crossorigin="anonymous"` pada elemen `<link>` dan `<script>`. Hal ini mengaktifkan *Subresource Integrity* (SRI) sehingga *browser* akan menolak menjalankan *script* jika file di CDN dimanipulasi pihak luar.
* **Aksesibilitas dan Semantik HTML:** Terdapat peringatan *"Anchor tags should not be used as buttons"* pada `ProductList.html` dan penggunaan *role button* yang kurang tepat pada `homePage.html`.
  * **Strategi:** Memperbaiki struktur HTML dengan elemen `<button>` yang sesungguhnya untuk interaksi klik, serta menambahkan *event listener* berbasis *keyboard* (seperti `onKeyDown`) agar web lebih ramah *screen reader*.
* **Code Smell pada Unit Test:** Ditemukan *method* kosong pada `EshopApplicationTests.java` dan `ProductRepositoryTest.java` yang dapat membingungkan *developer* lain.
  * **Strategi:** Menambahkan *nested comment* di dalam *method* tersebut untuk menjelaskan tujuannya secara eksplisit (misal: hanya untuk memverifikasi *application context load*).

### 2. CI/CD Implementation Evaluation
Menurut saya, implementasi yang saya terapkan saat ini sudah sepenuhnya memenuhi definisi **Continuous Integration (CI)** dan **Continuous Deployment (CD)**.

* **Continuous Integration:** Saya menggunakan GitHub Actions yang secara otomatis menjalankan *test suite* dan menganalisis kualitas kode menggunakan SonarCloud setiap kali ada *push* atau *pull request*. Hal ini memastikan setiap perubahan kode terverifikasi aman sebelum digabungkan.
* **Continuous Deployment:** Saya menggunakan platform Koyeb dengan pendekatan *pull-based* yang terhubung langsung ke repositori GitHub. Setiap kali kode di-*merge* ke *branch* `main`, Koyeb mendeteksi perubahan tersebut dan langsung melakukan *auto-deploy* ke server publik tanpa campur tangan manual.

Siklus otomatis dari tahap pengetesan hingga aplikasi siap diakses oleh pengguna inilah yang mencerminkan praktik CI/CD yang komprehensif.
