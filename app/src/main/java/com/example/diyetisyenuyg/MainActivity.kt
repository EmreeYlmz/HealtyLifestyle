package com.example.diyetisyenuyg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.diyetisyenuyg.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth= FirebaseAuth.getInstance()
        var currentUser=auth.currentUser
      /*  if (currentUser!=null)
        {
            startActivity(Intent(this@MainActivity,Anagris::class.java))
            finish()
        }*/

        binding.girisyapbutton.setOnClickListener {
            var girisemail=binding.girisemail.text.toString()
            var girisparola=binding.girisparola.text.toString()
            val passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$".toRegex()
            if (TextUtils.isEmpty(girisemail))
            {
                binding.girisemail.error=" eposta adresini giriniz"
                return@setOnClickListener
            }
            else if (TextUtils.isEmpty(girisparola))
            {
                binding.girisparola.error="Parolanızı giriniz"
                return@setOnClickListener
            }

            else if (!passwordRegex.matches(girisparola)) {
                binding.girisparola.error =
                    "Parola en az 8 karakter, bir büyük harf, bir küçük harf ve bir rakam içermelidir."
                return@setOnClickListener
            }

     //   var girisemail=binding.girisemail.text.toString()
       // var girisparola=binding.girisparola.text.toString()
        auth.signInWithEmailAndPassword(girisemail,girisparola)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    intent = Intent(applicationContext,Anagris ::class.java)
                    startActivity(intent)
                    finish()
                } else {
                   Toast.makeText(applicationContext,"Giriş hatalı, lütfen tekrar deneyiniz."
                        ,Toast.LENGTH_LONG).show()
                }
            }
    }
        binding.girisyeniuyelik.setOnClickListener {
            intent = Intent(applicationContext, UyeActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.girisparolaunuttum.setOnClickListener {
            intent = Intent(applicationContext, Psifirla::class.java)
            startActivity(intent)
            finish()
        }


    }
}