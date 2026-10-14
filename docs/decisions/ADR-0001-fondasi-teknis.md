# ADR-0001 — Fondasi teknis pedaree-app-kmp

Status: Diterima | Tanggal: 2026-10-14

## Konteks

Pedaree v1.0.0 membutuhkan aplikasi mobile pencatat stok dapur yang tetap berfungsi tanpa internet dan menampilkan peringatan kedaluwarsa tepat waktu. Sumber kebenaran: Pedaree-TownHall `platform/versions/v1.0.0` (https://github.com/Coding-Skuy/Pedaree-TownHall/tree/main/platform/versions/v1.0.0), beku 2026-10-07; lintas divisi: [KONTEKS-LINTAS-DIVISI.md](https://github.com/Coding-Skuy/ChefGenie-TownHall/blob/main/KONTEKS-LINTAS-DIVISI.md). Tiket fondasi: PDR-KMP-001 (issue #1).

## Keputusan

1. Stack mobile: Kotlin Multiplatform + Compose Multiplatform + Navigation3 untuk Android dan iOS; dilarang framework UI lain dan tidak ada target Windows.
2. Offline-first dengan SQLDelight + antrean tulis + retry; setiap permintaan tulis bertanda `X-Request-Id` untuk idempotensi server 24 jam.
3. Pedaree adalah konsumen `:shared:pantry-resep` milik Pawonee (`id.chefgenie.pawonee:pantry-resep:1.0.0`); logika konversi satuan dan FEFO dipakai dari modul itu, bukan ditulis ulang.
4. JWT audiens `pedaree` (akses 15 menit, refresh 7 hari); klien hanya berbicara ke API backend, tidak ke DB.
5. Satuan baku 13 kode dari `inventori/20-satuan-hortikultura.md`; kg disimpan sebagai gram, liter/sdm/sdt sebagai ml.

## Konsekuensi

- Perubahan model/enum/rumus FEFO hanya lewat Pawonee (pemilik modul); Pedaree menaikkan versi dependensi bersama rilis minor.
- Antrean tulis luring membutuhkan idempotensi sisi server (`X-Request-Id`, simpan 24 jam).
- Pengguna Windows desktop tidak didukung pada v1.0.0.
