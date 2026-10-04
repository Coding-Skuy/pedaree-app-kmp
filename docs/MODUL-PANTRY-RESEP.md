# Modul pantry-resep — Kontrak Pawonee (pemilik) dan Pedaree (konsumen)

TownHall Pedaree: https://github.com/Coding-Skuy/Pedaree-TownHall

## Kedudukan

- **Pemilik:** Pawonee. Menetapkan skema resep, takaran, langkah, dan `recipe_id`.
- **Konsumen:** Pedaree. Membaca resep untuk pencocokan stok, tampilan hemat,
  dan sinyal belanja. Tidak menulis definisi resep.

## Aturan konsumen (berlaku untuk app-kmp)

1. Seluler Pedaree memanggil API resep Pawonee secara baca saja.
2. Hasil pencocokan disimpan lokal dengan rujukan `recipe_id` Pawonee.
3. Usulan varian hemat dikirim sebagai usulan ke Pawonee, bukan perubahan langsung.
4. Bila API Pawonee tidak terjangkau, aplikasi memakai tembolok terakhir dan
   menandai data sebagai luring.

## Skema rujukan

```json
{
  "recipe_id": "pawonee-resep-ayam-goreng-001",
  "judul": "Ayam Goreng Bawang",
  "bahan_dibutuhkan": [
    {"nama": "ayam", "jumlah": 500, "satuan": "gram"},
    {"nama": "bawang putih", "jumlah": 5, "satuan": "siung"}
  ]
}
```
