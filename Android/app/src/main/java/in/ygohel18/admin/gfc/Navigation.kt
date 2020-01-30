package `in`.ygohel18.admin.gfc

import `in`.ygohel18.admin.gfc.adapter.StudentAdapter
import `in`.ygohel18.admin.gfc.helper.FirebaseHelper
import `in`.ygohel18.admin.gfc.model.Student
import `in`.ygohel18.admin.gfc.ui.StudentProfile
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.ListView
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_add_student.view.*

class Navigation : AppCompatActivity() {

    private val con = FirebaseHelper()

    private lateinit var mNodataImage: ImageView
    private lateinit var mStudentListView: ListView
    private lateinit var mAddStudentFab: FloatingActionButton
    private lateinit var mAppBar: RelativeLayout
    private lateinit var mSearchBar: RelativeLayout
    private lateinit var mBackIcon: MaterialButton
    private lateinit var mSearchIcon: MaterialButton


    private val mStudentList = ArrayList<Student>()
    private val studentAdapter =
        StudentAdapter(context = this, studentModelArrayList = mStudentList)

    private val USER: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    private var UID: String? = USER?.uid.toString()
    private lateinit var appBarConfiguration: AppBarConfiguration

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        this.mNodataImage = findViewById(R.id.no_data_img)
        this.mStudentListView = findViewById(R.id.student_list)
        this.mAddStudentFab = findViewById(R.id.add_student_fab)
        this.mAppBar = findViewById(R.id.view_app_bar)
        this.mSearchBar = findViewById(R.id.view_search_bar)
        this.mSearchIcon = findViewById(R.id.top_app_bar_icon_search)
        this.mBackIcon = findViewById(R.id.top_app_bar_icon_back)

        val userEmail: String? = intent.getStringExtra("email")
        val userUid: String? = intent.getStringExtra("uid")

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        if (userEmail == "bhatt_vishal7@yahoo.in" || userEmail == "yashgohel16@gmail.com" || userEmail == "admin@gfc.ygohel18.com" || userEmail == "gfc@ygohel18.in") {
            this.mAddStudentFab.visibility = View.VISIBLE
        } else {
            this.mAddStudentFab.visibility = View.GONE
        }
        getAllStudent(UID.toString())
        if (userEmail == "vishal@ygohel18.in") {
            this.UID = userUid
        }

        emptyStateNodate()

        this.mSearchIcon.setOnClickListener {
            this.mAppBar.visibility = View.INVISIBLE
            this.mSearchBar.visibility = View.VISIBLE
        }

        this.mBackIcon.setOnClickListener {
            this.mAppBar.visibility = View.VISIBLE
            this.mSearchBar.visibility = View.INVISIBLE
        }

        // Enroll a new student
        this.mAddStudentFab.setOnClickListener {
            showAddStudentDialog()
        }

        mStudentListView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->

                val intent = Intent(this, StudentProfile::class.java)
                intent.putExtra("iID", mStudentList[position].getStudentID())
                intent.putExtra("iName", mStudentList[position].getStudentName())
                intent.putExtra("iEmail", userEmail)
                intent.putExtra("uid", UID)
                startActivity(intent)
            }
        /*Snackbar.make(
            findViewById(R.id.main_activity_view),
            "Welcome Captain 🙏",
            Snackbar.LENGTH_LONG
        ).setTextColor(Color.parseColor("#FDD835")).show()*/
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.navigation, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun addNewStudent(userId: String, studentId: Int, studentName: String) {
        val student = Student()
        student.setStudentID(studentId)
        student.setStudentName(studentName)
        val path = "students/$userId/records/record$studentId"
        val reference = con.getDatabaseReference()
        con.setPath(path)
        reference.child(con.getPath()).setValue(student)
            .addOnSuccessListener {
                //Snackbar.make(findViewById(R.id.main_activity_view), "Student $studentName enrolled 🤩", Snackbar.LENGTH_LONG).setTextColor(Color.parseColor("#FDD835")).show()
            }
            .addOnFailureListener {
                /*Snackbar.make(
                    findViewById(R.id.main_activity_view),
                    "Something goes wrong 😵",
                    Snackbar.LENGTH_LONG
                ).setTextColor(Color.parseColor("#FDD835")).show()*/
            }
    }

    private fun getAllStudent(userId: String) {
        val path = "students/$userId/records"
        val reference = con.getDatabaseReference()
        con.setPath(path)
        reference.child(con.getPath())
            .orderByChild("studentID")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("GFC", "Can't get students")
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists() && dataSnapshot.childrenCount > 0) {
                        mStudentList.clear()
                        for (studentSnapshot: DataSnapshot in dataSnapshot.children) {
                            val student: Student? = studentSnapshot.getValue(Student::class.java)
                            if (student != null) {
                                mStudentList.add(student)
                                studentAdapter.notifyDataSetChanged()
                            }
                        }
                        mStudentListView.adapter = studentAdapter
                        emptyStateNodate()
                    }
                }
            })
    }

    // Popup Add Student Dialog
    @SuppressLint("InflateParams")
    @Suppress("NAME_SHADOWING")
    fun showAddStudentDialog() {
        val view: View = LayoutInflater.from(this).inflate(R.layout.activity_add_student, null)
        val builder: AlertDialog.Builder =
            AlertDialog
                .Builder(this).setView(view)
                .setView(view)
                .setTitle("Enrol new student")
                .setPositiveButton("ENROL") { dialog: DialogInterface, which: Int ->
                    val name: String = view.add_student_name_edit.text.toString()
                    if (name.isEmpty()) {
                        Snackbar.make(
                            findViewById(R.id.main_activity_view),
                            "Name is empty 😅",
                            Snackbar.LENGTH_LONG
                        ).setTextColor(Color.parseColor("#FDD835")).show()
                    } else {
                        if (UID != null) {
                            val id: Int = mStudentList.size + 1
                            addNewStudent(UID.toString(), id, name)
                        }
                    }
                }
                .setNegativeButton("CANCEL") { dialog: DialogInterface, which: Int -> }
        builder.show()
    }

    fun emptyStateNodate() {
        if (this.mStudentList.isEmpty()) {
            this.mNodataImage.visibility = View.VISIBLE
            this.mStudentListView.visibility = View.INVISIBLE
        } else {
            this.mNodataImage.visibility = View.INVISIBLE
            this.mStudentListView.visibility = View.VISIBLE
        }
    }
}
