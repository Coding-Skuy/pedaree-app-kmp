> Versi: v1.0.0 | Status: disetujui | Menggantikan: -

# ARSITEKTUR-AI-DEV — Stack terkunci dan batas context (pedaree-app-kmp)

Mengacu: Pedaree-TownHall `platform/versions/v1.0.0` (https://github.com/Coding-Skuy/Pedaree-TownHall/tree/main/platform/versions/v1.0.0), beku 2026-10-07. Dokumen ini MELENGKAPI `versions/v1.0.0/docs/40-TECHSTACK.md` dan `versions/v1.0.0/docs/60-BLAST-RADIUS.md` (keduanya beku v1.0.0 dan tidak diedit; isinya hanya ringkasan satu paragraf). Tidak menduplikasi BRD/PRD/FRD/FSD/SRS.

Baris Pedaree townhall-roadmap §2: point of concern "FEFO; stockout <8%; waste <1.500 g/Rp50.000/bln"; batas context "13 satuan baku; konsumen modul `pantry-resep` milik Pawonee". Rujukan lintas divisi: KONTEKS §3.1, §6, §8, §9.

## 1. Matriks stack

| Komponen | Kondisi saat ini (tercatat dari manifes/README) | Target terkunci v1.0.0 |
|---|---|---|
| Kotlin | 2.1.0 | Kotlin stabil terbaru (versi eksak dikunci saat kickoff) |
| UI | Compose Multiplatform 1.7.3 | Compose Multiplatform (eksak saat kickoff) |
| Navigasi | belum ada | Navigation3 |
| Android Gradle Plugin | 8.6.1 (compileSdk 35, minSdk 26) | mengikuti Kotlin/CMP terkunci |
| Penyimpanan lokal | SQLDelight 2.0.2 (driver Android) | SQLDelight (cache + antrean tulis), driver iOS ditambah |
| Modul bersama | tipe `PantryItem` lokal di `composeApp` | konsumsi `id.chefgenie.pawonee:pantry-resep:1.0.0` |
| Uji | JUnit 4.13.2, Turbine 1.2.1 | TDD merah-hijau; uji FEFO/konversi memakai fixture modul bersama |
| Platform | Android (iOS belum ada `iosApp`) | Android + iOS; TANPA Windows |

## 2. Matriks fitur per platform dan navigasi

| Fitur | Android | iOS | Windows |
|---|---|---|---|
| Catat masuk / pakai (FEFO) / opname / pindah / buang, luring | Ya | Ya | Tidak (di luar cakupan) |
| Daftar dapur + detail item + batch FEFO | Ya | Ya | Tidak |
| Tiga kartu ringkas (habis hari ini, segera 3 hari, buang bulan ini) | Ya | Ya | Tidak |
| Push kedaluwarsa FCM + badge tab Dapur + banner detail | Ya | Ya | Tidak |
| Tombol `Tambah ke TitipO` (aksi pengguna, bukan otomatis) | Ya | Ya | Tidak |
| Distribusi | Play Store + APK sideload | TestFlight | Tidak ada |

Navigation graph ringkas (Navigation3, dari `produk/10` dan `notifikasi/10`):

```
Dapur (tab: kartu ringkas + daftar) ──> DetailItem(id) ──> Batch FEFO / banner kedaluwarsa
   ├──> CatatMasuk  (Alur A, juga draf dari parcel TitipO diterima)
   ├──> CatatPakai  (Alur B; juga dibuka dari Pawonee `Masak ini`)
   ├──> Opname      (Alur C)
   ├──> PindahLokasi(Alur D, dari DetailItem/batch)
   └──> Buang       (Alur E, dari banner kedaluwarsa/detail batch)
```

Aturan umum: tiap alur selesai maks 3 layar di mobile (`produk/10`, Aturan Umum).

## 3. Boleh disentuh repo ini

- `composeApp/` (UI, navigasi, cache lokal, antrean tulis, notifikasi).
- Pemanggilan API backend `https://api.pedaree.chefgenie.id/v1` dengan header `X-Request-Id`.
- Mengonsumsi API publik `:shared:pantry-resep` versi 1.0.0.

## 4. Dilarang

- Menulis ulang konversi satuan, FEFO, atau skor resep di luar modul Pawonee.
- Menyimpan atau menghitung stok di luar kontrak server (klien hanya cache + antrean).
- Menambah target Windows atau framework UI selain Compose Multiplatform.
- Menyentuh DB divisi lain (DB per service; Titeny hanya read-only ke DB sumber).
- Membuat pesanan TitipO otomatis dari sinyal stok (hanya anjuran, relevansi 48 jam).
- Mengubah modul `:shared:pantry-resep` (milik Pawonee), daftar 13 satuan, ambang kedaluwarsa, atau audiens JWT tanpa PR TownHall + ADR.

## 5. Kontrak yang diproduksi

- Antrean tulis idempoten ke API Pedaree (`X-Request-Id`).
- Aksi `Tambah ke TitipO` (`POST /demand/titipo`, atas tindakan pengguna) dan tombol `Lihat resep` ke Pawonee.

## 6. Kontrak yang dikonsumsi

- API backend Pedaree v1 (`/pantry/*`, `/demand/*`).
- Modul `:shared:pantry-resep` 1.0.0 (Pawonee); token desain dari `pedaree-design`.

## 7. Dampak bila berubah

- Perubahan versi modul Pawonee memengaruhi konversi dan FEFO di klien; uji ulang fixture.
- Perubahan kontrak backend memutus sinkron antrean luring; wajib bump versi + dual-read 30 hari.

## Batasan

- Dokumen ini hanya mencatat stack dan batas; tidak mengubah manifes atau kode. Kondisi saat ini dicatat apa adanya.
- Versi dikunci eksak di manifes saat kickoff coding; perubahan dicatat lewat ADR baru.
- JWT audiens `pedaree`, DB logis `pedaree`, dan kepemilikan Pawonee atas `:shared:pantry-resep` tidak berubah oleh dokumen ini.
