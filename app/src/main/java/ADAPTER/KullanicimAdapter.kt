package ADAPTER
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diyetisyenuyg.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import de.hdodenhof.circleimageview.CircleImageView
import Model.Kullanicim
import com.bumptech.glide.Glide

class KullanicimAdapter(private val mContext: Context, private val mkullanicilar: List<Kullanicim>) : RecyclerView.Adapter<KullanicimAdapter.ViewHolder>() {

    private var Firebasekullanici: FirebaseUser? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(mContext).inflate(R.layout.add_friendsitem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Firebasekullanici = FirebaseAuth.getInstance().currentUser
        val kullanici = mkullanicilar[position]
        holder.tkpet_btn.visibility = View.VISIBLE
        holder.kullaniciadi.text = kullanici.kullaniciadi
        holder.adi.text = kullanici.ad
        Glide.with(mContext).load(kullanici.resimurl).into(holder.profileresmi)
        takipediliyor(kullanici.id, holder.tkpet_btn)
        if (kullanici.id == Firebasekullanici?.uid) {
            holder.tkpet_btn.visibility = View.GONE
        }
        holder.itemView.setOnClickListener { v: View ->
            val editor = mContext.getSharedPreferences("TERCİHLER", Context.MODE_PRIVATE).edit()
            editor.putString("profileid", kullanici.id)
            editor.apply()
            /* ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment,
           new profilefragmentgelecek()).commit();
            */
        }
        holder.tkpet_btn.setOnClickListener { v: View ->
            if (holder.tkpet_btn.text == "TakipET") {
                FirebaseDatabase.getInstance().getReference().child("Takip")
                    .child(Firebasekullanici?.uid!!)
                    .child("TakipEdilenler").child(kullanici.id).setValue(true)
                FirebaseDatabase.getInstance().getReference().child("TakipEdilenler")
                    .child(kullanici.id)
                    .child("Takip").child(Firebasekullanici?.uid!!).setValue(true)
            } else {
                FirebaseDatabase.getInstance().getReference().child("TakipEdilenler")
                    .child(kullanici.id)
                    .child("Takip").child(Firebasekullanici?.uid!!).removeValue()
                FirebaseDatabase.getInstance().getReference().child("Takip")
                    .child(Firebasekullanici?.uid!!)
                    .child("TakipEdilenler").child(kullanici.id).removeValue()
            }
        }
    }

    override fun getItemCount(): Int {
        return mkullanicilar.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val kullaniciadi: TextView = itemView.findViewById(R.id.tkp_kullaniciadi)
        val adi: TextView = itemView.findViewById(R.id.adsoyad_text)
        val profileresmi: CircleImageView = itemView.findViewById(R.id.photo_image)
        val tkpet_btn: Button = itemView.findViewById(R.id.tkp_btn)

    }

    private fun takipediliyor(kullaniciId: String, button: Button) {
        val takipyolu: DatabaseReference =
            FirebaseDatabase.getInstance().getReference().child("Takip")
                .child(Firebasekullanici!!.uid).child("TakipEdilenler")
        takipyolu.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child(kullaniciId).exists()) {
                    button.text = "TakipEdiliyor"
                } else {
                    button.text = "TakipET"
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}