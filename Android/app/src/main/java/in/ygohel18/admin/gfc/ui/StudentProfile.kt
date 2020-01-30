package `in`.ygohel18.admin.gfc.ui

import `in`.ygohel18.admin.gfc.R
import `in`.ygohel18.admin.gfc.helper.FirebaseHelper
import `in`.ygohel18.admin.gfc.model.Documents
import `in`.ygohel18.admin.gfc.model.Logbook
import `in`.ygohel18.admin.gfc.model.Paper
import `in`.ygohel18.admin.gfc.model.Time
import `in`.ygohel18.admin.gfc.util.CommonFunction
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_student_profile.*
import kotlinx.android.synthetic.main.dialog_add_document.view.*
import kotlinx.android.synthetic.main.dialog_add_log.view.*
import kotlinx.android.synthetic.main.dialog_add_paper.view.*
import kotlinx.android.synthetic.main.dialog_update_time.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class StudentProfile : AppCompatActivity() {

    private val con = FirebaseHelper()

    private var mClipboard: ClipboardManager? = null
    private var mDateFormat: SimpleDateFormat? = null

    private lateinit var database: DatabaseReference

    private lateinit var mAppBar: MaterialToolbar
    private lateinit var mUpdateBtn: MaterialButton
    private lateinit var mAddDocBtn: MaterialButton
    private lateinit var mAddPaperBtn: MaterialButton
    private lateinit var mLogbookBtn: MaterialButton
    private lateinit var mAddLogFab: FloatingActionButton
    private lateinit var mTabLayout: TabLayout

    private val mTimeList = ArrayList<Time>()
    private val mDocumentList = ArrayList<Documents>()
    private val mPaperList = ArrayList<Paper>()
    private var vTime = Time()
    private var vPaper = Paper()
    private var mcf = CommonFunction()

    private lateinit var mDualDoneTextView: TextView
    private lateinit var mSoloDoneTextView: TextView
    private lateinit var mSoloRemTextView: TextView
    private lateinit var mCtyDoneTextView: TextView
    private lateinit var mCtyRemTextView: TextView
    private lateinit var mNightDoneTextView: TextView
    private lateinit var mNightRemTextView: TextView
    private lateinit var mIfDoneTextView: TextView
    private lateinit var mIfRemTextView: TextView
    private lateinit var mTotalDoneTextView: TextView
    private lateinit var mTotalRemTextView: TextView
    private lateinit var mPicDoneTextView: TextView
    private lateinit var mPicRemTextView: TextView
    private lateinit var mSNightDoneTextView: TextView
    private lateinit var mSNightRemTextView: TextView
    private lateinit var mSIfDoneTextView: TextView
    private lateinit var mSIfRemTextView: TextView
    private lateinit var mSCtyDoneTextView: TextView
    private lateinit var mSCtyRemTextView: TextView
    private lateinit var mIrDoneTextView: TextView
    private lateinit var mGftDayTextView: TextView
    private lateinit var mGftNightTextView: TextView
    private lateinit var mDateMed: TextView
    private lateinit var mDateSpl: TextView
    private lateinit var mDateFrtol: TextView
    private lateinit var mNoMed: TextView
    private lateinit var mNoSpl: TextView
    private lateinit var mNoFrtol: TextView
    private lateinit var mTitleMed: TextView
    private lateinit var mTitleSpl: TextView
    private lateinit var mTitleFrtol: TextView
    private lateinit var mTitleAvimet: TextView
    private lateinit var mTitleAirreg: TextView
    private lateinit var mTitleAirnav: TextView
    private lateinit var mTitleTechgen: TextView
    private lateinit var mTitleTechspec: TextView
    private lateinit var mTitleRtra: TextView


    @SuppressLint("SimpleDateFormat", "ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_profile)

        mClipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
        mDateFormat = SimpleDateFormat("yyyyMMdd")
        val currentDate = mDateFormat!!.format(Date())

        val id: Int = intent.getIntExtra("iID", 0)
        val name: String? = intent.getStringExtra("iName")
        val uid: String? = intent.getStringExtra("uid")
        val email: String? = intent.getStringExtra("iEmail")

        database = FirebaseDatabase.getInstance().reference

        init()

        if (email == "bhatt_vishal7@yahoo.in" || email == "yashgohel16@gmail.com" || email == "admin@gfc.ygohel18.in" || email == "gfc@ygohel18.in") {
            this.add_log_fab.visibility = View.VISIBLE
            this.mAddPaperBtn.visibility = View.VISIBLE
            this.mAddDocBtn.visibility = View.VISIBLE
            this.mUpdateBtn.visibility = View.VISIBLE
        } else {
            this.add_log_fab.visibility = View.GONE
            this.mAddPaperBtn.visibility = View.GONE
            this.mAddDocBtn.visibility = View.GONE
            this.mUpdateBtn.visibility = View.GONE
        }

        mAppBar.title = name
        mAppBar.subtitle = id.toString()

        this.mcf.setDate(currentDate, "yyyymmdd")

        if (uid != null) {
            getAllTime(uid, id)
            getAllDocument(uid, id)
            getAllPaper(uid, id)
        }

        this.mLogbookBtn.setOnClickListener {
            val intent = Intent(this, `in`.ygohel18.admin.gfc.Logbook::class.java)
            intent.putExtra("uid", uid)
            intent.putExtra("sid", id)
            intent.putExtra("name", name)
            startActivity(intent)
        }

        this.mAddDocBtn.setOnClickListener {
            showAddDocumentDialog(id, uid)
        }

        this.mAddPaperBtn.setOnClickListener {
            showAddPaperDialog(id, uid)
        }

        this.add_log_fab.setOnClickListener {
            showAddLogDialog(id, uid)
        }

        this.mUpdateBtn.setOnClickListener {
            showChangeTimeDialog(id, uid, "00:00")
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setPaperData() {
        var f = true
        val p = Paper()
        val cf = CommonFunction()
        cf.setSeparator("/")
        val color = "#B00020"
        val color2 = "#FDD835"
        val color3 = "#43A047"

        val total: Int = mPaperList.count()
        var count = 0


        while (count < total) {
            val name = mPaperList[count].getPaperName()
            val status = mPaperList[count].getPaperStatus()

            when (name) {
                "AVI MET" -> {
                    when (status) {
                        "CLEARED" -> mTitleAvimet.setTextColor(Color.parseColor(color3))
                        "REMAINING" -> mTitleAvimet.setTextColor(Color.parseColor(color))
                    }
                }

                "AIR REG" -> {
                    when (status) {
                        "CLEARED" -> mTitleAirreg.setTextColor(Color.parseColor(color3))
                        "REMAINING" -> mTitleAirreg.setTextColor(Color.parseColor(color))
                    }
                }

                "AIR NAV" -> {

                    when (status) {
                        "CLEARED" -> mTitleAirnav.setTextColor(Color.parseColor(color3))
                        "REMAINING" -> mTitleAirnav.setTextColor(Color.parseColor(color))
                    }
                }

                "TECH GEN" -> {
                    when (status) {
                        "CLEARED" -> mTitleTechgen.setTextColor(Color.parseColor(color3))
                        "REMAINING" -> mTitleTechgen.setTextColor(Color.parseColor(color))
                    }
                }

                "TECH SPEC" -> {
                    when (status) {
                        "CLEARED" -> mTitleTechspec.setTextColor(Color.parseColor(color3))
                        "REMAINING" -> mTitleTechspec.setTextColor(Color.parseColor(color))
                    }
                }

                "RTR (A)" -> {
                    when (status) {
                        "CLEARED" -> mTitleRtra.setTextColor(Color.parseColor(color3))
                        "REMAINING" -> mTitleRtra.setTextColor(Color.parseColor(color))
                    }
                }
            }
            count++
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setDocumentData() {
        var f = true
        val d = Documents()
        val cf = CommonFunction()
        val cm = CommonFunction()
        cf.setSeparator("/")
        cm.setSeparator("/")
        val color = "#B00020"
        val color2 = "#FDD835"

        val total: Int = mDocumentList.count()
        var count = 0

        while (count < total) {
            val name = mDocumentList[count].getDocumentName()
            val date = mDocumentList[count].getDocumentExpiryDate()
            val no = mDocumentList[count].getDocumentNO()
            val compare = mcf.getDate().isNotEmpty()


            if (date == "0" || date == "1") {
                f = false
            } else {
                cf.setDate(date, "yyyymmdd")
                f = true
            }

            cm.setDate(date, "yyyymmdd")
            val expireMonth = cm.getMonth().toInt()
            val remain_month = expireMonth - mcf.getMonth().toInt()

            when (name) {
                "Medical" -> {
                    if (f) {
                        if (compare) {
                            when (cf.compareDate(date, mcf.getDate(), "yyyymmdd")) {
                                -1 -> {
                                    if (remain_month in 1..3) {
                                        mDateMed.setTextColor(Color.parseColor(color))
                                        Snackbar.make(
                                            findViewById(R.id.student_profile_view),
                                            "Medical Expiring Soon",
                                            Snackbar.LENGTH_LONG
                                        ).setTextColor(Color.parseColor(color2)).show()
                                    } else if (remain_month < 0) {
                                        mDateMed.setTextColor(Color.parseColor(color))
                                        Snackbar.make(
                                            findViewById(R.id.student_profile_view),
                                            "Medical Expired",
                                            Snackbar.LENGTH_LONG
                                        ).setTextColor(Color.parseColor(color2)).show()
                                    }
                                }
                                0 -> mDateMed.setTextColor(Color.parseColor(color))
                                1 -> {
                                    val year: Int =
                                        cf.compareNumber(
                                            cf.getYear().toInt(),
                                            mcf.getYear().toInt()
                                        )
                                    if (year == 0) {
                                        val ans =
                                            cf.divide(cf.getMonth().toInt(), mcf.getMonth().toInt())
                                        if (ans < 3) {
                                            mDateMed.setTextColor(Color.parseColor(color2))
                                            Snackbar.make(
                                                findViewById(R.id.student_profile_view),
                                                "Medical Expiring Soon",
                                                Snackbar.LENGTH_LONG
                                            ).setTextColor(Color.parseColor(color2)).show()
                                        }
                                    } else {
                                        if (year == 1) {
                                            val yd = cf.getYear().toInt() - mcf.getYear().toInt()
                                            if (yd == 1) {
                                                val om = cf.getMonth().toInt()
                                                val tm = mcf.getMonth().toInt()

                                                if (om == 1 || om == 2) {
                                                    if (tm == 11 || tm == 12) {
                                                        mDateMed.setTextColor(
                                                            Color.parseColor(
                                                                color2
                                                            )
                                                        )
                                                        Snackbar.make(
                                                            findViewById(R.id.student_profile_view),
                                                            "Medical Expiring Soon",
                                                            Snackbar.LENGTH_LONG
                                                        ).setTextColor(
                                                            Color.parseColor(color2)
                                                        ).show()
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        cf.setDateFormat("ddmmyyyy")
                        mDateMed.text = cf.getFormatedDate()
                    } else {
                        when (date) {
                            "0" -> mDateMed.text = "00:00"
                            "1" -> mDateMed.text = "TU"
                        }
                    }
                    mNoMed.text = no
                }
                "SPL" -> {
                    if (f) {
                        if (compare) {
                            when (cf.compareDate(date, mcf.getDate(), mcf.getDateFormat())) {
                                -1 -> {
                                    if (remain_month in 1..3) {
                                        mDateMed.setTextColor(Color.parseColor(color))
                                        Snackbar.make(
                                            findViewById(R.id.student_profile_view),
                                            "SPL Expiring Soon",
                                            Snackbar.LENGTH_LONG
                                        ).setTextColor(Color.parseColor(color2)).show()
                                    } else if (remain_month < 0) {
                                        mDateMed.setTextColor(Color.parseColor(color))
                                        Snackbar.make(
                                            findViewById(R.id.student_profile_view),
                                            "SPL Expired",
                                            Snackbar.LENGTH_LONG
                                        ).setTextColor(Color.parseColor(color2)).show()
                                    }
                                }
                                0 -> mDateSpl.setTextColor(Color.parseColor(color))
                                1 -> {
                                    val year: Int =
                                        cf.compareNumber(
                                            cf.getYear().toInt(),
                                            mcf.getYear().toInt()
                                        )
                                    if (year == 0) {
                                        val ans =
                                            cf.divide(cf.getMonth().toInt(), mcf.getMonth().toInt())
                                        if (ans in 1..2) {
                                            mDateSpl.setTextColor(Color.parseColor(color2))
                                            Snackbar.make(
                                                findViewById(R.id.student_profile_view),
                                                "SPL Expiring Soon",
                                                Snackbar.LENGTH_LONG
                                            ).setTextColor(Color.parseColor(color2)).show()
                                        }
                                    } else {
                                        if (year == 1) {
                                            val yd = cf.getYear().toInt() - mcf.getYear().toInt()
                                            if (yd == 1) {
                                                val om = cf.getMonth().toInt()
                                                val tm = mcf.getMonth().toInt()

                                                if (om == 1 || om == 2) {
                                                    if (tm == 11 || tm == 12) {
                                                        mDateSpl.setTextColor(
                                                            Color.parseColor(
                                                                color2
                                                            )
                                                        )
                                                        Snackbar.make(
                                                            findViewById(R.id.student_profile_view),
                                                            "SPL Expiring Soon",
                                                            Snackbar.LENGTH_LONG
                                                        ).setTextColor(
                                                            Color.parseColor(color2)
                                                        ).show()
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        cf.setDateFormat("ddmmyyyy")
                        mDateSpl.text = cf.getFormatedDate()
                    } else {
                        if (date == "0") {
                            mDateSpl.text = "00:00"
                        }
                    }
                    mNoSpl.text = no
                }
                "FRTOL" -> {
                    if (f) {
                        if (compare) {
                            when (cf.compareDate(date, mcf.getDate(), mcf.getDateFormat())) {
                                -1 -> {
                                    if (remain_month in 1..3) {
                                        mDateMed.setTextColor(Color.parseColor(color))
                                        Snackbar.make(
                                            findViewById(R.id.student_profile_view),
                                            "FRTOL Expiring Soon",
                                            Snackbar.LENGTH_LONG
                                        ).setTextColor(Color.parseColor(color2)).show()
                                    } else if (remain_month < 0) {
                                        mDateMed.setTextColor(Color.parseColor(color))
                                        Snackbar.make(
                                            findViewById(R.id.student_profile_view),
                                            "FRTOL Expired",
                                            Snackbar.LENGTH_LONG
                                        ).setTextColor(Color.parseColor(color2)).show()
                                    }
                                }
                                0 -> mDateFrtol.setTextColor(Color.parseColor(color))
                                1 -> {
                                    val year: Int =
                                        cf.compareNumber(
                                            cf.getYear().toInt(),
                                            mcf.getYear().toInt()
                                        )
                                    if (year == 0) {
                                        val ans =
                                            cf.divide(cf.getMonth().toInt(), mcf.getMonth().toInt())
                                        if (ans in 1..2) {
                                            mDateFrtol.setTextColor(Color.parseColor(color2))
                                            Snackbar.make(
                                                findViewById(R.id.student_profile_view),
                                                "FRTOL Expiring Soon",
                                                Snackbar.LENGTH_LONG
                                            ).setTextColor(Color.parseColor(color2)).show()
                                        }
                                    } else {
                                        if (year == 1) {
                                            val yd = cf.getYear().toInt() - mcf.getYear().toInt()
                                            if (yd == 1) {
                                                val om = cf.getMonth().toInt()
                                                val tm = mcf.getMonth().toInt()

                                                if (om == 1 || om == 2) {
                                                    if (tm == 11 || tm == 12) {
                                                        mDateFrtol.setTextColor(
                                                            Color.parseColor(
                                                                color2
                                                            )
                                                        )
                                                        Snackbar.make(
                                                            findViewById(R.id.student_profile_view),
                                                            "FRTOL Expiring Soon",
                                                            Snackbar.LENGTH_LONG
                                                        ).setTextColor(
                                                            Color.parseColor(color2)
                                                        ).show()
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        cf.setDateFormat("ddmmyyyy")
                        mDateFrtol.text = cf.getFormatedDate()
                    } else {
                        if (date == "0") {
                            mDateFrtol.text = "00:00"
                        }
                    }
                    mNoFrtol.text = no
                }
            }
            count++
        }

        when (mDateMed.text) {
            "TU" -> {
                mDateMed.setTextColor(Color.parseColor(color))
                mTitleMed.setTextColor(Color.parseColor("#000000"))
            }

            "EMPTY" -> {
                mTitleMed.setTextColor(Color.parseColor(color2))
            }
        }

        if (mDateSpl.text == "EMPTY" || mNoSpl.text == "EMPTY") {
            mTitleSpl.setTextColor(Color.parseColor(color2))
        }

        if (mDateFrtol.text == "EMPTY" || mNoFrtol.text == "EMPTY") {
            mTitleFrtol.setTextColor(Color.parseColor(color2))
        }
    }

    private fun setTimeData(t: Time) {
        this.mSoloDoneTextView.text = t.getSolo()
        this.mSoloRemTextView.text = t.getSoloRemain()
        this.mDualDoneTextView.text = t.getDual()
        this.mCtyDoneTextView.text = t.getXcty()
        this.mCtyRemTextView.text = t.getCtyRemain()
        this.mIfDoneTextView.text = t.getVif()
        this.mIfRemTextView.text = t.getIfRemain()
        this.mNightDoneTextView.text = t.getNight()
        this.mNightRemTextView.text = t.getNightRemain()
        this.mTotalDoneTextView.text = t.getTotal()
        this.mTotalRemTextView.text = t.getRemain()
        this.mPicDoneTextView.text = t.getPic()
        this.mPicRemTextView.text = t.getPicRemain()
        this.mSNightDoneTextView.text = t.getSnight()
        this.mSNightRemTextView.text = t.getSNightRemain()
        this.mSIfDoneTextView.text = t.getSif()
        this.mSIfRemTextView.text = t.getSIfRemain()
        this.mSCtyDoneTextView.text = t.getSxcty()
        this.mSCtyRemTextView.text = t.getSxctyNight()
        this.mGftDayTextView.text = t.getGftDay()
        this.mGftNightTextView.text = t.getGftNight()
        this.mIrDoneTextView.text = t.getIr()

        val c = "#3949ab"

        if (t.checkHour(t.getDual(), 85)) {
            this.mDualDoneTextView.setTextColor(Color.parseColor("#B00020"))
        } else if (t.checkHour(t.getDual(), 80)) {
            this.mDualDoneTextView.setTextColor(Color.parseColor("#Fdd835"))
        }

        if (t.checkHour(t.getVif(), 14)) {
            this.mIfDoneTextView.setTextColor(Color.parseColor("#Fdd835"))
        }

        if (t.checkMinute(t.getIr(), "00:45")) {
            this.mIrDoneTextView.setTextColor(Color.parseColor("#43A047"))
        } else {
            this.mIrDoneTextView.setTextColor(Color.parseColor("#B00020"))
        }

        if (t.checkMinute(t.getSnight(), "00:45")) {
            this.mSNightDoneTextView.setTextColor(Color.parseColor("#43A047"))
        } else {
            this.mSNightDoneTextView.setTextColor(Color.parseColor("#B00020"))
        }

        if (t.checkMinute(t.getGftDay(), "00:45")) {
            this.mGftDayTextView.setTextColor(Color.parseColor("#43A047"))
        } else {
            this.mGftDayTextView.setTextColor(Color.parseColor("#B00020"))
        }

        if (t.checkMinute(t.getGftNight(), "00:45")) {
            this.mGftNightTextView.setTextColor(Color.parseColor("#43A047"))
        } else {
            this.mGftNightTextView.setTextColor(Color.parseColor("#B00020"))
        }

        if (t.checkMinute(t.getSxcty(), "00:45")) {
            this.mSCtyDoneTextView.setTextColor(Color.parseColor("#43A047"))
        } else {
            this.mSCtyDoneTextView.setTextColor(Color.parseColor("#B00020"))
        }

        if (t.checkMinute(t.getSxctyNight(), "00:45")) {
            this.mSCtyRemTextView.setTextColor(Color.parseColor("#43A047"))
        } else {
            this.mSCtyRemTextView.setTextColor(Color.parseColor("#B00020"))
        }

        if (t.getRemain() == "00:00") {
            Snackbar.make(
                findViewById(R.id.student_profile_view),
                "Total 200 Hours Completed",
                Snackbar.LENGTH_LONG
            ).setTextColor(Color.parseColor("#FDD835")).show()
            this.mTotalDoneTextView.setTextColor(Color.parseColor(c))
        }

        if (t.getSoloRemain() == "00:00") {
            this.mSoloDoneTextView.setTextColor(Color.parseColor(c))
        }

        if (t.getIfRemain() == "00:00") {
            this.mIfDoneTextView.setTextColor(Color.parseColor(c))
        }

        if (t.getNightRemain() == "00:00") {
            this.mNightDoneTextView.setTextColor(Color.parseColor(c))
        }

        if (t.getCtyRemain() == "00:00") {
            this.mCtyDoneTextView.setTextColor(Color.parseColor(c))
        }

        if (t.getPicRemain() == "00:00") {
            this.mPicDoneTextView.setTextColor(Color.parseColor(c))
        }

        if (t.getSNightRemain() == "00:00") {
            this.mSNightDoneTextView.setTextColor(Color.parseColor(c))
        }

        if (t.getSIfRemain() == "00:00") {
            this.mSIfDoneTextView.setTextColor(Color.parseColor(c))
        }
    }

    private fun getAllPaper(uid: String, id: Int) {
        val path = "papers/$uid/records/record$id"

        database
            .child(path)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("GFC", "Can't get papers")
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists() && dataSnapshot.childrenCount > 0) {
                        mPaperList.clear()
                        for (paperSnapshot: DataSnapshot in dataSnapshot.children) {
                            val paper = paperSnapshot.getValue(Paper::class.java)
                            if (paper != null) {
                                mPaperList.add(paper)
                            }
                        }
                        setPaperData()
                    }
                }
            })
    }

    private fun getAllDocument(userId: String, studentId: Int) {
        val path = "record$studentId"
        database
            .child("documents")
            .child(userId)
            .child("records")
            .child(path)
            .orderByChild("documentID")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("GFC", "Can't get documents")
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists() && dataSnapshot.childrenCount > 0) {
                        mDocumentList.clear()
                        for (docSnapshot: DataSnapshot in dataSnapshot.children) {
                            val doc = docSnapshot.getValue(Documents::class.java)
                            if (doc != null) {
                                mDocumentList.add(doc)
                            }
                        }
                        setDocumentData()
                    }
                }
            })
    }

    private fun getAllTime(userId: String, studentId: Int) {
        val path = "times/$userId/records/record$studentId"
        database
            .child(path)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("GFC", "Can't get times")
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists() && dataSnapshot.childrenCount > 0) {
                        mTimeList.clear()
                        vTime = dataSnapshot.getValue(Time::class.java)!!
                        setTimeData(vTime)
                    }
                }
            })
    }

    // Popup Add Paper Dialog
    @SuppressLint("InflateParams")
    private fun showAddPaperDialog(id: Int, uid: String?) {
        val view: View = LayoutInflater.from(this).inflate(
            R.layout.dialog_add_paper,
            null
        )

        AlertDialog.Builder(this)
            .setView(view)
            .setTitle("Add Paper")
            .setPositiveButton("ADD") { dialog, which ->
                var name: String = view.paper_name_spinner.selectedItem.toString()
                val status: String = view.paper_status_spinner.selectedItem.toString()
                val nameid: String = view.paper_name_spinner.selectedItemPosition.toString()
                val pid = "$id$nameid"
                val paper = Paper()
                paper.setPaperName(name)
                paper.setPaperID(pid)
                paper.setPaperStatus(status)

                when (name) {
                    "AVI MET" -> name = "AviMet"
                    "AIR REG" -> name = "AirReg"
                    "AIR NAV" -> name = "AirNav"
                    "TECH GEN" -> name = "TechGen"
                    "TECH SPEC" -> name = "TechSpec"
                    "RTR (A)" -> name = "RtrA"
                }

                val path = "papers/${uid.toString()}/records/record$id/$name"

                database.child(path).setValue(paper).addOnSuccessListener {
                    Snackbar.make(
                        findViewById(R.id.student_profile_view),
                        "$name Paper Added 🤩",
                        Snackbar.LENGTH_LONG
                    ).setTextColor(Color.parseColor("#FDD835")).show()
                }.addOnFailureListener {
                    Snackbar.make(
                        findViewById(R.id.student_profile_view),
                        "Something goes wrong 😵",
                        Snackbar.LENGTH_LONG
                    ).setTextColor(Color.parseColor("#FDD835")).show()
                }
            }
            .setNegativeButton("CANCEL") { dialog, which -> }
            .show()
    }

    // Popup Add Document Dialog
    @SuppressLint("InflateParams")
    private fun showAddDocumentDialog(id: Int, uid: String?) {
        val view: View = LayoutInflater.from(this).inflate(
            R.layout.dialog_add_document,
            null
        )

        val builder = AlertDialog.Builder(this)
            .setView(view)
            .setTitle("Add Document")
            .setPositiveButton("ADD") { dialog: DialogInterface, which: Int ->
                val no = view.doc_no_edit.text.toString()
                val exp = view.doc_exp_edit.text.toString()
                val name = view.doc_type_spinner.selectedItem.toString()
                val nameid = view.doc_type_spinner.selectedItemPosition.toString()
                val path = "documents/${uid.toString()}/records/record$id/$name"
                val did = "$id$nameid"

                if (exp.isNotEmpty()) {
                    val doc = Documents()
                    doc.setDocumentID(did)
                    doc.setDocumentNO(no)
                    doc.setDocumentName(name)
                    doc.setDocumentExpiryDate(exp)
                    if (no.isEmpty()) doc.setDocumentNO("EMPTY") else doc.setDocumentNO(no)

                    database.child(path).setValue(doc).addOnSuccessListener {
                        Snackbar.make(
                            findViewById(R.id.student_profile_view),
                            "$name Document Added 🤩",
                            Snackbar.LENGTH_LONG
                        ).setTextColor(Color.parseColor("#FDD835")).show()
                    }.addOnFailureListener {
                        Snackbar.make(
                            findViewById(R.id.student_profile_view),
                            "Something goes wrong 😵",
                            Snackbar.LENGTH_LONG
                        ).setTextColor(Color.parseColor("#FDD835")).show()
                    }
                } else {
                    Snackbar.make(
                        findViewById(R.id.student_profile_view),
                        "Field is empty 😅",
                        Snackbar.LENGTH_LONG
                    ).setTextColor(Color.parseColor("#FDD835")).show()
                }
            }
            .setNegativeButton("CANCEL") { dialog, which -> }
        builder.show()
    }

    // Popup Add Log Dialog
    @SuppressLint("InflateParams")
    private fun showAddLogDialog(id: Int, uid: String?) {
        val view: View = LayoutInflater.from(this).inflate(R.layout.dialog_add_log, null)

        val builder = AlertDialog.Builder(this)
            .setView(view)
            .setTitle("Add New Log")
            .setPositiveButton("ADD") { dialog, which ->
                val date: String = view.flight_date_edit.text.toString()
                val eng = view.flight_eng_edit.text.toString()
                val vtype: String = view.flight_type_edit.text.toString()
                val regn = view.flight_regn_edit.text.toString()
                val p1: String = view.flight_p1_edit.text.toString()
                val p2 = view.flight_p2_edit.text.toString()
                val from: String = view.flight_from_edit.text.toString()
                val to = view.flight_to_edit.text.toString()
                val dep: String = view.flight_dep_edit.text.toString()
                val arr = view.flight_arr_edit.text.toString()

                val path = "logs/${uid.toString()}/record$id/"

                if (date.isNotEmpty() &&
                    eng.isNotEmpty() &&
                    vtype.isNotEmpty() &&
                    regn.isNotEmpty() &&
                    p1.isNotEmpty() &&
                    from.isNotEmpty() &&
                    to.isNotEmpty() &&
                    dep.isNotEmpty() &&
                    arr.isNotEmpty()
                ) {
                    val logbook = Logbook(date, eng, vtype, regn, p1, p2, arr, dep, from, to)
                    val u = "$id$date"
                    logbook.setLogbookID(u)
                    database.child(path).child(date).setValue(logbook).addOnSuccessListener {
                        val timevalue: String = Time().setZero(Time().subTime(dep, arr))
                        val mc = ClipData.newPlainText("time", timevalue)
                        mClipboard!!.setPrimaryClip(mc)
                        showChangeTimeDialog(id, uid, timevalue)
                        Toast.makeText(this, "Log Added", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        Snackbar.make(
                            findViewById(R.id.student_profile_view),
                            "Something goes wrong 😵",
                            Snackbar.LENGTH_LONG
                        ).setTextColor(Color.parseColor("#FDD835")).show()
                    }
                } else {
                    Snackbar.make(
                        findViewById(R.id.student_profile_view),
                        "Field is empty 😅",
                        Snackbar.LENGTH_LONG
                    ).setTextColor(Color.parseColor("#FDD835")).show()
                }
            }
            .setNegativeButton("CANCEL") { dialog: DialogInterface, which: Int -> }
        builder.show()
    }

    // Popup Change Time Dialog
    @SuppressLint("InflateParams")
    @Suppress("NAME_SHADOWING")
    fun showChangeTimeDialog(id: Int, uid: String?, tvalue: String) {
        val view: View = LayoutInflater.from(this).inflate(R.layout.dialog_update_time, null)
        view.flight_time_text.setText(tvalue)
        val reference = con.getDatabaseReference()
        val path = "times/${uid.toString()}/records/record$id"
        con.setPath(path)
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            .setView(view)
            .setTitle("Change Flight Time")
            .setPositiveButton("UPDATE") { dialog, which ->
                var time: String = view.flight_time_text.text.toString()
                var type: String = view.flight_type_spinner.selectedItem.toString()


                val t: Time = this.vTime

                when (type) {
                    "Dual" -> type = "dual"
                    "Solo" -> type = "solo"
                    "100 NM XCTY" -> type = "xcty"
                    "IF" -> type = "vif"
                    "NIGHT" -> type = "night"
                    "Last Six - PIC" -> type = "pic"
                    "Last Six - Night" -> type = "snight"
                    "Last Six - IF" -> type = "sif"
                    "Last Six - XCTY Day" -> type = "sxcty"
                    "Last Six - XCTY Night" -> type = "sxctynight"
                    "Last Six - GFT Day" -> type = "gftday"
                    "Last Six - GFT Night" -> type = "gftnight"
                    "Last Six - IR Check" -> type = "ir"
                }

                if (time.isNotEmpty() && type.isNotEmpty()) {

                    when (type) {
                        "dual" -> time = t.setZero(t.totalTime(t.getDual(), time))
                        "solo" -> time = t.setZero(t.totalTime(t.getSolo(), time))
                        "vif" -> time = t.setZero(t.totalTime(t.getVif(), time))
                        "night" -> time = t.setZero(t.totalTime(t.getNight(), time))
                        "xcty" -> time = t.setZero(t.totalTime(t.getXcty(), time))
                        "pic" -> time = t.setZero(t.totalTime(t.getPic(), time))
                        "snight" -> time = t.setZero(t.totalTime(t.getSnight(), time))
                        "sxcty" -> time = t.setZero(t.totalTime(t.getSxcty(), time))
                        "sxctynight" -> time = t.setZero(t.totalTime(t.getSxctyNight(), time))
                        "sif" -> time = t.setZero(t.totalTime(t.getSif(), time))
                        "ir" -> time = t.setZero(t.totalTime(t.getIr(), time))
                        "gftday" -> time = t.setZero(t.totalTime(t.getGftDay(), time))
                        "gftnight" -> time = t.setZero(t.totalTime(t.getGftNight(), time))
                    }

                    reference.child(path).child(type).setValue(time).addOnSuccessListener {
                        Snackbar.make(
                            findViewById(R.id.student_profile_view),
                            "$type updated with $time 🤩",
                            Snackbar.LENGTH_LONG
                        ).setTextColor(Color.parseColor("#FDD835")).show()
                    }.addOnFailureListener {
                        Snackbar.make(
                            findViewById(R.id.student_profile_view),
                            "Something goes wrong 😵",
                            Snackbar.LENGTH_LONG
                        ).setTextColor(Color.parseColor("#FDD835")).show()
                    }
                } else {
                    Snackbar.make(
                        findViewById(R.id.student_profile_view),
                        "Time is empty 😅",
                        Snackbar.LENGTH_LONG
                    ).setTextColor(Color.parseColor("#FDD835")).show()
                }
            }
            .setNegativeButton("REPLACE") { dialog: DialogInterface, which: Int ->
                val time = view.flight_time_text.text.toString()
                var type = view.flight_type_spinner.selectedItem.toString()

                when (type) {
                    "Dual" -> type = "dual"
                    "Solo" -> type = "solo"
                    "100 NM XCTY" -> type = "xcty"
                    "IF" -> type = "vif"
                    "NIGHT" -> type = "night"
                    "Last Six - PIC" -> type = "pic"
                    "Last Six - Night" -> type = "snight"
                    "Last Six - IF" -> type = "sif"
                    "Last Six - XCTY Day" -> type = "sxcty"
                    "Last Six - XCTY Night" -> type = "sxctynight"
                    "Last Six - GFT Day" -> type = "gftday"
                    "Last Six - GFT Night" -> type = "gftnight"
                    "Last Six - IR Check" -> type = "ir"
                }

                if (time.isNotEmpty() && type.isNotEmpty()) {
                    reference.child(path).child(type).setValue(time).addOnSuccessListener {
                        Snackbar.make(
                            findViewById(R.id.student_profile_view),
                            "$type replaced with $time 🤩",
                            Snackbar.LENGTH_LONG
                        ).setTextColor(Color.parseColor("#FDD835")).show()
                    }.addOnFailureListener {
                        Snackbar.make(
                            findViewById(R.id.student_profile_view),
                            "Something goes wrong 😵",
                            Snackbar.LENGTH_LONG
                        ).setTextColor(Color.parseColor("#FDD835")).show()
                    }
                } else {
                    Snackbar.make(
                        findViewById(R.id.student_profile_view),
                        "Time is empty 😅",
                        Snackbar.LENGTH_LONG
                    ).setTextColor(Color.parseColor("#FDD835")).show()
                }
            }
            .setNeutralButton("CANCLE") { dialog, which -> }
        builder.show()
    }

    private fun init() {
        mAppBar = findViewById(R.id.student_app_bar)
        mUpdateBtn = findViewById(R.id.update_time_btn)
        mAddDocBtn = findViewById(R.id.add_doc_btn)
        mAddPaperBtn = findViewById(R.id.add_paper_btn)
        mLogbookBtn = findViewById(R.id.logbook_btn)
        mAddLogFab = findViewById(R.id.add_log_fab)
        mTabLayout = findViewById(R.id.tabLayout)

        mDualDoneTextView = findViewById(R.id.dual_done)
        mSoloDoneTextView = findViewById(R.id.solo_done)
        mSoloRemTextView = findViewById(R.id.solo_rem)
        mIfDoneTextView = findViewById(R.id.if_done)
        mIfRemTextView = findViewById(R.id.if_rem)
        mCtyDoneTextView = findViewById(R.id.cty_done)
        mCtyRemTextView = findViewById(R.id.cty_rem)
        mNightDoneTextView = findViewById(R.id.night_done)
        mNightRemTextView = findViewById(R.id.night_rem)
        mTotalDoneTextView = findViewById(R.id.total_done)
        mTotalRemTextView = findViewById(R.id.total_rem)
        mPicDoneTextView = findViewById(R.id.pic_done)
        mPicRemTextView = findViewById(R.id.pic_rem)
        mIrDoneTextView = findViewById(R.id.ir_done)
        mSIfDoneTextView = findViewById(R.id.sif_done)
        mSIfRemTextView = findViewById(R.id.sif_rem)
        mSNightDoneTextView = findViewById(R.id.snight_done)
        mSNightRemTextView = findViewById(R.id.snight_rem)
        mSCtyDoneTextView = findViewById(R.id.scty_done)
        mSCtyRemTextView = findViewById(R.id.scty_rem)
        mGftDayTextView = findViewById(R.id.gft_day)
        mGftNightTextView = findViewById(R.id.gft_night)
        mDateMed = findViewById(R.id.date_med)
        mDateSpl = findViewById(R.id.date_spl)
        mDateFrtol = findViewById(R.id.date_frtol)
        mNoMed = findViewById(R.id.no_med)
        mNoSpl = findViewById(R.id.no_spl)
        mNoFrtol = findViewById(R.id.no_frtol)
        mTitleMed = findViewById(R.id.title_med)
        mTitleSpl = findViewById(R.id.title_spl)
        mTitleFrtol = findViewById(R.id.title_frtol)
        mTitleAvimet = findViewById(R.id.title_avimet)
        mTitleAirreg = findViewById(R.id.title_airreg)
        mTitleAirnav = findViewById(R.id.title_airnav)
        mTitleTechgen = findViewById(R.id.title_techgen)
        mTitleTechspec = findViewById(R.id.title_techspec)
        mTitleRtra = findViewById(R.id.title_rtra)

        //mTabLayout.addTab(mTabLayout.newTab().setText("Flight Records"))
        //mTabLayout.addTab(mTabLayout.newTab().setText("After Six Months"))
    }
}