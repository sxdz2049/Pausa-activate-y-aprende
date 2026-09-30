package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.BancoPausasScreen
import com.example.ui.screens.CierreScreen
import com.example.ui.screens.DiagnosticoScreen
import com.example.ui.screens.EvaluacionScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.KitDocenteScreen
import com.example.ui.screens.PausaPlayerModal
import com.example.ui.screens.PrivacidadEticaScreen
import com.example.ui.screens.ResultadosDashboardScreen
import com.example.ui.screens.TransferenciaScreen
import com.example.ui.theme.DeepNavy
import com.example.ui.theme.EnergyGold
import com.example.ui.theme.EnergyYellow
import com.example.ui.theme.MintGreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.OceanTeal
import com.example.ui.theme.SurfaceBorder
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.AppViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp(
    viewModel: AppViewModel = viewModel()
) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val snackbarMessage by viewModel.snackbarMessage.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(snackbarMessage) {
        snackbarMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.dismissSnackbar()
        }
    }

    // Hardware back button navigation: return to Home if not already there
    BackHandler(enabled = currentScreen != AppScreen.INICIO) {
        viewModel.navigateTo(AppScreen.INICIO)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = DeepNavy,
                    titleContentColor = Color.White
                ),
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "PAUSA, ACTÍVATE Y APRENDE",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                            fontSize = 15.sp,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = "Ecosistema de Transferencia Pedagógica",
                            style = MaterialTheme.typography.labelSmall,
                            color = EnergyYellow,
                            fontSize = 10.sp
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.navigateTo(AppScreen.CIERRE) },
                        modifier = Modifier.testTag("cierre_action_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "Reflexión final",
                            tint = EnergyGold
                        )
                    }
                }
            )
        },
        bottomBar = {
            // Responsive Scrollable Navigation Bar covering all 8 destinations
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars)
            ) {
                val primaryScreens = listOf(
                    AppScreen.INICIO,
                    AppScreen.DIAGNOSTICO,
                    AppScreen.PAUSAS,
                    AppScreen.EVALUACION,
                    AppScreen.RESULTADOS,
                    AppScreen.KIT_DOCENTE,
                    AppScreen.TRANSFERENCIA,
                    AppScreen.PRIVACIDAD
                )

                val selectedIndex = primaryScreens.indexOf(currentScreen).coerceAtLeast(0)

                ScrollableTabRow(
                    selectedTabIndex = selectedIndex,
                    edgePadding = 8.dp,
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = OceanTeal,
                    divider = {}
                ) {
                    primaryScreens.forEach { screen ->
                        val isSelected = currentScreen == screen
                        Tab(
                            selected = isSelected,
                            onClick = { viewModel.navigateTo(screen) },
                            modifier = Modifier
                                .testTag("nav_tab_${screen.name.lowercase()}")
                                .padding(vertical = 4.dp),
                            text = {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = screen.icon,
                                        fontSize = if (isSelected) 18.sp else 16.sp
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = screen.label,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) OceanTeal else MaterialTheme.colorScheme.onSurfaceVariant,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            when (currentScreen) {
                AppScreen.INICIO -> HomeScreen(viewModel = viewModel)
                AppScreen.DIAGNOSTICO -> DiagnosticoScreen(viewModel = viewModel)
                AppScreen.PAUSAS -> BancoPausasScreen(viewModel = viewModel)
                AppScreen.EVALUACION -> EvaluacionScreen(viewModel = viewModel)
                AppScreen.RESULTADOS -> ResultadosDashboardScreen(viewModel = viewModel)
                AppScreen.KIT_DOCENTE -> KitDocenteScreen(viewModel = viewModel)
                AppScreen.TRANSFERENCIA -> TransferenciaScreen(viewModel = viewModel)
                AppScreen.PRIVACIDAD -> PrivacidadEticaScreen(viewModel = viewModel)
                AppScreen.CIERRE -> CierreScreen(viewModel = viewModel)
            }

            // Interactive Pause player bottom sheet modal
            PausaPlayerModal(viewModel = viewModel)
        }
    }
}
