package com.shirleen.gearup.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shirleen.gearup.data.AccessoryDatabase

import com.shirleen.gearup.data.UserDatabase
import com.shirleen.gearup.repository.UserRepository
import com.shirleen.gearup.ui.screens.dashboards.BuyerDashboardScreen
import com.shirleen.gearup.ui.screens.about.AboutScreen
import com.shirleen.gearup.ui.screens.accessories.AccessoriesScreen
import com.shirleen.gearup.ui.screens.home.HomeScreen
import com.shirleen.gearup.ui.screens.splash.SplashScreen
import com.shirleen.gearup.viewmodel.AuthViewModel

import com.shirleen.gearup.ui.screens.auth.LoginScreen
import com.shirleen.gearup.ui.screens.auth.RegisterScreen
import com.shirleen.gearup.ui.screens.bookappointment.BookAppointmentScreen
import com.shirleen.gearup.ui.screens.buycar.BuyCarScreen
import com.shirleen.gearup.ui.screens.cars.AddCarScreen
import com.shirleen.gearup.ui.screens.cars.CarListScreen
import com.shirleen.gearup.ui.screens.cars.EditCarScreen
import com.shirleen.gearup.ui.screens.profilescreens.BuyerProfileScreen
import com.shirleen.gearup.ui.screens.dashboards.SellerDashboardScreen
import com.shirleen.gearup.ui.screens.dashboards.ServiceProviderDashboardScreen
import com.shirleen.gearup.ui.screens.profilescreens.ServiceProviderProfileScreen
import com.shirleen.gearup.ui.screens.explore.ExploreScreen
import com.shirleen.gearup.ui.screens.profilescreens.SellerProfileScreen
import com.shirleen.gearup.ui.screens.services.ServicesScreen
import com.shirleen.gearup.viewmodel.CarViewModel
import com.shirleen.gearup.viewmodel.CarViewModelFactory
import com.shirleen.gearup.repository.CarRepository // Corrected: Import CarRepository
import com.shirleen.gearup.data.CarDatabase // Corrected: Import CarDatabase
import com.shirleen.gearup.repository.AccessoryRepository
import com.shirleen.gearup.ui.screens.accessory.AccessoryListScreen
import com.shirleen.gearup.ui.screens.accessory.AddAccessoryScreen
import com.shirleen.gearup.ui.screens.accessory.EditAccessoryScreen
import com.shirleen.gearup.viewmodel.AccessoryViewModel
import com.shirleen.gearup.viewmodel.AccessoryViewModelFactory

@Composable
@RequiresApi(Build.VERSION_CODES.Q)
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUT_BOOKAPPOINTMENT,
) {

    val context = LocalContext.current

    // Initialize ViewModels here to be shared across composables
    val appDatabase = UserDatabase.getDatabase(context)
    val authRepository = UserRepository(appDatabase.userDao())
    val authViewModel: AuthViewModel = viewModel { AuthViewModel(authRepository) }

    // Corrected: Initializing CarViewModel without AppContainer
    val carDatabase = CarDatabase.getDatabase(context)
    val carRepository = CarRepository(carDatabase.carDao())
    val carViewModel: CarViewModel = viewModel { CarViewModelFactory(carRepository).create(CarViewModel::class.java) }

    // Create the ViewModel instance outside the NavHost
    val accessoryViewModel: AccessoryViewModel = viewModel(
        factory = AccessoryViewModelFactory(
            AccessoryRepository(AccessoryDatabase.getDatabase(context).accessoryDao())
        )
    )


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

        composable(ROUT_BUYCAR) {
            BuyCarScreen(navController)
        }

        composable(ROUT_SERVICES) {
            ServicesScreen(navController)
        }

        composable(ROUT_ACCESSORIES) {
            AccessoriesScreen(navController)
        }

        composable(ROUT_BOOKAPPOINTMENT) {
            BookAppointmentScreen(navController)
        }

        composable(ROUT_EXPLORE) {
            ExploreScreen(navController)
        }

        composable(ROUT_SELLERDASHBOARD) {
            SellerDashboardScreen(navController)
        }

        composable(ROUT_SERVICEPROVIDERPROFILESCREEN) {
            ServiceProviderProfileScreen(navController)
        }

        composable(ROUT_BUYERPROFILESCREEN) {
            BuyerProfileScreen(navController)
        }

        composable(ROUT_SELLERPROFILESCREEN) {
            SellerProfileScreen(navController)
        }

        composable(ROUT_SERVICEPROVIDERDASHBOARD) {
            ServiceProviderDashboardScreen(navController)
        }


        //AUTHENTICATION

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

        //CRUD
        //Cars
        composable(ROUT_ADD_CAR) {
            AddCarScreen(navController, carViewModel)
        }

        composable(ROUT_CAR_LIST) {
            CarListScreen(navController, carViewModel)
        }

        composable(
            route = ROUT_EDIT_CAR,
            arguments = listOf(navArgument("carId") { type = NavType.IntType })
        ) { backStackEntry ->
            val carId = backStackEntry.arguments?.getInt("carId")
            EditCarScreen(carId, navController, carViewModel)
        }

        //Accessories
        composable(ROUT_ADD_ACCESSORY) {
            AddAccessoryScreen(navController, accessoryViewModel)
        }

        composable(ROUT_ACCESSORY_LIST) {
            AccessoryListScreen(navController, accessoryViewModel)
        }

        composable(
            route = ROUT_EDIT_ACCESSORY,
            arguments = listOf(navArgument("accessoryId") { type = NavType.IntType })
        ) { backStackEntry ->
            val accessoryId = backStackEntry.arguments?.getInt("accessoryId")
            EditAccessoryScreen(accessoryId, navController, accessoryViewModel)
        }




    }
}