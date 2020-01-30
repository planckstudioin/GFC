package `in`.ygohel18.admin.gfc

import `in`.ygohel18.admin.gfc.adapter.LogbookAdapter
import `in`.ygohel18.admin.gfc.helper.FirebaseHelper
import `in`.ygohel18.admin.gfc.model.Logbook
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class Logbook : AppCompatActivity() {

    private val con = FirebaseHelper()
    private val mLogbookList = ArrayList<Logbook>()
    private val logbookAdapter = LogbookAdapter(
        context = this,
        logbookModelArrayList = mLogbookList
    )

    private lateinit var mLogbookListView: ListView
    private lateinit var mAppBar: MaterialToolbar
    private lateinit var mSearchText: TextInputEditText

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logbook)

        val sid: Int = intent.getIntExtra("sid", 0)
        val uid: String? = intent.getStringExtra("uid")
        val name: String? = intent.getStringExtra("name")

        this.mLogbookListView = findViewById(R.id.logbook_list)
        this.mAppBar = findViewById(R.id.logbook_app_bar)

        mAppBar.subtitle = name

        getLogbook(uid.toString(), sid)

        mLogbookListView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                showLogbookDialog(position, view)
            }
    }

    @SuppressLint("InflateParams")
    private fun showLogbookDialog(position: Int, v: View) {
        val view: View = LayoutInflater.from(this).inflate(
            R.layout.dialog_logbook,
            null
        )

        view.findViewById<MaterialTextView>(R.id.flight_id).text =
            mLogbookList[position].getLogbookID()
        view.findViewById<MaterialTextView>(R.id.flight_eng).text = mLogbookList[position].getEng()
        view.findViewById<MaterialTextView>(R.id.flight_type).text =
            mLogbookList[position].getType()
        view.findViewById<MaterialTextView>(R.id.flight_regn).text =
            mLogbookList[position].getRegn()
        view.findViewById<MaterialTextView>(R.id.flight_pic).text = mLogbookList[position].getP1()
        view.findViewById<MaterialTextView>(R.id.flight_trp).text = mLogbookList[position].getP2()
        view.findViewById<MaterialTextView>(R.id.flight_takeoff_place).text =
            mLogbookList[position].getFrom()
        view.findViewById<MaterialTextView>(R.id.flight_landing_place).text =
            mLogbookList[position].getTo()
        view.findViewById<MaterialTextView>(R.id.flight_departure_time).text =
            mLogbookList[position].getDep()
        view.findViewById<MaterialTextView>(R.id.flight_arrival_time).text =
            mLogbookList[position].getArr()
        view.findViewById<MaterialTextView>(R.id.flight_flying_time).text =
            v.findViewById<MaterialTextView>(R.id.flight_flying_time).text
        view.findViewById<MaterialTextView>(R.id.flight_flying_date).text =
            v.findViewById<MaterialTextView>(R.id.flight_flying_date).text

        AlertDialog.Builder(this)
            .setView(view)
            .show()
    }

    private fun getLogbook(uid: String, sid: Int) {
        val reference = con.getDatabaseReference()
        val path = "logs/$uid/record$sid"
        con.setPath(path)

        reference.child(con.getPath())
            .orderByChild("date")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("GFC", "Can't get Logbook")
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists() && dataSnapshot.childrenCount > 0) {
                        mLogbookList.clear()
                        for (logbookSnapshot: DataSnapshot in dataSnapshot.children) {
                            val logbook = logbookSnapshot.getValue(Logbook::class.java)
                            if (logbook != null) {
                                mLogbookList.add(logbook)
                                logbookAdapter.notifyDataSetChanged()
                            }
                        }
                        mLogbookListView.adapter = logbookAdapter
                    }
                }
            })
    }
}
