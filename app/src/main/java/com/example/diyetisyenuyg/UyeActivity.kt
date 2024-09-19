package com.example.diyetisyenuyg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.diyetisyenuyg.databinding.ActivityUyeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UyeActivity : AppCompatActivity() {
    lateinit var binding: ActivityUyeBinding
    private lateinit var auth: FirebaseAuth
    var databaseReference:DatabaseReference?=null//ilk değer boş olabilir
    var database:FirebaseDatabase?=null// yukardaki üç satır realtime databasede veriokuyup yazabilmek için
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivityUyeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth=FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()
        databaseReference =database?.reference!!.child("profile")

        binding.uyekaydetbutton.setOnClickListener {
            var uyeadsoyad=binding.uyeadsoyad.text.toString()
            var uyeemail=binding.uyeemail.text.toString()
            var uyeparola=binding.uyeparola.text.toString()
            val passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$".toRegex()
            if (TextUtils.isEmpty(uyeadsoyad))
            {
                binding.uyeadsoyad.error = "Lütfen adınızı ve soyadınızı giriniz"
                return@setOnClickListener
            }
            else if (TextUtils.isEmpty(uyeemail))
            {
                binding.uyeemail.error = "Lütfen e-mail adresinizi giriniz."
                return@setOnClickListener
            }
            else if (TextUtils.isEmpty(uyeparola))
            {
                binding.uyeparola.error = "Parolanızı giriniz."
                return@setOnClickListener
            }
            else if (!passwordRegex.matches(uyeparola)) {
                binding.uyeparola.error =
                    "Parola en az 8 karakter, bir büyük harf, bir küçük harf ve bir rakam içermelidir."
                return@setOnClickListener
            }


            auth.createUserWithEmailAndPassword(binding.uyeemail.text.toString(), binding.uyeparola.text.toString())
                .addOnCompleteListener(this){ gorev ->
                    if (gorev.isSuccessful) {
                        // Şuanki kullanıcı bilgilerini alalım
                        var currentUser = auth.currentUser
                        // Kullanıcı id sini alıp o id adı altında adımızı ve soyadımızı kaydedelim

                        var currentUserDb = currentUser?.let { it1 -> databaseReference?.child(it1.uid) }
                        currentUserDb?.child("adisoyadi")?.setValue(binding.uyeadsoyad.text.toString())
                        currentUserDb = currentUser?.let { it1 -> databaseReference?.child(it1.uid) }
                        currentUserDb?.child("kullaniciadi")?.setValue(binding.uyeadsoyad.text.toString())
                        currentUserDb?.child("resim_url")?.setValue("https://firebasestorage.googleapis.com/v0/b/diyetisyen-724a3.appspot.com/o/profilee.jpg?alt=media&token=295491f3-d992-4f84-8369-93ff481c80a6")
                      Toast.makeText(this@UyeActivity,"KAYIT BAŞARILI",Toast.LENGTH_LONG).show()




                    }
                    else
                    {
                        Toast.makeText(this@UyeActivity, "Kayıt Başarısız",Toast.LENGTH_LONG).show()
                    }

                }


            }




        binding.uyegirisyapbutton.setOnClickListener {
            intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
            finish()
        }


        }
    }
