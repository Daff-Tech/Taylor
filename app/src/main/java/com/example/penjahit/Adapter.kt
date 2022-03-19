package com.example.penjahit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Adapter (val mCtx: Context, val layoutResId: Int, val list: List<Pesanan>)
    : ArrayAdapter<Pesanan>(mCtx, layoutResId, list){

    private lateinit var auth : FirebaseAuth

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId,null)



        val textNama = view.findViewById<TextView>(R.id.textNama)
        val textDelete = view.findViewById<TextView>(R.id.delete)



        val pesan = list[position]

        textNama.text = pesan.pesanan
        textDelete.setOnClickListener {
            Deleteinfo(pesan)
        }



        return view
    }

    private fun Deleteinfo(pesan: Pesanan) {
        auth = FirebaseAuth.getInstance()
        val pesanan = auth.currentUser

        val mydatabase = FirebaseDatabase.getInstance().getReference(pesanan?.uid.toString())
        mydatabase.child(pesan.id).removeValue()
        Toast.makeText(mCtx,"Deleted,Swipe To Refresh",Toast.LENGTH_SHORT).show()

    }
    }