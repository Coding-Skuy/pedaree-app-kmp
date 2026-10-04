# pedaree-app-kmp — Aplikasi Pantry Mobile (Kotlin Multiplatform)

Divisi **Pedaree (Smart Pantry)**, org **Coding-Skuy**. Opsi A.

TownHall: https://github.com/Coding-Skuy/Pedaree-TownHall

## Ringkasan

Aplikasi seluler pantry cerdas lintas Android dan iOS dari satu basis kode Kotlin.
Fokus: inventori bahan di rumah, pengingat kedaluwarsa, dan saran resep hemat.

## Modul pantry-resep

Kontrak lintas divisi (detail: `docs/MODUL-PANTRY-RESEP.md`):

- **Pemilik modul:** Pawonee (logika resep, takaran, langkah masak).
- **Konsumen modul:** Pedaree (membaca daftar resep dan memetakan ke stok pantry lokal).
- Pedaree tidak mengubah definisi resep; bila butuh varian hemat berbasis stok,
  Pedaree mengajukan usulan ke Pawonee melalui TownHall Pawonee.

## Teknologi (versi dikunci)

- Kotlin 2.1.0
- Compose Multiplatform 1.7.3
- Android Gradle Plugin 8.6.1
- compileSdk 35, minSdk 26
- Xcode 16.2 (untuk target iOS), Swift 6.0 (interop)
- JUnit 4.13.2, Turbine 1.2.1 (uji)

Lihat `gradle/libs.versions.toml` sebagai sumber kebenaran versi.

## Struktur

```text
composeApp/        kode bersama Compose Multiplatform
androidApp/        entrypoint Android
iosApp/            entrypoint iOS (SwiftUI + Compose interop)
docs/              arsitektur dan kontrak modul
```

## Cara jalan (Android)

```bash
./gradlew :composeApp:assembleDebug
./gradlew :composeApp:connectedDebugAndroidTest
```

## Cara jalan (iOS)

Buka `iosApp/iosApp.xcworkspace` di Xcode 16.2 lalu jalankan skema `iosApp`.

## CI

Workflow `.github/workflows/ci.yml` menjalankan build Android dan pemeriksaan KMP
pada setiap push dan pull request.
