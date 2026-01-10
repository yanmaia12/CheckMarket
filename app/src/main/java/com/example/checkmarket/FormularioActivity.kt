package com.example.checkmarket

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class FormularioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario)

        val editNome = findViewById<EditText>(R.id.editNome)
        val editQuantidade = findViewById<EditText>(R.id.editQuantidade)
        val spinnerCategoria = findViewById<Spinner>(R.id.spinnerCategoria)
        val btnSalvar = findViewById<Button>(R.id.btnSalvar)

        val categorias = listOf("Mercado", "Frutas e Legumes", "Talho", "Padaria", "Limpeza", "Outros")
        val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_item, categorias)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategoria.adapter = adapterSpinner

        btnSalvar.setOnClickListener {
            val nome = editNome.text.toString()
            val quantidadeTexto = editQuantidade.text.toString()
            val categoriaSelecionada = spinnerCategoria.selectedItem.toString()

            if (nome.isEmpty()) {
                editNome.error = "Preencha o nome!"
                return@setOnClickListener
            }

            val quantidade = quantidadeTexto.toIntOrNull() ?: 0

            val novoProduto = Produto(
                id = "",
                nome = nome,
                quantidade = quantidade,
                categoria = categoriaSelecionada,
                comprado = false
            )

            salvarNoFirebase(novoProduto)
        }
    }

    private fun salvarNoFirebase(produto: Produto) {
        val db = FirebaseFirestore.getInstance()

        db.collection("produtos")
            .add(produto)
            .addOnSuccessListener {
                Toast.makeText(this, "Produto salvo!", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao salvar: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }
}