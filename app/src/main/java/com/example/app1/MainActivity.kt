package com.example.app1

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener
{
    //String vars
    private var strFirstName: String? = null
    private var strMiddleName: String? = null
    private var strLastName: String? = null

    //These variables will hold all the strings given to us from the UI
    private var etFirstName: EditText? = null
    private var etMiddleName: EditText? = null
    private var etLastName: EditText? = null

    //Text view vars
    private var tvFirstName: TextView? = null
    private var tvMiddleName: TextView? = null
    private var tvLastName: TextView? = null

    //Button Vars
    private var bTakePicture: Button? = null
    private var bSubmit: Button? = null

    //Picture vars
    private var ivPicture: ImageView? = null

    private var bmThumbnailImage: Bitmap? = null
    private var mDisplayIntent: Intent? = null

    private var mProfilePicPath: String?= null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etFirstName = findViewById<View>(R.id.et_first_name) as EditText
        etMiddleName = findViewById<View>(R.id.et_middle_name) as EditText
        etLastName = findViewById<View>(R.id.et_last_name) as EditText
        bTakePicture = findViewById<View>(R.id.b_prof_picture) as Button
        bSubmit =  findViewById<View>(R.id.b_submit) as Button
        ivPicture = findViewById<View>(R.id.iv_pic) as ImageView
        mDisplayIntent = Intent(this, MainActivity::class.java)

        bSubmit!!.setOnClickListener(this)
        bTakePicture!!.setOnClickListener(this)

        if (savedInstanceState != null) {
            etMiddleName!!.setText(savedInstanceState.getString("MiddleName"))
        }
        if (savedInstanceState != null) {
            etLastName!!.setText(savedInstanceState.getString("LastName"))
        }
        if (savedInstanceState != null) {
            mProfilePicPath = savedInstanceState.getString("Path")
        }
        //ivPicture = findViewById<View>(R.id.iv_pic) as ImageView

        if (mProfilePicPath != null) {
            bmThumbnailImage = BitmapFactory.decodeFile(mProfilePicPath)
            ivPicture!!.setImageBitmap(bmThumbnailImage)
        }


        /*val imagePath3 = mDisplayIntent!!.getStringExtra("imagePath")
        val thumbnailImage = BitmapFactory.decodeFile(imagePath3)
        if (thumbnailImage != null) {
            ivPicture!!.setImageBitmap(thumbnailImage)
        }*/
        //mDisplayIntent = Intent(this, MainActivity::class.java)
        //savedInstanceState.putParcelable()

    }

    override fun onSaveInstanceState(outState: Bundle)
    {

        outState.putString("FirstName", etFirstName!!.text.toString())
        outState.putString("MiddleName", etMiddleName!!.text.toString())
        outState.putString("LastName", etLastName!!.text.toString())
        outState.putString("Path", mProfilePicPath)
        super.onSaveInstanceState(outState)

    }
   /* override fun onRestoreInstanceState(savedInstanceState: Bundle)
    {
        super.onRestoreInstanceState(savedInstanceState)
        //etFirstName!!.setText(savedInstanceState.getString("FirstName"))



    }*/
  /*  override fun onSaveInstanceState(outState: Bundle)
    {
        super.onSaveInstanceState(outState)

        outState.putString("imagePath", imagePath)
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {

        imagePath = savedInstanceState.getString("imagePath")
        ivPicture = findViewById<View>(R.id.iv_pic) as ImageView

        if (imagePath != null) {
            bmThumbnailImage = BitmapFactory.decodeFile(imagePath)
            ivPicture!!.setImageBitmap(bmThumbnailImage)
        }
    }*/

    override fun onClick(newView: View)
    {
        when(newView.id)
        {
            R.id.b_submit ->
            {
                //Gets the string from the EditText
                strFirstName = etFirstName!!.text.toString()
                strMiddleName = etMiddleName!!.text.toString()
                strLastName = etLastName!!.text.toString()
                val intent = Intent(this, LoginSuccessActivity::class.java)
                intent.putExtra("firstName", strFirstName)
                intent.putExtra("middleName", strMiddleName)
                intent.putExtra("lastName", strLastName)


                startActivity(intent)
            }
            R.id.b_prof_picture ->
            {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                try
                {
                    camera.launch(cameraIntent)
                }
                catch(ex: ActivityNotFoundException)
                {

                }

            }

        }



    }


    private val camera = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == RESULT_OK) {
            //val thumbnailImage = result.data!!.extras
            if (Build.VERSION.SDK_INT >= 33)
            {
                //val thumbnailImage = result.data!!.getParcelableExtra("data", Bitmap::class.java)
                //ivPicture!!.setImageBitmap(thumbnailImage)

                //bmThumbnailImage = result.data!!.getParcelableExtra("data", Bitmap::class.java)


                //val filePathString = saveImage(bmThumbnailImage)
                //val imagePath2 = saveImage(bmThumbnailImage)
                //mDisplayIntent!!.putExtra("imagePath", imagePath2)


                //ivPicture!!.setImageBitmap(bmThumbnailImage)




                val extras = result.data!!.extras
                bmThumbnailImage = result.data!!.getParcelableExtra("data", Bitmap::class.java)
                ivPicture = findViewById<View>(R.id.iv_pic) as ImageView
                mProfilePicPath = saveImage(bmThumbnailImage)
                ivPicture!!.setImageBitmap(bmThumbnailImage)

            }
            else
            {
                //val thumbnailImage = result.data!!.getParcelableExtra<Bitmap>("data")
                //ivPicture!!.setImageBitmap(thumbnailImage)

                bmThumbnailImage = result.data!!.getParcelableExtra<Bitmap>("data")
                val filePathString = saveImage(bmThumbnailImage)
                ivPicture!!.setImageBitmap(bmThumbnailImage)
            }


        }
    }
    private fun saveImage(finalBitmap: Bitmap?): String {
        val root = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val myDir = File("$root/saved_images")
        myDir.mkdirs()
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val fname = "Thumbnail_$timeStamp.jpg"
        val file = File(myDir, fname)
        if (file.exists()) file.delete()
        try {
            val out = FileOutputStream(file)
            finalBitmap!!.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return file.absolutePath
    }
}