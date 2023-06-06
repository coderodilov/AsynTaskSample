package uz.coderodilov.asyntasksample

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uz.coderodilov.asyntasksample.databinding.ActivityMainBinding
import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URL

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var taskDownloader : TaskExecution? = null
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGetImages.setOnClickListener{
            count = 0
            taskDownloader = TaskExecution()
            taskDownloader?.execute(
               "https://mobimg.b-cdn.net/v3/fetch/de/decf5b50734813f71862231b5db4d3bc.jpeg",
               "https://i.pinimg.com/originals/da/6f/ce/da6fceaae301627725b93d55b89554bb.jpg",
               "https://stop-klopu.com/wp-content/uploads/7/6/8/768bc9519348d9a8df181a7b6a9740f1.jpg"
            )
        }

    }

    @SuppressLint("StaticFieldLeak")
    inner class TaskExecution : AsyncTask<String, Bitmap, Unit>() {
        private var httpUrlConnection: HttpURLConnection? = null
        private var inputStream: BufferedInputStream? = null
        private val list = ArrayList<Bitmap>()

        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: String?) {
            try {
                for (i in params.indices) {
                    httpUrlConnection = URL(params[i]).openConnection() as HttpURLConnection
                    inputStream = BufferedInputStream(httpUrlConnection!!.inputStream)
                    publishProgress(BitmapFactory.decodeStream(inputStream))
                    //TimeUnit.SECONDS.sleep(3)
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }

        @Deprecated("Deprecated in Java")
        override fun onProgressUpdate(vararg values: Bitmap?) {
            super.onProgressUpdate(*values)
            count++
            when(count){
                1 -> binding.image1.setImageBitmap(values[0])
                2 -> binding.image2.setImageBitmap(values[0])
                3 -> binding.image3.setImageBitmap(values[0])
            }

        }

    }

}
