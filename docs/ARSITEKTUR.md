# Arsitektur pedaree-app-kmp

TownHall: https://github.com/Coding-Skuy/Pedaree-TownHall

## Lapisan

1. `ui` (Compose): layar Stok, Mutasi, Resep Hemat.
2. `domain`: model `PantryItem`, `MutasiStok`, aturan kedaluwarsa.
3. `data`: repositori ke backend Pedaree (`GET /v1/stok`, `POST /v1/mutasi`)
   dan baca resep Pawonee (`GET {PAWONEE_RECIPE_API}/resep/:id`).

## Alur utama

Pengguna memindai atau mengetik bahan -> tersimpan sebagai `PantryItem` ->
pengingat kedaluwarsa muncul -> layar Resep Hemat menampilkan resep Pawonee
yang paling cocok dengan stok.

## Keputusan

- Satu basis kode KMP untuk Android dan iOS; logika kedaluwarsa di kode bersama
  agar perilaku identik di dua platform.
- Tembolok luring memakai SQLDelight 2.0.2.
