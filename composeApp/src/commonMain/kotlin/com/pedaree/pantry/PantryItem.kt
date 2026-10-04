package com.pedaree.pantry

data class PantryItem(
    val id: String,
    val nama: String,
    val satuan: String,
    val jumlah: Double,
    val kedaluwarsa: String,
)

fun statusKedaluwarsa(sisaHari: Long): String = when {
    sisaHari < 0 -> "lewat"
    sisaHari <= 7 -> "segera"
    else -> "aman"
}

fun statusStok(jumlah: Double, batasMenipis: Double): String = when {
    jumlah <= 0 -> "habis"
    jumlah < batasMenipis -> "menipis"
    else -> "tersedia"
}
