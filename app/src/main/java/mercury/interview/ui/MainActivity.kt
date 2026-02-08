package mercury.interview.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import mercury.interview.databinding.ActivityMainBinding
import mercury.interview.ui.theme.MercuryAndroidInterviewBootstrapTheme

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        composeSetup()
        /*⚠️ if you're using compose, uncomment ABOVE;
        * ⚠️ if you're using XML views, uncomment BELOW */
        //legacyXmlSetup()
    }

    private fun composeSetup() {
        setContent {
            MercuryAndroidInterviewBootstrapTheme {
                val navController = rememberNavController()
                MainContent(navController = navController)
            }
        }
    }

    private fun legacyXmlSetup() {
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navController =
            (supportFragmentManager.findFragmentById(binding.fragment.id) as NavHostFragment)
                .navController

        val appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    MercuryAndroidInterviewBootstrapTheme {
        //TODO code here
    }
}