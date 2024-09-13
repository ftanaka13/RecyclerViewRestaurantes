package br.com.faculdadeimpacta.recyclerviewrestaurantes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
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
    private val listaRestaurante = mutableListOf<Restaurante>()

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

    fun adicionarRestaurante(restaurante: Restaurante) {
        this.listaRestaurante.add(restaurante)
        binding.recyclerView.adapter?.notifyItemInserted(this.listaRestaurante.indexOf(restaurante))
    }

    override fun onStart() {
        super.onStart()
        adicionarRestaurante(
            Restaurante(
                "Coco bambu",
                "Frutos do mar",
                5.0F,
                true
            )
        )
        adicionarRestaurante(Restaurante("Outback", "Churrasco", 5.0f, true))
        adicionarRestaurante(Restaurante("Girafas", "Brasileira", 3.0f, false))
        adicionarRestaurante(Restaurante("Mc Donalds", "Hamburger", 3.5f, false))
        adicionarRestaurante(Restaurante("Pizza hut", "Pizza", 3.8f, false))
        adicionarRestaurante(Restaurante("Guaraná", "Bebida", 4.4f, true))
        adicionarRestaurante(Restaurante("Madero", "Hamburger", 4.8f, false))
    }

    class CustomAdapter(private val listaRestaurante: List<Restaurante>) :
        RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {

        fun notificaMudanca(restaurante: Restaurante) {
            this.notifyItemChanged(listaRestaurante.indexOf(restaurante))
        }

        inner class CustomViewHolder(private val binding: RestauranteItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun onBind(restaurante: Restaurante) {
                binding.restaurante = restaurante

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

                binding.cardViewRoot.setOnClickListener {
                    Toast.makeText(context, restaurante.toString(), Toast.LENGTH_LONG).show()
                }

                binding.imageViewFavorito.setOnClickListener {
                    restaurante.favorito = !restaurante.favorito
                    // Persistir alteração do valor
                    notificaMudanca(restaurante)
                }
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