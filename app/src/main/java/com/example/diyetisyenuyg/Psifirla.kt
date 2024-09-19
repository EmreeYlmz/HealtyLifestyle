package com.example.diyetisyenuyg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.example.diyetisyenuyg.databinding.ActivityPsifirlaBinding
import com.google.firebase.auth.FirebaseAuth

class Psifirla : AppCompatActivity() {
    lateinit var binding: ActivityPsifirlaBinding
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =ActivityPsifirlaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.psifirlamabutton.setOnClickListener {
            var psifirlaemail = binding.psifirlaemail.text.toString().trim()
            if (TextUtils.isEmpty(psifirlaemail)){
                binding.psifirlaemail.error = "Lütfen e-mail adresinizi yazınız."
            } else {
                auth.sendPasswordResetEmail(psifirlaemail)
                    .addOnCompleteListener(this){ sifirlama ->
                        if (sifirlama.isSuccessful){
                            binding.psifirlamesaj.text= "E-mail adresinize sıfırlama bağlantısı gönderildi, Lütfen kontrol ediniz"
                        } else {
                            binding.psifirlamesaj.text= "Sıfırlama işlemi başarısız."
                        }

                    }
            }
        }

        // Giriş sayfasına gitmek için
        binding.psifirlamagirisyapbutton.setOnClickListener {
            intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
