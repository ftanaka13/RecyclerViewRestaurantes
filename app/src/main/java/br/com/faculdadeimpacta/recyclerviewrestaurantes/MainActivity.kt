package br.com.faculdadeimpacta.recyclerviewrestaurantes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.faculdadeimpacta.recyclerviewrestaurantes.databinding.ActivityMainBinding
import br.com.faculdadeimpacta.recyclerviewrestaurantes.databinding.RestauranteItemBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val listaRestaurante = listOf(
        Restaurante("Outback", "Churrasco", 5.0f, true),
        Restaurante("Girafas", "Brasileira", 3.0f, false),
        Restaurante("Mc Donalds", "Hamburger", 3.5f, false),
        Restaurante("Pizza hut", "Pizza", 3.8f, false),
        Restaurante("Guaraná", "Bebida", 4.4f, true),
        Restaurante("Madero", "Hamburger", 4.8f, false),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        with(binding.recyclerView) {
            adapter = CustomAdapter(listaRestaurante)
            layoutManager = LinearLayoutManager(binding.root.context)
        }
    }

    class CustomAdapter(private val listaRestaurante: List<Restaurante>) :
        RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {

        inner class CustomViewHolder(private val binding: RestauranteItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun onBind(restaurante: Restaurante) {
                binding.textViewNome.text = restaurante.nome
                binding.textViewNota.text = restaurante.nota.toString()
                binding.textViewTipoCozinha.text = restaurante.tipoCozinha

                val context = binding.root.context

                val drawableFavorito = when (restaurante.favorito) {
                    true -> R.drawable.baseline_favorite_24
                    else -> R.drawable.baseline_favorite_border_24
                }
                binding.imageViewFavorito.setImageDrawable(context.getDrawable(drawableFavorito))

                val drawableRestaurante = when (restaurante.tipoCozinha.lowercase()) {
                    "pizza" -> R.drawable.pizza_svgrepo_com
                    "hamburger" -> R.drawable.hamburger_svgrepo_com
                    "bebida" -> R.drawable.food_color_beer_95_svgrepo_com
                    else -> R.drawable.food_dish_svgrepo_com
                }
                binding.imageViewRestaurante.setImageDrawable(
                    context.getDrawable(
                        drawableRestaurante
                    )
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = RestauranteItemBinding.inflate(layoutInflater, parent, false)
            return CustomViewHolder(binding)
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            holder.onBind(listaRestaurante[position])
        }

        override fun getItemCount(): Int = listaRestaurante.size

    }
}