package com.netcracker.appfordemo

import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Camera
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast

import com.google.zxing.Result

import me.dm7.barcodescanner.zxing.ZXingScannerView

import android.Manifest.permission.CAMERA
import android.app.Activity
import android.app.TaskStackBuilder
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.support.v4.app.NavUtils

/**
 * Created by mozil on 21.01.2018.
 */

class BarcodeMainActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {
    private var scannerView: ZXingScannerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        scannerView = ZXingScannerView(this)
        setContentView(scannerView)
        val currentApiVersion = Build.VERSION.SDK_INT

        if (currentApiVersion >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                Toast.makeText(applicationContext, "Permission already granted!", Toast.LENGTH_LONG).show()
            } else {
                requestPermission()
            }
        }
    }

    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(applicationContext, CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(CAMERA), REQUEST_CAMERA)
    }

    public override fun onResume() {
        super.onResume()

        val currentapiVersion = android.os.Build.VERSION.SDK_INT
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if (scannerView == null) {
                    scannerView = ZXingScannerView(this)
                    setContentView(scannerView)
                }
                scannerView!!.setResultHandler(this)
                scannerView!!.startCamera()
            } else {
                requestPermission()
            }
        }
    }

    public override fun onDestroy() {
        super.onDestroy()
        scannerView!!.stopCamera()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CAMERA -> if (grantResults.size > 0) {

                val cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                if (cameraAccepted) {
                    Toast.makeText(applicationContext, "Permission Granted, Now you can access camera", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(applicationContext, "Permission Denied, You cannot access and camera", Toast.LENGTH_LONG).show()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(CAMERA)) {
                            showMessageOKCancel("You need to allow access to both the permissions",
                                    DialogInterface.OnClickListener { dialog, which ->
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            requestPermissions(arrayOf(CAMERA),
                                                    REQUEST_CAMERA)
                                        }
                                    })
                            return
                        }
                    }
                }
            }
        }
    }

    private fun showMessageOKCancel(message: String, okListener: DialogInterface.OnClickListener) {
        android.support.v7.app.AlertDialog.Builder(this@BarcodeMainActivity)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show()
    }

    override fun handleResult(result: Result) {
        val myResult = result.text
        Log.d("QRCodeScanner", result.text)
        Log.d("QRCodeScanner", result.barcodeFormat.toString())

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Scan Result")
        builder.setPositiveButton("Cancel") { dialog, which -> scannerView!!.resumeCameraPreview(this@BarcodeMainActivity) }
        builder.setNeutralButton("Main") { dialog, which ->
            // Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(myResult));
            //startActivity(browserIntent);
//            val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
//            viewModel.parent_id = myResult.toString()           // 9149925907113305697 9149821130313262032
//            val mainIntent = Intent(applicationContext, MainActivity::class.java)
//            mainIntent.flags = FLAG_ACTIVITY_SINGLE_TOP
//            mainIntent.putExtra("id", myResult.toString())
//            startActivity(mainIntent)

            val data = Intent()
            data.putExtra("id", myResult.toString())
// Activity finished ok, return the data
            setResult(RESULT_OK, data)
            finish()

//            val upIntent = NavUtils.getParentActivityIntent(this)
//            upIntent.flags = FLAG_ACTIVITY_SINGLE_TOP
//            upIntent.putExtra("id", myResult.toString())
//            if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
//                // This activity is NOT part of this app's task, so create a new task
//                // when navigating up, with a synthesized back stack.
//                TaskStackBuilder.create(this)
//                        // Add all of this activity's parents to the back stack
//                        .addNextIntentWithParentStack(upIntent)
//                        // Navigate up to the closest parent
//                        .startActivities()
//            } else {
//                // This activity is part of this app's task, so simply
//                // navigate up to the logical parent activity.
//                NavUtils.navigateUpTo(this, upIntent)
//            }

//            val backStackCount = supportFragmentManager.backStackEntryCount
//            if (backStackCount >= 1) {
//                supportFragmentManager.popBackStack()
//                // Change to hamburger icon if at bottom of stack
//            } else {
//                super.onBackPressed()
//            }
        }
        builder.setMessage(result.text)
        val alert1 = builder.create()
        alert1.show()
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_CANCELED,Intent())
        finish()
    }

    companion object {

        private val REQUEST_CAMERA = 1
        private val camId = Camera.CameraInfo.CAMERA_FACING_BACK
    }
}
