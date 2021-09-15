package telegram.free.myapplicationkt.Utils

import android.app.ProgressDialog
import android.content.Context
import android.widget.Toast

class Utilities {
    companion object {
        lateinit var progress: ProgressDialog
        fun showprogress(context: Context,message: String="Processing Please wait...") {
            progress = ProgressDialog(context)
            progress.setMessage(message)
            progress.show()
        }

        fun hideProgress() {
            progress.dismiss()
        }
        fun showToast(context: Context,message:String)
        {
            Toast.makeText(context,message,Toast.LENGTH_LONG).show()
        }
    }


}