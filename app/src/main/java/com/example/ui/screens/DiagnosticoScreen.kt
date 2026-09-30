package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PedagogicalData
import com.example.ui.components.LikertOptionSelector
import com.example.ui.components.SectionHeader
import com.example.ui.theme.DeepNavy
import com.example.ui.theme.EnergyGold
import com.example.ui.theme.EnergyYellow
import com.example.ui.theme.MintGreen
import com.example.ui.theme.OceanTeal
import com.example.ui.theme.SurfaceBorder
import com.example.ui.viewmodel.AppViewModel
import java.util.Locale

@Composable
fun DiagnosticoScreen(
    viewModel: AppViewModel,
    modifier: Modifier = Modifier
) {
    val groupInfo by viewModel.groupInfo.collectAsState()
    val pretestAnswers by viewModel.pretestAnswers.collectAsState()
    val pretestStudentId by viewModel.pretestStudentId.collectAsState()
    val recodeNegative by viewModel.recodeNegativePolarity.collectAsState()
    val pretestHistory by viewModel.pretestHistory.collectAsState()

    var teacherName by remember(groupInfo) { mutableStateOf(groupInfo.teacherName) }
    var grade by remember(groupInfo) { mutableStateOf(groupInfo.grade) }
    var subject by remember(groupInfo) { mutableStateOf(groupInfo.subject) }
    var studentCountStr by remember(groupInfo) { mutableStateOf(groupInfo.studentCount.toString()) }
    var approxAge by remember(groupInfo) { mutableStateOf(groupInfo.approxAge) }
    var dateStr by remember(groupInfo) { mutableStateOf(groupInfo.date) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))
            SectionHeader(
                title = "CONOCE TU GRUPO",
                subtitle = "Eje 1: Caracterización y diagnóstico inicial del conocimiento"
            )
        }

        // Justificación contextual
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "💡", fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "¿Por qué nace esta experiencia?",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = DeepNavy
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Durante la jornada académica pueden presentarse momentos de cansancio, distracción, disminución de la atención o desmotivación. Esta propuesta incorpora pausas activas breves y planificadas como estrategia pedagógica para favorecer la disposición de los estudiantes hacia el aprendizaje.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 21.sp
                    )
                }
            }
        }

        // Pregunta Orientadora
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = OceanTeal.copy(alpha = 0.08f)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "PREGUNTA ORIENTADORA",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = OceanTeal
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "“¿De qué manera la implementación de pausas activas puede contribuir al fortalecimiento de la atención y motivación de los estudiantes y favorecer su desempeño académico?”",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = DeepNavy,
                        lineHeight = 22.sp
                    )
                }
            }
        }

        // Formulario: Datos del Grupo
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "DATOS DEL GRUPO",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = DeepNavy
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = teacherName,
                        onValueChange = { teacherName = it },
                        label = { Text("Nombre del docente") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("docente_input"),
                        shape = RoundedCornerShape(10.dp),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = grade,
                            onValueChange = { grade = it },
                            label = { Text("Curso / Grado") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("curso_input"),
                            shape = RoundedCornerShape(10.dp),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = subject,
                            onValueChange = { subject = it },
                            label = { Text("Área de conocimiento") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("area_input"),
                            shape = RoundedCornerShape(10.dp),
                            singleLine = true
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = studentCountStr,
                            onValueChange = { studentCountStr = it },
                            label = { Text("N° de estudiantes") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = approxAge,
                            onValueChange = { approxAge = it },
                            label = { Text("Edad aproximada") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            singleLine = true
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = dateStr,
                        onValueChange = { dateStr = it },
                        label = { Text("Fecha de inicio") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = {
                            val count = studentCountStr.toIntOrNull() ?: 25
                            viewModel.updateGroupInfo(teacherName, grade, subject, count, approxAge, dateStr)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("guardar_grupo_btn"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = OceanTeal)
                    ) {
                        Icon(imageVector = Icons.Default.Save, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("GUARDAR DATOS DEL GRUPO", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Sección: Personas
        item {
            SectionHeader(
                title = "¿A QUIÉN ESTÁ DIRIGIDA?",
                subtitle = "Caracterización de las necesidades por rol pedagógico"
            )
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                // Docente
                PersonaCard(
                    role = "DOCENTE",
                    icon = "👩‍🏫",
                    need = "Contar con estrategias sencillas para recuperar la atención y participación de los estudiantes durante la clase.",
                    accentColor = OceanTeal
                )
                // Estudiante
                PersonaCard(
                    role = "ESTUDIANTE",
                    icon = "🎒",
                    need = "Participar en experiencias dinámicas que rompan la rutina académica, disminuyan el cansancio y motiven el aprendizaje.",
                    accentColor = EnergyYellow
                )
                // Institución
                PersonaCard(
                    role = "INSTITUCIÓN",
                    icon = "🏫",
                    need = "Contar con estrategias pedagógicas replicables, evaluables y transferibles basadas en evidencia observable.",
                    accentColor = DeepNavy
                )
            }
        }

        // Módulo Pretest: Diagnóstico Inicial
        item {
            SectionHeader(
                title = "DIAGNÓSTICO INICIAL (PRETEST)",
                subtitle = "Escala Likert de 10 afirmaciones sobre atención, motivación y cansancio"
            )
        }

        // Student Code & Recode options
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Código anónimo de estudiante",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "No se usan nombres personales",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        OutlinedTextField(
                            value = pretestStudentId,
                            onValueChange = { viewModel.setPretestStudentId(it) },
                            modifier = Modifier
                                .width(120.dp)
                                .testTag("student_id_input"),
                            shape = RoundedCornerShape(8.dp),
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Recodificar preguntas negativas (5 y 6)",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "Alinea 'Nunca' con mayor bienestar cognitivo (4 pts)",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Switch(
                            checked = recodeNegative,
                            onCheckedChange = { viewModel.toggleRecodePolarity() },
                            colors = SwitchDefaults.colors(checkedThumbColor = OceanTeal)
                        )
                    }

                    if (pretestHistory.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "📝 Registros guardados en base de datos: ${pretestHistory.size} cuestionarios",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = OceanTeal
                        )
                    }
                }
            }
        }

        // 10 Likert Questions
        items(PedagogicalData.QUESTIONS.size) { index ->
            val question = PedagogicalData.QUESTIONS[index]
            val currentScore = pretestAnswers[question.number] ?: 3

            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = OceanTeal.copy(alpha = 0.12f)
                        ) {
                            Text(
                                text = "Afirmación #${question.number}",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = OceanTeal,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = if (question.isNegativePolarity) EnergyYellow.copy(alpha = 0.15f) else MintGreen.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = question.category,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (question.isNegativePolarity) EnergyYellow else MintGreen,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = question.text,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    LikertOptionSelector(
                        selectedScore = currentScore,
                        onScoreSelected = { score ->
                            viewModel.updatePretestAnswer(question.number, score)
                        }
                    )
                }
            }
        }

        // Live calculation summary card
        item {
            val q = pretestAnswers
            // Calculate categorized averages
            fun score(itemNum: Int, isNeg: Boolean): Float {
                val raw = q[itemNum] ?: 3
                return if (isNeg && recodeNegative) (5 - raw).toFloat() else raw.toFloat()
            }

            val atn = (score(1, false) + score(3, false) + score(6, true) + score(8, false)) / 4f
            val mot = (score(2, false) + score(7, false) + score(9, false) + score(10, false)) / 4f
            val part = score(4, false)
            val disp = score(5, true)

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = DeepNavy
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "RESUMEN VISUAL DEL PRETEST (${pretestStudentId})",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        DimensionScorePill("Atención", String.format(Locale.US, "%.1f", atn), OceanTeal)
                        DimensionScorePill("Motivación", String.format(Locale.US, "%.1f", mot), EnergyYellow)
                        DimensionScorePill("Participación", String.format(Locale.US, "%.1f", part), MintGreen)
                        DimensionScorePill("Disposición", String.format(Locale.US, "%.1f", disp), EnergyGold)
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    Button(
                        onClick = { viewModel.submitPretest() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("guardar_pretest_btn"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = OceanTeal)
                    ) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("GUARDAR PRETEST ANÓNIMO", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun PersonaCard(
    role: String,
    icon: String,
    need: String,
    accentColor: Color
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(accentColor.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = icon, fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = role,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = accentColor
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Necesidad:",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "“$need”",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Composable
private fun DimensionScorePill(
    label: String,
    score: String,
    color: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = score,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color.White.copy(alpha = 0.8f)
        )
    }
}
