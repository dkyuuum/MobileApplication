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
    }
}