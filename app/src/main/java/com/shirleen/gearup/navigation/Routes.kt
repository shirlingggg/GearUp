package com.shirleen.gearup.navigation

const val ROUT_BUYERDASHBOARD = "buyerdashboard"
const val ROUT_BUYCAR = "buycar"
const val ROUT_SERVICES = "services"
const val ROUT_ACCESSORIES = "accessories"
const val ROUT_BOOKAPPOINTMENT = "bookappointment"
const val ROUT_EXPLORE = "explore"
const val ROUT_SELLERDASHBOARD = "sellerdashboard"
const val ROUT_SELLERPROFILESCREEN = "sellerprofilescreen"
const val ROUT_SERVICEPROVIDERPROFILESCREEN = "serviceproviderprofilescreen"
const val ROUT_SERVICEPROVIDERDASHBOARD = "serviceproviderdashboard"
const val ROUT_BUYERPROFILESCREEN = "buyerprofilescreen"
const val ROUT_HOME = "home"
const val ROUT_ABOUT = "about"
const val ROUT_SPLASH = "splash"



//Auth

const val ROUT_REGISTER= "register"
const val ROUT_LOGIN= "login"

//CRUD- Products
const val ROUT_ADD_CAR = "add_car"
const val ROUT_ADD_ACCESSORY = "add_accessory"
const val ROUT_ADD_SERVICE = "add_service"
const val ROUT_CAR_LIST = "car_list"
const val ROUT_ACCESSORY_LIST = "accessory_list"
const val ROUT_EDIT_CAR = "edit_car/{carId}"
const val ROUT_EDIT_ACCESSORY = "edit_accessory/{accessoryId}"

// ✅ Helper function for navigation
fun editCarRoute(carId: Int) = "edit_car/$carId"
fun editAccessoryRoute(accessoryId: Int) = "edit_accessory/$accessoryId"
