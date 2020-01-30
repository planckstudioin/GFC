package `in`.ygohel18.admin.gfc

import `in`.ygohel18.admin.gfc.ui.LoginActivity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class Selection : AppCompatActivity() {

    private val url = "https://gfcweb.000webhostapp.com/"

    private lateinit var mWebViewBtn: MaterialButton
    private lateinit var mNormalBtn: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)

        this.mWebViewBtn = findViewById(R.id.open_web)
        this.mNormalBtn = findViewById(R.id.open_normal)

        this.mWebViewBtn.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(this.url)
            startActivity(i)
        }
        this.mNormalBtn.setOnClickListener {
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }
    }
}
