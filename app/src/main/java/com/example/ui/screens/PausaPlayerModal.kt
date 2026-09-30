package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DeepNavy
import com.example.ui.theme.EnergyGold
import com.example.ui.theme.EnergyYellow
import com.example.ui.theme.MintGreen
import com.example.ui.theme.OceanTeal
import com.example.ui.theme.SurfaceBorder
import com.example.ui.viewmodel.AppViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PausaPlayerModal(
    viewModel: AppViewModel,
    modifier: Modifier = Modifier
) {
    val isPlayerOpen by viewModel.isPlayerOpen.collectAsState()
    val pause = viewModel.selectedPause.collectAsState().value ?: return

    val timerSeconds by viewModel.timerSeconds.collectAsState()
    val timerTotal by viewModel.timerTotal.collectAsState()
    val isRunning by viewModel.isTimerRunning.collectAsState()
    val isCompleted by viewModel.isTimerCompleted.collectAsState()

    val mood by viewModel.feedbackMood.collectAsState()
    val attention by viewModel.feedbackAttention.collectAsState()
    val motivation by viewModel.feedbackMotivation.collectAsState()
    val participation by viewModel.feedbackParticipation.collectAsState()
    val disposition by viewModel.feedbackDisposition.collectAsState()
    val notes by viewModel.feedbackNotes.collectAsState()

    if (!isPlayerOpen) return

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = { viewModel.closePlayer() },
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier.testTag("modal_pausa_player")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header with close button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = OceanTeal.copy(alpha = 0.12f)
                ) {
                    Text(
                        text = "${pause.category.icon} ${pause.category.displayName} • ${pause.durationMinutes} min",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = OceanTeal,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }

                IconButton(onClick = { viewModel.closePlayer() }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Cerrar")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Title & Purpose
            Text(
                text = "${pause.id}. ${pause.name}",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraBold,
                color = DeepNavy
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = pause.purpose,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Step-by-step instructions card
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "INSTRUCCIONES PASO A PASO",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = OceanTeal
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    pause.instructions.forEachIndexed { idx, step ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(
                                text = "•",
                                style = MaterialTheme.typography.titleMedium,
                                color = OceanTeal,
                                modifier = Modifier.width(16.dp)
                            )
                            Text(
                                text = step,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 20.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    // Adaptación y materiales
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "Adaptación para el aula:",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = pause.adaptation,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // LIVE VISUAL TIMER SECTION
            val progressFraction = if (timerTotal > 0) {
                (timerSeconds.toFloat() / timerTotal.toFloat()).coerceIn(0f, 1f)
            } else 0f

            val animatedProgress by animateFloatAsState(
                targetValue = progressFraction,
                animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
                label = "timerProgress"
            )

            val minutes = timerSeconds / 60
            val seconds = timerSeconds % 60
            val formattedTime = String.format(Locale.US, "%02d:%02d", minutes, seconds)

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = DeepNavy
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "TEMPORIZADOR DE LA PAUSA",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Circular Progress Timer Display
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(160.dp)
                    ) {
                        CircularProgressIndicator(
                            progress = { 1f },
                            modifier = Modifier.fillMaxSize(),
                            color = Color.White.copy(alpha = 0.15f),
                            strokeWidth = 10.dp,
                        )
                        CircularProgressIndicator(
                            progress = { animatedProgress },
                            modifier = Modifier.fillMaxSize(),
                            color = if (timerSeconds < 30) EnergyYellow else OceanTeal,
                            strokeWidth = 10.dp,
                        )
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = formattedTime,
                                style = MaterialTheme.typography.displaySmall,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = if (isRunning) "En curso..." else if (isCompleted) "Completada" else "Listo para iniciar",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isCompleted) MintGreen else Color.White.copy(alpha = 0.7f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Completion Alert Banner
                    AnimatedVisibility(visible = isCompleted) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MintGreen,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = DeepNavy
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "¡Pausa completada! Registra la respuesta del grupo abajo.",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = DeepNavy,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    // Timer Controls: Iniciar, Pausar, Reiniciar, Finalizar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (!isRunning) {
                            Button(
                                onClick = { viewModel.startTimer() },
                                colors = ButtonDefaults.buttonColors(containerColor = OceanTeal),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.testTag("iniciar_timer_btn")
                            ) {
                                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(text = if (timerSeconds < timerTotal) "Continuar" else "Iniciar")
                            }
                        } else {
                            Button(
                                onClick = { viewModel.pauseTimer() },
                                colors = ButtonDefaults.buttonColors(containerColor = EnergyYellow),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.testTag("pausar_timer_btn")
                            ) {
                                Icon(imageVector = Icons.Default.Pause, contentDescription = null)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(text = "Pausar")
                            }
                        }

                        OutlinedButton(
                            onClick = { viewModel.resetTimer() },
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.testTag("reiniciar_timer_btn")
                        ) {
                            Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Reiniciar")
                        }

                        Button(
                            onClick = { viewModel.finishTimer() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.2f)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.testTag("finalizar_timer_btn")
                        ) {
                            Icon(imageVector = Icons.Default.Stop, contentDescription = null, tint = Color.White)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Finalizar", color = Color.White)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // POST-PAUSE REGISTRATION FORM (Eje 3 & Seguimiento)
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "¿CÓMO RESPONDIÓ EL GRUPO?",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = DeepNavy
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // Reaction smileys
                    val moodOptions = listOf(
                        "Muy bien" to "😊",
                        "Bien" to "🙂",
                        "Regular" to "😐",
                        "Poco" to "🙁"
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        moodOptions.forEach { (label, emoji) ->
                            val isSelected = mood == label
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isSelected) OceanTeal else MaterialTheme.colorScheme.surfaceVariant)
                                    .border(
                                        1.dp,
                                        if (isSelected) OceanTeal else SurfaceBorder,
                                        RoundedCornerShape(10.dp)
                                    )
                                    .clickable { viewModel.feedbackMood.value = label }
                                    .padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(text = emoji, fontSize = 22.sp)
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = label,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.SemiBold,
                                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Level selectors: Atención, Motivación, Participación, Disposición
                    LevelSelectorRow(
                        title = "ATENCIÓN",
                        current = attention,
                        onSelect = { viewModel.feedbackAttention.value = it }
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    LevelSelectorRow(
                        title = "MOTIVACIÓN",
                        current = motivation,
                        onSelect = { viewModel.feedbackMotivation.value = it }
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    LevelSelectorRow(
                        title = "PARTICIPACIÓN",
                        current = participation,
                        onSelect = { viewModel.feedbackParticipation.value = it }
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    LevelSelectorRow(
                        title = "DISPOSICIÓN PARA CONTINUAR",
                        current = disposition,
                        onSelect = { viewModel.feedbackDisposition.value = it }
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        value = notes,
                        onValueChange = { viewModel.feedbackNotes.value = it },
                        label = { Text("Observaciones del docente") },
                        placeholder = { Text("Ej: Mayor silencio activo y concentración al retomar...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("observaciones_docente_input"),
                        shape = RoundedCornerShape(10.dp),
                        minLines = 2
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { viewModel.savePostPauseRecord() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("guardar_registro_pausa_btn"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = OceanTeal)
                    ) {
                        Icon(imageVector = Icons.Default.Save, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "GUARDAR REGISTRO",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun LevelSelectorRow(
    title: String,
    current: String,
    onSelect: (String) -> Unit
) {
    val levels = listOf("Baja", "Media", "Alta")
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1.3f)
        )
        Row(
            modifier = Modifier.weight(1.7f),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            levels.forEach { level ->
                val isSelected = current == level
                val bg = if (isSelected) OceanTeal else MaterialTheme.colorScheme.surfaceVariant
                val txt = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(bg)
                        .clickable { onSelect(level) }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = level,
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = txt
                    )
                }
            }
        }
    }
}
