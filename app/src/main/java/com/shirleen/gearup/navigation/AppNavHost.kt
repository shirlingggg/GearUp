package com.shirleen.gearup.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shirleen.gearup.data.UserDatabase
import com.shirleen.gearup.repository.UserRepository
import com.shirleen.gearup.ui.screens.dashboards.BuyerDashboardScreen
import com.shirleen.gearup.ui.screens.about.AboutScreen
import com.shirleen.gearup.ui.screens.home.HomeScreen
import com.shirleen.gearup.ui.screens.splash.SplashScreen
import com.shirleen.gearup.viewmodel.AuthViewModel

import com.shirleen.gearup.ui.screens.auth.LoginScreen
import com.shirleen.gearup.ui.screens.auth.RegisterScreen


@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUT_BUYERDASHBOARD,
) {

    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(ROUT_BUYERDASHBOARD) {
            BuyerDashboardScreen(navController)
        }

        composable(ROUT_HOME) {
            HomeScreen(navController)
        }
        composable(ROUT_ABOUT) {
            AboutScreen(navController)
        }

        composable(ROUT_SPLASH) {
            SplashScreen(navController)
        }


        //AUTHENTICATION

        // Initialize Room Database and Repository for Authentication
        val appDatabase = UserDatabase.getDatabase(context)
        val authRepository = UserRepository(appDatabase.userDao())
        val authViewModel: AuthViewModel = AuthViewModel(authRepository)
        composable(ROUT_REGISTER) {
            RegisterScreen(authViewModel, navController) {
                navController.navigate(ROUT_LOGIN) {
                    popUpTo(ROUT_REGISTER) { inclusive = true }
                }
            }
        }

        composable(ROUT_LOGIN) {
            LoginScreen(authViewModel, navController) {
                navController.navigate(ROUT_HOME) {
                    popUpTo(ROUT_LOGIN) { inclusive = true }
                }
            }
        }

        //end of authentication





    }


}