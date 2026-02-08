## Reflection 1

### Prinsip Clean Code dan Secure Coding yang Saya Terapkan

Setelah menganalisis source code(dari modul) dan mengerjakan fitur *Edit* serta *Delete*, berikut adalah prinsip-prinsip *Clean Code* dan *Secure Coding* yang telah diterapkan dalam tugas ini:

1.  **Single Responsibility Principle (SRP) & Arsitektur MVC:**
    Kode terorganisir dengan baik menggunakan pola MVC.
    * `ProductController`: Hanya mengurusi *routing* HTTP dan *view*.
    * `ProductService`: Logika bisnis.
    * `ProductRepository`: Manipulasi data (List).
      Pemisahan ini membuat kode mudah dibaca dan dimodifikasi. 
2.  **Meaningful Names (Penamaan yang Jelas):**
    Variabel dan fungsi dinamai dengan tujuan yang jelas.
    * Contoh: `deleteProductById(String productId)` jauh lebih deskriptif daripada sekadar `delete(String id)`.
    * Penggunaan `productQuantity` juga memperjelas bahwa ini merupakan jumlah barang.

3.  **Penggunaan Lombok untuk Mengurangi Boilerplate:**
    Penggunaan anotasi `@Getter` dan `@Setter` pada model `Product.java` menjaga kode tetap ringkas dan bersih dari metode *getter/setter* yang panjang dan repetitif.

4.  **Secure Coding: UUID untuk Identifier:**
    Penggunaan `UUID.randomUUID()` untuk *Product ID* lebih aman daripada menggunakan *integer* berurutan (1, 2, 3). UUID membuat ID produk sulit ditebak oleh pihak yang tidak bertanggung jawab (*Enumeration Attack protection*).

### Evaluasi Kesalahan pada Kode Sumber Asli & Perbaikan yang Saya Lakukan

Saat mengerjakan tugas ini, saya menemukan beberapa kelemahan pada *Source Code Asli* modul dan telah melakukan perbaikan pada kode akhir saya (penerapan *Boy Scout Rule*: meninggalkan kode lebih bersih dari saat ditemukan).

#### 1. Validasi Input HTML & Tipe Data (Secure Coding)
* **Kode Asli:**
  Pada `createProduct.html`, input untuk quantity menggunakan tipe teks biasa:
    ```html
    <input type="text" ... placeholder="Enter product' name">
    ```
  Ini buruk karena pengguna bisa memasukkan huruf atau angka negatif, dan *placeholder*-nya salah (tertulis "name" padahal kolom quantity).
* **Perbaikan Saya:**
  Saya mengubahnya menjadi tipe `number` dan menambahkan validasi minimal 0:
    ```html
    <input type="number" min="0" ... placeholder="Enter product quantity">
    ```
  Ini mencegah input negatif dan memastikan data yang masuk adalah angka (Basic Client-Side Validation).

#### 2. Logika Pembuatan ID (Logic Improvement)
* **Kode Asli:**
  Pada `ProductRepository.java` versi awal, metode `create` langsung menambahkan produk ke list tanpa mengecek atau membuat ID baru.
* **Perbaikan Saya:**
  Saya menambahkan logika pengecekan untuk menjamin setiap produk memiliki ID unik sebelum disimpan:
    ```java
    if (product.getProductId() == null) {
        product.setProductId(UUID.randomUUID().toString());
    }
    ```

#### 4. Keterbatasan yang Masih Ada (Field Injection)
* **Kode Asli & Kode Saya:**
  Baik kode asli maupun kode saya saat ini masih menggunakan *Field Injection* (`@Autowired` langsung di atas variabel).
    ```java
    @Autowired
    private ProductService service;
    ```
* **Rencana Perbaikan:**
  Saya menyadari ini bukan praktik terbaik karena menyulitkan *Unit Testing*. Ke depannya, saya berencana mengubahnya menjadi *Constructor Injection* agar dependensi lebih jelas dan mudah di-*mock* saat pengujian.

#### 5. Validasi Input & Penanganan Error (Robustness)
* **Masalah (Bug):** Saya menemukan celah fatal di mana jika pengguna mengosongkan *Input Field* (Nama atau Quantity) dan menekan Submit, aplikasi akan mengalami *crash* dan menampilkan halaman *Whitelabel Error Page*.

* **Analisis Penyebab:**
  Menurut saya, ini terjadi karena dua hal:
    1.  Kurangnya **Validasi Sisi Server** (Java) untuk menolak input kosong/null sebelum diproses.
    2.  Tidak adanya **Global Error Handling** (Exception Handling) yang menangkap kesalahan saat runtime.

* **Rencana Perbaikan:**
    * **Short-term:** Menambahkan validasi `@NotBlank` dan `@Min(0)` pada Model Product agar data buruk ditolak di awal.
    * **Long-term:** Mengimplementasikan `@ControllerAdvice` atau mekanisme `try-catch` untuk menangani error tak terduga (seperti database down atau data tidak ditemukan), sehingga pengguna melihat halaman pesan error yang ramah (*User Friendly*) alih-alih *Stack Trace* kode program.