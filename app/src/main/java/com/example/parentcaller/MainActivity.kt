package com.example.parentcaller

import android.app.Activity
import android.content.Intent
import android.content.Intent.ACTION_CALL
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.parentcaller.databinding.ActivityMainBinding

class MainActivity : Activity() {

    private lateinit var binding: ActivityMainBinding
    private var caller_id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    fun callPhone(view: View) {
        caller_id = view.id
        checkPermission()
    }
    //권한 확인
    private fun checkPermission() {

        // 1. 위험권한(Camera) 권한 승인상태 가져오기
        val callPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE)
        if (callPermission == PackageManager.PERMISSION_GRANTED) {
            // 카메라 권한이 승인된 상태일 경우
            startProcess()

        } else {
            // 카메라 권한이 승인되지 않았을 경우
            requestPermission()
        }
    }

    // 2. 권한 요청
    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE), 99)
    }

    // 권한 처리
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray) {
        when (requestCode) {
            99 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startProcess()
                } else {
                    Log.d("MainActivity", "종료")
                }
            }
        }
    }

    // 3. 전화 실행
    fun startProcess() {
        val dialIntent = Intent(Intent.ACTION_CALL)

        when (caller_id) {
            R.id.b_daddy -> {
                dialIntent.data = Uri.parse("tel:" + "01012341234")
            }
            R.id.b_mommy -> {
                dialIntent.data = Uri.parse("tel:" + "01012341234")
            }
        }
        startActivity(dialIntent)
    }
}