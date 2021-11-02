package telegram.free.firebaseanalytics

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    lateinit var btn: Button
    lateinit var etname: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        clickListener()
    }

    private fun clickListener() {
        btn.setOnClickListener({
            var name = etname.text.toString()
            updateData(name)
            throw  RuntimeException("This is a crash");
            startActivity(Intent(this, MainActivity2::class.java))
        })


    }

    private fun updateData(name: String) {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name)
        firebaseAnalytics?.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
    }

    private fun init() {
        btn = findViewById(R.id.btn_submit);
        etname = findViewById(R.id.etname);
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
    }
}