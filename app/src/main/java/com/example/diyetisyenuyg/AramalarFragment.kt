import ADAPTER.KullanicimAdapter
import Model.Kullanicim
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diyetisyenuyg.R
import com.google.firebase.database.*

class AramalarFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var kullanicimAdapter: KullanicimAdapter
    private val kullanicilar: ArrayList<Kullanicim> = arrayListOf()
    private lateinit var aramaBar: EditText
    private lateinit var aramaValueEventListener: ValueEventListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_aramalar, container, false)

        recyclerView = view.findViewById(R.id.recycler_view_Arama)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

        aramaBar = view.findViewById(R.id.edt_arama_bar)

        kullanicimAdapter = KullanicimAdapter(requireContext(), kullanicilar)
        recyclerView.adapter = kullanicimAdapter

        aramaBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                kullaniciAra(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        return view
    }

    private fun kullaniciAra(s: String) {
        if (::aramaValueEventListener.isInitialized) {
            val query = FirebaseDatabase.getInstance().getReference("profile")
                .orderByChild("kullaniciadi")
                .startAt(s.toLowerCase())
                .endAt(s.toLowerCase() + "\uf8ff")
            query.removeEventListener(aramaValueEventListener)
        }

        val query = FirebaseDatabase.getInstance().getReference("profile")
            .orderByChild("kullaniciadi")
            .startAt(s.toLowerCase())
            .endAt(s.toLowerCase() + "\uf8ff")

        aramaValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                kullanicilar.clear()
                for (snapshot in dataSnapshot.children) {
                    val kullanici = snapshot.getValue(Kullanicim::class.java)
                    if (kullanici != null) {
                        kullanicilar.add(kullanici)
                    }
                }
                kullanicimAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }

        query.addValueEventListener(aramaValueEventListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val query = FirebaseDatabase.getInstance().getReference("profile")
            .orderByChild("kullaniciadi")
        query.removeEventListener(aramaValueEventListener)
    }
}
