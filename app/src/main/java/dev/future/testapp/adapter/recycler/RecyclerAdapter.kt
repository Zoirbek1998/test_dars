package dev.future.testapp.adapter.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import dev.future.testapp.R
import org.w3c.dom.Text

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    val image = arrayOf(
        R.drawable.a,
        R.drawable.b,
        R.drawable.d,
        R.drawable.e,
        R.drawable.f,
        R.drawable.g,
        R.drawable.image,
    )

    val name = arrayOf(
        "Salim",
        "Kozim",
        "Nodir",
        "Jamshid",
        "Begmatov",
        "Axrorbek",
        "Jamila",
    )
    val subTitle = arrayOf(
        "Kiristofer",
        "Craig",
        "Sergio",
        "Mubariz",
        "Mike",
        "Tao",
        "Sadjvhj",
    )

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val itemImage: ImageView
        val itemName :TextView
        val itemSubTitle : TextView

        init {
            itemImage =  itemView.findViewById(R.id.image)
            itemName =  itemView.findViewById(R.id.name)
            itemSubTitle =  itemView.findViewById(R.id.sub_title)

            itemView.setOnClickListener {
                val position : Int  = adapterPosition
                Toast.makeText(itemView.context, "${name[position]}", Toast.LENGTH_SHORT).show()

            }
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemImage.setImageResource(image[position])
        holder.itemName.text = name[position]
        holder.itemSubTitle.text = subTitle[position]


    }

    override fun getItemCount(): Int {
        return name.size
    }

}