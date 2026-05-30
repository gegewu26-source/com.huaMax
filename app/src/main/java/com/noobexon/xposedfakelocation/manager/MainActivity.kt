package com.noobexon.xposedfakelocation.manager

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.noobexon.xposedfakelocation.manager.localization.LocaleController
import com.noobexon.xposedfakelocation.manager.ui.navigation.AppNavGraph
import com.noobexon.xposedfakelocation.manager.ui.theme.XposedFakeLocationTheme
import org.osmdroid.config.Configuration

class MainActivity : ComponentActivity() {
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleController.attachBaseContext(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Configuration.getInstance().load(this, getPreferences(MODE_PRIVATE))

        enableEdgeToEdge()

        setContent {
            XposedFakeLocationTheme {
                val navController = rememberNavController()
                AppNavGraph(navController = navController)
            }
        }
    }
}
