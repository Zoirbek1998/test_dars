package dev.future.testapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import dev.future.testapp.adapter.list.ListAdapter
import dev.future.testapp.databinding.Fragment1Binding
import dev.future.testapp.model.UserModel


class Fragment1 : Fragment() {

    lateinit var binding: Fragment1Binding
    lateinit var userArrLis : ArrayList<UserModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = Fragment1Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        userArrLis = ArrayList()


        for (i in name.indices){
            val userModel = UserModel(name[i],subTitle[i],image[i])
            userArrLis.add(userModel)
        }

        binding.listMode.isClickable = true
        binding.listMode.adapter = ListAdapter(requireActivity(),userArrLis)

        binding.listMode.setOnItemClickListener { parent, view, position, id ->
           val name = name[position]
           val subTitle = subTitle[position]
           val imageView = image[position]

            val intent = Intent(requireActivity(),IntentActivity::class.java)
            intent.putExtra("name",name)
            intent.putExtra("subtitle",subTitle)
            intent.putExtra("image" , imageView)
            startActivity(intent)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = Fragment1()
    }
}