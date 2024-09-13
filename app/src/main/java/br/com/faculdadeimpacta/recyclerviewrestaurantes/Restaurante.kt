package br.com.faculdadeimpacta.recyclerviewrestaurantes

data class Restaurante(
    val nome: String,
    val tipoCozinha: String,
    val nota: Float,
    var favorito: Boolean = false
)
