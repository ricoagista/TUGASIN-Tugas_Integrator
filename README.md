# 📝 TUGASIN - Task Manager Mahasiswa

**TUGASIN** adalah aplikasi manajemen tugas berbasis desktop yang dirancang khusus untuk mahasiswa guna memantau progres tugas kuliah dengan gaya visual yang modern (Gen Z Aesthetic). Aplikasi ini dibangun menggunakan **Java Swing** dan mengimplementasikan operasi **CRUD** lengkap dengan penyimpanan data permanen.

---

## ✨ Fitur Utama

- **Dashboard**: Tampilan awal yang memberikan motivasi untuk menyelesaikan tugas.
- **Manajemen Tugas (CRUD)**:
    - **Create**: Menambah tugas baru dengan ID otomatis.
    - **Read**: Menampilkan daftar tugas dalam tabel yang bersih.
    - **Update**: Menandai tugas yang sudah selesai langsung dari daftar.
    - **Delete**: Menghapus tugas yang sudah tidak diperlukan.
- **Sistem Filter**: Memisahkan tampilan tugas berdasarkan status: *Semua*, *Belum Selesai*, dan *Sudah Selesai*.
- **Visual Calendar Picker**: Input tanggal yang intuitif menggunakan dialog kalender visual (bukan input manual teks).
- **Persistensi Data**: Semua data tersimpan secara permanen dalam file `database_tugasin.txt`.
- **UI Aesthetic**: Desain antarmuka modern dengan skema warna yang nyaman dan tipografi *Segoe UI Black*.

---

## 🛠️ Teknologi yang Digunakan

- **Bahasa Pemrograman**: Java.
- **GUI Framework**: Java Swing & AWT.
- **Penyimpanan**: Flat File System (.txt).
- **Build Tool**: Maven.

---

## 📂 Struktur Proyek

```text
TUGASIN-Tugas_Integrator/
├── src/
│   └── main/
│       └── java/
│           └── org/example/
│               └── Main.java       # Kode utama aplikasi (GUI & Logika)
├── database_tugasin.txt            # File penyimpanan data tugas
├── pom.xml                         # Konfigurasi Maven
└── README.md                       # Dokumentasi proyek