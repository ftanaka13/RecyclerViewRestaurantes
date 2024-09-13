package br.com.faculdadeimpacta.recyclerviewrestaurantes

import android.content.Context
import android.graphics.drawable.Drawable

data class Restaurante(
    val nome: String,
    val tipoCozinha: String,
    val nota: Float,
    var favorito: Boolean = false
)
