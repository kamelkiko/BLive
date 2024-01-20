package com.expert.blive.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
//import com.expert.nicolive.R
import com.google.firebase.installations.FirebaseInstallations
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class CommonUtilsPk {
    var alertDialog: AlertDialog? = null

    companion object {
        @SuppressLint("HardwareIds")
        fun getDeviceId(context: Activity): String? {
            return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        }

        fun requestPermission(permission: Array<String>, requestCode: Int, activity: Activity) {
            ActivityCompat.requestPermissions(
                activity,
                permission,
                requestCode
            )
        }

        fun checkPermission(permissionName: String, activity: Activity): Boolean {
            var result = ContextCompat.checkSelfPermission(
                activity,
                permissionName
            )
            return result == PackageManager.PERMISSION_GRANTED
        }




        var alertDialog: AlertDialog? = null


        private fun selectAnyoneLayout(
            img1: AppCompatImageView,
            img2: AppCompatImageView,
            img3: AppCompatImageView
        ) {
            img1.visibility = View.VISIBLE
            img2.visibility = View.GONE
            img3.visibility = View.GONE
        }



        fun prettyCount(number: Number): String? {
            val suffix = charArrayOf(' ', 'k', 'M', 'B', 'T', 'P', 'E')
            val numValue = number.toLong()
            val value = Math.floor(Math.log10(numValue.toDouble())).toInt()
            val base = value / 3
            return if (value >= 3 && base < suffix.size) {
                DecimalFormat("#0.0").format(
                    numValue / Math.pow(
                        10.0,
                        (base * 3).toDouble()
                    )
                ) + suffix[base]
            } else {
                DecimalFormat("#,##0").format(numValue)
            }
        }


        fun isNetworkConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.activeNetworkInfo != null
        }

        fun toastBodyNullOrError(activity: Activity, msg: String) {
            Toast.makeText(activity, "" + msg, Toast.LENGTH_SHORT).show()
        }



        fun stringTimeToDays(s: String): String? {

            Log.i("stringTimeToDays: ", s)

            val dateFormatNeeded: DateFormat = SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
            var date: Date? = null
            date = Date(s)
            var crdate1 = dateFormatNeeded.format(date)

            // Date Calculation

            // Date Calculation
            val dateFormat: DateFormat = SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
            crdate1 = SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(date)

            // get current date time with Calendar()
            val cal: Calendar = Calendar.getInstance()
            val currenttime: String = dateFormat.format(cal.time)
            var CreatedAt: Date? = null
            var current: Date? = null
            try {
                CreatedAt = dateFormat.parse(crdate1)
                current = dateFormat.parse(currenttime)
            } catch (e: ParseException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }

            Log.i("stringTimeToDays: ", current.toString())

            // Get msec from each, and subtract.
            val diff: Long = current?.time!! - CreatedAt?.time!!

            Log.i("stringTimeToDays: ", "diff$diff")

            val diffSeconds = diff / 1000
            val diffMinutes = diff / (60 * 1000) % 60
            val diffHours = diff / (60 * 60 * 1000) % 24
            val diffDays = diff / (24 * 60 * 60 * 1000)
            var time: String? = null



            Log.i("stringTimeToDays: ", "diff$diff")
            Log.i("stringTimeToDays: ", "diff$diffDays")
            Log.i("stringTimeToDays: ", "diff$diffHours")
            Log.i("stringTimeToDays: ", "diff$diffSeconds")
            Log.i("stringTimeToDays: ", "diff$diffMinutes")


            if (diffDays > 0) {
                time = if (diffDays == 1L) {
                    diffDays.toString() + " day ago "
                } else {
                    diffDays.toString() + " days ago "
                }
            } else {
                if (diffHours > 0) {
                    time = if (diffHours == 1L) {
                        diffHours.toString() + " hr ago"
                    } else {
                        diffHours.toString() + " hrs ago"
                    }
                } else {
                    if (diffMinutes > 0) {
                        time = if (diffMinutes == 1L) {

                            diffMinutes.toString() + " min ago"

                        } else {
                            Log.i("stringTimeToDays: ", "diff$diff")

                            diffMinutes.toString() + " mins ago"

                        }
                    } else {
                        if (diffSeconds > 0) {
                            time = diffSeconds.toString() + " secs ago"
                        } else {
                            time = "just now"
                        }
                    }
                }
            }
            return time
        }


        fun getRegId(activity: Activity): String {
            var reg = ""
            if (isNetworkConnected(activity)) {
                FirebaseInstallations.getInstance().getToken(true).addOnCompleteListener {
                    if (it.isComplete) {
                        reg = it.result.token
                        App.getSharedpref().saveString("reg", reg)
                        Log.i("token1", reg)
                        Log.i("token2", it.result.token)
                    }
                }
            } else {
                Toast.makeText(activity, "No Internet", Toast.LENGTH_SHORT).show()
            }

            return reg
        }

        private var dialog: Dialog? = null

        fun getCurrentDiamond(): String {
            return if (App.getSharedpref().getString("session") == "1") {
                App.getSharedpref().getString(AppConstants.CURRENT_DIAMON)
                        .toString()
            } else {
                ""
            }
        }


        fun imageToMultiPartImage(parameter: String, imagePath: String): MultipartBody.Part {
            return if (imagePath != "") {
                var file = File(imagePath)
                var imageRequest =
                    RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
                MultipartBody.Part.createFormData(parameter, file.name, imageRequest)
            } else {
                var imageRequest = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), "")
                MultipartBody.Part.createFormData(parameter, "", imageRequest)
            }
        }

        fun imageListToMultiPartImage(
            parameter: String,
            imageList: ArrayList<String>
        ): ArrayList<MultipartBody.Part> {
            var imagesListMulti: ArrayList<MultipartBody.Part> = ArrayList()
            Log.i("listSize", imageList.size.toString())

            if (imageList.isNotEmpty()) {
                for (i in 0 until imageList.size) {
                    Log.i("listSize", imageList[i])
                    var file = File(imageList[i])
                    var imageRequest =
                        RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
                    imagesListMulti.add(
                        MultipartBody.Part.createFormData(
                            parameter,
                            file.name,
                            imageRequest
                        )
                    )
                }
                return imagesListMulti
            } else {
                var imageRequest = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), "")
                imagesListMulti.add(MultipartBody.Part.createFormData(parameter, "", imageRequest))
                return imagesListMulti
            }
        }

        fun stringToRequestBody(string: String): RequestBody {
            return RequestBody.create("text/plain".toMediaTypeOrNull(), string)
        }

    }


}