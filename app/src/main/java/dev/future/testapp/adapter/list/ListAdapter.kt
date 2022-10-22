package dev.future.testapp.adapter.list

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import dev.future.testapp.R
import dev.future.testapp.model.UserModel

class ListAdapter(val context: Activity,val arrayList: ArrayList<UserModel>) : ArrayAdapter<UserModel>(
    context,
    R.layout.item_layout, arrayList
) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater : LayoutInflater=  LayoutInflater.from(context)
        val view :View = inflater.inflate(R.layout.item_layout,null)

        val image = view.findViewById<ImageView>(R.id.image)
        val name = view.findViewById<TextView>(R.id.name)
        val subTitle = view.findViewById<TextView>(R.id.sub_title)


        image.setImageResource(arrayList[position].image)
        name.text= arrayList[position].name
        subTitle.text= arrayList[position].subtitle

        return view

    }

}