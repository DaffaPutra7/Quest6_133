package com.example.quest6_133.navigation

import RencanaStudiView
import RencanaStudiViewModel
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quest6_133.ui.view.screen.MahasiswaFormView
import com.example.quest6_133.ui.view.screen.SplashView
import com.example.quest6_133.ui.view.screen.TampilView
import com.example.quest6_133.ui.view.viewmodel.MahasiswaViewModel

enum class Halaman {
    Splash,
    Mahasiswa,
    RencanaStudi,
    Tampil
}

@Composable
fun MahasiswaApp(
    modifier: Modifier = Modifier,
    mahasiswaViewModel: MahasiswaViewModel = viewModel(),
    krsViewModel: RencanaStudiViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
){
    val statusUI = mahasiswaViewModel.statusUI.collectAsState().value
    val krsStateUi = krsViewModel.krsStateUi.collectAsState().value

    NavHost(
        navController = navController,
        startDestination = Halaman.Splash.name,
        modifier = Modifier.padding()
    ){
        composable(route = Halaman.Splash.name) {
            SplashView(onMulaiButton = {
                navController.navigate(
                    Halaman.Mahasiswa.name
                )
            })
        }
        composable(route = Halaman.Mahasiswa.name) {
            MahasiswaFormView(
                onSubmitButtonClicked = {
                    mahasiswaViewModel.setDataSiswa(it)
                    navController.navigate(Halaman.RencanaStudi.name)
                },
                onBackButtonClicked = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = Halaman.RencanaStudi.name) {
            RencanaStudiView(
                mahasiswa = statusUI,
                onBackButtonClicked = { navController.popBackStack() },
                onSubmitButtonClicked = { krsViewModel.saveDataKRS(it)
                    navController.navigate(Halaman.Tampil.name)}
            )
        }
        composable(route = Halaman.Tampil.name) {
            TampilView(
                mahasiswa = statusUI,
                krs = krsStateUi,
                onResetButtonClicked = {navController.navigate(Halaman.Splash.name)},
                onBackButtonClicked = {navController.popBackStack()}
            )
        }
    }
}