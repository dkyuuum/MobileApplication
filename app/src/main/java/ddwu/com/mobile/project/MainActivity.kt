package ddwu.com.mobile.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ddwu.com.mobile.project.databinding.ActivityMainBinding
import ddwu.com.mobile.project.databinding.ActivityMainBinding.inflate

class MainActivity : AppCompatActivity() {
	private lateinit var binding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = inflate(layoutInflater)
		setContentView(binding.root)

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
							.replace(R.id.navi_search, SearchFragment())
							.commit()
					}
				}
				true
			}
			selectedItemId = R.id.navi_search
		}
	}
}