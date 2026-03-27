# Voxelization with Octree, Divide and Conquer, and Concurrency

## Deskripsi Program
Program ini dibuat untuk melakukan konversi model tiga dimensi berformat `.obj` menjadi representasi voxel menggunakan struktur data **Octree** dengan strategi **divide and conquer**. Proses voxelization dilakukan dengan membagi ruang tiga dimensi secara rekursif menjadi delapan subkubus pada setiap level, kemudian memeriksa apakah subkubus tersebut masih relevan terhadap model. Jika masih beririsan dengan objek dan belum mencapai kedalaman maksimum, subkubus akan dibagi lagi hingga diperoleh detail yang diinginkan.

Program ini juga mendukung **concurrency** sehingga beberapa subkubus yang independen dapat diproses secara bersamaan untuk meningkatkan efisiensi eksekusi. Selain itu, program menyediakan **.obj viewer** untuk membantu visualisasi model dan hasil pemrosesan.

## Fitur Utama
- Voxelization model 3D berbasis **Octree**
- Menggunakan strategi **divide and conquer**
- Mendukung **concurrency** untuk mempercepat pemrosesan
- Mendukung mode **CLI** dan **GUI**
- Menyediakan **.obj viewer** untuk visualisasi model

## Requirement
Pastikan perangkat telah memiliki:
- **Java Development Kit (JDK)**
- **Apache Maven**

## Instalasi
1. Pastikan **JDK** dan **Maven** sudah terpasang pada sistem
2. Clone atau unduh repositori ini
```bash
git clone https://github.com/alivovue/Tucil2_13524010_13524052.git
```
3. Masuk ke direktori src\Tucil2_Project(root)
```bash
cd src/Tucil2_Project
```
4. Jalankan Maven agar seluruh dependency terpasang

## Cara Kompilasi
Apabila ingin mengompilasi program, jalankan perintah berikut:

```bash
mvn compile
```

Cara Menjalankan Program
Mode CLI
```bash
mvn exec:java -Dexec.mainClass=backend.CLIMain
```

Mode GUI
```bash
mvn javafx:run
```

Cara Menggunakan Program
1. Siapkan file model tiga dimensi berformat .obj
2. Jalankan program melalui mode CLI atau GUI
3. Masukkan file .obj yang ingin diproses
4. Tentukan parameter yang diperlukan, seperti kedalaman maksimum Octree
5. Program akan melakukan proses voxelization menggunakan pendekatan divide and conquer
6. Hasil pemrosesan dapat diamati melalui output program maupun melalui viewer yang tersedia

Pengembang:<br>
13524010 — Audric Yusuf Maynard Simatupang<br>
13524052 — Raynard Fausta
