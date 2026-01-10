package com.example.checkmarket

data class Produto(
    var id: String = "",
    var nome: String = "",
    var quantidade: Int = 0,
    var categoria: String = "",
    var comprado: Boolean = false
)
