# 📝 TUGASIN - Task Manager Mahasiswa

**TUGASIN** adalah aplikasi manajemen tugas berbasis desktop yang dirancang khusus untuk mahasiswa guna memantau progres tugas kuliah dengan gaya visual yang modern (Gen Z Aesthetic). Aplikasi ini dibangun menggunakan **Java Swing** dengan arsitektur **Object-Oriented Programming (OOP)** yang terstruktur dan mengimplementasikan operasi **CRUD** lengkap dengan penyimpanan data permanen.

---

## ✨ Fitur Utama

- **Dashboard**: Tampilan awal yang memberikan motivasi untuk menyelesaikan tugas.
- **Manajemen Tugas (CRUD)**:
    - **Create**: Menambah tugas baru dengan ID otomatis.
    - **Read**: Menampilkan daftar tugas dalam tabel yang bersih.
    - **Update**: Menandai tugas yang sudah selesai langsung dari daftar.
    - **Delete**: Menghapus tugas yang sudah tidak diperlukan.
- **Sistem Filter**: Memisahkan tampilan tugas berdasarkan status: *Semua*, *Belum Selesai*, dan *Sudah Selesai*.
- **Fitur Pencarian Real-time**: Cari tugas berdasarkan kata kunci di semua kolom (case-insensitive).
- **Sorting Otomatis**: Urutkan data dengan mengklik header kolom tabel.
- **Laporan Statistik**: Melihat statistik dan detail semua tugas dengan persentase penyelesaian.
- **Visual Calendar Picker**: Input tanggal yang intuitif menggunakan dialog kalender visual (bukan input manual teks).
- **Persistensi Data**: Semua data tersimpan secara permanen dalam file `database_tugasin.txt`.
- **UI Aesthetic**: Desain antarmuka modern dengan skema warna yang nyaman dan tipografi *Segoe UI Black*.
- **Arsitektur OOP**: Kode terorganisir dengan prinsip Separation of Concerns untuk kemudahan maintenance.

---

## 🛠️ Teknologi yang Digunakan

- **Bahasa Pemrograman**: Java 8+
- **GUI Framework**: Java Swing & AWT
- **Table Components**: JTable dengan TableRowSorter dan RowFilter
- **Penyimpanan**: Flat File System (.txt)
- **Build Tool**: Maven
- **Arsitektur**: Object-Oriented Programming (OOP)

---

## 📂 Struktur Proyek

```text
TUGASIN-Tugas_Integrator/
├── src/
│   └── main/
│       └── java/
│           └── org/example/
│               ├── Main.java               # Entry point & orchestrator aplikasi
│               ├── DashboardPanel.java     # Panel dashboard dengan motivasi
│               ├── ListDataPanel.java      # Panel daftar tugas (Search & Sort)
│               ├── InputPanel.java         # Panel input tugas baru
│               ├── HistoryPanel.java       # Panel laporan statistik tugas
│               ├── SidebarPanel.java       # Panel navigasi sidebar
│               ├── CalendarDialog.java     # Dialog kalender visual
│               ├── DataManager.java        # Helper untuk operasi file I/O
│               └── UIUtils.java            # Helper untuk styling UI komponen
├── database_tugasin.txt                    # File penyimpanan data tugas
├── pom.xml                                 # Konfigurasi Maven
└── README.md                               # Dokumentasi proyek
```

---

## 🏗️ Arsitektur Aplikasi

Aplikasi ini menggunakan **Object-Oriented Programming (OOP)** dengan pemisahan tanggung jawab yang jelas:

### 1. **Model Layer**
- Data direpresentasikan dalam bentuk `ArrayList<String[]>` yang dimuat dari file.
- Setiap tugas memiliki 6 atribut: ID, Matkul, Tugas, Tanggal Diberikan, Deadline, dan Status.

### 2. **Data Access Layer**
- `DataManager.java`: Mengelola operasi CRUD dan persistensi data ke file.
  - `loadData()`: Membaca data dari file ke memory.
  - `saveData()`: Menyimpan perubahan data ke file.
  - `getNextID()`: Menghasilkan ID unik untuk tugas baru.

### 3. **View Layer (UI Components)**
- `Main.java`: Entry point & orchestrator utama dengan CardLayout untuk navigasi.
- `ListDataPanel.java`: 
  - Menampilkan daftar tugas dalam JTable.
  - Implementasi `TableRowSorter` untuk sorting otomatis.
  - Implementasi `RowFilter` untuk pencarian real-time.
  - Filter berdasarkan status tugas (SEMUA/BELUM/SUDAH).
  - Update dan delete dengan konversi index (view ↔ model).
- `InputPanel.java`: 
  - Form input tugas dengan validasi.
  - Integrasi dengan CalendarDialog untuk input tanggal.
  - Auto-refresh list setelah tambah tugas.
- `HistoryPanel.java`:
  - Menampilkan statistik tugas (total, belum, sudah).
  - Persentase penyelesaian tugas.
  - Detail semua tugas dengan status dan deadline.
- `DashboardPanel.java`: Halaman sambutan dengan motivasi.
- `SidebarPanel.java`: Navigasi antar halaman (Dashboard, Daftar Tugas, Input Tugas, Laporan).
- `CalendarDialog.java`: 
  - Dialog custom untuk pemilihan tanggal.
  - Navigasi bulan (prev/next).
  - Grid calendar dengan hari dalam seminggu.

### 4. **Utility Layer**
- `UIUtils.java`: Helper untuk styling komponen UI secara konsisten.
- Konstanta warna dan font didefinisikan di `Main.java` untuk reusability.

---

## 🎨 Prinsip OOP yang Diterapkan

1. **Encapsulation**: 
   - Setiap panel memiliki data dan method yang terkait dengan visibility yang tepat.
   - Data access logic terpisah di DataManager.
2. **Separation of Concerns**: 
   - Data access (DataManager) terpisah dari UI (Panel classes).
   - Setiap panel memiliki tanggung jawab spesifik.
3. **Single Responsibility Principle**: 
   - Setiap class memiliki satu tanggung jawab yang jelas.
4. **Modularity**: 
   - Kode terorganisir dalam class-class kecil (~50-150 baris).
   - Easy to maintain, test, dan extend.
5. **Observer Pattern**: 
   - HistoryPanel di-refresh otomatis saat data berubah di ListDataPanel.
6. **Event-Driven Programming**: 
   - ActionListener untuk button clicks.
   - KeyListener untuk real-time search.

---

## 🚀 Cara Menjalankan

### Prerequisites
- **Java Development Kit (JDK)** 8 atau lebih tinggi
- **Maven** (opsional, untuk build management)

### Langkah-langkah
1. Clone repository:
   ```bash
   git clone https://github.com/ricoagista/TUGASIN-Tugas_Integrator.git
   cd TUGASIN-Tugas_Integrator
   ```

2. Compile dan jalankan:
   ```bash
   # Menggunakan Maven
   mvn clean compile
   mvn exec:java -Dexec.mainClass="org.example.Main"
   
   # Atau langsung dengan javac
   javac -d bin src/main/java/org/example/*.java
   java -cp bin org.example.Main
   ```

3. Aplikasi akan terbuka dan file `database_tugasin.txt` akan dibuat secara otomatis.

---

## 📋 Cara Penggunaan

### 1. **Dashboard**
- Halaman awal dengan motivasi untuk mengerjakan tugas.

### 2. **Input Tugas**
- Klik menu **"Input Tugas"** di sidebar.
- Isi form:
  - **Matkul** (wajib diisi)
  - **Tugas** (opsional)
  - **Tanggal Diberikan** (pilih dari kalender 📅)
  - **Deadline** (pilih dari kalender 📅)
- Klik **"ADD TASK 🔥"** untuk menyimpan.
- Setelah disimpan, otomatis pindah ke halaman Daftar Tugas.

### 3. **Daftar Tugas**
- **Filter**: Pilih "SEMUA", "BELUM", atau "SUDAH" untuk memfilter berdasarkan status.
- **Pencarian**: Ketik kata kunci di kolom "CARI" untuk mencari tugas secara real-time di semua kolom.
- **Sorting**: Klik header kolom untuk mengurutkan data (ascending/descending).
- **Update Status**: Pilih baris → klik **"Tandai Selesai"** untuk mengubah status menjadi "SUDAH".
- **Hapus**: Pilih baris → klik **"Hapus"** untuk menghapus tugas permanen.

### 4. **Laporan**
- Menampilkan statistik lengkap:
  - Total tugas
  - Jumlah tugas belum selesai (dengan persentase)
  - Jumlah tugas sudah selesai (dengan persentase)
- Detail semua tugas dengan status dan deadline.
- Auto-refresh saat ada perubahan data (tambah, update, atau hapus).

---

## 📊 Format Data

Data disimpan dalam file `database_tugasin.txt` dengan format pipe-delimited:
```
ID|Matkul|Tugas|TanggalDiberikan|Deadline|Status
```

Contoh:
```
1|Pemrograman Java|Tugas OOP|2024-01-15|2024-01-22|BELUM
2|Basis Data|Desain ERD|2024-01-16|2024-01-23|SUDAH
3|Algoritma|Implementasi Sorting|2024-01-17|2024-01-24|BELUM
```

---

## 🎯 Fitur Teknis

### Pencarian (Search)
- **Implementasi**: `RowFilter.regexFilter("(?i)" + searchText)`
- **Case-insensitive**: Mencari di semua kolom tanpa membedakan huruf besar/kecil
- **Real-time**: Update hasil pencarian saat mengetik (KeyListener)
- **Clear on filter**: Search field otomatis clear saat ganti filter

### Sorting
- **Implementasi**: `TableRowSorter<DefaultTableModel>`
- **Multi-kolom**: Bisa sort berdasarkan kolom mana saja
- **Bidirectional**: Klik 1x = ascending, klik 2x = descending
- **Index Conversion**: Menggunakan `convertRowIndexToModel()` untuk operasi update/delete

### Persistensi Data
- **Auto-save**: Setiap perubahan (add/update/delete) langsung tersimpan ke file
- **Auto-load**: Data dimuat otomatis saat aplikasi dibuka
- **Error Handling**: Try-catch untuk menangani IOException

### Laporan & Statistik
- **Real-time Update**: HistoryPanel ter-refresh otomatis setelah perubahan data
- **Perhitungan Persentase**: Menghitung persentase tugas selesai secara dinamis
- **Formatted Output**: Statistik ditampilkan dengan format yang rapi dan mudah dibaca

### Calendar Dialog
- **Modal Dialog**: Blocking dialog hingga tanggal dipilih
- **Month Navigation**: Tombol prev/next untuk navigasi bulan
- **Dynamic Grid**: Grid calendar yang dinamis sesuai jumlah hari dalam bulan
- **Auto-fill**: Tanggal yang dipilih otomatis terisi di textfield

---

## 🔧 Troubleshooting

**Q: Data tidak tersimpan?**
A: Pastikan aplikasi memiliki permission untuk menulis file di direktori aplikasi.

**Q: Kalender tidak muncul?**
A: Pastikan JDK mendukung Java Swing dan AWT lengkap.

**Q: Search tidak berfungsi?**
A: Pastikan menggunakan JDK 8 atau lebih tinggi (RowFilter memerlukan Java 6+).

**Q: Error saat delete/update setelah sorting?**
A: Sudah diperbaiki dengan `convertRowIndexToModel()` untuk konversi index.

**Q: Laporan tidak update setelah hapus/ubah data?**
A: Pastikan method `refreshHistory()` dipanggil setelah operasi data.

**Q: Aplikasi crash saat startup?**
A: Check apakah semua file .java sudah ter-compile dengan benar.

---

## 🤝 Kontribusi

Kontribusi selalu diterima! Untuk kontribusi:
1. Fork repository ini
2. Buat branch fitur baru (`git checkout -b feature/AmazingFeature`)
3. Commit perubahan (`git commit -m 'Add some AmazingFeature'`)
4. Push ke branch (`git push origin feature/AmazingFeature`)
5. Buat Pull Request

---

### 👨‍💻 Authors:

<table>
  <tr>
    <td align="center">
      <a href="https://github.com/ricoagista">
        <img src="https://github.com/ricoagista.png" width="100px;" alt="Rico Agista"/><br />
        <sub><b>Rico Agista</b></sub>
      </a><br />
      <a href="https://github.com/ricoagista" title="Profile">🔗 GitHub</a>
    </td>
    <td align="center">
      <a href="https://github.com/dickynovanda">
        <img src="https://github.com/dickynovanda.png" width="100px;" alt="Dicky Novanda"/><br />
        <sub><b>Dicky Novanda</b></sub>
      </a><br />
      <a href="https://github.com/dickynovanda" title="Profile">🔗 GitHub</a>
    </td>
  </tr>
</table>

---

**TUGASIN** - *Do Your Tasks. No Excuses.*

---

## 📝 Changelog

### Version 2.0 (Current)
- ✨ Menambahkan fitur pencarian real-time dengan RowFilter
- ✨ Menambahkan fitur sorting pada tabel dengan TableRowSorter
- ✨ Menambahkan panel Laporan dengan statistik tugas
- 🎨 Refactoring ke arsitektur OOP yang lebih clean
- 🐛 Memperbaiki bug pada update dan delete dengan sorting aktif menggunakan convertRowIndexToModel()
- ⚡ Implementasi auto-refresh untuk HistoryPanel saat data berubah
- 📝 Dokumentasi lengkap dengan diagram arsitektur

### Version 1.0
- 🎉 Release awal dengan fitur CRUD dasar
- 📅 Calendar picker untuk input tanggal
- 🎨 UI dengan Gen Z Aesthetic (purple theme)
- 💾 Persistensi data menggunakan flat file
- 🔍 Filter berdasarkan status tugas

---

<div align="center">
  <p>⭐ Jangan lupa beri star jika project ini membantu! ⭐</p>
  <p>Made with ❤️ and lots of ☕</p>
</div>
