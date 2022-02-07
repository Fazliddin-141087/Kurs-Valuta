package uz.mobiler.valutekurs

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import uz.mobiler.valutekurs.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow), drawerLayout
        )

        navView.setupWithNavController(navController)
        val bottomNavigationView=findViewById<BottomNavigationView>(R.id.bottom_navigation)
        NavigationUI.setupWithNavController(bottomNavigationView,navController)
        navView.setNavigationItemSelectedListener {
            when (it.itemId){
                R.id.share->{
                    drawerLayout.closeDrawers()
                    val intent = Intent()
                    intent.action=Intent.ACTION_SEND
                    intent.type="text/plain"
                    intent.putExtra(Intent.EXTRA_TEXT,"")
                    startActivity(Intent.createChooser(intent,"Share"))
                }
                R.id.info->{
                    Toast.makeText(this, "Info", Toast.LENGTH_SHORT).show()
                    drawerLayout.closeDrawers()
                }
            }
            true
        }

        supportActionBar?.elevation=0f
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_vector)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}