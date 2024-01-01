package ddwu.com.mobile.project.ui

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import ddwu.com.mobile.project.R
import ddwu.com.mobile.project.databinding.ActivityMainBinding
import ddwu.com.mobile.project.databinding.ActivityMainBinding.inflate
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class MainActivity : AppCompatActivity() {
	private lateinit var binding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = inflate(layoutInflater)
		setContentView(binding.root)

		initBottomNavi()
		getHashKey()
	}

	// Bottom Navigation 설정
	private fun initBottomNavi() {
		binding.bottomNavi.run {
			setOnNavigationItemSelectedListener {
				when (it.itemId) {
					R.id.navi_diary -> {
						supportFragmentManager
							.beginTransaction()
							.replace(R.id.fl_container, DiaryFragment())
							.commit()
					}

					R.id.navi_search -> {
						supportFragmentManager
							.beginTransaction()
							.replace(R.id.fl_container, SearchFragment())
							.commit()
					}
				}
				true
			}
			selectedItemId = R.id.navi_search
		}
	}

	private fun getHashKey() {
		var packageInfo: PackageInfo? = null
		try {
			packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
		} catch (e: PackageManager.NameNotFoundException) {
			e.printStackTrace()
		}
		if (packageInfo == null) Log.e("KeyHash", "KeyHash:null")
		for (signature in packageInfo!!.signatures) {
			try {
				val md: MessageDigest = MessageDigest.getInstance("SHA")
				md.update(signature.toByteArray())
				Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT))
				//ILog.iLogDebug("KeyHash", Base64.getEncoder().encodeToString(md.digest()))
			} catch (e: NoSuchAlgorithmException) {
				Log.d("KeyHash", "Unable to get MessageDigest. signature=$signature")

			}
		}

	}
}