package com.example.penjahit

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var mCtx: Context
    lateinit var ref : DatabaseReference
    lateinit var list : MutableList<com.example.penjahit.Pesanan>
    lateinit var listView: ListView
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        val pesanan = auth.currentUser


        add.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }


        val progresbar = findViewById<ShimmerFrameLayout>(R.id.progressBar2)
        progresbar.startShimmerAnimation()

        swipe_to_refresh_layout.setOnRefreshListener{
            Toast.makeText(this, "Page Refreshed", Toast.LENGTH_SHORT).show()

            swipe_to_refresh_layout.isRefreshing = false
            recreate()
        }


        add.setOnClickListener {
            val intAdd = Intent(this, AddActivity::class.java)
            startActivity(intAdd)
        }






        ref = FirebaseDatabase.getInstance().getReference(pesanan?.uid.toString())
        list = mutableListOf()
        listView = findViewById(R.id.listView)



        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()){

                    for (h in p0.children){
                        val user = h.getValue(com.example.penjahit.Pesanan::class.java)
                        list.add(user!!)
                    }
                    val adapter = Adapter(applicationContext,R.layout.pesanan,list)
                    listView.adapter = adapter
                }
            }


        })
    }
}