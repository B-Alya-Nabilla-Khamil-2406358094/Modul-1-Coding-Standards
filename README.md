## Reflection 1

### Clean Code and Secure Coding Principles
Dalam pengerjaan fitur-fitur pada modul ini, saya telah menerapkan beberapa prinsip *Clean Code* dan *Secure Coding* untuk menjaga kualitas perangkat lunak. Penerapan paling signifikan adalah **Single Responsibility Principle (SRP)** melalui arsitektur MVC, di mana saya memisahkan tanggung jawab antara `ProductController` (untuk routing dan view), `ProductService` (untuk logika bisnis), dan `ProductRepository` (untuk manipulasi data). Pemisahan ini membuat kode menjadi lebih modular dan mudah dipahami. Selain itu, saya juga berfokus pada penamaan variabel dan metode yang deskriptif (*Meaningful Names*), seperti penggunaan `deleteProductById` yang lebih jelas tujuannya dibandingkan sekadar `delete`. Dari sisi keamanan (*Secure Coding*), saya mengganti penggunaan ID sekuensial (integer) menjadi **UUID (Universally Unique Identifier)**. Langkah ini krusial untuk mencegah serangan enumerasi (*Enumeration Attack*), di mana penyerang bisa menebak ID produk lain dengan mudah jika hanya menggunakan angka urut.

### Source Code Analysis and Improvement
Selama proses pengembangan, saya menemukan beberapa kekurangan pada kode sumber asli dan menerapkan prinsip *Boy Scout Rule* (meninggalkan kode lebih bersih dari saat ditemukan). Salah satu perbaikan utama yang saya lakukan adalah pada **validasi input**. Pada kode asli, *form* jumlah produk (`productQuantity`) masih menggunakan tipe teks biasa tanpa validasi, yang memungkinkan input negatif atau karakter non-angka. Saya memperbaikinya dengan mengubah tipe input HTML menjadi `number` dan menetapkan atribut `min="0"` untuk mencegah input yang tidak logis di sisi klien.

Selain itu, saya juga mengidentifikasi celah stabilitas (*robustness*) di mana aplikasi bisa mengalami *crash* (Whitelabel Error) jika pengguna mengirimkan input kosong. Hal ini menyadarkan saya akan pentingnya validasi sisi server (seperti anotasi `@NotNull` atau `@Min`) dan penanganan eksepsi global (*Global Exception Handling*) agar aplikasi tetap berjalan mulus meski menerima data buruk. Terakhir, saya menyadari bahwa penggunaan **Field Injection** (`@Autowired` langsung pada variabel) yang ada di kode lama kurang ideal untuk *Unit Testing*. Sebagai langkah perbaikan di masa depan, saya berencana menggantinya dengan **Constructor Injection** agar dependensi antar-komponen menjadi lebih eksplisit dan mudah di-*mock* saat pengujian.

---

## Reflection 2

### 1. Unit Testing and Code Coverage
Setelah menulis unit test, saya merasakan manfaat yang signifikan terhadap kualitas kode (*code confidence*). Awalnya pembuatan tes terasa memakan waktu, namun keberadaannya sangat membantu saat saya melakukan perubahan logika atau *refactoring*, karena tes akan langsung mendeteksi jika ada fitur lama yang rusak (*regression*).

Mengenai jumlah unit test dalam satu *class*, menurut saya tidak ada angka baku yang harus dijadikan patokan. Fokus utamanya bukanlah pada kuantitas tes, melainkan pada cakupan skenario yang diuji. Sebuah *class* baru bisa dibilang memiliki tes yang cukup jika telah memverifikasi setidaknya tiga skenario utama: **Positive Case** (alur normal), **Negative Case** (input tidak valid), dan **Edge Case** (batasan nilai ekstrem).

Saya juga menyadari bahwa mencapai **100% Code Coverage tidak menjamin kode tersebut bebas dari bug**. Coverage hanyalah metrik yang menunjukkan baris kode mana yang telah dieksekusi, namun tidak memverifikasi kebenaran logika bisnisnya. Bisa saja semua baris kode dijalankan oleh tes, namun hasil kalkulasi atau logikanya tetap salah karena penulis tes melewatkan skenario tertentu. Oleh karena itu, kualitas tes (*assertion quality*) jauh lebih penting daripada sekadar mengejar angka persentase coverage.

### 2. Clean Code in Functional Testing
Terkait rencana pembuatan *functional test* baru untuk memverifikasi jumlah produk, saya menyadari bahwa menyalin (*copy-paste*) kode setup dan variabel instance dari tes sebelumnya adalah praktik yang buruk. Jika saya melakukan duplikasi kode konfigurasi—seperti setup `WebDriver`, definisi `baseUrl`, dan anotasi port—saya akan menurunkan kualitas kode dan melanggar prinsip **DRY (Don't Repeat Yourself)**.

Masalah utama dari pendekatan duplikasi ini berkaitan erat dengan *maintainability* atau kemudahan pemeliharaan kode dalam jangka panjang. Bayangkan jika di masa depan terdapat perubahan konfigurasi mendasar, misalnya penggantian port server atau perubahan jenis driver Selenium; saya terpaksa harus mengubah setiap file tes satu per satu secara manual. Hal ini tentu sangat tidak efisien dan rentan terhadap kesalahan manusia (*human error*).

Sebagai solusi perbaikan untuk menjaga kebersihan kode (*Clean Code*), saya menyarankan penggunaan teknik **Inheritance (Pewarisan)** atau pembuatan **Base Test Class**. Saya dapat membuat satu kelas induk yang khusus menangani seluruh konfigurasi awal (*setup*) dan inisialisasi driver. Nantinya, kelas tes fungsional baru cukup melakukan *extends* ke kelas induk tersebut tanpa perlu menulis ulang kode setup yang sama. Pendekatan ini membuat struktur kode pengujian menjadi jauh lebih rapi, terpusat, dan mudah dikelola.